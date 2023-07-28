package spring.angular.social;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import spring.angular.social.util.SystemPathUtil;

@SpringBootApplication
@OpenAPIDefinition
@Slf4j
public class SocialMediaPlatformApplication {

    public static void main(String[] args) {
        String systemPath = SystemPathUtil.getCurrentSystemPath();
        log.info("Current System Path: " + systemPath);
        SpringApplication.run(SocialMediaPlatformApplication.class, args);
    }

}
