package util;

import java.util.Collection;
import java.util.List;

/**
 * Created by huangxiao on 2017/3/28.
 */
public class Statistics {

    /**
     * 无风险利率
     * 使用中国1年期银行定期存款回报
     */
    public static final double RISK_FREE = 0.04;

    /**
     * 平均数计算
     */
    public static double mean(Collection<? extends Number> data) {
        double result = 0;
        for (Number datum : data) {
            result += datum.doubleValue();
        }
        result /= data.size();
        return result;
    }

    /**
     * 方差计算
     */
    public static double variance(Collection<? extends Number> data) {
        if (data.size() == 0 || data.size() == 1) {
            return 0;
        }
        double squareSum = 0;
        for (Number datum : data) {
            squareSum += datum.doubleValue() * datum.doubleValue();
        }
        double mean = mean(data);
        int n = data.size();
        return (squareSum - n * mean * mean) / n;
    }

    /**
     * 样本方差计算
     */
    public static double sampleVariance(Collection<? extends Number> data) {
        if (data.size() == 0 || data.size() == 1) {
            return 0;
        }
        double squareSum = 0;
        for (Number datum : data) {
            squareSum += datum.doubleValue() * datum.doubleValue();
        }
        double mean = mean(data);
        int n = data.size();
        return (squareSum - n * mean * mean) / (n - 1);
    }

    /**
     * 标准差计算
     */
    public static double standardDeviation(Collection<? extends Number> data) {
        return Math.sqrt(variance(data));
    }

    /**
     * 协方差计算
     */
    public static double covariance(List<Double> data1, List<Double> data2) {
        int n = data1.size();
        double EX = mean(data1), EY = mean(data2), EXY = 0;
        for (int i = 0; i < n; i++) {
            double Xi = data1.get(i);
            double Yi = data2.get(i);
            EXY += Xi * Yi;
        }
        EXY /= n;
        return EXY - EX * EY;
    }

    /**
     * Beta系数
     * @param strategy 策略收益
     * @param benchmark 基准收益
     * @return
     */
    public static double betaCoefficient(List<Double> strategy, List<Double> benchmark) {
        return covariance(strategy, benchmark) / variance(benchmark);
    }

    /**
     * alpha系数
     * @return
     */
    public static double alphaCoefficient(List<Double> strategy, List<Double> benchmark) {
        double beta = betaCoefficient(strategy, benchmark);
        double Ri = (strategy.get(strategy.size() - 1) / strategy.size()) * 365;
        double ERm = mean(benchmark);
        return Ri - RISK_FREE - beta * (ERm - RISK_FREE);
    }

    /**
     * sharpe比率
     */
    public static double sharpeRatio(List<Double> data) {
        double ERp = mean(data);
        double sigmaP = standardDeviation(data);

        return (ERp - RISK_FREE) / sigmaP;
    }

    /**
     * double转百分比（String）
     * @param number
     * @param accuracy 精确位数
     * @return "xx.xx%"
     */
    public static String doubleToPercent(double number, int accuracy) {
        double copy = number;
        copy *= 100;
        if (accuracy <= 0) {
            return "" + (int)copy + "%";
        }
        copy = (int)(copy * Math.pow(10, accuracy)) / Math.pow(10, accuracy);
        return "" + copy + "%";
    }

    /**
     * double精确到小数点后确定位数
     * @param number
     * @param accuracy 精确到小数点后位数
     * @return 精确结果
     */
    public static double accurate(double number, int accuracy) {
        double copy = number;
        copy = (int)(number * Math.pow(10, accuracy)) / Math.pow(10, accuracy);
        return copy;
    }

}
