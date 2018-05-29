package bl;

import config.BlockType;
import vo.BlockVO;
import vo.StockVO;

import java.util.List;

/**
 * Created by huangxiao on 2017/4/7.
 */
public interface StocksInBlock {

    /**
     * 获取板块内股票列表
     * @param blockType
     * @param blocks
     * @return
     */
    List<StockVO> getBlockStocks(BlockType blockType, String... blocks);

    /**
     * 获取股票所属所有板块
     * @param code
     * @return
     */
    List<BlockVO> getStockBlocks(String code);

}
