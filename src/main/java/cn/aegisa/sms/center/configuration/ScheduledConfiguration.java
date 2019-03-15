package cn.aegisa.sms.center.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xianyingda@gmail.com
 * @serial
 * @since 2019-03-14 12:44
 */
@Configuration
public class ScheduledConfiguration implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(setTaskExecutors());
    }

    @Bean(destroyMethod = "shutdownNow")
    public ExecutorService setTaskExecutors() {
        return Executors.newScheduledThreadPool(10);
    }
}
