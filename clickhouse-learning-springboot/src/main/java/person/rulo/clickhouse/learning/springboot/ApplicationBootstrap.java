package person.rulo.clickhouse.learning.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Author rulo
 * @Date 2020/11/22 12:04
 */
@SpringBootApplication
@EnableAsync
public class ApplicationBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationBootstrap.class);
    }
}
