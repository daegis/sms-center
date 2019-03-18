package cn.aegisa.sms.center;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * @author xianyingda@gmail.com
 * @serial
 * @since 2019-03-12 17:30
 */
@Slf4j
public class TimeTest {
    public static void main(String[] args) throws IOException {
        RestTemplate template = new RestTemplate();
        String result = template.getForObject("https://wxhelo.fesco.com.cn/api/?appkey=weixin&method=GetData&parm={json}", String.class, "{\"name\":\"sel1\",\"ct\":\"8\",\"user\":\"18698889280|2019-03-11|70DADB406CDEEFDBB62ABECF71FB95BD\"}");
        result = result.substring(1, result.length() - 1);
        JSONObject jsonResult = JSON.parseObject(result);
        JSONArray data = jsonResult.getJSONArray("Data");
        int size = data.size();
        String msg = jsonResult.getString("Msg");
        String code = jsonResult.getString("Code");
        log.info("code:{},msg:{}", code, msg);

    }
}
