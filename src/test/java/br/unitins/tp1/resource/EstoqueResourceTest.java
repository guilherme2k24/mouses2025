package br.unitins.tp1.resource;

import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
public class EstoqueResourceTest {

    private static final String TOKEN_FIXO = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ1bml0aW5zLWp3dCIsInN1YiI6ImpvYW8iLCJncm91cHMiOlsiQWRtIl0sImV4cCI6MTc1MjUxNjk0NSwiaWF0IjoxNzQ5OTI0OTQ1LCJqdGkiOiJiODRkOGE2MC1iNDIwLTRkYTEtYTczNi1kZDFjZjFmOWQwNjgifQ.rxZ749FR4hKKxK58ofsxYBjKWxlGHbhu8yfHX_O6bpKPs75_HC-o_Cah21ZF6Uc_YppeMNvV91j4E0PwuPV02bb_jxnEupiOvqH8sH4VqJI8lTXnhmq-zuRbDqe3Y8NF6gyM7L2GL-QwSdHuwbD8pGASvfLE8OBUH18Bs-QYMwDDJQfohE6ucC9PDxpTlm6SfUMXfxlV_ssbZ7U_cCzqL9niZESxZ4fzO_kvBXLSMryRy6Gaih1qhsQfwYTGC8GvzMoWenyLBbWqbwJrvm0a2gpUMWQBfvwIZ2HrsAuGI1JTbuTHpy3cJXcuizoeG4DADgQRuVM7UXfEP0NItyeMLg";

    private int criarMouseParaEstoque() {
        String mouseJson = """
        {
            "modelo": "Mouse Teste Estoque",
            "dpi": 16000,
            "sensor": "Óptico",
            "pollingRate": 1000,
            "botoes": 7,
            "formato": "Ergonomico",
            "cor": "Preto",
            "peso": 95,
            "conexao": "COM_FIO"
        }
        """;

        return given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(mouseJson)
            .when().post("/mouses")
            .then()
                .statusCode(201)
                .extract().path("id");
    }

    @Test
    public void testCreateEstoque() {
        int mouseId = criarMouseParaEstoque();

        String estoqueJson = """
        {
            "quantidade": 50,
            "localizacao": "A1",
            "mouseId": %d
        }
        """.formatted(mouseId);

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(estoqueJson)
        .when()
            .post("/estoques")
        .then()
            .statusCode(201)
            .body("quantidade", is(50))
            .body("localizacao", is("A1"))
            .body("mouses.id", is(mouseId));
    }

    @Test
    public void testGetAllEstoques() {
        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .get("/estoques")
        .then()
            .statusCode(200);
    }

    @Test
    public void testGetEstoqueById() {
        int mouseId = criarMouseParaEstoque();

        String estoqueJson = """
        {
            "quantidade": 30,
            "localizacao": "B2",
            "mouseId": %d
        }
        """.formatted(mouseId);

        int id = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(estoqueJson)
        .when()
            .post("/estoques")
        .then()
            .statusCode(201)
            .extract().path("id");

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .get("/estoques/" + id)
        .then()
            .statusCode(200)
            .body("id", is(id))
            .body("localizacao", is("B2"));
    }

    @Test
    public void testUpdateEstoque() {
        int mouseId = criarMouseParaEstoque();

        String estoqueJson = """
        {
            "quantidade": 10,
            "localizacao": "C3",
            "mouseId": %d
        }
        """.formatted(mouseId);

        int id = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(estoqueJson)
        .when()
            .post("/estoques")
        .then()
            .statusCode(201)
            .extract().path("id");

        String updateJson = """
        {
            "quantidade": 25,
            "localizacao": "D4",
            "mouseId": %d
        }
        """.formatted(mouseId);

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(updateJson)
        .when()
            .put("/estoques/" + id)
        .then()
            .statusCode(204);

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .get("/estoques/" + id)
        .then()
            .statusCode(200)
            .body("quantidade", is(25))
            .body("localizacao", is("D4"));
    }

    @Test
    public void testDeleteEstoque() {
        int mouseId = criarMouseParaEstoque();

        String estoqueJson = """
        {
            "quantidade": 99,
            "localizacao": "E5",
            "mouseId": %d
        }
        """.formatted(mouseId);

        int id = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(estoqueJson)
        .when()
            .post("/estoques")
        .then()
            .statusCode(201)
            .extract().path("id");

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .delete("/estoques/" + id)
        .then()
            .statusCode(204);
    }

    @Test
    public void testCreateEstoqueInvalid() {
        String invalidJson = """
        {
            "quantidade": null,
            "localizacao": "",
            "mouseId": null
        }
        """;

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(invalidJson)
        .when()
            .post("/estoques")
        .then()
            .statusCode(400);
    }
}
