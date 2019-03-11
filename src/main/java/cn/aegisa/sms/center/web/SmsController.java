package cn.aegisa.sms.center.web;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping("/submit")
    @ResponseBody
    public Object test(HttpServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();
        log.info("请求入参：{}", JSON.toJSONString(map));
        return "OK";
    }
}
