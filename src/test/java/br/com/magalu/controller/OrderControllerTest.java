package br.com.magalu.controller;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class OrderControllerTest {

    @Test
    void testProcessEndpoint() throws IOException {
        // Carregando o arquivo de teste usando ClassLoader
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("data_3.txt");

        // Criando um arquivo temporário para ser enviado via RestAssured
        Path tempFile = Files.createTempFile("orders", ".txt");
        Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);

        given()
                .multiPart("file", tempFile.toFile()) // Enviando o arquivo como multipart/form-data
                .when()
                .post("/order")
                .then()
                .statusCode(200) // Verifica se a resposta foi 200 OK
                .body("$.size()", greaterThan(0)) // Verifica se a resposta contém pelo menos um usuário
                .body("[0].user_id", notNullValue()) // Verifica se o primeiro usuário retornado tem ID
                .body("[0].name", notNullValue()); // Verifica se o primeiro usuário retornado tem nome
    }

    @Test
    void testProcessWithEmptyFile() throws Exception {
        Path emptyFile = Files.createTempFile("empty_orders", ".txt");

        given()
                .multiPart("file", emptyFile.toFile()) // Enviando arquivo vazio
                .when()
                .post("/order")
                .then()
                .statusCode(200) // A API deve retornar 200 com uma lista vazia
                .body("$.size()", equalTo(0)); // Verifica se não há usuários retornados
    }

    @Test
    void testProcessWithInvalidDates() throws Exception {
        // Carregando o arquivo de teste usando ClassLoader
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("data_3.txt");

        // Criando um arquivo temporário para ser enviado via RestAssured
        Path tempFile = Files.createTempFile("orders", ".txt");
        Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);

        given()
                .multiPart("file", tempFile.toFile())
                .queryParam("orderId", 1)
                .queryParam("startDate", "invalid-date") // Data inválida
                .queryParam("endDate", "20211231")
                .when()
                .post("/order")
                .then()
                .statusCode(500);
    }

    @Test
    void testProcessWithoutFile() {
        given()
                .queryParam("orderId", 1)
                .queryParam("startDate", "20210101")
                .queryParam("endDate", "20211231")
                .when()
                .post("/order")
                .then()
                .statusCode(415); // Verifica se o código de status é 415
    }



}