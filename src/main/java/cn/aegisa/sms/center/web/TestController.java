package cn.aegisa.sms.center.web;

import cn.aegisa.sms.center.model.Student;
import cn.aegisa.sms.center.service.JingdongSkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xianyingda@gmail.com
 * @serial
 * @since 2019-04-12 21:01
 */
@Controller
@Slf4j
public class TestController {

    @Autowired
    private JingdongSkuService jingdongSkuService;

    @RequestMapping("/aaa")
    @ResponseBody
    public Object doTest(Student student) {
        try {
            jingdongSkuService.processJDScanner();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }
}
