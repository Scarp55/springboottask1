package ru.pivovarov.springboottask1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootTask1ApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Container
    private static GenericContainer<?> devApp = new GenericContainer<>("devapp:latest")
            .withExposedPorts(8080);

    @Container
    private static GenericContainer<?> prodApp = new GenericContainer<>("prodapp:latest")
            .withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devApp.start();
        prodApp.start();

    }

    @Test
    public void contextLoadsDev() {
        ResponseEntity<String> forEntity = restTemplate
                .getForEntity("http://localhost:" + devApp.getMappedPort(8080) + "/profile", String.class);
        System.out.println(forEntity.getBody());
        Assertions.assertEquals("Current profile is dev", forEntity.getBody());
    }

    @Test
    public void contextLoadsProd() {
        ResponseEntity<String> forEntity = restTemplate
                .getForEntity("http://localhost:" + prodApp.getMappedPort(8081) + "/profile", String.class);
        System.out.println(forEntity.getBody());
        Assertions.assertEquals("Current profile is production", forEntity.getBody());
    }

}
