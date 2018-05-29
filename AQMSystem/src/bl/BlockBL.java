package bl;

import blservice.BlockBLService;
import config.BlockType;
import config.IndexType;
import dataservice.BlockDataService;
import model.Block;
import model.KData;
import model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vo.BlockInfoVO;
import vo.BlockNumVO;
import vo.BlockVO;
import vo.StockVO;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by huangxiao on 2017/3/27.
 */
@Service
public class BlockBL implements BlockBLService, StocksInBlock, PlateIndex {

    private final BlockDataService blockDataService;

    @Autowired
    public BlockBL(BlockDataService blockDataService) {
        this.blockDataService = blockDataService;
    }

    @Override
    public List<StockVO> getBlockStocks(BlockType blockType, String... blocks) {
        List<Stock> stocks = blockDataService.findBlockStocks(blockType, blocks);
        // Entity 转 VO
        return stocks.stream().map(StockVO::new).collect(Collectors.toList());
    }

    @Override
    public List<BlockInfoVO> getBlockTypeList(BlockType... blockTypes) {
        List<BlockInfoVO> result = new ArrayList<>();

        for (BlockType blockType : blockTypes) {
            List<Block> blocks = blockDataService.findBlocksByType(blockType);

            List<String> subBlocks = blocks.stream().map(Block::getBlockName).collect(Collectors.toList());
            result.add(new BlockInfoVO(blockType, subBlocks));
        }
        return result;
    }

    @Override
    public BlockInfoVO getSpecificBlock(BlockType blockType) {
        return getBlockTypeList(blockType).get(0);
    }


    @Override
    public List<KData> getPlateIndex(IndexType indexType, Date begin, Date end) {
        return blockDataService.getPlateIndex(indexType, begin, end);
    }

    @Override
    public List<BlockVO> getStockBlocks(String code) {
        List<Block> blocks = blockDataService.findBlocksByStock(code);
        // Entity 转 VO
        return blocks.stream().map(BlockVO::new).collect(Collectors.toList());
    }

    /**
     * 获取某板块内股票数量分布
     * @param blockType
     * @return Map
     */
    private Map<String, Integer> getBlockStockDistribute(BlockType blockType, int displayNum) {
        BlockInfoVO blockInfo = getSpecificBlock(blockType);

        Map<String, Integer> distribute = new HashMap<>();
        blockInfo.sub_blocks.forEach(s -> distribute.put(s, blockDataService.countBlockStocks(blockType, s)));

        // 按各板块股票数逆序排序
        List<Map.Entry<String, Integer>> entryList = distribute.entrySet()
                .stream()
                .sorted((o1, o2) -> (o2.getValue() - o1.getValue()))
                .collect(Collectors.toList());

        distribute.clear();

        int anotherCount= 0;
        for (int i = 0; i < entryList.size(); i++) {
            Map.Entry<String, Integer> entry = entryList.get(i);
            if (i < displayNum) {
                distribute.put(entry.getKey(), entry.getValue());
            } else {
                anotherCount += entry.getValue();
            }
        }
        distribute.put("其他", anotherCount);

        return distribute;
    }

    @Override
    public BlockNumVO getBlockNum() {
        Map<String, Integer> industryDistribute = getBlockStockDistribute(BlockType.BLOCK_INDUSTRY, 10);
        Map<String, Integer> locationDistribute = getBlockStockDistribute(BlockType.BLOCK_LOCATION, 10);
        return new BlockNumVO(industryDistribute, locationDistribute);
    }

}
