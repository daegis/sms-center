package cn.aegisa.sms.center;

import cn.aegisa.sms.center.vo.GJJVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author xianyingda@guazi.com
 * @serial
 * @since 2019-03-12 17:30
 */
public class TimeTest {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
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
            if (name.equals("YE")) {
                System.out.println("ye:" + vo.getInfo());
            }
            if (name.equals("BNZQE")) {
                System.out.println("tq:" + vo.getInfo());
            }
        }
        client.close();
    }
}
