package blservice;

import config.BlockType;
import config.IndexType;
import model.KData;
import vo.BlockInfoVO;
import vo.BlockNumVO;
import vo.BlockVO;
import vo.StockVO;

import java.util.Date;
import java.util.List;

/**
 * Created by huangxiao on 2017/3/27.
 */
public interface BlockBLService {

    /**
     * 获取指定板块类型下某板块的股票列表
     *
     * @param blockType : 板块类型
     * @param blocks    : 板块. if length(blocks) == 0 then return all blocks
     * @return
     */
    List<StockVO> getBlockStocks(BlockType blockType, String... blocks);

    /**
     * 获取所有的板块类型及板块信息
     *
     * @return 板块类型及板块信息列表
     */
    List<BlockInfoVO> getBlockTypeList(BlockType... blockTypes);

    BlockInfoVO getSpecificBlock(BlockType blockType);

    /**
     * 获取板块指数
     * @param indexType
     * @param begin
     * @param end
     * @return
     */
    List<KData> getPlateIndex(IndexType indexType, Date begin, Date end);

    /**
     * 获取股票所属所有板块
     * @param code
     * @return
     */
    List<BlockVO> getStockBlocks(String code);

    /**
     * 获取Industry, Location小板块股票数量
     * @return
     */
    BlockNumVO getBlockNum();

}
