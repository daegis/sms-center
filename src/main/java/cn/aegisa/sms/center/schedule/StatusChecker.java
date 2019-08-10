package cn.aegisa.sms.center.schedule;

import cn.aegisa.sms.center.push.DingPusher;
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


    @Scheduled(cron = "0 0/25 * * * ?")
    public void webChecker() {
        try {
            taobaoSkuService.processTBSku();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0/15 0/1 * * * ?")
    public void web1Checker() {
    }

}
