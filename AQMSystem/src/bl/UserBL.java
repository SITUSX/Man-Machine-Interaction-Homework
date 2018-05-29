package bl;

import blservice.UserBLService;
import config.BlockType;
import dataservice.UserDataService;
import model.Block;
import model.Stock;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import config.ResultMessage;
import vo.BlockVO;
import vo.StockNewestInfoVO;
import vo.StockVO;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by huangxiao on 2017/6/1.
 */
@Service
public class UserBL implements UserBLService {

    private final UserDataService userDataService;

    private final StocksInBlock stocksInBlock;

    private final StockInfo stockInfo;

    @Autowired
    public UserBL(UserDataService userDataService, StocksInBlock stocksInBlock, StockInfo stockInfo) {
        this.userDataService = userDataService;
        this.stocksInBlock = stocksInBlock;
        this.stockInfo = stockInfo;
    }

    @Override
    public ResultMessage login(String username, String password) {
        User user = userDataService.findUser(username);
        if (user == null) {
            return ResultMessage.USER_NOT_EXISTS;
        }
        if (!password.equals(user.getPassword())) {
            return ResultMessage.PASSWORD_ERROR;
        }
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage register(String username, String password) {
        // 用户名已存在
        if (userDataService.findUser(username) != null) {
            return ResultMessage.USER_EXISTS;
        }
        // 添加用户信息
        User user = new User(username, password);
        userDataService.insertUser(user);
        return ResultMessage.SUCCESS;
    }

    @Override
    public List<StockVO> getCollectStock(String username) {
        User user = userDataService.findUser(username);

        return user.getStocks()
                .stream()
                .map(StockVO::new)
                .sorted(Comparator.comparing(o1 -> o1.code))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isCollected(String username, String code) {
        User user = userDataService.findUser(username);
        for (Stock stock : user.getStocks()) {
            if (stock.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addCollectStock(String username, String code) {
        User user = userDataService.findUser(username);
        Stock stock = stockInfo.getSpecificStock(code);
        user.addStock(stock);
        userDataService.updateUser(user);
    }

    @Override
    public void deleteCollectStock(String username, String code) {
        User user = userDataService.findUser(username);
        Stock stock = stockInfo.getSpecificStock(code);
        user.removeStock(stock);
        userDataService.updateUser(user);
    }

    /**
     * 获取用户收藏最多股票板块排序
     * @param collectStocks 已收藏股票
     * @return
     */
    private List<BlockVO> getFavouriteBlock(List<StockVO> collectStocks) {
        Map<Block, Integer> blockDistribute = new HashMap<>();
        for (StockVO stock : collectStocks) {
            List<BlockVO> blocks = stocksInBlock.getStockBlocks(stock.code);
            for (BlockVO blockVO : blocks) {
                Block block = new Block(blockVO);
                // 去除主板
                if (blockVO.blockType == BlockType.BLOCK_MAIN) {
                    continue;
                }
                if (blockDistribute.containsKey(block)) {
                    blockDistribute.put(block, blockDistribute.get(block) + 1);
                } else {
                    blockDistribute.put(block, 1);
                }
            }
        }
        // 值排序
        return blockDistribute.entrySet()
                .stream()
                .sorted((o1, o2) -> (o2.getValue() - o1.getValue()))
                .map(e -> new BlockVO(e.getKey()))
                .collect(Collectors.toList());
    }

    private List<StockNewestInfoVO> getMaxRangeStock(BlockType blockType, int number) {
        List<String> mainStockCodes = stocksInBlock.getBlockStocks(blockType)
                .stream()
                .map(s -> s.code)
                .collect(Collectors.toList());

        return stockInfo.getNewestInfo(mainStockCodes)
                .stream()
                .filter(Objects::nonNull)
                .sorted((o1, o2) -> Double.compare(o2.stock_range, o1.stock_range))
                .collect(Collectors.toList()).subList(0, number);
    }

    @Override
    public List<StockNewestInfoVO> predictFavouriteStocks(String username) {
        // 获取用户收藏股票
        List<StockVO> collectStocks = getCollectStock(username);
        // 用户未收藏股票，选取主板涨幅最大10支股票作为推荐
        if (collectStocks.isEmpty()) {
            return getMaxRangeStock(BlockType.BLOCK_MAIN, 10);
        }

        List<BlockVO> favouriteBlocks = getFavouriteBlock(collectStocks);
        List<StockNewestInfoVO> recommend = new ArrayList<>();

        for (int i = 0; i < Integer.min(4, favouriteBlocks.size()); i++) {
            BlockVO blockVO = favouriteBlocks.get(i);
            List<String> stockCodes = stocksInBlock
                    .getBlockStocks(blockVO.blockType, blockVO.blockName)
                    .stream()
                    .map(s -> s.code)
                    .collect(Collectors.toList());

            List<StockNewestInfoVO> newestInfo = stockInfo
                    .getNewestInfo(stockCodes)
                    .stream()
                    .filter(Objects::nonNull)
                    .sorted((o1, o2) -> Double.compare(o2.stock_range, o1.stock_range))
                    .collect(Collectors.toList());

            int count = 0;
            for (StockNewestInfoVO vo : newestInfo) {
                boolean isRecommend = false;
                for (StockNewestInfoVO selectStock : recommend) {
                    if (selectStock.stock_code.equals(vo.stock_code)) {
                        isRecommend = true;
                        break;
                    }
                }
                boolean isCollected = false;
                for (StockVO collectStock : collectStocks) {
                    if (collectStock.code.equals(vo.stock_code)) {
                        isCollected = true;
                        break;
                    }
                }
                if (!isRecommend && !isCollected) {
                    recommend.add(vo);
                    count++;
                    if (count == 8 - i) {
                        break;
                    }
                }
            }
        }

        return recommend;
    }

}
