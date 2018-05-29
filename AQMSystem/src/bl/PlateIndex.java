package bl;

import config.IndexType;
import model.KData;

import java.util.Date;
import java.util.List;

/**
 * Created by huangxiao on 2017/4/18.
 */
public interface PlateIndex {

    List<KData> getPlateIndex(IndexType indexType, Date begin, Date end);

}
