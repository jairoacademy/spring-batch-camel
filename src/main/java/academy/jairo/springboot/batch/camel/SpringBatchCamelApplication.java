package academy.jairo.springboot.batch.camel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringBatchCamelApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBatchCamelApplication.class, args);
	}
	@GetMapping
	public String hello() {
		return "Hy, hello!";
	}
}
