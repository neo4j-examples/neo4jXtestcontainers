package com.example.neo4jxtestcontainers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.Driver;
import org.neo4j.harness.Neo4j;
import org.neo4j.harness.Neo4jBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
class RepositoryTests {

	private static Neo4j neo4j;

	@BeforeAll
	static void startNeo4j() {
		neo4j = Neo4jBuilders
			.newInProcessBuilder()
			.build();
	}

	@DynamicPropertySource
	static void neo4jProperties(DynamicPropertyRegistry registry) {

		registry.add("spring.neo4j.uri", neo4j::boltURI);
		registry.add("spring.neo4j.authentication.username", () -> "neo4j");
		registry.add("spring.neo4j.authentication.password", () -> null);
	}

	@AfterAll
	static void stopNeo4j() {
		neo4j.close();
	}

	@Test
	void repositoryShouldWork(@Autowired Driver driver) {

		Movie.Repository repository = new Movie.Repository(driver);
		Movie newMovie = repository.createOrUpdate("Event Horizon");
		assertNotNull(newMovie.id());
	}
}
