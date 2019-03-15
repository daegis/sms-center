package cn.aegisa.sms.center;

import cn.aegisa.sms.center.push.DingPusher;
import cn.aegisa.sms.center.push.DingTextMessage;
import cn.aegisa.spring.boot.mybatis.component.service.ICommonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsCenterApplicationTests {

    @Autowired
    private ICommonService commonService;

    @Autowired
    private DingPusher pusher;

    @Test
    public void contextLoads() {
        pusher.pushTextMessage(new DingTextMessage("测试就咪啾咪"));
    }

}
