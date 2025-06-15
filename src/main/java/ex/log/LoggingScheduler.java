package ex.log;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class LoggingScheduler {

    private static final Logger log = LoggerFactory.getLogger(LoggingScheduler.class);

    @Scheduled(fixedRate = 10000)
    public void logEveryTenSeconds() {
        log.info("info 테스트 로그: {}", LocalDateTime.now());
        log.warn("warn 테스트 로그: {}", LocalDateTime.now());
        log.error("error 테스트 로그: {}", LocalDateTime.now());
    }
}
