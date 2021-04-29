package na.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// mapper 못 찾으면 MapperScan추가
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
