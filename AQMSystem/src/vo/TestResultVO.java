package vo;

import java.util.Date;
import java.util.Map;

/**
 * Created by huangxiao on 2017/3/23.
 */
public class TestResultVO {

    /**
     * 选择股票数目
     */
    public int stockNumber;
    /**
     * 年化收益率
     */
    public double annualizedReturn;
    /**
     * 基准年化收益率
     */
    public double benchmarkAnnualizedReturn;
    /**
     * 超额收益率
     */
    public double excessReturn;
    /**
     * 策略胜率
     */
    public double winRate;
    /**
     * 最大回撤
     */
    public double maxDrawdown;
    /**
     * 阿尔法比率
     */
    public double alpha;
    /**
     * 贝塔系数
     */
    public double beta;
    /**
     * 夏普比率
     */
    public double sharpe;
    /**
     * 策略收益率
     */
    public Map<Date, Double> strategyReturn;
    /**
     * 基准收益率
     */
    public Map<Date, Double> benchmarkReturn;
    /**
     * 正收益天数分布
     */
    public Map<Double, Integer> positiveReturnDistribute;
    /**
     * 负收益天数分布
     */
    public Map<Double, Integer> negativeReturnDistrubute;

    /**
     * Default Constructor
     */
    public TestResultVO() {
    }

    public TestResultVO(int stockNumber, double annualizedReturn, double benchmarkAnnualizedReturn, double excessReturn, double winRate, double maxDrawdown, double alpha, double beta, double sharpe, Map<Date, Double> strategyReturn, Map<Date, Double> benchmarkReturn, Map<Double, Integer> positiveReturnDistribute, Map<Double, Integer> negativeReturnDistrubute) {
        this.stockNumber = stockNumber;
        this.annualizedReturn = annualizedReturn;
        this.benchmarkAnnualizedReturn = benchmarkAnnualizedReturn;
        this.excessReturn = excessReturn;
        this.winRate = winRate;
        this.maxDrawdown = maxDrawdown;
        this.alpha = alpha;
        this.beta = beta;
        this.sharpe = sharpe;
        this.strategyReturn = strategyReturn;
        this.benchmarkReturn = benchmarkReturn;
        this.positiveReturnDistribute = positiveReturnDistribute;
        this.negativeReturnDistrubute = negativeReturnDistrubute;
    }
}
