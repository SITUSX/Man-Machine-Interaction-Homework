package vo;

import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heleninsa on 2017/3/5.
 */
public class MarketInfoVO {
    @Nullable
    public List<String> block;
    /**
     * 当日交易总量
     */
    public long totalVolume;
    /**
     * 涨停股票数
     */
    public int totalSurgedLimit;
    /**
     * 跌停股票数
     */
    public int totalDeclineLimit;
    /**
     * 涨幅超过5%的股票数
     */
    public int fivePercentOffsetIncrease;
    /**
     * 跌幅超过5%的股票数
     */
    public int fivePercentOffsetDecrease;
    /**
     * 开盘‐收盘大于5%*上一个交易日收盘价的股票个数
     */
    public int fivePercentChangeIncrease;
    /**
     * 开盘‐收盘小于5%*上一个交易日收盘价的股票个数
     */
    public int fivePercentChangeDecrease;
    /**
     * 每日股票计数上限
     */
    public int maxLimit;
    /**
     * 每日股票计数下限
     */
    public int minLimit;

//    /**
//     * default constructor
//     */
//    public MarketInfoVO() {
//    }

    public MarketInfoVO(long totalVolume, int totalSurgedLimit, int totalDeclineLimit, int fivePercentOffsetIncrease, int fivePercentOffsetDecrease, int fivePercentChangeIncrease, int fivePercentChangeDecrease, int maxLimit, int minLimit) {
        this(new ArrayList<>(), totalVolume, totalSurgedLimit, totalDeclineLimit, fivePercentOffsetIncrease, fivePercentOffsetDecrease, fivePercentChangeIncrease, fivePercentChangeDecrease, maxLimit, minLimit);
    }

    public MarketInfoVO(List<String> block, long totalVolume, int totalSurgedLimit, int totalDeclineLimit, int fivePercentOffsetIncrease, int fivePercentOffsetDecrease, int fivePercentChangeIncrease, int fivePercentChangeDecrease, int maxLimit, int minLimit) {
        this.block = block;
        this.totalVolume = totalVolume;
        this.totalSurgedLimit = totalSurgedLimit;
        this.totalDeclineLimit = totalDeclineLimit;
        this.fivePercentOffsetIncrease = fivePercentOffsetIncrease;
        this.fivePercentOffsetDecrease = fivePercentOffsetDecrease;
        this.fivePercentChangeIncrease = fivePercentChangeIncrease;
        this.fivePercentChangeDecrease = fivePercentChangeDecrease;
        this.maxLimit = maxLimit;
        this.minLimit = minLimit;
    }
}
