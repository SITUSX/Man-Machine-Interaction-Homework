package bl;

import blservice.StockBLService;
import config.BlockType;
import dataservice.StockDataService;
import model.KData;
import model.Newest;
import model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.Time;
import vo.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by heleninsa on 2017/3/5.
 */
@Service
public class StockBL implements StockBLService, StockInfo {

    private StockDataService stockDataService;

    private StocksInBlock stocksInBlock;

    private Date last_query = Time.DEFAULT_DATE;

    private List<KData[]> last_query_result;

    @Autowired
    public StockBL(StockDataService stockDataService, StocksInBlock stocksInBlock) {
        this.stockDataService = stockDataService;
        this.stocksInBlock = stocksInBlock;
        last_query_result = stockDataService.getMarketInfoByDate(last_query);
    }

    @Override
    public List<StockVO> getStockList(String keyword) {
        return stockDataService.searchStock(keyword)
                .stream()
                // Entity to VO
                .map(StockVO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getStockByCondition(StockConditionVO vo) {
        List<String> result = new ArrayList<>();
        // 获取所有股票
        List<StockVO> allStock = getStockList("");
        // 获取股票最新信息
        List<StockNewestInfoVO> newestInfoList = getNewestInfo(allStock.stream().map(s -> s.code).collect(Collectors.toList()));

        for (int i = 0; i < newestInfoList.size(); i++) {
            StockNewestInfoVO newestInfoVO = newestInfoList.get(i);
            if (newestInfoVO != null) {
                if (newestInfoVO.volume >= vo.minVolume && newestInfoVO.stock_range >= vo.minChangePercent) {
                    boolean isInSection = false;
                    for (int j = 0; j < vo.sectionChoose.size(); j++) {
                        if (vo.sectionChoose.get(j)) {
                            if (j < vo.sectionChoose.size() - 1 && newestInfoVO.trade >= j * 20 && newestInfoVO.trade < (j + 1) * 20) {
                                isInSection = true;
                                break;
                            }
                            if (j == vo.sectionChoose.size() - 1 && newestInfoVO.trade >= j * 20) {
                                isInSection = true;
                                break;
                            }
                        }
                    }
                    if (isInSection) {
                        result.add(allStock.get(i).code);
                    }
                }
            }

        }
        return result;
    }

    @Override
    public StockDetailsVO getStockDetails(String code, Date from, Date to) {
        List<Date> dateException = new ArrayList<>();
        List<KData> beans = stockDataService.getStockRecords(code, from, to);

        String stockName = stockDataService.findStock(code).getName();

        Map<Date, Double> close = new TreeMap<>();
        Map<Date, Double> logReturn = new TreeMap<>();

        // 涨跌幅计算
        double change = 0, maxPrice = 0, minPrice = 0, variance = 0;
        long volume = 0;
        // 记录数多于1条则计算
        if (beans.size() > 1) {
            change = (beans.get(beans.size() - 1).getAdjClose() - beans.get(0).getAdjClose()) / beans.get(0).getAdjClose();

            volume = beans.get(0).getVolume();
            // 对数收益率平方和
            double squareSum = 0;
            // 对数收益率之和
            double sum = 0;
            maxPrice = beans.get(0).getHigh();
            minPrice = beans.get(0).getLow();
            close.put(beans.get(0).getDate(), beans.get(0).getClose());

            for (int i = 1; i < beans.size(); i++) {
                KData bean = beans.get(i);
                volume += bean.getVolume();
                maxPrice = Math.max(bean.getHigh(), maxPrice);
                minPrice = Math.min(bean.getLow(), minPrice);
                close.put(bean.getDate(), bean.getClose());
                // 对数收益率计算
                double eachLogReturn = Math.log10(bean.getAdjClose() / beans.get(i - 1).getAdjClose());
                logReturn.put(bean.getDate(), eachLogReturn);
                sum += eachLogReturn;
                squareSum += eachLogReturn * eachLogReturn;
            }
            // 对数收益率样本方差计算
            long total = logReturn.size();
            double average = sum / total;
            variance = (squareSum - total * average * average) / (total == 1 ? 1 : total - 1);
        }

        return new StockDetailsVO(stockName, beans, minPrice, maxPrice, change, volume, close, logReturn, variance, dateException);
    }

    @Override
    public List<StockNewestInfoVO> getNewestInfo(List<String> codes) {
        List<StockNewestInfoVO> result = new ArrayList<>();
        for (String code : codes) {
            Newest newest = stockDataService.findNewest(code);
            if (newest != null) {
                result.add(new StockNewestInfoVO(newest));
                continue;
            }

            KData[] kDatas = stockDataService.getNewestInfo(code);
            if (kDatas == null || kDatas[0] == null || kDatas[1] == null) {
                result.add(null);
                continue;
            }

            double range = (kDatas[0].getAdjClose() - kDatas[1].getAdjClose()) / kDatas[1].getAdjClose();
            // 化为百分数
            range *= 100;
            // 保留三位小数
            range = (int)(range * 1000) / 1000.0;
            StockNewestInfoVO vo = new StockNewestInfoVO(
                    code,
                    stockDataService.findStock(code).getName(),
                    kDatas[0].getOpen(), kDatas[0].getHigh(),
                    kDatas[0].getLow(), kDatas[0].getClose(),
                    range,
                    kDatas[0].getVolume()
            );
            result.add(vo);

        }
        return result;

    }

    @Override
    public List<StockNewestInfoVO> getNewestInfoByOrder(Comparator<StockNewestInfoVO> comparator, int number) {
        List<StockNewestInfoVO> newestInfoList = getNewestInfo(
                        getStockList("")
                        .stream().map(s -> s.code)
                        .collect(Collectors.toList())
        );
        return newestInfoList
                .stream()
                .filter(Objects::nonNull)
                .sorted(comparator)
                .collect(Collectors.toList())
                .subList(0, number);

    }

    private MarketInfoVO calculateMarketInfo(List<KData[]> marketInfo) {
        long totalVolume = 0;
        int totalSurgedLimit = 0;
        int totalDeclineLimit = 0;
        int fivePercentOffsetIncrease = 0;
        int fivePercentOffsetDecrease = 0;
        int fivePercentChangeIncrease = 0;
        int fivePercentChangeDecrease = 0;

        for (KData[] market : marketInfo) {
            // 若查询日期即为上市日期，则跳过此股票
            if (market[1] == null) {
                continue;
            }
            //交易总量
            totalVolume += market[0].getVolume();
            //涨跌幅
            double offset = (market[0].getAdjClose() - market[1].getAdjClose()) / market[1].getAdjClose();
            double offset_abs_value = Math.abs(offset);
            if (offset_abs_value > 0.05d) {
                if (offset > 0) {
                    fivePercentOffsetIncrease++;
                } else {
                    fivePercentOffsetDecrease++;
                }

                if (offset_abs_value * 100 >= 10 - 0.01d * 100 / market[1].getAdjClose()) {
                    if (offset > 0) {
                        totalSurgedLimit++;
                    } else {
                        totalDeclineLimit++;
                    }
                }
            }
            //开收盘
            double pure_offset = (market[0].getOpen() - market[0].getClose()) / market[1].getClose();
            if (Math.abs(pure_offset) > 0.05d) {
                if (pure_offset > 0.0d) {
                    fivePercentChangeIncrease++;
                } else {
                    fivePercentChangeDecrease++;
                }
            }
        }

        int maxLimit;
        int minLimit;

        maxLimit = Math.max(Math.max(totalSurgedLimit, totalDeclineLimit),
                Math.max(Math.max(fivePercentOffsetIncrease, fivePercentOffsetDecrease),
                        Math.max(fivePercentChangeIncrease, fivePercentChangeDecrease)));
        minLimit = Math.min(Math.min(totalSurgedLimit, totalDeclineLimit),
                Math.min(Math.min(fivePercentOffsetIncrease, fivePercentOffsetDecrease),
                        Math.min(fivePercentChangeIncrease, fivePercentChangeDecrease)));

        MarketInfoVO vo = new MarketInfoVO(totalVolume, totalSurgedLimit, totalDeclineLimit,
                fivePercentOffsetIncrease, fivePercentOffsetDecrease, fivePercentChangeIncrease, fivePercentChangeDecrease,
                maxLimit, minLimit);

        return vo;
    }


    @Override
    public MarketInfoVO getMarketInfo(Date date) {
        checkDate(date);
        return calculateMarketInfo(last_query_result);
    }

    @Override
    public MarketInfoVO getMarketInfo(Date date, BlockType blockType, String... blocks) {
        List<StockVO> stocks = stocksInBlock.getBlockStocks(blockType, blocks);

        List<String> codes = new ArrayList<>();
        for (StockVO stock : stocks) {
            codes.add(stock.code);
        }

        checkDate(date);

        List<KData[]> blockMarketInfo = new ArrayList<>();
        for (KData[] kData : last_query_result) {
            if (codes.contains(kData[0].getCode())) {
                blockMarketInfo.add(kData);
            }
        }

        MarketInfoVO vo = calculateMarketInfo(blockMarketInfo);
        Collections.addAll(vo.block, blocks);
        return vo;
    }

    private void checkDate(Date query) {
        if (!query.equals(last_query)) {
            last_query = query;
            last_query_result = stockDataService.getMarketInfoByDate(query);
        }
    }

    @Override
    public Stock getSpecificStock(String code) {
        return stockDataService.findStock(code);
    }

}
