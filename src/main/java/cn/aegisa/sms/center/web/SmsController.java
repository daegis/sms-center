package cn.aegisa.sms.center.web;

import cn.aegisa.sms.center.model.Message;
import cn.aegisa.sms.center.push.DingPusher;
import cn.aegisa.sms.center.push.DingTextMessage;
import cn.aegisa.sms.center.vo.sms.Resp;
import cn.aegisa.sms.center.vo.sms.SmsPublish;
import cn.aegisa.sms.center.vo.sms.SmsResp;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

/**
 * Using IntelliJ IDEA.
 *
 * @author XIANYINGDA at 2019/3/11 22:18
 */
@RequestMapping("/sms")
@Controller
@Slf4j
public class SmsController {


    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private DingPusher pusher;

    @RequestMapping("/publish")
    @ResponseBody
    public Object test(SmsPublish publish) {
        log.info("请求入参：{}", JSON.toJSONString(publish));
        Message message = new Message();
        message.setContext(publish.getMessage());
        message.setSender(publish.getFrom());
        message.setCreateTime(LocalDateTime.now());
        pusher.pushTextMessage(new DingTextMessage(publish.getMessage()));
        return new Resp().setPayload(new SmsResp().setSuccess("true"));
    }
}
