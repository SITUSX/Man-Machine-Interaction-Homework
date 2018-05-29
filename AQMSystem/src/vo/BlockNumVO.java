package vo;

import java.util.Map;

/**
 * Created by gkj on 2017/6/3.
 */
public class BlockNumVO {
    public Map<String, Integer> industry;

    public Map<String, Integer> location;

    public BlockNumVO() {
    }

    public BlockNumVO(Map<String, Integer> industry, Map<String, Integer> location) {
        this.industry = industry;
        this.location = location;
    }
}
