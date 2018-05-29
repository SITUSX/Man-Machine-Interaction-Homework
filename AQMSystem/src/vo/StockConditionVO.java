package vo;

import java.util.List;

/**
 * Created by huangxiao on 2017/6/14.
 */
public class StockConditionVO {

    public List<Boolean> sectionChoose;

    public int minVolume;

    public int minChangePercent;

    public StockConditionVO(List<Boolean> sectionChoose, int minVolume, int minChangePercent) {
        this.sectionChoose = sectionChoose;
        this.minVolume = minVolume;
        this.minChangePercent = minChangePercent;
    }
}
