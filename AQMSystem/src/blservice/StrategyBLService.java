package blservice;

import config.BlockType;
import vo.TestResultVO;

import java.util.Date;
import java.util.List;

/**
 * Created by huangxiao on 2017/3/23.
 * 策略模式
 * 量化交易策略
 */
public interface StrategyBLService {

    /**
     * 回测（自选股）
     * @param codePool      ：股票池
     * @param begin         ：开始日期
     * @param end           ：结束日期
     * @param returnPeriod  ：形成期
     * @param holdingPeriod ：持有期
     * @return
     */
    public TestResultVO backTest(List<String> codePool, Date begin, Date end, int returnPeriod, int holdingPeriod);

    /**
     * 回测（板块股票）
     * @param blockType     ：板块（主板、中小板、创业板之一）
     * @param begin         ：开始日期
     * @param end           ：结束日期
     * @param returnPeriod  ：形成期
     * @param holdingPeriod ：持有期
     * @return
     */
    public TestResultVO backTest(BlockType blockType, Date begin, Date end, int returnPeriod, int holdingPeriod);

}
