package ex.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {

    private static final Logger log = LoggerFactory.getLogger(LogController.class);

    @GetMapping("/logs")
    public String logs() {
        log.info("info !!");
        log.warn("warn !!");
        log.error("error !!");
        return "success";
    }
}
