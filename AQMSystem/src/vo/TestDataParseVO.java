package vo;

import com.sun.istack.internal.Nullable;
import config.BlockType;

import java.text.ParseException;
import java.util.Date;

import static config.Config.DATE_FORMAT;

/**
 * Created by gkj on 2017/6/1.
 */
public class TestDataParseVO {
    @Nullable
    /**
     *板块转换
     */
    public BlockType blockType;
    /**
     *回测开始日期
     */
    public Date begin;
    /**
     *回测结束日期
     */
    public Date end;
    /**
     *回测形成期
     */
    public int forming_period;
    /**
     *回测持有期
     */
    public int holding_period;

    public TestDataParseVO(String begin, String end, String forming_period, String holding_period){
        try {
            this.begin = DATE_FORMAT.parse(begin);
            this.end = DATE_FORMAT.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.forming_period = Integer.valueOf(forming_period);
        this.holding_period = Integer.valueOf(holding_period);
    }

    public TestDataParseVO(String blockName, String begin, String end, String forming_period, String holding_period){
        this.blockType = BlockType.valueOf(blockName);
        try {
            this.begin = DATE_FORMAT.parse(begin);
            this.end = DATE_FORMAT.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.forming_period = Integer.valueOf(forming_period);
        this.holding_period = Integer.valueOf(holding_period);
    }

}
