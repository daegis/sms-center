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
@SuppressWarnings("Duplicates")
@Component
@Slf4j
public class StatusChecker {

    @Autowired
    private DingPusher pusher;


    @Scheduled(cron = "0/15 0/1 * * * ?")
    public void webChecker() {
        HttpRequest get = HttpUtil.createGet("http://zk.hagaozhong.com/");
        get.timeout(10000);
        try {
            HttpResponse execute = get.execute();
            int status = execute.getStatus();
            if (status < 300) {
                pusher.pushTextMessage(new DingTextMessage("http://zk.hagaozhong.com/网站好像是可以访问啦！！！"));
            } else {
                log.info("状态码：" + status);
            }
        } catch (Exception e) {
            log.info("连接超时");
        }
    }

    @Scheduled(cron = "0/15 0/1 * * * ?")
    public void web1Checker() {
        HttpRequest get = HttpUtil.createGet("http://zk.haedu.gov.cn/");
        get.timeout(10000);
        try {
            HttpResponse execute = get.execute();
            int status = execute.getStatus();
            if (status < 300) {
                pusher.pushTextMessage(new DingTextMessage("http://zk.haedu.gov.cn/网站好像是可以访问啦！！！"));
            } else {
                log.info("状态码：" + status);
            }
        } catch (Exception e) {
            log.info("连接超时");
        }
    }

}
