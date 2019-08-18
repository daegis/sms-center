package cn.aegisa.sms.center.service.impl;

import cn.aegisa.sms.center.push.DingPusher;
import cn.aegisa.sms.center.push.DingTextMessage;
import cn.aegisa.sms.center.service.JingdongSkuService;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Using IntelliJ IDEA.
 *
 * @author XIANYINGDA at 2019/8/13 14:24
 */
@Service
@Slf4j
public class JingdongSkuServiceImpl implements JingdongSkuService {

    @Value("${jd.cookie}")
    private String cookie;

    @Value("${jd.body}")
    private String body;

    @Autowired
    private DingPusher pusher;

    @Override
    public void processJDScanner() throws Exception {
        HttpRequest post = HttpUtil.createPost("https://api.m.jd.com/client.action?functionId=cart");
        post.cookie(cookie);
        post.header("Host", "api.m.jd.com");
        post.header("accept", "*/*");
        post.header("content-type", "application/x-www-form-urlencoded");
        post.header("user-agent", "JD4iPhone/166538 (iPhone; iOS 12.4; Scale/3.00)");
        post.header("accept-language", "zh-Hans-CN;q=1");
        post.body(body);
        HttpResponse execute = post.execute();
        String body = execute.body();
        log.info(body);
        JSONObject bodyObject = JSON.parseObject(body);
        String a = bodyObject.getJSONObject("cartInfo").getJSONArray("vendors").getJSONObject(0).getJSONArray("sorted").getJSONObject(0)
                .getJSONObject("item").getString("Price");
        log.info(a);
        BigDecimal price = new BigDecimal(a);
        if (price.compareTo(BigDecimal.valueOf(1399.00)) != 0) {
            pusher.pushTextMessage(new DingTextMessage("new price:" + a));
        }
    }
}
