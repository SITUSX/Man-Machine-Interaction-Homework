package dataservice;

import config.BlockType;
import config.IndexType;
import model.Block;
import model.KData;
import model.Stock;

import java.util.Date;
import java.util.List;

/**
 * Created by heleninsa on 2017/3/25.
 */
public interface BlockDataService {

    /**
     * 获取指定板块的股票列表
     *
     * @param blockType  : 板块类型
     * @param blocks     : 板块
     * @return
     */
    List<Stock> findBlockStocks(BlockType blockType, String... blocks);

    /**
     * 获取某板块类型下所有板块
     *
     * @return 板块类型及板块信息列表
     */
    List<Block> findBlocksByType(BlockType blockType);

    /**
     * 获取股票所在所有板块
     * @param code
     * @return
     */
    List<Block> findBlocksByStock(String code);

    /**
     * 计算板块内股票数
     * @param blockType
     * @param blockName
     * @return
     */
    int countBlockStocks(BlockType blockType, String blockName);

    /**
     * 获取指定板块在指定时间内的行情数据
     *
     * @param indexType    : 指数类型
     * @param begin         : 开始日期
     * @param end           : 结束日期
     * @return 指数列表
     */
    List<KData> getPlateIndex(IndexType indexType, Date begin, Date end);

}
