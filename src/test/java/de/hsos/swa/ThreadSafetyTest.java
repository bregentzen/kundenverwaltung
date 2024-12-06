package de.hsos.swa;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hsos.swa.boundary.dto.AdresseWebDTO;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ThreadSafetyTest {

    static {
        RestAssured.baseURI = "http://localhost:8080"; // Setze die Basis-URI deiner API
    }

    @Test
    @Execution(ExecutionMode.CONCURRENT)
    public void testParallelCrudOperations() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        ObjectMapper objectMapper = new ObjectMapper();

        // Create a customer
        Response createResponse = given()
                .contentType("application/json")
                .body("{ \"name\": \"John Doe\" }")
                .post("/kunden");
        assertEquals(201, createResponse.statusCode());
        assertNotNull(createResponse.getBody().asString(), "Response body is null");

        long kundenId = createResponse.jsonPath().getLong("id");

        // Create an address for the customer
        Response createAddressResponse = given()
                .contentType("application/json")
                .body("{ \"strasse\": \"Musterstrasse\", \"hausnummer\": \"1\", \"plz\": \"12345\", \"ort\": \"Musterstadt\" }")
                .post("/kunden/" + kundenId + "/adresse");
        assertEquals(201, createAddressResponse.statusCode());

        Future<?> future1 = executor.submit(() -> {
            // Update address
            Response updateAddressResponse = given()
                    .contentType("application/json")
                    .body("{ \"strasse\": \"Thread Straße 1\", \"hausnummer\": \"2\", \"plz\": \"54321\", \"ort\": \"Neustadt\" }")
                    .put("/kunden/" + kundenId + "/adresse");
            assertEquals(200, updateAddressResponse.statusCode());

            // Read address after update
            Response readAfterUpdateAddressResponse = given()
                    .get("/kunden/" + kundenId + "/adresse");
            AdresseWebDTO address1 = null;
            try {
                address1 = objectMapper.readValue(readAfterUpdateAddressResponse.getBody().asString(), AdresseWebDTO.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            AdresseWebDTO expectedAddress1 = new AdresseWebDTO("Thread Straße 1", "2", "54321", "Neustadt");
            assertAdresseEquals(expectedAddress1, address1);
        });

        Future<?> future2 = executor.submit(() -> {
            // Update address
            Response updateAddressResponse = given()
                    .contentType("application/json")
                    .body("{ \"strasse\": \"Thread Strasse 2\", \"hausnummer\": \"3\", \"plz\": \"54322\", \"ort\": \"Neustadt\" }")
                    .put("/kunden/" + kundenId + "/adresse");
            assertEquals(200, updateAddressResponse.statusCode());

            // Read address after update
            Response readAfterUpdateAddressResponse = given()
                    .get("/kunden/" + kundenId + "/adresse");
            AdresseWebDTO address2 = null;
            try {
                address2 = objectMapper.readValue(readAfterUpdateAddressResponse.getBody().asString(), AdresseWebDTO.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            AdresseWebDTO expectedAddress2 = new AdresseWebDTO("Thread Strasse 2", "3", "54322", "Neustadt");
            assertAdresseEquals(expectedAddress2, address2);
        });

        future1.get();
        future2.get();

        executor.shutdown();
    }

    private void assertAdresseEquals(AdresseWebDTO expected, AdresseWebDTO actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError(
                    "\nErwartete Adresse: " +
                    "\n Straße: " + expected.getStrasse() +
                    "\n Hausnummer: " + expected.getHausnummer() +
                    "\n PLZ: " + expected.getPlz() +
                    "\n Ort: " + expected.getOrt() +
                    "\nAktuelle Adresse: " +
                    "\n Straße: " + actual.getStrasse() +
                    "\n Hausnummer: " + actual.getHausnummer() +
                    "\n PLZ: " + actual.getPlz() +
                    "\n Ort: " + actual.getOrt()
            );
        }
    }
}