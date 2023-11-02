package com.example.neo4jxtestcontainers;

import org.neo4j.driver.Driver;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	private final Driver driver;

	public Application(Driver driver) {
		this.driver = driver;
	}

	@Override
	public void run(String... args) throws Exception {
		driver.verifyConnectivity();
		System.out.println("driver is connected");
	}
}
