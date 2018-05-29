package controller;

import blservice.StockBLService;
import blservice.StrategyBLService;
import config.StrategyType;
import factory.BLFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import vo.StockConditionVO;
import vo.StockVO;
import vo.TestDataParseVO;
import vo.TestResultVO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/strategy")
public class StrategyController {

    private StockBLService stockBLService = BLFactory.getBlService(StockBLService.class);
    private StrategyBLService strategyMeanReversion = BLFactory.getStrategy(StrategyType.MEAN_REVERSION);
    private StrategyBLService strategyMomentum = BLFactory.getStrategy(StrategyType.MOMENTUM);
    private StrategyBLService strategyReverse = BLFactory.getStrategy(StrategyType.MEAN_REVERSION);
    private StrategyBLService strategyMinVariance = BLFactory.getStrategy(StrategyType.MIN_VARIANCE);

    //all code
    @RequestMapping(value = "/allCodePool.form")
    @ResponseBody
    public TestResultVO AllCodePool(String type, String begin, String end, String forming_period, String holding_period) {
        TestDataParseVO vo = new TestDataParseVO(begin, end, forming_period, holding_period);

        List<StockVO> stockList = stockBLService.getStockList("");

        List<String> codePool = stockList.stream().map(s -> s.code).collect(Collectors.toList());

        TestResultVO result = null;
        if (type.equals(StrategyType.MEAN_REVERSION.toString())) {
            result = strategyMeanReversion.backTest(codePool, vo.begin, vo.end, vo.forming_period, vo.holding_period);
        } else if (type.equals(StrategyType.MOMENTUM.toString())) {
            result = strategyMomentum.backTest(codePool, vo.begin, vo.end, vo.forming_period, vo.holding_period);
        } else if (type.equals(StrategyType.REVERSE.toString())) {
            result = strategyMomentum.backTest(codePool, vo.begin, vo.end, vo.forming_period, vo.holding_period);
        } else if (type.equals(StrategyType.MIN_VARIANCE.toString())) {
            try{
                result = strategyMinVariance.backTest(codePool, vo.begin, vo.end, vo.forming_period, vo.holding_period);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }


    @RequestMapping(value = "/block.form")
    @ResponseBody
    public TestResultVO block(String type, String blockName, String begin, String end, String forming_period, String holding_period) {
        System.out.println(blockName + " " + begin + " "+ end);

        TestDataParseVO vo = new TestDataParseVO(blockName, begin, end, forming_period, holding_period);

        TestResultVO result = null;
        if (type.equals(StrategyType.MEAN_REVERSION.toString())) {
            result = strategyMeanReversion.backTest(vo.blockType, vo.begin, vo.end, vo.forming_period, vo.holding_period);
        } else if (type.equals(StrategyType.MOMENTUM.toString())) {
            result = strategyMomentum.backTest(vo.blockType, vo.begin, vo.end, vo.forming_period, vo.holding_period);
        } else if (type.equals(StrategyType.REVERSE.toString())) {
            result = strategyReverse.backTest(vo.blockType, vo.begin, vo.end, vo.forming_period, vo.holding_period);
        } else if (type.equals(StrategyType.MIN_VARIANCE.toString())) {
            result = strategyMinVariance.backTest(vo.blockType, vo.begin, vo.end, vo.forming_period, vo.holding_period);
        }
        return result;
    }

    @RequestMapping(value = "/optionalCodePool.form")
    @ResponseBody
    public TestResultVO optionalCodePool(String type, String minVolume, String minStockRange, String section1, String section2, String section3, String section4, String section5, String begin, String end, String forming_period, String holding_period) {
        TestDataParseVO vo = new TestDataParseVO(begin, end, forming_period, holding_period);
        int min_volume = Integer.valueOf(minVolume);
        int min_stock_range = Integer.valueOf(minStockRange);
        List<Boolean> sectionChoose = new ArrayList<>();
        sectionChoose.add(Boolean.valueOf(section1));
        sectionChoose.add(Boolean.valueOf(section2));
        sectionChoose.add(Boolean.valueOf(section3));
        sectionChoose.add(Boolean.valueOf(section4));
        sectionChoose.add(Boolean.valueOf(section5));
        List<String> codePool = stockBLService.getStockByCondition(new StockConditionVO(sectionChoose, min_volume, min_stock_range));

        TestResultVO result = null;
        if (type.equals(StrategyType.MEAN_REVERSION.toString())) {
            result = strategyMeanReversion.backTest(codePool, vo.begin, vo.end, vo.forming_period, vo.holding_period);
        } else if (type.equals(StrategyType.MOMENTUM.toString())) {
            result = strategyMomentum.backTest(codePool, vo.begin, vo.end, vo.forming_period, vo.holding_period);
        } else if (type.equals(StrategyType.REVERSE.toString())) {
            result = strategyReverse.backTest(codePool, vo.begin, vo.end, vo.forming_period, vo.holding_period);
        } else if (type.equals(StrategyType.MIN_VARIANCE.toString())) {
            result = strategyMinVariance.backTest(codePool, vo.begin, vo.end, vo.forming_period, vo.holding_period);
        }

        return result;
    }

}
