package cn.aegisa.sms.center.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * Message Entity.
 */
@Data
public class Message implements Serializable {

    //列信息
    private Integer id;

    private String context;

    private String sender;

    private LocalDateTime createTime;

}

