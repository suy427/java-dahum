package com.sondahum.javadahum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
class TestDahumMockApplicationStarter extends SpringBootServletInitializer {

	static void main(String[] args) {
		SpringApplication.run(TestDahumMockApplicationStarter.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TestDahumMockApplicationStarter.class);
	}

}
