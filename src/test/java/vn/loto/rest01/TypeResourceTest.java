package vn.loto.rest01;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import vn.loto.rest01.metier.Type;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TypeResourceTest {
    private static int id;

    @Test
    @Order(1)
    void getAllTypes() {
        Type[] types = given().get("api/type")
                                .then()
                                .contentType(MediaType.APPLICATION_JSON)
                                .statusCode(HttpServletResponse.SC_OK)
                                .extract().as(Type[].class);

        assertTrue(Arrays.stream(types).anyMatch(type -> type.getId()==2 && type.getLibelle().equals("Ale")));
    }

    @Test
    @Order(2)
    void getTypeById() {
        given().get("api/type/2")
                .then()
                .contentType(MediaType.APPLICATION_JSON)
                .statusCode(HttpServletResponse.SC_OK)
                .extract().as(Type.class).equals(new Type(2, "Ale"));
    }

    @Test
    @Order(3)
    void insert() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}