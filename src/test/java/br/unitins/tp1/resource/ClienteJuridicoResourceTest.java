package br.unitins.tp1.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
public class ClienteJuridicoResourceTest {

    private static final String TOKEN_FIXO = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ1bml0aW5zLWp3dCIsInN1YiI6ImpvYW8iLCJncm91cHMiOlsiQWRtIl0sImV4cCI6MTc0OTc1MzEzMiwiaWF0IjoxNzQ5NjY2NzMyLCJqdGkiOiIzYmVmYTMyZS1kNDYxLTQ3NjItOThmNi1jZjIyY2M2MWZjYTQifQ.jpQfaHY9qzJ37ypaSBXv9xW3FWbEK_lj4gM20fnZCrGebsTXAGL6tefem488Eo2inK7OPPPVVK6-7Jv9uUUIqWeuJVW2C31NVDFCX9yO5qfd6bY4kNNiQD4qkojQ0b_nC_vJuayNe0pE1Z8B8JfTN4gDu53R2SqsSjilTM_0mnINc1u3UI_JeXicilnw9mmxwmnNzZC14EAGCFvwncjvXwA-4JEcbrOYhwERDakeBbJXKfGN7teYtbiUwB0-6qrqcj8C4Zcu_mzfStq5Yv4VWibKRW93oEONAasVlvc1DnQcDbFW--bCZzKIRitKy6dVPYCT2hpSQFSf47wh-YK4DQ";

    @Test
    public void testSalvarClienteJuridico() {
        String json = """
            {
                "nome": "Empresa Teste",
                "sobreNome": "LTDA",
                "idade": 5,
                "email": "contato@empresa.com",
                "cnpj": "12345678000190"
            }
        """;

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when()
            .post("/clientes/juridicos")
        .then()
            .statusCode(201)
            .body("id", is(notNullValue()))
            .body("nome", is("Empresa Teste"))
            .body("cnpj", is("12345678000190"));
    }

    @Test
    public void testUpdateClienteJuridico() {
        String jsonCreate = """
            {
                "nome": "Empresa Original",
                "sobreNome": "ME",
                "idade": 10,
                "email": "original@empresa.com",
                "cnpj": "00111222000133"
            }
        """;

        int id = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(jsonCreate)
        .when()
            .post("/clientes/juridicos")
        .then()
            .statusCode(201)
            .extract().path("id");

        String jsonUpdate = """
            {
                "nome": "Empresa Atualizada",
                "sobreNome": "LTDA",
                "idade": 12,
                "email": "atualizada@empresa.com",
                "cnpj": "00111222000133"
            }
        """;

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(jsonUpdate)
        .when()
            .put("/clientes/juridicos/" + id)
        .then()
            .statusCode(204);

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .get("/clientes/juridicos/" + id)
        .then()
            .statusCode(200)
            .body("nome", is("Empresa Atualizada"))
            .body("idade.toString()", is("12"));
    }

    @Test
    public void testBuscarPorId() {
        String json = """
            {
                "nome": "Empresa Alpha",
                "sobreNome": "EIRELI",
                "idade": 3,
                "email": "alpha@empresa.com",
                "cnpj": "33333333000133"
            }
        """;

        int id = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when()
            .post("/clientes/juridicos")
        .then()
            .statusCode(201)
            .extract().path("id");

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .get("/clientes/juridicos/" + id)
        .then()
            .statusCode(200)
            .body("nome", is("Empresa Alpha"))
            .body("cnpj", is("33333333000133"));
    }

    @Test
    public void testListarTodos() {
        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .get("/clientes/juridicos")
        .then()
            .statusCode(200);
    }

    @Test
    public void testDeletarClienteJuridico() {
        String json = """
            {
                "nome": "Empresa Delete",
                "sobreNome": "LTDA",
                "idade": 4,
                "email": "delete@empresa.com",
                "cnpj": "44444444000144"
            }
        """;

        int id = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when()
            .post("/clientes/juridicos")
        .then()
            .statusCode(201)
            .extract().path("id");

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .delete("/clientes/juridicos/" + id)
        .then()
            .statusCode(204);

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when()
            .get("/clientes/juridicos/" + id)
        .then()
            .statusCode(404);
    }

    @Test
    public void testCreateClienteJuridicoInvalid() {
        String json = """
            {
                "nome": "",
                "sobreNome": "LTDA",
                "idade": 5,
                "email": "invalido@empresa.com",
                "cnpj": ""
            }
        """;

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when()
            .post("/clientes/juridicos")
        .then()
            .statusCode(400);
    }
}
