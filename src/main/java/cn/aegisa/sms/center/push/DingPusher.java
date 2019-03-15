package cn.aegisa.sms.center.push;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author xianyingda@gmail.com
 * @serial
 * @since 2019-03-15 16:11
 */
@Component
@Slf4j
public class DingPusher {

    private String url = "https://oapi.dingtalk.com/robot/send?access_token=9619d0b12908baa58c78a2b1ce11613b92a6d2d90380c6bd7feaf5d6768a1a3d";

    public void pushTextMessage(DingTextMessage message) {
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            String payload = JSON.toJSONString(message);
            log.info("发送的信息：{}", payload);
            HttpPost httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(payload, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            CloseableHttpResponse execute = client.execute(httpPost);


        } catch (Exception e) {
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
