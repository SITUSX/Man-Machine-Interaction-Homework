package config;

/**
 * Created by huangxiao on 2017/6/1.
 */
public enum IndexType {
    CSI_300("沪深300", "hs300"),

    GEI("创业板指", "cyb"),

    SME("中小板指", "zxb"),

    SSE("上证指数", "sh"),

    SZ("深圳成指", "sz"),
    ;

    private String plateIndexName;
    private String plateIndexForShort;

    IndexType(String plateIndexName, String plateIndexForShort) {
        this.plateIndexName = plateIndexName;
        this.plateIndexForShort = plateIndexForShort;
    }

    public String getPlateIndexForShort() {
        return plateIndexForShort;
    }

    @Override
    public String toString() {
        return plateIndexName;
    }
}
