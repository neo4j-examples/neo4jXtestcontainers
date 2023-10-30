package com.example.neo4jxtestcontainers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
class RepositoryTests {

	@ServiceConnection
	private static Neo4jContainer<?> neo4j = new Neo4jContainer<>("neo4j:5.13")
		.withReuse(true);

	@BeforeAll
	static void startNeo4j() {
		neo4j.start();
	}

	@Test
	void repositoryShouldWork(@Autowired Driver driver) {

		Movie.Repository repository = new Movie.Repository(driver);
		Movie newMovie = repository.createOrUpdate("Event Horizon");
		assertNotNull(newMovie.id());
	}
}
