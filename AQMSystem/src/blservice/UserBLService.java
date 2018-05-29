package blservice;

import config.ResultMessage;
import vo.StockNewestInfoVO;
import vo.StockVO;

import java.util.List;

/**
 * Created by huangxiao on 2017/6/1.
 */
public interface UserBLService {

    /**
     * 登陆
     * @param username
     * @param password
     * @return ResultMessage
     */
    ResultMessage login(String username, String password);

    /**
     * 注册
     * @param username
     * @param password
     * @return ResultMessage
     */
    ResultMessage register(String username, String password);

    /**
     * 获取个人收藏股票
     * @param username
     * @return
     */
    List<StockVO> getCollectStock(String username);

    /**
     * 判断股票是否被收藏
     * @param username
     * @param code
     * @return
     */
    boolean isCollected(String username, String code);

    /**
     * 添加收藏股票
     * @param username
     * @param code
     */
    void addCollectStock(String username, String code);

    /**
     * 删除收藏股票
     * @param username
     * @param code
     */
    void deleteCollectStock(String username, String code);

    /**
     * 猜你喜欢
     * @param username
     * @return
     */
    List<StockNewestInfoVO> predictFavouriteStocks(String username);

}
