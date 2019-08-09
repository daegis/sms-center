package cn.aegisa.sms.center.service.impl;

import cn.aegisa.sms.center.push.DingPusher;
import cn.aegisa.sms.center.push.DingTextMessage;
import cn.aegisa.sms.center.service.TaobaoSkuService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Using IntelliJ IDEA.
 *
 * @author XIANYINGDA at 2019/8/9 21:47
 */
@Service
@Slf4j
public class TaobaoSkuServiceImpl implements TaobaoSkuService {

    @Autowired
    private DingPusher pusher;

    @Value("${tb.rouge.url}")
    private String url;

    private List<String> skuList = CollectionUtil.list(false, "4158573081669", "4158573081672");

    @Override
    public void processTBSku() {
        HttpRequest get = HttpUtil.createGet(url);
        get.timeout(2000);
        HttpResponse response = get.execute();
        String body = response.body();
        JSONObject bodyObject = JSON.parseObject(body);
        JSONObject data = bodyObject.getJSONObject("data");
        JSONArray apiStack = data.getJSONArray("apiStack");
        JSONObject apiStack_0 = apiStack.getJSONObject(0);
        String value = apiStack_0.getString("value");
        log.info(value);
        JSONObject skuObject = JSON.parseObject(value);
        JSONObject skuCore = skuObject.getJSONObject("skuCore");
        JSONObject sku2info = skuCore.getJSONObject("sku2info");
        Set<String> skuSet = sku2info.keySet();
        for (String sku : skuSet) {
            JSONObject skuInfo = sku2info.getJSONObject(sku);
            String quantity = skuInfo.getString("quantity");
            log.info("sku:{},quantity:{}", sku, quantity);
            if (skuList.contains(sku)) {
                if (Integer.valueOf(quantity) > 0) {
                    pusher.pushTextMessage(new DingTextMessage("sku:" + sku + "/quantity:" + quantity));
                }
            }
        }
    }
}
