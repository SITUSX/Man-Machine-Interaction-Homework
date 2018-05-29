package factory;

import bl.strategybl.StrategyMeanReversion;
import bl.strategybl.StrategyMinVariance;
import bl.strategybl.StrategyMomentum;
import bl.strategybl.StrategyReverse;
import blservice.BlockBLService;
import blservice.StockBLService;
import blservice.StrategyBLService;
import blservice.UserBLService;
import config.StrategyType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by heleninsa on 2017/5/13.
 */

public class BLFactory extends SpringFactory {

    private BLFactory() {
    }


    private final static Map<Class, String> BL_CLASS;

    static {
        BL_CLASS = new HashMap<>();
        BL_CLASS.put(BlockBLService.class, "");
        BL_CLASS.put(StockBLService.class, "");
        BL_CLASS.put(StrategyBLService.class, "");
        BL_CLASS.put(UserBLService.class, "");
    }


    /**
     * @param bl_service
     * @param <T>
     * @return Return BlService .
     * <p>
     * {@link BLFactory#getStrategy}
     * Notice : Use getStrategy to get StrategyBl instead of this method
     */
    public static <T> T getBlService(Class<T> bl_service) {
        if (BL_CLASS.containsKey(bl_service)) {
            if (bl_service.equals(StrategyBLService.class)) {
                getStrategy(StrategyType.MOMENTUM);
            }
            return getBeanByClass(bl_service);
        }
        return null;
    }

    public static StrategyBLService getStrategy(StrategyType strategyType) {
        switch (strategyType) {
            case MOMENTUM:
                return getBeanByClass(StrategyMomentum.class);
            case MEAN_REVERSION:
                return getBeanByClass(StrategyMeanReversion.class);
            case REVERSE:
                return getBeanByClass(StrategyReverse.class);
            case MIN_VARIANCE:
                return getBeanByClass(StrategyMinVariance.class);
            default:
                break;
        }
        return null;
    }

}
