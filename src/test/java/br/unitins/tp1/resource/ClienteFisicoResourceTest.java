package br.unitins.tp1.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
public class ClienteFisicoResourceTest {

    private static final String TOKEN_FIXO = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ1bml0aW5zLWp3dCIsInN1YiI6ImpvYW8iLCJncm91cHMiOlsiQWRtIl0sImV4cCI6MTc1MjUxNjk0NSwiaWF0IjoxNzQ5OTI0OTQ1LCJqdGkiOiJiODRkOGE2MC1iNDIwLTRkYTEtYTczNi1kZDFjZjFmOWQwNjgifQ.rxZ749FR4hKKxK58ofsxYBjKWxlGHbhu8yfHX_O6bpKPs75_HC-o_Cah21ZF6Uc_YppeMNvV91j4E0PwuPV02bb_jxnEupiOvqH8sH4VqJI8lTXnhmq-zuRbDqe3Y8NF6gyM7L2GL-QwSdHuwbD8pGASvfLE8OBUH18Bs-QYMwDDJQfohE6ucC9PDxpTlm6SfUMXfxlV_ssbZ7U_cCzqL9niZESxZ4fzO_kvBXLSMryRy6Gaih1qhsQfwYTGC8GvzMoWenyLBbWqbwJrvm0a2gpUMWQBfvwIZ2HrsAuGI1JTbuTHpy3cJXcuizoeG4DADgQRuVM7UXfEP0NItyeMLg";

    @Test
    public void testSalvarClienteFisico() {
        String json = """
            {
                "nome": "João",
                "sobreNome": "Silva",
                "idade": 30,
                "email": "joao.silva@example.com",
                "cpf": "12345678901"
            }
        """;

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when()
            .post("/clientes/fisicos")
        .then()
            .statusCode(201)
            .body("id", is(notNullValue()))
            .body("nome", is("João"))
            .body("cpf", is("12345678901"));
    }

    @Test
    public void testUpdateClienteFisico() {
        String jsonCreate = """
            {
                "nome": "Carlos",
                "sobreNome": "Pereira",
                "idade": 40,
                "email": "carlos.pereira@example.com",
                "cpf": "98765432100"
            }
        """;

        int id = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(jsonCreate)
        .when()
            .post("/clientes/fisicos")
        .then()
            .statusCode(201)
            .extract().path("id");

        String jsonUpdate = """
            {
                "nome": "Carlos Alberto",
                "sobreNome": "Pereira",
                "idade": 42,
                "email": "carlos.alberto.pereira@example.com",
                "cpf": "98765432100"
            }
        """;

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(jsonUpdate)
        .when()
            .put("/clientes/fisicos/" + id)
        .then()
            .statusCode(204);

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .get("/clientes/fisicos/" + id)
        .then()
            .statusCode(200)
            .body("nome", is("Carlos Alberto"))
            .body("idade.toString()", is("42"));
    }

    @Test
    public void testBuscarPorId() {
        String json = """
            {
                "nome": "Ana",
                "sobreNome": "Souza",
                "idade": 25,
                "email": "ana.souza@example.com",
                "cpf": "33344455566"
            }
        """;

        int id = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when()
            .post("/clientes/fisicos")
        .then()
            .statusCode(201)
            .extract().path("id");

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .get("/clientes/fisicos/" + id)
        .then()
            .statusCode(200)
            .body("nome", is("Ana"))
            .body("cpf", is("33344455566"));
    }

    @Test
    public void testListarTodos() {
        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .get("/clientes/fisicos")
        .then()
            .statusCode(200);
    }

    @Test
    public void testDeletarClienteFisico() {
        String json = """
            {
                "nome": "Pedro",
                "sobreNome": "Oliveira",
                "idade": 35,
                "email": "pedro.oliveira@example.com",
                "cpf": "55667788990"
            }
        """;

        int id = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when()
            .post("/clientes/fisicos")
        .then()
            .statusCode(201)
            .extract().path("id");

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .delete("/clientes/fisicos/" + id)
        .then()
            .statusCode(204);

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .get("/clientes/fisicos/" + id)
        .then()
            .statusCode(404);
    }

    @Test
    public void testCreateClienteFisicoInvalid() {
        String json = """
            {
                "nome": "",
                "sobreNome": "Souza",
                "idade": 28,
                "email": "invalido@example.com",
                "cpf": "12345678900"
            }
        """;

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when()
            .post("/clientes/fisicos")
        .then()
            .statusCode(400);
    }
}
