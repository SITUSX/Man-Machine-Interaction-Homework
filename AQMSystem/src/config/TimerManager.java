package config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

/**
 * Created by huangxiao on 2017/6/10.
 */
@Component
public class TimerManager {

    private static final String K_DATA_UPDATE_FILE;
    private static final String REAL_TIME_UPDATE_FILE;

    static {
        String path = TimerManager.class.getResource("").getPath();
        K_DATA_UPDATE_FILE = path + "tushare/k_data_update.py";
        REAL_TIME_UPDATE_FILE = path + "tushare/real_time_update.py";
    }

    /**
     * 命令行运行python3
     * @param file
     */
    private void executePython3(String file) {
        try {
            Process proc = Runtime.getRuntime().exec("python3 " + file);
            BufferedReader bf = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = bf.readLine()) != null) {
                System.out.println(line);
            }
            bf.close();
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新前一日k数据
     * 周二至周六每天01:00执行
     */
    @Scheduled(cron = "0 0 1 ? * TUE-SAT")
    public void updateKDataByDate() {
        System.out.println("Update k data at:\t" + Calendar.getInstance().getTime());
        executePython3(K_DATA_UPDATE_FILE);
    }

    /**
     * 更新股票实时数据
     * 周一至周五每天9:00至15:00内每五分钟执行
     */
    @Scheduled(cron = "0 0/5 9-15 ? * MON-FRI")
    public void updateRealTimeData() {
        System.out.println("Update Real time data at:\t" + Calendar.getInstance().getTime());
        executePython3(REAL_TIME_UPDATE_FILE);
    }

}
