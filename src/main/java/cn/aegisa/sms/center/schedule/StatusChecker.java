package cn.aegisa.sms.center.schedule;

import cn.aegisa.sms.center.push.DingPusher;
import cn.aegisa.sms.center.service.JingdongSkuService;
import cn.aegisa.sms.center.service.TaobaoSkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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


    @Scheduled(cron = "0 0/30 * * * ?")
    public void webChecker() {
    }

    @Scheduled(cron = "0 0/15 * * * ?")
    public void web1Checker() {
        try {
            jingdongSkuService.processJDScanner();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
