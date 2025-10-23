package br.unitins.tp1.resource;

import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
public class EstoqueResourceTest {

    private static final String TOKEN_FIXO = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ1bml0aW5zLWp3dCIsInN1YiI6ImpvYW8iLCJncm91cHMiOlsiQWRtIl0sImV4cCI6MTc0OTc1MzEzMiwiaWF0IjoxNzQ5NjY2NzMyLCJqdGkiOiIzYmVmYTMyZS1kNDYxLTQ3NjItOThmNi1jZjIyY2M2MWZjYTQifQ.jpQfaHY9qzJ37ypaSBXv9xW3FWbEK_lj4gM20fnZCrGebsTXAGL6tefem488Eo2inK7OPPPVVK6-7Jv9uUUIqWeuJVW2C31NVDFCX9yO5qfd6bY4kNNiQD4qkojQ0b_nC_vJuayNe0pE1Z8B8JfTN4gDu53R2SqsSjilTM_0mnINc1u3UI_JeXicilnw9mmxwmnNzZC14EAGCFvwncjvXwA-4JEcbrOYhwERDakeBbJXKfGN7teYtbiUwB0-6qrqcj8C4Zcu_mzfStq5Yv4VWibKRW93oEONAasVlvc1DnQcDbFW--bCZzKIRitKy6dVPYCT2hpSQFSf47wh-YK4DQ";

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
