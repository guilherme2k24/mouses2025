package br.unitins.tp1.resource;

import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
public class PagamentoResourceTest {

    private static final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ1bml0aW5zLWp3dCIsInN1YiI6ImpvYW8iLCJncm91cHMiOlsiQWRtIl0sImV4cCI6MTc0OTc1MzEzMiwiaWF0IjoxNzQ5NjY2NzMyLCJqdGkiOiIzYmVmYTMyZS1kNDYxLTQ3NjItOThmNi1jZjIyY2M2MWZjYTQifQ.jpQfaHY9qzJ37ypaSBXv9xW3FWbEK_lj4gM20fnZCrGebsTXAGL6tefem488Eo2inK7OPPPVVK6-7Jv9uUUIqWeuJVW2C31NVDFCX9yO5qfd6bY4kNNiQD4qkojQ0b_nC_vJuayNe0pE1Z8B8JfTN4gDu53R2SqsSjilTM_0mnINc1u3UI_JeXicilnw9mmxwmnNzZC14EAGCFvwncjvXwA-4JEcbrOYhwERDakeBbJXKfGN7teYtbiUwB0-6qrqcj8C4Zcu_mzfStq5Yv4VWibKRW93oEONAasVlvc1DnQcDbFW--bCZzKIRitKy6dVPYCT2hpSQFSf47wh-YK4DQ";

    @Test
    public void testCreatePagamento() {
        String pagamentoJson = """
        {
            "formaPagamento": "PIX",
            "valor": 299.99,
            "data": "2025-06-05T10:00:00",
            "pedidoId": 4
        }
        """;

        given()
            .header("Authorization", "Bearer " + TOKEN)
            .contentType(ContentType.JSON)
            .body(pagamentoJson)
        .when()
            .post("/pagamentos")
        .then()
            .statusCode(201)
            .body("formaPagamento", is("PIX"));
    }

    @Test
    public void testGetAllPagamentos() {
        given()
            .header("Authorization", "Bearer " + TOKEN)
        .when()
            .get("/pagamentos")
        .then()
            .statusCode(200);
    }

    @Test
    public void testGetPagamentoById() {
        String pagamentoJson = """
        {
            "formaPagamento": "BOLETO",
            "valor": 180.50,
            "data": "2023-11-21T10:00:00",
            "pedidoId": 5
        }
        """;

        int idPagamento = given()
            .header("Authorization", "Bearer " + TOKEN)
            .contentType(ContentType.JSON)
            .body(pagamentoJson)
        .when()
            .post("/pagamentos")
        .then()
            .statusCode(201)
            .extract().path("id");

        given()
            .header("Authorization", "Bearer " + TOKEN)
        .when()
            .get("/pagamentos/" + idPagamento)
        .then()
            .statusCode(200)
            .body("id", is(idPagamento));
    }

    @Test
    public void testUpdatePagamento() {
        String pagamentoCreate = """
        {
            "formaPagamento": "CARTAO_DEBITO",
            "valor": 150.00,
            "data": "2023-11-22T14:00:00",
            "pedidoId": 6
        }
        """;

        int idPagamento = given()
            .header("Authorization", "Bearer " + TOKEN)
            .contentType(ContentType.JSON)
            .body(pagamentoCreate)
        .when()
            .post("/pagamentos")
        .then()
            .statusCode(201)
            .extract().path("id");

        String pagamentoUpdate = """
        {
            "formaPagamento": "CARTAO_DEBITO",
            "valor": 150.00,
            "data": "2023-11-22T14:30:00",
            "pedidoId": 6
        }
        """;

        given()
            .header("Authorization", "Bearer " + TOKEN)
            .contentType(ContentType.JSON)
            .body(pagamentoUpdate)
        .when()
            .put("/pagamentos/" + idPagamento)
        .then()
            .statusCode(204);
    }

    @Test
    public void testDeletePagamento() {
        // Criação do pedido válido
        String pedidoJson = """
        {
            "cliente": { "id": 4 },
            "dataPedido": "2025-06-11T10:00:00",
            "valorTotal": 300.00,
            "enderecoEntrega": "Rua Teste, 123",
            "status": "AGUARDANDO_PAGAMENTO"
        }
        """;

        int idPedido = given()
            .header("Authorization", "Bearer " + TOKEN)
            .contentType(ContentType.JSON)
            .body(pedidoJson)
        .when()
            .post("/pedidos")
        .then()
            .statusCode(201)
            .extract().path("id");

        // Criação do pagamento associado ao novo pedido
        String pagamentoJson = String.format("""
        {
            "formaPagamento": "PIX",
            "valor": 300.00,
            "data": "2025-06-11T10:30:00",
            "pedidoId": %d
        }
        """, idPedido);

        int idPagamento = given()
            .header("Authorization", "Bearer " + TOKEN)
            .contentType(ContentType.JSON)
            .body(pagamentoJson)
        .when()
            .post("/pagamentos")
        .then()
            .statusCode(201)
            .extract().path("id");

        // Exclusão do pagamento
        given()
            .header("Authorization", "Bearer " + TOKEN)
        .when()
            .delete("/pagamentos/" + idPagamento)
        .then()
            .statusCode(204);
    }



    @Test
    public void testCreatePagamentoInvalid() {
        String pagamentoJson = """
        {
            "formaPagamento": "PIX",
            "valor": 300.0,
            "data": "2025-06-05T10:00:00",
            "pedidoId": 9999
        }
        """;

        given()
            .header("Authorization", "Bearer " + TOKEN)
            .contentType(ContentType.JSON)
            .body(pagamentoJson)
        .when()
            .post("/pagamentos")
        .then()
            .statusCode(404);
    }
}
