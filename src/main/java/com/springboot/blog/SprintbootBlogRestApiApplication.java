package com.springboot.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SprintbootBlogRestApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(SprintbootBlogRestApiApplication.class, args);
	}
}

// Entity or Model or Domain : We keep all JPA Entities inside entity package.
// We keep all the services interfaces inside 'service' package and Service classes inside "service'impl" package.
// repository  :  We keep all the spring data JPA repositories inside 'repository' package.
