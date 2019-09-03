package cn.aegisa.sms.center.web;

import cn.aegisa.sms.center.model.Student;
import cn.aegisa.sms.center.push.DingPusher;
import cn.aegisa.sms.center.push.DingTextMessage;
import cn.aegisa.sms.center.service.JingdongSkuService;
import cn.aegisa.sms.center.service.TaobaoSkuService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author xianyingda@gmail.com
 * @serial
 * @since 2019-04-12 21:01
 */
@Controller
@Slf4j
public class TestController {

    @Autowired
    private JingdongSkuService jingdongSkuService;

    @Autowired
    private TaobaoSkuService taobaoSkuService;

    @Autowired
    private DingPusher pusher;

    private Map<String, String> map = new HashMap<>();

    @RequestMapping("/aaa")
    @ResponseBody
    public Object doTest(Student student) {
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
        return student;
    }
}
