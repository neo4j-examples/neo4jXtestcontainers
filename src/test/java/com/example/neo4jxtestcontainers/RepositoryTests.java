package com.example.neo4jxtestcontainers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
class RepositoryTests {

	private static Neo4jContainer<?> neo4j;

	@BeforeAll
	static void startNeo4j() {
		neo4j = new Neo4jContainer<>("neo4j:4.4")
			.waitingFor(Neo4jContainer.WAIT_FOR_BOLT)
			.withReuse(true);
		neo4j.start();
	}

	@DynamicPropertySource
	static void neo4jProperties(DynamicPropertyRegistry registry) {

		registry.add("spring.neo4j.uri", neo4j::getBoltUrl);
		registry.add("spring.neo4j.authentication.username", () -> "neo4j");
		registry.add("spring.neo4j.authentication.password", neo4j::getAdminPassword);
	}

	@Test
	void repositoryShouldWork(@Autowired Driver driver) {

		Movie.Repository repository = new Movie.Repository(driver);
		Movie newMovie = repository.createOrUpdate("Event Horizon");
		assertNotNull(newMovie.id());
	}
}
