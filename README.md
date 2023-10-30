# neo4jXtestcontainers

Example repository for the Neo4j X Testcontainers livestream on Thursday, 2023-11-02.

## Class under test:

Homegrown repository, that uses Neo4j Java driver directly, without involving any mapping framework to keep things simple. The repository merges a movie under the `title` property, nothing fancy, really. Shouldn't distract from what we wanna show for the test.

## Step one:

Spring Boot 2.7 project, running on Java 11.
The Project uses `org.neo4j.test:neo4j-harness`, our official test harness, latest 4.4 version.
We at Neo4j usually recommends using the test harness for testing custom stored procedures, as shown in the [procedure template](https://github.com/neo4j-examples/neo4j-procedure-template), which makes a lot of sense: A stored procedure runs in the same process as the database. No network involved.

You can of course also use it to test your application.
It does make sense for some, even, especially those who do use Neo4j embedded in production.
Less so for any application that uses a network  connection like ours. 

### Problems

- Tests doesn't take network issues into account
- Being a JVM product makes embedding easy, but comes at a cost:
    - Neo4j has a lot of dependencies (See below)
    - Neo4j is usually tied very close to a Java version, as we do a lot to optimize our product and need often to reach out to Unsafe, Off-Heap memory and the like: Nothing you want to spread into your application or even test code

## Misc

### Dependencies of Neo4j 4.4 test harness

```
[INFO] \- org.neo4j.test:neo4j-harness:jar:4.4.13:test
[INFO]    +- org.neo4j:annotations:jar:4.4.13:test
[INFO]    |  +- org.eclipse.collections:eclipse-collections:jar:10.4.0:test
[INFO]    |  |  \- org.eclipse.collections:eclipse-collections-api:jar:10.4.0:test
[INFO]    |  \- org.apache.commons:commons-lang3:jar:3.12.0:test
[INFO]    +- org.neo4j:neo4j:jar:4.4.13:test
[INFO]    |  +- org.neo4j:neo4j-fabric:jar:4.4.13:test
[INFO]    |  |  +- io.projectreactor:reactor-core:jar:3.4.33:test
[INFO]    |  |  +- org.neo4j:neo4j-front-end:jar:4.4.13:test
[INFO]    |  |  |  +- org.neo4j:neo4j-expressions:jar:4.4.13:test
[INFO]    |  |  |  +- org.neo4j:neo4j-parser:jar:4.4.13:test
[INFO]    |  |  |  \- org.neo4j:neo4j-cypher-macros:jar:4.4.13:test
[INFO]    |  |  \- org.scala-lang:scala-reflect:jar:2.12.13:test
[INFO]    |  +- org.neo4j:neo4j-procedure:jar:4.4.13:test
[INFO]    |  |  +- org.neo4j:neo4j-procedure-api:jar:4.4.13:test
[INFO]    |  |  +- org.neo4j:neo4j-codegen:jar:4.4.13:test
[INFO]    |  |  |  +- org.ow2.asm:asm-util:jar:9.2:test
[INFO]    |  |  |  +- org.ow2.asm:asm-analysis:jar:9.2:test
[INFO]    |  |  |  \- org.ow2.asm:asm-tree:jar:9.2:test
[INFO]    |  |  \- org.neo4j:neo4j-cypher-expression-evaluator:jar:4.4.13:test
[INFO]    |  |     \- org.neo4j:neo4j-ast:jar:4.4.13:test
[INFO]    |  +- org.neo4j:neo4j-lucene-index:jar:4.4.13:test
[INFO]    |  |  +- org.neo4j:neo4j-resource:jar:4.4.13:test
[INFO]    |  |  +- org.apache.lucene:lucene-analyzers-common:jar:8.11.2:test
[INFO]    |  |  +- org.apache.lucene:lucene-core:jar:8.11.2:test
[INFO]    |  |  +- org.apache.lucene:lucene-queryparser:jar:8.11.2:test
[INFO]    |  |  \- org.apache.lucene:lucene-backward-codecs:jar:8.11.2:test
[INFO]    |  +- org.neo4j:neo4j-fulltext-index:jar:4.4.13:test
[INFO]    |  +- org.neo4j:neo4j-graph-algo:jar:4.4.13:test
[INFO]    |  |  \- org.neo4j:neo4j-cypher-runtime-util:jar:4.4.13:test
[INFO]    |  |     \- org.neo4j:neo4j-cypher-logical-plans:jar:4.4.13:test
[INFO]    |  +- org.neo4j:neo4j-data-collector:jar:4.4.13:test
[INFO]    |  |  +- org.neo4j:neo4j-cypher-planner:jar:4.4.13:test
[INFO]    |  |  |  +- org.neo4j:neo4j-cypher-ir:jar:4.4.13:test
[INFO]    |  |  |  +- org.neo4j:neo4j-cypher-javacc-parser:jar:4.4.13:test
[INFO]    |  |  |  |  \- org.neo4j:cypher-ast-factory:jar:4.4.13:test
[INFO]    |  |  |  +- org.neo4j:neo4j-cypher-ast-factory:jar:4.4.13:test
[INFO]    |  |  |  \- com.github.ben-manes.caffeine:caffeine:jar:2.9.3:test
[INFO]    |  |  \- org.neo4j:neo4j-rewriting:jar:4.4.13:test
[INFO]    |  +- org.neo4j:neo4j-cypher:jar:4.4.13:test
[INFO]    |  |  +- org.scala-lang:scala-library:jar:2.12.13:test
[INFO]    |  |  +- org.neo4j:neo4j-util:jar:4.4.13:test
[INFO]    |  |  +- org.neo4j:neo4j-cypher-config:jar:4.4.13:test
[INFO]    |  |  |  \- com.propensive:magnolia_2.12:jar:0.17.0:test
[INFO]    |  |  |     \- com.propensive:mercator_2.12:jar:0.2.1:test
[INFO]    |  |  +- org.neo4j:neo4j-cypher-planner-spi:jar:4.4.13:test
[INFO]    |  |  +- org.neo4j:neo4j-cypher-interpreted-runtime:jar:4.4.13:test
[INFO]    |  |  +- org.parboiled:parboiled-scala_2.12:jar:1.2.0:test
[INFO]    |  |  |  \- org.parboiled:parboiled-core:jar:1.2.0:test
[INFO]    |  |  \- org.apache.shiro:shiro-core:jar:1.10.0:test
[INFO]    |  |     +- org.apache.shiro:shiro-lang:jar:1.10.0:test
[INFO]    |  |     +- org.apache.shiro:shiro-cache:jar:1.10.0:test
[INFO]    |  |     +- org.apache.shiro:shiro-crypto-hash:jar:1.10.0:test
[INFO]    |  |     |  \- org.apache.shiro:shiro-crypto-core:jar:1.10.0:test
[INFO]    |  |     +- org.apache.shiro:shiro-crypto-cipher:jar:1.10.0:test
[INFO]    |  |     +- org.apache.shiro:shiro-config-core:jar:1.10.0:test
[INFO]    |  |     +- org.apache.shiro:shiro-config-ogdl:jar:1.10.0:test
[INFO]    |  |     |  \- commons-beanutils:commons-beanutils:jar:1.9.4:test
[INFO]    |  |     |     \- commons-collections:commons-collections:jar:3.2.2:test
[INFO]    |  |     \- org.apache.shiro:shiro-event:jar:1.10.0:test
[INFO]    |  +- org.neo4j:neo4j-security:jar:4.4.13:test
[INFO]    |  |  \- org.neo4j:neo4j-command-line:jar:4.4.13:test
[INFO]    |  |     \- info.picocli:picocli:jar:4.6.1:test
[INFO]    |  +- org.neo4j:neo4j-bolt:jar:4.4.13:test
[INFO]    |  |  +- io.netty:netty-codec-http:jar:4.1.100.Final:test
[INFO]    |  |  |  +- io.netty:netty-common:jar:4.1.100.Final:test
[INFO]    |  |  |  +- io.netty:netty-buffer:jar:4.1.100.Final:test
[INFO]    |  |  |  +- io.netty:netty-transport:jar:4.1.100.Final:test
[INFO]    |  |  |  \- io.netty:netty-codec:jar:4.1.100.Final:test
[INFO]    |  |  +- io.netty:netty-transport-native-epoll:jar:linux-x86_64:4.1.100.Final:test
[INFO]    |  |  |  +- io.netty:netty-transport-native-unix-common:jar:4.1.100.Final:test
[INFO]    |  |  |  \- io.netty:netty-transport-classes-epoll:jar:4.1.100.Final:test
[INFO]    |  |  \- io.netty:netty-transport-native-epoll:jar:linux-aarch_64:4.1.100.Final:test
[INFO]    |  +- org.neo4j:neo4j-consistency-check:jar:4.4.13:test
[INFO]    |  +- org.neo4j:neo4j-record-storage-engine:jar:4.4.13:test
[INFO]    |  |  \- org.neo4j:neo4j-storage-engine-util:jar:4.4.13:test
[INFO]    |  +- org.neo4j:neo4j-dbms:jar:4.4.13:test
[INFO]    |  |  +- org.jprocesses:jProcesses:jar:1.6.5:test
[INFO]    |  |  |  \- com.profesorfalken:WMI4Java:jar:1.6.3:test
[INFO]    |  |  |     \- com.profesorfalken:jPowerShell:jar:3.0:test
[INFO]    |  |  +- org.apache.commons:commons-compress:jar:1.21:test
[INFO]    |  |  +- org.apache.commons:commons-text:jar:1.10.0:test
[INFO]    |  |  \- org.neo4j.licensing-proxy:zstd-proxy:jar:4.4.13:test
[INFO]    |  |     \- com.github.luben:zstd-jni:jar:1.5.0-4:test
[INFO]    |  +- org.neo4j:neo4j-import-tool:jar:4.4.13:test
[INFO]    |  +- org.neo4j:neo4j-batch-insert:jar:4.4.13:test
[INFO]    |  +- org.neo4j.app:neo4j-server:jar:4.4.13:test
[INFO]    |  +- org.neo4j:neo4j-buffers:jar:4.4.13:test
[INFO]    |  |  \- io.netty:netty-handler:jar:4.1.100.Final:test
[INFO]    |  |     \- io.netty:netty-resolver:jar:4.1.100.Final:test
[INFO]    |  \- org.neo4j:neo4j-capabilities:jar:4.4.13:test
[INFO]    +- org.slf4j:slf4j-nop:jar:1.7.36:test
[INFO]    +- org.neo4j:test-utils:jar:4.4.13:test
[INFO]    |  +- org.neo4j:neo4j-common:jar:4.4.13:test
[INFO]    |  +- org.neo4j:neo4j-graphdb-api:jar:4.4.13:test
[INFO]    |  +- org.ow2.asm:asm:jar:9.2:test
[INFO]    |  +- org.bouncycastle:bcpkix-jdk15on:jar:1.69:test
[INFO]    |  |  \- org.bouncycastle:bcutil-jdk15on:jar:1.69:test
[INFO]    |  \- org.awaitility:awaitility:jar:4.2.0:test
[INFO]    +- org.neo4j:io-test-utils:jar:4.4.13:test
[INFO]    |  +- org.neo4j:neo4j-io:jar:4.4.13:test
[INFO]    |  |  +- org.neo4j:neo4j-unsafe:jar:4.4.13:test
[INFO]    |  |  \- org.neo4j:neo4j-concurrent:jar:4.4.13:test
[INFO]    |  \- org.neo4j:neo4j-collections:jar:4.4.13:test
[INFO]    +- commons-logging:commons-logging:jar:1.2:test
[INFO]    +- org.neo4j.app:neo4j-server:test-jar:tests:4.4.13:test
[INFO]    |  +- org.neo4j:server-api:jar:4.4.13:test
[INFO]    |  |  \- javax.ws.rs:javax.ws.rs-api:jar:2.1.1:test
[INFO]    |  +- org.neo4j:neo4j-ssl:jar:4.4.13:test
[INFO]    |  +- org.neo4j:neo4j-exceptions:jar:4.4.13:test
[INFO]    |  +- org.eclipse.jetty:jetty-server:jar:9.4.53.v20231009:test
[INFO]    |  |  +- javax.servlet:javax.servlet-api:jar:4.0.1:test
[INFO]    |  |  +- org.eclipse.jetty:jetty-http:jar:9.4.53.v20231009:test
[INFO]    |  |  |  \- org.eclipse.jetty:jetty-util:jar:9.4.53.v20231009:test
[INFO]    |  |  \- org.eclipse.jetty:jetty-io:jar:9.4.53.v20231009:test
[INFO]    |  +- org.eclipse.jetty:jetty-webapp:jar:9.4.53.v20231009:test
[INFO]    |  |  +- org.eclipse.jetty:jetty-xml:jar:9.4.53.v20231009:test
[INFO]    |  |  \- org.eclipse.jetty:jetty-servlet:jar:9.4.53.v20231009:test
[INFO]    |  |     +- org.eclipse.jetty:jetty-security:jar:9.4.53.v20231009:test
[INFO]    |  |     \- org.eclipse.jetty:jetty-util-ajax:jar:9.4.53.v20231009:test
[INFO]    |  +- org.glassfish.jersey.core:jersey-server:jar:2.35:test
[INFO]    |  |  +- org.glassfish.jersey.core:jersey-common:jar:2.35:test
[INFO]    |  |  |  \- org.glassfish.hk2:osgi-resource-locator:jar:1.0.3:test
[INFO]    |  |  +- org.glassfish.jersey.core:jersey-client:jar:2.35:test
[INFO]    |  |  +- jakarta.ws.rs:jakarta.ws.rs-api:jar:2.1.6:test
[INFO]    |  |  +- org.glassfish.hk2.external:jakarta.inject:jar:2.6.1:test
[INFO]    |  |  \- jakarta.validation:jakarta.validation-api:jar:2.0.2:test
[INFO]    |  +- org.glassfish.jersey.inject:jersey-hk2:jar:2.35:test
[INFO]    |  |  +- org.glassfish.hk2:hk2-locator:jar:2.6.1:test
[INFO]    |  |  |  +- org.glassfish.hk2:hk2-api:jar:2.6.1:test
[INFO]    |  |  |  \- org.glassfish.hk2:hk2-utils:jar:2.6.1:test
[INFO]    |  |  \- org.javassist:javassist:jar:3.25.0-GA:test
[INFO]    |  +- org.glassfish.jersey.containers:jersey-container-servlet:jar:2.35:test
[INFO]    |  |  \- org.glassfish.jersey.containers:jersey-container-servlet-core:jar:2.35:test
[INFO]    |  +- commons-io:commons-io:jar:2.11.0:test
[INFO]    |  +- com.fasterxml.jackson.core:jackson-core:jar:2.13.5:test
[INFO]    |  +- com.fasterxml.jackson.jaxrs:jackson-jaxrs-json-provider:jar:2.13.5:test
[INFO]    |  |  +- com.fasterxml.jackson.jaxrs:jackson-jaxrs-base:jar:2.13.5:test
[INFO]    |  |  \- com.fasterxml.jackson.module:jackson-module-jaxb-annotations:jar:2.13.5:test
[INFO]    |  +- com.fasterxml.jackson.core:jackson-databind:jar:2.13.5:test
[INFO]    |  |  \- com.fasterxml.jackson.core:jackson-annotations:jar:2.13.5:test
[INFO]    |  +- org.bouncycastle:bcprov-jdk15on:jar:1.69:test
[INFO]    |  +- org.glassfish.jaxb:jaxb-runtime:jar:2.3.8:test
[INFO]    |  |  +- org.glassfish.jaxb:txw2:jar:2.3.8:test
[INFO]    |  |  +- com.sun.istack:istack-commons-runtime:jar:3.0.12:test
[INFO]    |  |  \- com.sun.activation:jakarta.activation:jar:1.2.2:test
[INFO]    |  \- javax.xml.bind:jaxb-api:jar:2.3.1:test
[INFO]    |     \- javax.activation:javax.activation-api:jar:1.2.0:test
[INFO]    +- commons-codec:commons-codec:jar:1.15:test
[INFO]    \- org.neo4j:neo4j-kernel:jar:4.4.13:test
[INFO]       +- org.neo4j:neo4j-native:jar:4.4.13:test
[INFO]       |  \- net.java.dev.jna:jna:jar:5.9.0:test
[INFO]       +- org.neo4j:neo4j-storage-engine-api:jar:4.4.13:test
[INFO]       |  +- org.neo4j:neo4j-lock:jar:4.4.13:test
[INFO]       |  +- org.neo4j:neo4j-diagnostics:jar:4.4.13:test
[INFO]       |  +- org.neo4j:neo4j-token-api:jar:4.4.13:test
[INFO]       |  +- org.neo4j:neo4j-schema:jar:4.4.13:test
[INFO]       |  \- org.neo4j:neo4j-monitoring:jar:4.4.13:test
[INFO]       +- org.neo4j:neo4j-kernel-api:jar:4.4.13:test
[INFO]       +- org.neo4j:neo4j-values:jar:4.4.13:test
[INFO]       |  \- com.github.jbellis:jamm:jar:0.3.3:test
[INFO]       +- org.neo4j:neo4j-logging:jar:4.4.13:test
[INFO]       |  +- org.codehaus.jettison:jettison:jar:1.4.1:test
[INFO]       |  \- org.apache.logging.log4j:log4j-core:jar:2.17.2:test
[INFO]       +- org.neo4j:neo4j-configuration:jar:4.4.13:test
[INFO]       |  \- com.github.seancfoley:ipaddress:jar:5.3.3:test
[INFO]       +- org.neo4j:neo4j-layout:jar:4.4.13:test
[INFO]       +- org.neo4j:neo4j-index:jar:4.4.13:test
[INFO]       +- org.neo4j:neo4j-spatial-index:jar:4.4.13:test
[INFO]       +- org.neo4j:neo4j-id-generator:jar:4.4.13:test
[INFO]       +- org.neo4j:neo4j-wal:jar:4.4.13:test
[INFO]       +- org.neo4j:neo4j-import-util:jar:4.4.13:test
[INFO]       |  \- org.neo4j:neo4j-csv:jar:4.4.13:test
[INFO]       \- org.jctools:jctools-core:jar:3.3.0:test
```

