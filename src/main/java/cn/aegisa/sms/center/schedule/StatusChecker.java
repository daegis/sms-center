package cn.aegisa.sms.center.schedule;

import cn.aegisa.sms.center.push.DingPusher;
import cn.aegisa.sms.center.push.DingTextMessage;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

    private int count = 10;

    @Scheduled(cron = "0/10 0/1 * * * ?")
    public void webChecker() {
        HttpRequest get = HttpUtil.createGet("http://zk.hagaozhong.com/");
        get.timeout(5000);
        try {
            HttpResponse execute = get.execute();
            int status = execute.getStatus();
            if (status < 400) {
                if (count > 0) {
                    pusher.pushTextMessage(new DingTextMessage("网站好像是可以访问啦！！！"));
                }
                this.count--;
            } else {
                log.info("状态码：" + status);
            }
        } catch (Exception e) {
            log.info("连接超时");
        }
    }

}
