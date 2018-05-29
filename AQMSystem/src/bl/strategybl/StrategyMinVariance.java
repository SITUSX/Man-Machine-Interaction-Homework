package bl.strategybl;

import bl.PlateIndex;
import bl.StocksInBlock;
import dataservice.StockDataService;
import model.KData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.Statistics;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by huangxiao on 2017/6/10.
 */
@Service
public class StrategyMinVariance extends StrategyBL {

    @Autowired
    public StrategyMinVariance(StockDataService stockDataService, StocksInBlock stocksInBlock, PlateIndex plateIndex) {
        super(stockDataService, stocksInBlock, plateIndex);
    }

    private double getVariance(String code, Date date, int returnPeriod) {
        List<KData> beans = stockRecords.get(code);
        int index = binarySearch(beans, date);
        // 股票还未上市或上市时间还未达到形成期天数
        if (index == -1 || index < returnPeriod - 1) {
            return Double.NaN;
        }

        // 方差计算
        return Statistics.variance(
                beans.subList(index - returnPeriod + 1, index)
                .stream()
                .map(KData::getAdjClose)
                .collect(Collectors.toList())
        );
    }

    @Override
    protected List<String> selectStock(Date date, int returnPeriod) {
        Map<String, Double> variances = new HashMap<>(stockRecords.size());
        for (String code : stockRecords.keySet()) {
            double variance = getVariance(code, date, returnPeriod);
            if (!Double.valueOf(variance).equals(Double.NaN)) {
                variances.put(code, variance);
            }
        }

        if (variances.size() == 0) {
            return new ArrayList<>();
        }

        // 按方差从小到大排序，并取前20%
        List<String> result = variances
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return result.subList(0, result.size() * 4 / 5);
    }

}
