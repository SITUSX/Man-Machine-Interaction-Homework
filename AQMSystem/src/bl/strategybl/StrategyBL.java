package bl.strategybl;

import bl.PlateIndex;
import bl.StocksInBlock;
import blservice.StrategyBLService;
import config.BlockType;
import config.IndexType;
import dataservice.StockDataService;
import model.KData;
import util.Statistics;
import util.Time;
import vo.StockVO;
import vo.TestResultVO;

import java.util.*;

/**
 * Created by huangxiao on 2017/3/29.
 */

public abstract class StrategyBL implements StrategyBLService {

    private StockDataService stockDataService;

    private StocksInBlock stocksInBlock;

    private PlateIndex plateIndex;

    public StrategyBL(StockDataService stockDataService, StocksInBlock stocksInBlock, PlateIndex plateIndex) {
        this.stockDataService = stockDataService;
        this.stocksInBlock = stocksInBlock;
        this.plateIndex = plateIndex;
    }

    /**
     * 股票池中股票记录
     */
    protected Map<String, List<KData>> stockRecords;

    /**
     * 股票记录初始化
     *
     * @param codePool  ：股票池
     * @param beginDate ：开始日期
     */
    private void initStockRecords(List<String> codePool, Date beginDate, Date endDate, int returnPeriod) {
        // 初始化：获取股票池中所有股票记录
        stockRecords = new HashMap<>(codePool.size());

        Date begin = Time.moveDate(beginDate, -4 * returnPeriod);

        for (String code : codePool) {
            List<KData> beans = stockDataService.getStockRecords(code, begin, endDate);
            // 筛选出已上市股票
            if (beans.size() > 0 && beans.get(0).getDate().compareTo(beginDate) < 0) {
                stockRecords.put(code, beans);
            }
        }
    }

    /**
     * 二分法获取records中date或date前第一个日期元素下标
     *
     * @param list
     * @param date
     * @return 若记录列表中date存在，则返回date所在AQMBean下标
     * 若记录列表中date不存在，则返回date紧接着前一日期所在AQMBean下标
     * 若date早于记录中最早日期，则返回-1
     */
    protected int binarySearch(List<KData> list, Date date) {
        // date早于记录中最早日期
        if (list.size() == 0 || list.get(0).getDate().compareTo(date) > 0) {
            return -1;
        }
        int left = 0, right = list.size() - 1;
        int mid = right >> 1;
        while (left + 1 < right) {
            Date search = list.get(mid).getDate();
            if (date.compareTo(search) == 0) {
                return mid;
            } else if (date.compareTo(search) < 0) {
                right = mid - 1;
            } else {
                left = mid;
            }
            mid = left + ((right - left) >> 1);
        }
        return list.get(right).getDate().compareTo(date) == 0 ? right : left;
    }

    /**
     * 计算形成期收益率
     *
     * @param code
     * @param date
     * @param returnPeriod
     * @return 收益率（若在形成期内有股票停牌情况或股票还未上市则返回NaN）
     */
    protected double getYield(String code, Date date, int returnPeriod) {
        List<KData> beans = stockRecords.get(code);
        int index = binarySearch(beans, date);
        // 股票还未上市或上市时间还未达到形成期天数
        if (index == -1 || index < returnPeriod - 1) {
            return Double.NaN;
        }

        // 删除股票停盘
        for (int i = 0; i < returnPeriod; i++) {
            if (beans.get(index - i).getVolume() == 0) {
                return Double.NaN;
            }
        }

        //计算收益率
        double earning = beans.get(index).getAdjClose();
        double capital = beans.get(index - returnPeriod + 1).getAdjClose();

        return (earning / capital) / returnPeriod;
    }

    /**
     * 计算某天部分股票的复权收盘总价
     *
     * @param stocks ： 所选股票
     * @param date   ： 日期
     * @return 复权收盘总价
     */
    private double getStocksPriceByDate(List<String> stocks, Date date) {
        double result = 0;
        for (String code : stocks) {
            List<KData> beans = stockRecords.get(code);
            if (beans != null) {
                int index = binarySearch(beans, date);
                if (index != -1) {
                    KData bean = beans.get(index);
                    if (bean.getDate().compareTo(date) == 0) {
                        result += beans.get(index).getAdjClose();
                    }
                }
            }
        }
        return result;
    }

    /**
     * 形成期选择持有股票
     *
     * @param date         ：日期
     * @param returnPeriod ：形成期
     * @return 前20%股票代码列表
     */
    protected abstract List<String> selectStock(Date date, int returnPeriod);

    /**
     * 板块类型转指数类型
     * @param blockType
     * @return IndexType
     */
    private IndexType blockType2IndexType(BlockType blockType) {
        switch (blockType) {
            case BLOCK_MAIN:
                return IndexType.CSI_300;
            case BLOCK_GEM:
                return IndexType.GEI;
            case BLOCK_SMALL:
                return IndexType.SME;
            default:
                return IndexType.CSI_300;
        }
    }

    @Override
    public TestResultVO backTest(BlockType blockType, Date begin, Date end, int returnPeriod, int holdingPeriod) {
        List<KData> plateIndexList = plateIndex.getPlateIndex(blockType2IndexType(blockType), begin, end);

        List<StockVO> blockStocks = stocksInBlock.getBlockStocks(blockType);
        List<String> stockCodes = new ArrayList<>();
        for (StockVO stockVO : blockStocks) {
            stockCodes.add(stockVO.code);
        }
        return backTest(stockCodes, begin, end, returnPeriod, holdingPeriod, plateIndexList);
    }

    @Override
    public TestResultVO backTest(List<String> codePool, Date begin, Date end, int returnPeriod, int holdingPeriod) {
        return backTest(codePool, begin, end, returnPeriod, holdingPeriod, null);
    }

    private TestResultVO backTest(List<String> codePool, Date begin, Date end, int returnPeriod, int holdingPeriod, List<KData> plateIndexList) {
        boolean isBlock = plateIndexList != null;

        // 初始化
        initStockRecords(codePool, begin, end, returnPeriod);
        List<String> realCodePool = new ArrayList<>(stockRecords.keySet());

        Map<Date, Double> strategyReturn = new TreeMap<>();
        List<Double> strategyReturnList = new ArrayList<>();
        Map<Date, Double> benchmarkReturn = new TreeMap<>();
        List<Double> benchmarkReturnList = new ArrayList<>();

        double maxDrawdown = -Double.MAX_VALUE;
        double excessReturn = 0;
        double winRate = 0;

        // 所选日期区间内第一个交易日日期
        Date realBegin = null;
        // 初始股票池总价/板块指数（用于计算基准收益率）
        double initBenchmarkPrice = 0;

        if (isBlock) {
            initBenchmarkPrice = plateIndexList.get(0).getAdjClose();
            realBegin = plateIndexList.get(0).getDate();
        } else {
            for (Date date = begin; date.compareTo(end) <= 0; date = Time.moveDate(date, 1)) {
                initBenchmarkPrice = getStocksPriceByDate(realCodePool, date);
                if (initBenchmarkPrice > 0) {
                    realBegin = date;
                    break;
                }
            }
        }

        // 选择初始仓位
        List<String> selected = selectStock(realBegin, returnPeriod);
        // 本金
        double principal = getStocksPriceByDate(selected, realBegin);
        // 收益
        double income = principal;
        // 策略所选股上一天复权总收盘价（初始化为第一天所选股票复权收盘价）
        double lastClose = income;

        // 股票持有天数计数
        int count = 0;
        for (Date date = realBegin; date.compareTo(end) <= 0; date = Time.moveDate(date, 1)) {
            if (Time.isWeekend(date)) {
                continue;
            }
            double presentStockPoolPrice = getStocksPriceByDate(realCodePool, date);
            if (presentStockPoolPrice == 0) {
                continue;
            }

            // 策略收益率计算并更新上一天所选股票复权收盘价（收益减去上一天所选股票复权收盘价加上当天股票复权收盘价）
            income -= lastClose;
            lastClose = getStocksPriceByDate(selected, date);
            income += lastClose;
            strategyReturn.put(date, (income - principal) / principal);
            strategyReturnList.add(strategyReturn.get(date));

            // 基准收益率计算
            if (!isBlock) {
                benchmarkReturn.put(date, (presentStockPoolPrice - initBenchmarkPrice) / initBenchmarkPrice);
            } else {
                int index = binarySearch(plateIndexList, date);
                if (index != -1) {
                    double presentIndex = plateIndexList.get(index).getAdjClose();
                    benchmarkReturn.put(date, presentIndex - initBenchmarkPrice / initBenchmarkPrice);
                } else {
                    benchmarkReturn.put(date, count == 0 ? 0.0 : benchmarkReturnList.get(count - 1));
                }
            }
            benchmarkReturnList.add(benchmarkReturn.get(date));

            excessReturn = strategyReturnList.get(count) - benchmarkReturnList.get(count);
            if (excessReturn > 0) {
                winRate++;
            }

            count++;
            // 持有期结束则调仓
            if (count % holdingPeriod == 0) {
                // 重新选择股票，加入本金和收益
                List<String> reSelect = selectStock(date, returnPeriod);
                if (reSelect != null && reSelect.size() != 0) {
                    selected = selectStock(date, returnPeriod);
                    lastClose = getStocksPriceByDate(selected, date);
                    income += lastClose;
                    principal += lastClose;
                }
            }

        }
        if (isBlock) {
            benchmarkReturn.clear();
            for (KData kData : plateIndexList) {
                benchmarkReturn.put(kData.getDate(), (kData.getAdjClose() - initBenchmarkPrice) / initBenchmarkPrice);
            }
        }
        // 最大回撤计算
        for (int i = 0; i < strategyReturnList.size() - 1; i++) {
            double eachMaxDrawdown = strategyReturnList.get(i) - Collections.min(strategyReturnList.subList(i + 1, strategyReturnList.size()));
            maxDrawdown = Double.max(eachMaxDrawdown, maxDrawdown);
        }
        // 策略胜率计算
        winRate /= benchmarkReturnList.size();
        // 年化收益率计算
        double annualizedReturn = (strategyReturnList.get(strategyReturnList.size() - 1)) / strategyReturnList.size() * 365;
        // 基准年化收益率计算
        double benchmarkAnnualizedReturn = (benchmarkReturnList.get(benchmarkReturnList.size() - 1) / benchmarkReturnList.size()) * 365;
        // 其他变量计算
        double beta = Statistics.betaCoefficient(strategyReturnList, benchmarkReturnList);
        double alpha = Statistics.alphaCoefficient(strategyReturnList, benchmarkReturnList);
        double sharpe = Statistics.sharpeRatio(strategyReturnList);

        Map<Double, Integer> positiveReturnDistribute = getReturnDistribute(strategyReturnList, true);
        Map<Double, Integer> negativeReturnDistribute = getReturnDistribute(strategyReturnList, false);
        // Map键值数统一
        for (Double data : positiveReturnDistribute.keySet()) {
            if (!negativeReturnDistribute.containsKey(-data)) {
                negativeReturnDistribute.put(-data, 0);
            }
        }
        for (Double data : negativeReturnDistribute.keySet()) {
            if (!positiveReturnDistribute.containsKey(-data)) {
                positiveReturnDistribute.put(-data, 0);
            }
        }

        return new TestResultVO(codePool.size(), annualizedReturn, benchmarkAnnualizedReturn,
                excessReturn, winRate, maxDrawdown, alpha, beta, sharpe, strategyReturn, benchmarkReturn,
                positiveReturnDistribute, negativeReturnDistribute);
    }

    /**
     * 计算策略收益率分布
     *
     * @param strategyReturn
     * @return
     */
    private Map<Double, Integer> getReturnDistribute(List<Double> strategyReturn, boolean positive) {
        Map<Double, Integer> result = new TreeMap<>();
        for (Double returnEachDay : strategyReturn) {
            if ((returnEachDay >= 0 && positive) || (returnEachDay < 0 && !positive)) {
                // 收益率精确到小数点后两位
                double modifyReturn = (int) (returnEachDay * 100) / 100.0;
                if (result.containsKey(modifyReturn)) {
                    Integer times = result.get(modifyReturn);
                    times++;
                    result.put(modifyReturn, times);
                } else {
                    result.put(modifyReturn, 1);
                }
            }
        }
        return result;
    }

}
