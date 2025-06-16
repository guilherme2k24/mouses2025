package br.unitins.tp1.resource;

import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
public class CategoriaResourceTest {

    private static final String TOKEN_FIXO = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ1bml0aW5zLWp3dCIsInN1YiI6ImpvYW8iLCJncm91cHMiOlsiQWRtIl0sImV4cCI6MTc1MjUxNjk0NSwiaWF0IjoxNzQ5OTI0OTQ1LCJqdGkiOiJiODRkOGE2MC1iNDIwLTRkYTEtYTczNi1kZDFjZjFmOWQwNjgifQ.rxZ749FR4hKKxK58ofsxYBjKWxlGHbhu8yfHX_O6bpKPs75_HC-o_Cah21ZF6Uc_YppeMNvV91j4E0PwuPV02bb_jxnEupiOvqH8sH4VqJI8lTXnhmq-zuRbDqe3Y8NF6gyM7L2GL-QwSdHuwbD8pGASvfLE8OBUH18Bs-QYMwDDJQfohE6ucC9PDxpTlm6SfUMXfxlV_ssbZ7U_cCzqL9niZESxZ4fzO_kvBXLSMryRy6Gaih1qhsQfwYTGC8GvzMoWenyLBbWqbwJrvm0a2gpUMWQBfvwIZ2HrsAuGI1JTbuTHpy3cJXcuizoeG4DADgQRuVM7UXfEP0NItyeMLg";
 
    @Test
    public void testGetAllCategorias() {
        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .get("/categorias")
        .then()
            .statusCode(200);
    }

    @Test
    public void testUpdateCategoria() {
        String jsonCreate = """
            {
                "nome": "Periféricos"
            }
        """;

        int id = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(jsonCreate)
        .when()
            .post("/categorias")
        .then()
            .statusCode(201)
            .extract().path("id");

        String jsonUpdate = """
            {
                "nome": "Hardware"
            }
        """;

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(jsonUpdate)
        .when()
            .put("/categorias/" + id)
        .then()
            .statusCode(204);

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .get("/categorias/" + id)
        .then()
            .statusCode(200)
            .body("nome", is("Hardware"));
    }

    @Test
    public void testCreateCategoria() {
        String json = """
            {
                "nome": "Áudio"
            }
        """;

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when()
            .post("/categorias")
        .then()
            .statusCode(201)
            .body("nome", is("Áudio"));
    }

    @Test
    public void testGetCategoriaById() {
        String json = """
            {
                "nome": "Gamer"
            }
        """;

        int id = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when()
            .post("/categorias")
        .then()
            .statusCode(201)
            .extract().path("id");

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .get("/categorias/" + id)
        .then()
            .statusCode(200)
            .body("id", is(id))
            .body("nome", is("Gamer"));
    }

    @Test
    public void testDeleteCategoria() {
        String json = """
            {
                "nome": "Descartável"
            }
        """;

        int id = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when()
            .post("/categorias")
        .then()
            .statusCode(201)
            .extract().path("id");

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .delete("/categorias/" + id)
        .then()
            .statusCode(204);
    }

    @Test
    public void testCreateCategoriaInvalid() {
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
            .post("/categorias")
        .then()
            .statusCode(400);
    }
}
