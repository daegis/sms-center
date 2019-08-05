package cn.aegisa.sms.center.vo.sms;

import lombok.Data;

/**
 * @author xianyingda@gmail.com
 * @serial
 * @since 2019-08-05 10:57
 */
@Data
public class SmsPublish {
    private String secret;
    private String from;
    private String message;
    private String sent_timestamp;
    private String sent_to;
    private String message_id;
}
