package com.example.neo4jxtestcontainers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.neo4j.driver.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers(disabledWithoutDocker = true)
@Import(ServiceConnectionsConfig.class)
class RepositoryTests {

	@Test
	void repositoryShouldWork(@Autowired Driver driver) {

		Movie.Repository repository = new Movie.Repository(driver);
		Movie newMovie = repository.createOrUpdate("Event Horizon");
		assertNotNull(newMovie.id());
	}
}
