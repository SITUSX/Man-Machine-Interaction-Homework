package bl.strategybl;

import bl.PlateIndex;
import bl.StocksInBlock;
import dataservice.StockDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by huangxiao on 2017/3/23.
 * 动量策略
 */
@Service
public class StrategyMomentum extends StrategyBL {

    @Autowired
    public StrategyMomentum(StockDataService stockDataService, StocksInBlock stocksInBlock, PlateIndex plateIndex) {
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
        // 按收益率从高到低排序
        List<String> result = yields.entrySet()
                .stream()
                .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return result.subList(0, result.size() * 4 / 5);
    }

}
