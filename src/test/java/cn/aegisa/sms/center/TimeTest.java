package cn.aegisa.sms.center;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

/**
 * @author xianyingda@gmail.com
 * @serial
 * @since 2019-03-12 17:30
 */
@Slf4j
public class TimeTest {
    public static void main(String[] args) throws IOException {
        String uuid = UUID.randomUUID().toString();
        System.out.println("uuid = " + uuid);
        System.out.println("uuid.substring(0,8) = " + uuid.substring(0, 8));
    }
}
