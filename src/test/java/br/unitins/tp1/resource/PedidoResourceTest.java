package br.unitins.tp1.resource;

import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
public class PedidoResourceTest {

    private static final String TOKEN_FIXO = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ1bml0aW5zLWp3dCIsInN1YiI6ImpvYW8iLCJncm91cHMiOlsiQWRtIl0sImV4cCI6MTc1MjUxNjk0NSwiaWF0IjoxNzQ5OTI0OTQ1LCJqdGkiOiJiODRkOGE2MC1iNDIwLTRkYTEtYTczNi1kZDFjZjFmOWQwNjgifQ.rxZ749FR4hKKxK58ofsxYBjKWxlGHbhu8yfHX_O6bpKPs75_HC-o_Cah21ZF6Uc_YppeMNvV91j4E0PwuPV02bb_jxnEupiOvqH8sH4VqJI8lTXnhmq-zuRbDqe3Y8NF6gyM7L2GL-QwSdHuwbD8pGASvfLE8OBUH18Bs-QYMwDDJQfohE6ucC9PDxpTlm6SfUMXfxlV_ssbZ7U_cCzqL9niZESxZ4fzO_kvBXLSMryRy6Gaih1qhsQfwYTGC8GvzMoWenyLBbWqbwJrvm0a2gpUMWQBfvwIZ2HrsAuGI1JTbuTHpy3cJXcuizoeG4DADgQRuVM7UXfEP0NItyeMLg";

    private int criarCliente() {
        String json = """
        {
            "nome": "João",
            "sobreNome": "Silva",
            "idade": 30,
            "email": "joao@email.com",
            "cpf": "12345678901"
        }
        """;

        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + TOKEN_FIXO)
                .body(json)
                .when().post("/clientes/fisicos") 
                .then().statusCode(201)
                .extract().path("id");
    }

    @Test
    public void testGetAllPedidos() {
        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when().get("/pedidos")
        .then().statusCode(200);
    }

    @Test
    public void testCreatePedido() {
        int idCliente = criarCliente();

        String json = String.format("""
        {
            "cliente": { "id": %d },
            "dataPedido": "2022-03-10T12:15:50",
            "valorTotal": 0.1,
            "enderecoEntrega": "rua 20",
            "status": "AGUARDANDO_PAGAMENTO"
        }
        """, idCliente);

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when().post("/pedidos")
        .then().statusCode(201);
    }

    @Test
    public void testGetPedidoById() {
        int idCliente = criarCliente();

        String json = String.format("""
        {
            "cliente": { "id": %d },
            "dataPedido": "2022-03-10T12:15:50",
            "valorTotal": 200.0,
            "enderecoEntrega": "Rua B, 456",
            "status": "AGUARDANDO_PAGAMENTO"
        }
        """, idCliente);

        int idPedido = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when().post("/pedidos")
        .then().statusCode(201)
        .extract().path("id");

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when().get("/pedidos/" + idPedido)
        .then().statusCode(200)
        .body("id", is(idPedido));
    }

    @Test
    public void testUpdatePedido() {
        int idCliente = criarCliente();

        String jsonCreate = String.format("""
        {
            "cliente": { "id": %d },
            "dataPedido": "2022-03-10T12:15:50",
            "valorTotal": 100.0,
            "enderecoEntrega": "Rua Original",
            "status": "AGUARDANDO_PAGAMENTO"
        }
        """, idCliente);

        int idPedido = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(jsonCreate)
        .when().post("/pedidos")
        .then().statusCode(201)
        .extract().path("id");

        String jsonUpdate = String.format("""
        {
            "cliente": { "id": %d },
            "dataPedido": "2022-03-10T15:30:00",
            "valorTotal": 150.0,
            "enderecoEntrega": "Rua Atualizada",
            "status": "EM_PROCESSAMENTO"
        }
        """, idCliente);

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(jsonUpdate)
        .when().put("/pedidos/" + idPedido)
        .then().statusCode(204);
    }

    @Test
    public void testDeletePedido() {
        int idCliente = criarCliente();

        String json = String.format("""
        {
            "cliente": { "id": %d },
            "dataPedido": "2022-03-10T12:15:50",
            "valorTotal": 100.0,
            "enderecoEntrega": "Rua A, 123",
            "status": "AGUARDANDO_PAGAMENTO"
        }
        """, idCliente);

        int idPedido = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when().post("/pedidos")
        .then().statusCode(201)
        .extract().path("id");

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when().delete("/pedidos/" + idPedido)
        .then().statusCode(204);
    }

    @Test
    public void testCreatePedidoInvalid() {
        int idCliente = criarCliente();

        String json = String.format("""
        {
            "cliente": { "id": %d },
            "dataPedido": "2022-03-10T12:15:50",
            "valorTotal": 100.0,
            "enderecoEntrega": "",
            "status": "AGUARDANDO_PAGAMENTO"
        }
        """, idCliente);

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when().post("/pedidos")
        .then().statusCode(400);
    }
}
