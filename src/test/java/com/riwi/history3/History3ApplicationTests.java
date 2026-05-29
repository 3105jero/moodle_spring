package com.riwi.history3;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
// Usa H2 en memoria para los tests — no interfiere con la BD de desarrollo
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
        "spring.flyway.enabled=true",
        "spring.jpa.hibernate.ddl-auto=none"
})
class History3ApplicationTests {

    @Test
    void contextLoads() {
        // Verifica que el contexto de Spring levanta sin errores
    }
}