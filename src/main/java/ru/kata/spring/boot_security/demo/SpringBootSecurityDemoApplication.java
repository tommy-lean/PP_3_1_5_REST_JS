package ru.kata.spring.boot_security.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.kata.spring.boot_security.demo.models.User;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {

	public static void main(String[] args) throws JsonProcessingException {
		SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
		ObjectMapper objectMapper = new ObjectMapper();
		User user = new User("newUser", "12345", "us@mail.ru", 25);
		System.out.println(objectMapper.writeValueAsString(user));
//		objectMapper.
	}

}
