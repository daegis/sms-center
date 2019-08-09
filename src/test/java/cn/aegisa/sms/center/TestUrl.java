package cn.aegisa.sms.center;

import cn.aegisa.sms.center.service.TaobaoSkuService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Using IntelliJ IDEA.
 *
 * @author XIANYINGDA at 2019/8/9 21:53
 */
public class TestUrl extends BaseTester {

    @Autowired
    private TaobaoSkuService skuService;

    @Test
    public void test01(){
        skuService.processTBSku();
    }
}
