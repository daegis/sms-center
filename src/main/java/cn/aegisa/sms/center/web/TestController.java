package cn.aegisa.sms.center.web;

import cn.aegisa.sms.center.model.Student;
import cn.aegisa.sms.center.service.JingdongSkuService;
import cn.aegisa.sms.center.service.TaobaoSkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private TaobaoSkuService taobaoSkuService;

    @RequestMapping("/aaa")
    @ResponseBody
    public Object doTest(Student student) {
        Process process = null;
        List<String> processList = new ArrayList<String>();
        try {
            process = Runtime.getRuntime().exec("sh /root/dev/hcp.sh");
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
                processList.add(line);
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String line : processList) {
            System.out.println(line);
        }
        return student;
    }
}
