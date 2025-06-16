package br.unitins.tp1.resource;

import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
public class PagamentoResourceTest {

    private static final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ1bml0aW5zLWp3dCIsInN1YiI6ImpvYW8iLCJncm91cHMiOlsiQWRtIl0sImV4cCI6MTc1MjUxNjk0NSwiaWF0IjoxNzQ5OTI0OTQ1LCJqdGkiOiJiODRkOGE2MC1iNDIwLTRkYTEtYTczNi1kZDFjZjFmOWQwNjgifQ.rxZ749FR4hKKxK58ofsxYBjKWxlGHbhu8yfHX_O6bpKPs75_HC-o_Cah21ZF6Uc_YppeMNvV91j4E0PwuPV02bb_jxnEupiOvqH8sH4VqJI8lTXnhmq-zuRbDqe3Y8NF6gyM7L2GL-QwSdHuwbD8pGASvfLE8OBUH18Bs-QYMwDDJQfohE6ucC9PDxpTlm6SfUMXfxlV_ssbZ7U_cCzqL9niZESxZ4fzO_kvBXLSMryRy6Gaih1qhsQfwYTGC8GvzMoWenyLBbWqbwJrvm0a2gpUMWQBfvwIZ2HrsAuGI1JTbuTHpy3cJXcuizoeG4DADgQRuVM7UXfEP0NItyeMLg";

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
