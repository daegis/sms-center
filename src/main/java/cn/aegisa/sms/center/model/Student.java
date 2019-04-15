package cn.aegisa.sms.center.model;

import lombok.Data;

import java.util.List;

/**
 * @author xianyingda@gmail.com
 * @serial
 * @since 2019-04-12 21:06
 */
@Data
public class Student {
    private String name;
    private List<Integer> integerList;
}
