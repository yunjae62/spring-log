package ex.log;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ExternalController {

    private final RestTemplate restTemplate;

    @GetMapping("/external-api")
    public ResponseEntity<?> externalApi() {
        log.warn("externalApi called");
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/external")
    @Retry(name = "externalApi")
    @CircuitBreaker(name = "externalApi", fallbackMethod = "fallback")
    public String callExternalApi() {
        String url = "http://localhost:8080/external-api";
        return restTemplate.getForObject(url, String.class);
    }

    private String fallback(Throwable t) {
        log.warn("Fallback activated due to: {}", t.toString());
        return "기본 응답 (Fallback)";
    }
}
