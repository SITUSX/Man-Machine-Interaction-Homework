package controller;

import factory.BLFactory;
import blservice.BlockBLService;
import config.BlockType;
import config.IndexType;
import model.KData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import vo.BlockInfoVO;
import vo.BlockNumVO;
import vo.StockVO;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static config.Config.DATE_FORMAT;

@Controller
@RequestMapping("/block")
public class BlockController {

    private BlockBLService blockBLService = BLFactory.getBlService(BlockBLService.class);

    @RequestMapping(value = "/getStockList.form")
    public @ResponseBody
    List<StockVO> getStockList(String blockName) {
        BlockType blockType = BlockType.valueOf(blockName);

        return blockBLService.getBlockStocks(blockType);
    }

    @RequestMapping(value = "/getBlockTypeList.form")
    public @ResponseBody
    List<BlockInfoVO> getBlockTypeList(BlockType... blockTypes) {

        return blockBLService.getBlockTypeList(blockTypes);
    }

    @RequestMapping(value = "/getSpecificBlock.form")
    public @ResponseBody
    BlockInfoVO getSpecificBlock(BlockType blockType) {

        return blockBLService.getSpecificBlock(blockType);
    }

    @RequestMapping(value = "/getPlateIndex.form")
    public @ResponseBody
    List<KData> getPlateIndex(String indexName, String begin, String end) {

        IndexType indexType = IndexType.valueOf(indexName);

        Date from = null;
        Date to = null;
        try {
            from = DATE_FORMAT.parse(begin);
            to = DATE_FORMAT.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return blockBLService.getPlateIndex(indexType, from, to);
    }

    @RequestMapping(value = "/getBlockNum.form")
    public @ResponseBody
    BlockNumVO getBlockNum() {
        return blockBLService.getBlockNum();
    }

}
