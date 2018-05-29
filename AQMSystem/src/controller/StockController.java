package controller;

import blservice.StockBLService;
import config.BlockType;
import factory.BLFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vo.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static config.Config.DATE_FORMAT;

@Controller
@RequestMapping("/stock")
public class StockController {

    private StockBLService stockBLService = BLFactory.getBlService(StockBLService.class);

    @RequestMapping(value = "/getStockList.form")
    public @ResponseBody List<StockVO> getStockList(@RequestParam(value = "keyword") String keyword) {
        return stockBLService.getStockList(keyword);
    }

    @RequestMapping(value = "/getStockByCondition.form")
    public @ResponseBody List<String> getStockByCondition(String minVolume, String minStockRange, List<String> section) {
        int min_volume = Integer.valueOf(minVolume);
        int min_stock_range = Integer.valueOf(minStockRange);
        List<Boolean> sectionChoose = section.stream().map(Boolean::valueOf).collect(Collectors.toList());
        return stockBLService.getStockByCondition(new StockConditionVO(sectionChoose, min_volume, min_stock_range));
    }

    @RequestMapping(value = "/getStockDetails.form")
    public @ResponseBody StockDetailsVO getStockDetails(String code, String begin, String end) {
        Date from = null;
        Date to = null;
        try {
            from = DATE_FORMAT.parse(begin);
            to = DATE_FORMAT.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return stockBLService.getStockDetails(code, from, to);
    }

    @RequestMapping(value = "/getNewestInfo.form")
    public @ResponseBody StockNewestInfoVO getNewestInfo(String code) {
        List<String> codes = new ArrayList<>();
        codes.add(code);

        List<StockNewestInfoVO> vos = stockBLService.getNewestInfo(codes);

        return vos.get(0);
    }

    @RequestMapping(value = "/getNewestInfoList.form")
    public @ResponseBody List<StockNewestInfoVO> getNewestInfo(List<String> codes) {

        return stockBLService.getNewestInfo(codes);
    }

    @RequestMapping(value = "/getNewestInfoListByOrder.form")
    public @ResponseBody List<StockNewestInfoVO> getNewestInfoByOrder(String param, String number) {

        Comparator<StockNewestInfoVO> comparator;

        if (param.equals("stock_range")) {
            comparator = ((o1, o2) -> Double.compare(o2.stock_range, o1.stock_range));
        } else if (param.equals("volume")) {
            comparator = ((o1, o2) -> Long.compare(o2.volume, o1.volume));
        } else if (param.equals("stock_range_desc")) {
            comparator = (Comparator.comparingDouble(o -> o.stock_range));
        } else {
            comparator = null;
        }

        return stockBLService.getNewestInfoByOrder(comparator, Integer.valueOf(number));
    }

    @RequestMapping(value = "/getMarketInfoByDate.form")
    public @ResponseBody MarketInfoVO getMarketInfo(String date) {
        Date date1 = null;
        try {
            date1 = DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return stockBLService.getMarketInfo(date1);
    }

    @RequestMapping(value = "/getMarketInfo.form")
    public @ResponseBody MarketInfoVO getMarketInfo(String date, String blockName) {
        BlockType blockType = BlockType.valueOf(blockName);

        Date date1 = null;
        try {
            date1 = DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return stockBLService.getMarketInfo(date1, blockType);
    }

}
