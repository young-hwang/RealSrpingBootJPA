package io.ggammu.realspringbootjpa;

import java.util.Arrays;
import java.util.function.IntFunction;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RealspringbootjpaApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void arraytest() {
		int[] ints = {1, 2, 3};
		System.out.println(Arrays.stream(ints).mapToObj(String::valueOf).toArray());
	}

}
