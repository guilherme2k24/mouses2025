package br.unitins.tp1.resource;

import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
public class MousesResourceTest {

    private static final String TOKEN_FIXO = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ1bml0aW5zLWp3dCIsInN1YiI6ImpvYW8iLCJncm91cHMiOlsiQWRtIl0sImV4cCI6MTc1MjUxNjk0NSwiaWF0IjoxNzQ5OTI0OTQ1LCJqdGkiOiJiODRkOGE2MC1iNDIwLTRkYTEtYTczNi1kZDFjZjFmOWQwNjgifQ.rxZ749FR4hKKxK58ofsxYBjKWxlGHbhu8yfHX_O6bpKPs75_HC-o_Cah21ZF6Uc_YppeMNvV91j4E0PwuPV02bb_jxnEupiOvqH8sH4VqJI8lTXnhmq-zuRbDqe3Y8NF6gyM7L2GL-QwSdHuwbD8pGASvfLE8OBUH18Bs-QYMwDDJQfohE6ucC9PDxpTlm6SfUMXfxlV_ssbZ7U_cCzqL9niZESxZ4fzO_kvBXLSMryRy6Gaih1qhsQfwYTGC8GvzMoWenyLBbWqbwJrvm0a2gpUMWQBfvwIZ2HrsAuGI1JTbuTHpy3cJXcuizoeG4DADgQRuVM7UXfEP0NItyeMLg";

    @Test
    public void testGetAllMouses() {
        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .get("/mouses")
        .then()
            .statusCode(200);
    }

    @Test
    public void testGetMouseById() {
        String json = """
        {
            "modelo": "Razer Viper Mini",
            "dpi": 8500,
            "sensor": "Óptico",
            "pollingRate": 1000,
            "botoes": 6,
            "formato": "Ambidestro",
            "cor": "Preto",
            "peso": 61,
            "conexao": "SEM_FIO"
        }
        """;  

        int id = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when()
            .post("/mouses")
        .then()
            .statusCode(201)
            .extract().path("id");

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .get("/mouses/" + id)
        .then()
            .statusCode(200)
            .body("id", is(id));
    }

    @Test
    public void testCreateMouse() {
        String json = """
        {
            "modelo": "Logitech G502",
            "dpi": 25600,
            "sensor": "HERO_25K",
            "pollingRate": 1000,
            "botoes": 11,
            "formato": "Ergonomico",
            "cor": "Preto",
            "peso": 121,
            "conexao": "COM_FIO"
        }
        """;  

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when()
            .post("/mouses")
        .then()
            .statusCode(201);
    }

    @Test
    public void testUpdateMouse() {
        String jsonCreate = """
        {
            "modelo": "Logitech G502",
            "dpi": 25600,
            "sensor": "HERO_25K",
            "pollingRate": 1000,
            "botoes": 11,
            "formato": "Ergonomico",
            "cor": "Preto",
            "peso": 121,
            "conexao": "COM_FIO"
        }
        """;

        int id = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(jsonCreate)
        .when()
            .post("/mouses")
        .then()
            .statusCode(201)
            .extract().path("id");

        String jsonUpdate = """
        {
            "modelo": "Logitech G502 Hero",
            "dpi": 25600,
            "sensor": "HERO_25K",
            "pollingRate": 1000,
            "botoes": 11,
            "formato": "Ergonomico",
            "cor": "Preto",
            "peso": 121,
            "conexao": "COM_FIO"
        }
        """;

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(jsonUpdate)
        .when()
            .put("/mouses/" + id)
        .then()
            .statusCode(204);

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .get("/mouses/" + id)
        .then()
            .statusCode(200)
            .body("modelo", is("Logitech G502 Hero"));
    }

    @Test
    public void testDeleteMouse() {
        String json = """
        {
            "modelo": "Test Mouse Delete",
            "dpi": 10000,
            "sensor": "Optico",
            "pollingRate": 1000,
            "botoes": 5,
            "formato": "Ergonomico",
            "cor": "Branco",
            "peso": 90,
            "conexao": "COM_FIO"
        }
        """;

        int id = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when()
            .post("/mouses")
        .then()
            .statusCode(201)
            .extract().path("id");

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .delete("/mouses/" + id)
        .then()
            .statusCode(204);

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .get("/mouses/" + id)
        .then()
            .statusCode(404);
    }

    @Test
    public void testCreateMouseInvalid() {
        String json = """
        {
            "modelo": "",
            "dpi": 8000,
            "sensor": "Mercury",
            "pollingRate": 1000,
            "botoes": 6,
            "formato": "Ambidestro",
            "cor": "Preto",
            "peso": 85.0,
            "conexao": "FIO"
        }
        """;  

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when()
            .post("/mouses")
        .then()
            .statusCode(400);
    }
}
