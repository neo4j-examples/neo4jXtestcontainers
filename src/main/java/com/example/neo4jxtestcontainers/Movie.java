package com.example.neo4jxtestcontainers;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Values;
import org.neo4j.driver.types.Node;

public final class Movie {

	public static final class Repository {

		private final Driver driver;

		public Repository(Driver driver) {
			this.driver = driver;
		}

		public Movie createOrUpdate(String title) {
			try (var session = driver.session()) {
				return session.run("MERGE (n:Movie {title: $title}) RETURN n", Map.of("title", title))
					.stream()
					.map(r -> {
						var node = r.get("n").asNode();
						return new Movie(node.id(), node.get("title").asString());
					})
					.findFirst()
					.orElseThrow(NoSuchElementException::new);
			}
		}
	}

	private final Long id;

	private final String title;

	public Movie(Long id, String title) {
		this.id = id;
		this.title = title;
	}

	public Long id() {
		return id;
	}

	public String title() {
		return title;
	}
}
