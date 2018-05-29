package vo;

import config.BlockType;
import model.Block;

/**
 * Created by huangxiao on 2017/6/5.
 */
public class BlockVO {

    public BlockType blockType;

    public String blockName;

    public BlockVO(BlockType blockType, String blockName) {
        this.blockType = blockType;
        this.blockName = blockName;
    }

    public BlockVO(Block block) {
        String type = block.getBlockType();
        if (type.equals("main")) {
            this.blockType = BlockType.BLOCK_MAIN;
        } else if (type.equals("small")) {
            this.blockType = BlockType.BLOCK_SMALL;
        } else if (type.equals("gem")) {
            this.blockType = BlockType.BLOCK_GEM;
        } else if (type.equals("area")) {
            this.blockType = BlockType.BLOCK_LOCATION;
        } else if (type.equals("industry")) {
            this.blockType = BlockType.BLOCK_INDUSTRY;
        } else {
            this.blockType = BlockType.BLOCK_CONCEPT;
        }

        blockName = block.getBlockName();
    }
}
