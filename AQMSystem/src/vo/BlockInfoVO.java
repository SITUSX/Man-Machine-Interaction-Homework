package vo;

import config.BlockType;

import java.util.List;

/**
 * Created by heleninsa on 2017/3/25.
 */
public class BlockInfoVO {

    /**
     * 板块类型
     */
    public BlockType blockType;

    /**
     * 板块列表
     */
    public List<String> sub_blocks;


    public BlockInfoVO(BlockType blockType, List<String> sub_blocks) {
        this.blockType = blockType;
        this.sub_blocks = sub_blocks;
    }

}
