package br.unitins.tp1.resource;

import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
public class MarcaResourceTest {

    private static final String TOKEN_FIXO = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ1bml0aW5zLWp3dCIsInN1YiI6ImpvYW8iLCJncm91cHMiOlsiQWRtIl0sImV4cCI6MTc0OTc1MzEzMiwiaWF0IjoxNzQ5NjY2NzMyLCJqdGkiOiIzYmVmYTMyZS1kNDYxLTQ3NjItOThmNi1jZjIyY2M2MWZjYTQifQ.jpQfaHY9qzJ37ypaSBXv9xW3FWbEK_lj4gM20fnZCrGebsTXAGL6tefem488Eo2inK7OPPPVVK6-7Jv9uUUIqWeuJVW2C31NVDFCX9yO5qfd6bY4kNNiQD4qkojQ0b_nC_vJuayNe0pE1Z8B8JfTN4gDu53R2SqsSjilTM_0mnINc1u3UI_JeXicilnw9mmxwmnNzZC14EAGCFvwncjvXwA-4JEcbrOYhwERDakeBbJXKfGN7teYtbiUwB0-6qrqcj8C4Zcu_mzfStq5Yv4VWibKRW93oEONAasVlvc1DnQcDbFW--bCZzKIRitKy6dVPYCT2hpSQFSf47wh-YK4DQ";

    @Test
    public void testGetAllMarcas() {
        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .get("/marcas")
        .then()
            .statusCode(200);
    }

    @Test
    public void testUpdateMarca() {
        String jsonCreate = """
            {
                "nome": "HyperX"
            }
        """;

        int id = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(jsonCreate)
        .when()
            .post("/marcas")
        .then()
            .statusCode(201)
            .extract().path("id");

        String jsonUpdate = """
            {
                "nome": "Corsair"
            }
        """;

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(jsonUpdate)
        .when()
            .put("/marcas/" + id)
        .then()
            .statusCode(204);

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .get("/marcas/" + id)
        .then()
            .statusCode(200)
            .body("nome", is("Corsair"));
    }

    @Test
    public void testCreateMarca() {
        String json = """
            {
                "nome": "Logitech"
            }
        """;

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when()
            .post("/marcas")
        .then()
            .statusCode(201)
            .body("nome", is("Logitech"));
    }

    @Test
    public void testGetMarcaById() {
        String json = """
            {
                "nome": "Razer"
            }
        """;

        int id = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when()
            .post("/marcas")
        .then()
            .statusCode(201)
            .extract().path("id");

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .get("/marcas/" + id)
        .then()
            .statusCode(200)
            .body("id", is(id))
            .body("nome", is("Razer"));
    }

    @Test
    public void testDeleteMarca() {
        String json = """
            {
                "nome": "HyperX"
            }
        """;

        int id = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when()
            .post("/marcas")
        .then()
            .statusCode(201)
            .extract().path("id");

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .delete("/marcas/" + id)
        .then()
            .statusCode(204);
    }

    @Test
    public void testCreateMarcaInvalid() {
        String json = """
            {
                "nome": ""
            }
        """;

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when()
            .post("/marcas")
        .then()
            .statusCode(400);
    }
}
