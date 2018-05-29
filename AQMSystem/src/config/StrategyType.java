package config;

/**
 * Created by huangxiao on 2017/4/12.
 */
public enum StrategyType {
    MOMENTUM ("动量策略"),
    MEAN_REVERSION ("均值回归"),
    REVERSE ("反向策略"),
    MIN_VARIANCE("最小方差");

    private String strategyName;
    StrategyType(String strategyName) {
        this.strategyName = strategyName;
    }

    @Override
    public String toString() {
        return strategyName;
    }

}
