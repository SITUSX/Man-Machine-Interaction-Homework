package controller;

import factory.BLFactory;
import blservice.StockBLService;
import blservice.UserBLService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import config.ResultMessage;
import vo.StockNewestInfoVO;
import vo.StockVO;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserBLService userBLService = BLFactory.getBlService(UserBLService.class);
    private StockBLService stockBLService = BLFactory.getBlService(StockBLService.class);

    @RequestMapping(value="/login.form", method = RequestMethod.POST)
    @ResponseBody
    public String login(String username, String password) {
        ResultMessage resultMessage = null;
        try {
            resultMessage = userBLService.login(username, password);
        }catch (Exception e){
            e.printStackTrace();
        }

        return resultMessage.toString();
    }

    @RequestMapping(value="/signup.form", method = RequestMethod.POST)
    @ResponseBody
    public String signup(String username, String password) {

        ResultMessage resultMessage = userBLService.register(username, password);

        return resultMessage.toString();
    }

    @RequestMapping(value = "/getCollectStock.form")
    @ResponseBody
    public List<StockNewestInfoVO> getCollectStock(String username){
        List<StockVO> vo = userBLService.getCollectStock(username);

        List<String> codes = new ArrayList<>();
        for (int i = 0; i < vo.size(); i++) {
            String code = vo.get(i).code;
            codes.add(code);
        }

        List<StockNewestInfoVO> result = stockBLService.getNewestInfo(codes);
        return result;
    }

    @RequestMapping(value = "/isCollected.form")
    @ResponseBody
    public boolean isCollected(String username, String code){
        return userBLService.isCollected(username, code);
    }

    @RequestMapping(value = "/addCollect.form")
    @ResponseBody
    public boolean addCollect(String username, String code){
        try {
            userBLService.addCollectStock(username, code);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/delCollect.form")
    @ResponseBody
    public boolean delCollect(String username, String code){
        try {
            userBLService.deleteCollectStock(username, code);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/predictFavouriteStocks.form")
    @ResponseBody
    public List<StockNewestInfoVO> predictFavouriteStocks(String username){

        return userBLService.predictFavouriteStocks(username);
    }
}
