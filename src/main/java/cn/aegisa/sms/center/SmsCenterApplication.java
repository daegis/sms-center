package cn.aegisa.sms.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmsCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsCenterApplication.class, args);
    }

}
