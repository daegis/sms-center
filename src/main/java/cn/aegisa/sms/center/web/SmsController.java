package cn.aegisa.sms.center.web;

import cn.aegisa.sms.center.model.Message;
import cn.aegisa.spring.boot.mybatis.component.service.ICommonService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

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
    private ICommonService commonService;

    @Autowired
    private JavaMailSender mailSender;

    @RequestMapping("/submit")
    @ResponseBody
    public Object test(HttpServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();
        log.info("请求入参：{}", JSON.toJSONString(map));
        String ctx = request.getParameter("ctx");
        String from = request.getParameter("from");
        Message message = new Message();
        message.setContext(ctx);
        message.setSender(from);
        message.setCreateTime(LocalDateTime.now());
        commonService.save(message);
        // 发送电子邮件
        new Thread(() -> {
            try {
                SimpleMailMessage msg = new SimpleMailMessage();
                msg.setFrom("daegis@126.com");
                msg.setTo("daegis@yeah.net");
                msg.setSubject(String.format("转发的%s发来的短信", from));
                msg.setText(ctx);
                mailSender.send(msg);
                log.info("邮件发送成功");
            } catch (Exception e) {
                log.error("邮件发送失败", e);
            }
        }).start();
        return "OK";
    }
}
