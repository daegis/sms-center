package cn.aegisa.sms.center.schedule;

import cn.aegisa.sms.center.push.DingPusher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

}
