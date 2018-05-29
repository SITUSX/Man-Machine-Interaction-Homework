package config;

/**
 * 股票板块类型
 */
public enum BlockType {
    BLOCK_MAIN("主板", "main"),

    BLOCK_SMALL("中小板", "small"),

    BLOCK_GEM("创业板", "gem"),
    /*行业板块*/
    BLOCK_INDUSTRY("行业板块", "industry"),
    /*地域板块*/
    BLOCK_LOCATION("地域板块", "area"),
    /*概念板块*/
    BLOCK_CONCEPT("概念板块", "concept");

    private String blockName;
    private String blockForShort;

    BlockType(String blockName, String blockForShort) {
        this.blockName = blockName;
        this.blockForShort = blockForShort;
    }

    public String getBlockForShort() {
        return blockForShort;
    }

    @Override
    public String toString() {
        return blockName;
    }
}
