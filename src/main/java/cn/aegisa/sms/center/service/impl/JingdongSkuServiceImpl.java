package cn.aegisa.sms.center.service.impl;

import cn.aegisa.sms.center.service.JingdongSkuService;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Using IntelliJ IDEA.
 *
 * @author XIANYINGDA at 2019/8/13 14:24
 */
@Service
@Slf4j
public class JingdongSkuServiceImpl implements JingdongSkuService {

    @Override
    public void processJDScanner() throws Exception {
        ClassPathResource classPathResource = new ClassPathResource("go.sh");
        @Cleanup InputStream inputStream = classPathResource.getInputStream();
        String s = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        @Cleanup Process process = Runtime.getRuntime().exec(s);
        @Cleanup InputStream processInputStream = process.getInputStream();
        @Cleanup BufferedReader input = new BufferedReader(new InputStreamReader(processInputStream));

        String s1 = input.readLine();
        log.info(s1);
    }
}
