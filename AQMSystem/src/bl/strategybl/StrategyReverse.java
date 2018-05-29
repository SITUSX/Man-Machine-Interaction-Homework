package bl.strategybl;

import bl.PlateIndex;
import bl.StocksInBlock;
import dataservice.StockDataService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 反向策略
 * Created by huangxiao on 2017/6/9.
 */
@Service
public class StrategyReverse extends StrategyBL {

    public StrategyReverse(StockDataService stockDataService, StocksInBlock stocksInBlock, PlateIndex plateIndex) {
        super(stockDataService, stocksInBlock, plateIndex);
    }

    @Override
    protected List<String> selectStock(Date date, int returnPeriod) {
        Map<String, Double> yields = new HashMap<>(stockRecords.size());
        for (String code : stockRecords.keySet()) {
            double yield = getYield(code, date, returnPeriod);
            if (!Double.valueOf(yield).equals(Double.NaN)) {
                yields.put(code, yield);
            }
        }
        if (yields.size() == 0) {
            return new ArrayList<>();
        }
        // 按收益率从低到高排序，并选出前20%
        List<String> result = yields.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return result.subList(0, result.size() * 4 / 5);
    }
}
