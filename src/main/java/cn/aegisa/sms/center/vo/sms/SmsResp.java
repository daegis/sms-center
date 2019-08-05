package cn.aegisa.sms.center.vo.sms;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xianyingda@gmail.com
 * @serial
 * @since 2019-08-05 10:52
 */
@Data
@Accessors(chain = true)
public class SmsResp {
    private String success;
}
