package com.example.neo4jxtestcontainers;

import org.springframework.boot.SpringApplication;

public class TestApplication {

	public static void main(String...a) {
		SpringApplication.from(Application::main)
			.with(ServiceConnectionsConfig.class)
			.run(a);
	}
}
