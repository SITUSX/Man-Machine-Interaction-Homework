package dao;

import config.BlockType;
import config.IndexType;
import model.Block;
import model.KData;
import model.Stock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static config.Config.DATE_FORMAT;

/**
 * Created by huangxiao on 2017/5/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:factory/bean_factory.xml", "file:web/WEB-INF/applicationContext.xml"})
public class BlockDaoTest {

    @Autowired
    private BlockDao blockDao;

    @Test
    public void testGetStockList() {
        BlockType blockType = BlockType.BLOCK_GEM;
        List<Stock> stocks = blockDao.findBlockStocks(blockType);
        System.out.println(blockType + "(" + stocks.size() + "):");
        for (Stock stock : stocks) {
            System.out.println(stock.getCode());
        }
    }

    @Test
    public void testFindBlocks() {
        List<Block> result = blockDao.findBlocksByType(BlockType.BLOCK_LOCATION);
        for (Block block : result) {
            System.out.println(block.getBlockName());
        }
    }

    @Test
    public void testCountBlockStocks() {
        System.out.println(blockDao.countBlockStocks(BlockType.BLOCK_LOCATION, "江苏"));
    }

    @Test
    public void testFindBlockByStock() {
        List<Block> blocks = blockDao.findBlocksByStock("000004");
        for (Block block : blocks) {
            System.out.println(block.getBlockType() + "\t" + block.getBlockName());
        }
    }

    @Test
    public void testGetPlateIndex() throws ParseException {
        Date begin = DATE_FORMAT.parse("2017-04-01");
        Date end = DATE_FORMAT.parse("2017-05-10");
        List<KData> result = blockDao.getPlateIndex(IndexType.CSI_300, begin, end);
        for (KData kData : result) {
            System.out.println(kData.getCode() + '\t'
                    + kData.getDate() + '\t'
                    + kData.getOpen() + '\t'
                    + kData.getHigh() + '\t'
                    + kData.getLow() + '\t'
                    + kData.getClose() + '\t'
                    + kData.getVolume() + '\t'
                    + kData.getAdjClose());
        }
    }

}
