package academy.jairo.springboot.batch.camel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {

		LocalDate dt1 = LocalDate.parse("2022-10-01");
		LocalDate dt2 = LocalDate.parse("2022-10-31");

		Assertions.assertNotEquals(dt1,dt2);

	}

}
