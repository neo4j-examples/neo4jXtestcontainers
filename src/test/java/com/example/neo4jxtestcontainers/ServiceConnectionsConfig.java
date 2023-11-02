package com.example.neo4jxtestcontainers;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.Neo4jContainer;

@TestConfiguration(proxyBeanMethods = false)
public class ServiceConnectionsConfig {

	@Bean
	@ServiceConnection
	Neo4jContainer<?> neo4j() {
		return new Neo4jContainer<>("neo4j:5.13")
			.withReuse(true);
	}
}
