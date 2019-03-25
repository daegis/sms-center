package cn.aegisa.sms.center.schedule;

import cn.aegisa.sms.center.push.DingPusher;
import cn.aegisa.sms.center.push.DingTextMessage;
import cn.aegisa.sms.center.vo.GJJVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author xianyingda@gmail.com
 * @serial
 * @since 2019-03-14 12:45
 */
@Component
@Slf4j
public class StatusChecker {

    @Autowired
    private DingPusher pusher;

    private boolean searchFesco = true;

    @Scheduled(cron = "0 0/20 * * * ?")
    public void checkStatus() throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            HttpPost post = new HttpPost("https://weixin.bjgjj.gov.cn/weixin/NGM2NmQ1ZWY2ZmJhNGY5YjlkNjc4MWVmMzBlZWY1NDY0YzAyY2JlMDJjMjg0Y2NjYTE1YTlhM2MxZmEzYzIyMjE1NTI2MzE2NDkwMjY.json?fromuser=o3ioNxC1L8f6Wx_FOZ2AiTabfqV4&touser=gh_d7dfe024a186&method=doExecute");
            CloseableHttpResponse response = client.execute(post);
            InputStream content = response.getEntity().getContent();
            String s = IOUtils.toString(content, StandardCharsets.UTF_8);
            JSONObject resultObj = JSON.parseObject(s);
            JSONArray resultArray = resultObj.getJSONArray("result");
            JSONArray resultArray0 = resultArray.getJSONArray(0);
            List<GJJVo> gjjVos = resultArray0.toJavaList(GJJVo.class);
            for (GJJVo vo : gjjVos) {
                String name = vo.getName();
                String info = vo.getInfo();
                if (name.equals("YE")) {
                    log.info("ye:" + info);
                    Double yeDouble = Double.valueOf(info);
                    if (yeDouble < 49523) {
                        pusher.pushTextMessage(new DingTextMessage("内存余额有变动，请留意：" + yeDouble));
                    }
                }
                if (name.equals("BNZQE")) {
                    log.info("tq:" + info);
                    Double tqDouble = Double.valueOf(info);
                    if (tqDouble > 0) {
                        pusher.pushTextMessage(new DingTextMessage("内存余额有变动，请留意：" + tqDouble));
                    }
                }
            }
        } catch (Exception e) {
            log.error("监控异常", e);
            pusher.pushTextMessage(new DingTextMessage("gjj监控已失效，请重启"));
        }
        client.close();
    }

    @Scheduled(cron = "0/5 * * * * ?")
    public void checkFesco() {
        try {
            if (searchFesco) {
                RestTemplate template = new RestTemplate();
                String result = template.getForObject("https://wxhelo.fesco.com.cn/api/?appkey=weixin&method=GetData&parm={json}", String.class, "{\"name\":\"sel1\",\"ct\":\"8\",\"user\":\"18698889280|2019-03-11|70DADB406CDEEFDBB62ABECF71FB95BD\"}");
                result = result.substring(1, result.length() - 1);
                JSONObject jsonResult = JSON.parseObject(result);
                JSONArray data = jsonResult.getJSONArray("Data");
                int size = data.size();
                String msg = jsonResult.getString("Msg");
                String code = jsonResult.getString("Code");
                log.info("code:{},msg:{}", code, msg);
                if (size != 0) {
                    // found fesco
                    searchFesco = false;
                    pusher.pushTextMessage(new DingTextMessage("fesco found! 请留意"));
                }
            }
        } catch (Exception e) {
            log.error("监控异常", e);
            pusher.pushTextMessage(new DingTextMessage("fesco监控已失效，请关注"));
        }
    }
}
