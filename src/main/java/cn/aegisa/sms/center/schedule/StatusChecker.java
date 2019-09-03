package cn.aegisa.sms.center.schedule;

import cn.aegisa.sms.center.push.DingPusher;
import cn.aegisa.sms.center.push.DingTextMessage;
import cn.aegisa.sms.center.service.JingdongSkuService;
import cn.aegisa.sms.center.service.TaobaoSkuService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author xianyingda@gmail.com
 * @serial
 * @since 2019-03-14 12:45
 */
@SuppressWarnings("Duplicates")
@Component
@Slf4j
public class StatusChecker {

    @Autowired
    private DingPusher pusher;

    @Autowired
    private TaobaoSkuService taobaoSkuService;

    @Autowired
    private JingdongSkuService jingdongSkuService;

    private Map<String, String> map = new HashMap<>();

    //@Scheduled(cron = "0 0/30 * * * ?")
    public void webChecker() {
    }

    @Scheduled(cron = "0 0/15 * * * ?")
    public void web1Checker() {
        Process process = null;
        List<String> processList = new ArrayList<String>();
        try {
            process = Runtime.getRuntime().exec("sh /root/dev/hcp.sh");
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
                processList.add(line);
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String line : processList) {
            if (StringUtils.isEmpty(line)) {
                continue;
            }
            JSONObject jsonObject = JSON.parseObject(line);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray list = data.getJSONArray("list");
            for (int i = 0; i < list.size(); i++) {
                JSONObject o = list.getJSONObject(i);
                JSONArray needs = o.getJSONArray("needs");
                for (int i1 = 0; i1 < needs.size(); i1++) {
                    JSONObject o1 = needs.getJSONObject(i1);
                    String boardTrainCode = o1.getString("board_train_code");
                    String queueNum = o1.getString("queue_num");
                    String old = map.get(boardTrainCode);
                    if (Objects.equals(old, queueNum)) {
                        log.info("{}没有变化：{}", boardTrainCode, old);
                    } else {
                        pusher.pushTextMessage(new DingTextMessage(boardTrainCode + ":" + queueNum));
                        map.put(boardTrainCode, queueNum);
                    }
                }
            }
        }
    }

}
