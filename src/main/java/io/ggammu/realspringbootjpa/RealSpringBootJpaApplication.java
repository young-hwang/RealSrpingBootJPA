package io.ggammu.realspringbootjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RealSpringBootJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealSpringBootJpaApplication.class, args);

		Hello hello = new Hello();
		hello.setData("hello");
		System.out.println("hello.getData() = " + hello.getData());
	}

}
