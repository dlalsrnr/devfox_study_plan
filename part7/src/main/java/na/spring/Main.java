package na.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}
