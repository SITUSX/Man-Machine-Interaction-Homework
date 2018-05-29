package bl.strategybl;

import bl.PlateIndex;
import bl.StocksInBlock;
import dataservice.StockDataService;
import model.KData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by huangxiao on 2017/3/23.
 * Default
 * 均值回归
 */
@Service("strategyBLService")
public class StrategyMeanReversion extends StrategyBL {

    @Autowired
    public StrategyMeanReversion(StockDataService stockDataService, StocksInBlock stocksInBlock, PlateIndex plateIndex) {
        super(stockDataService, stocksInBlock, plateIndex);
    }

    /**
     * 计算形成期股票股价与均线价格偏离度
     * @param code         ：股票代码
     * @param date         ：日期
     * @param returnPeriod ：形成期
     * @return 偏离度（若在形成期内有股票停牌情况或股票还未上市则返回NaN）
     */
    private double getDeviation(String code, Date date, int returnPeriod) {
        List<KData> beans = stockRecords.get(code);
        int index = binarySearch(beans, date);
        // 股票还未上市或上市时间还未达到形成期天数
        if (index == -1 || index < returnPeriod - 1) {
            return Double.NaN;
        }

        double p = beans.get(index).getAdjClose();

        // 均线价格计算
        double sumOfAdjClose = 0;
        for (int i = 0; i < returnPeriod; i++) {
            KData bean = beans.get(index - i);
            // 股票在形成期有停牌情况
            if (bean.getVolume() == 0) {
                return Double.NaN;
            }
            sumOfAdjClose += bean.getAdjClose();
        }
        double ma = sumOfAdjClose / returnPeriod;

        // 偏移量计算
        return (ma - p) / ma;
    }

    @Override
    protected List<String> selectStock(Date date, int returnPeriod) {
        Map<String, Double> deviations = new HashMap<>(stockRecords.size());
        for (String code : stockRecords.keySet()) {
            double deviation = getDeviation(code, date, returnPeriod);
            if (!Double.valueOf(deviation).equals(Double.NaN)) {
                deviations.put(code, deviation);
            }
        }

        if (deviations.size() == 0) {
            return new ArrayList<>();
        }

        // 按偏离度从高到低排序，并取前20%
        List<String> result = deviations
                .entrySet()
                .stream()
                .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return result.subList(0, result.size() * 4 / 5);
    }

}
