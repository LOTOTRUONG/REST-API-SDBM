package vn.loto.rest01;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import vn.loto.rest01.metier.Couleur;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class CouleurResourceTest {
    private static final String BASE_URL = "http://localhost:8080";
    private static int id;

    @Test
    @Order(1)
    void getAllColors() {
        Couleur [] couleurs = given().get("api/color")
                                    .then()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .statusCode(HttpServletResponse.SC_OK)
                                    .extract().as(Couleur[].class);
        assertTrue(Arrays.stream(couleurs).anyMatch(couleur -> couleur.getId() == 1 && couleur.getNomCouleur().equals("Blonde")));
    }

    @Test
    @Order(2)
    void getColorById() {
        given().get("api/color/1")
                .then()
                .contentType(MediaType.APPLICATION_JSON)
                .statusCode(HttpServletResponse.SC_OK)
                .extract().as(Couleur.class).equals(new Couleur(1, "Blonde"));
    }

    @Test
    @Order(3)
    void insert() {
        RestAssured.defaultParser = Parser.JSON;
        Couleur couleur = new Couleur( 0,"Test3");
        Couleur newCouleur = given().contentType(MediaType.APPLICATION_JSON)
                .body(couleur) // Sending the couleur object in the request body
                .when()
                .post("/api/color")
                .then()
                .statusCode(HttpServletResponse.SC_CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .extract().response().body().as(Couleur.class);

        if (newCouleur!=null && newCouleur.getId()!=0)
            couleur.setId(newCouleur.getId());
        assert id != 0; // Check if the returned ID is not 0
    }


    @Test
    @Order(4)
    void update() {
        // Prepare new data for the Couleur object
        Couleur updatedCouleur = new Couleur(5, "Noir");
        // Send a request to update the Couleur resource
        Response response = given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(updatedCouleur)
                .when()
                .put("/api/color/{id}", 5); // Assuming you have an endpoint like this for updating a specific couleur
        // Verify that the update was successful
        response.then()
                .statusCode(HttpServletResponse.SC_OK);
        // Optionally, retrieve the updated Couleur object from the server and assert that it matches the expected data
        Couleur updatedCouleurResponse = response.as(Couleur.class);
        assertEquals(updatedCouleur, updatedCouleurResponse);
    }


    @Test
    @Order(5)
    void delete() {
        Response response = given()
                .when()
                .delete("/api/color/{id}", id); // Assuming you have an endpoint like this for deleting a specific couleur by ID

        // Verify that the delete operation was successful
        response.then()
                .statusCode(HttpServletResponse.SC_OK);
    }

    @Test
    @Order(6)
    void deleteFalse(){

    }
}