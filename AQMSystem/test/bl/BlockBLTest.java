package bl;

import factory.BLFactory;
import blservice.BlockBLService;
import config.BlockType;
import org.junit.Test;
import vo.BlockInfoVO;
import vo.BlockNumVO;

import java.util.List;

/**
 * Created by huangxiao on 2017/6/3.
 */
public class BlockBLTest {

    private BlockBLService blockBLService = BLFactory.getBlService(BlockBLService.class);

    @Test
    public void testBlockTypeList() {
        List<BlockInfoVO> list = blockBLService.getBlockTypeList(BlockType.BLOCK_INDUSTRY);
        System.out.println(list.get(0).blockType.getBlockForShort());
        System.out.println(list.get(0).sub_blocks.size());
    }

    @Test
    public void testGetBlockNum() {
        BlockNumVO vo = blockBLService.getBlockNum();
        vo.location.forEach((k, v) -> System.out.println(k + '\t' + v));
        System.out.println();
        vo.industry.forEach((k, v) -> System.out.println(k + '\t' + v));
    }

}
