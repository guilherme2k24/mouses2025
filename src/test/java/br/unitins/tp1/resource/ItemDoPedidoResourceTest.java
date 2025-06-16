package br.unitins.tp1.resource;

import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
public class ItemDoPedidoResourceTest {

    private static final String TOKEN_FIXO = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ1bml0aW5zLWp3dCIsInN1YiI6ImpvYW8iLCJncm91cHMiOlsiQWRtIl0sImV4cCI6MTc1MjUxNjk0NSwiaWF0IjoxNzQ5OTI0OTQ1LCJqdGkiOiJiODRkOGE2MC1iNDIwLTRkYTEtYTczNi1kZDFjZjFmOWQwNjgifQ.rxZ749FR4hKKxK58ofsxYBjKWxlGHbhu8yfHX_O6bpKPs75_HC-o_Cah21ZF6Uc_YppeMNvV91j4E0PwuPV02bb_jxnEupiOvqH8sH4VqJI8lTXnhmq-zuRbDqe3Y8NF6gyM7L2GL-QwSdHuwbD8pGASvfLE8OBUH18Bs-QYMwDDJQfohE6ucC9PDxpTlm6SfUMXfxlV_ssbZ7U_cCzqL9niZESxZ4fzO_kvBXLSMryRy6Gaih1qhsQfwYTGC8GvzMoWenyLBbWqbwJrvm0a2gpUMWQBfvwIZ2HrsAuGI1JTbuTHpy3cJXcuizoeG4DADgQRuVM7UXfEP0NItyeMLg";

    private int criarCliente() {
        String json = """
        {
            "nome": "Carlos",
            "sobreNome": "Oliveira",
            "idade": 28,
            "email": "carlos@email.com",
            "cpf": "12345678900"
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

    private int criarMouse() {
        String json = """
        {
            "modelo": "Razer DeathAdder",
            "dpi": 16000,
            "sensor": "Óptico",
            "pollingRate": 1000,
            "botoes": 5,
            "formato": "Destro",
            "cor": "Preto",
            "peso": 105,
            "conexao": "COM_FIO"
        }
        """;

        return given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when().post("/mouses")
        .then().statusCode(201)
        .extract().path("id");
    }

    private int criarPedido(int idCliente) {
        String json = String.format("""
        {
            "cliente": { "id": %d },
            "dataPedido": "2025-01-01T10:00:00",
            "valorTotal": 0.0,
            "enderecoEntrega": "Av. Brasil, 1000",
            "status": "AGUARDANDO_PAGAMENTO"
        }
        """, idCliente);

        return given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when().post("/pedidos")
        .then().statusCode(201)
        .extract().path("id");
    }

    @Test
    public void testGetAllItensDoPedido() {
        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when().get("/itenspedido")
        .then().statusCode(200);
    }

    @Test
    public void testCreateItemDoPedido() {
        int idPedido = criarPedido(criarCliente());
        int idMouse = criarMouse();

        String json = String.format("""
        {
            "idPedido": %d,
            "idMouse": %d,
            "quantidade": 2,
            "precoUnitario": 150.0,
            "precoTotal": 300.0
        }
        """, idPedido, idMouse);

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when().post("/itenspedido")
        .then().statusCode(201)
        .body("quantidade", is(2));
    }

    @Test
    public void testGetItemDoPedidoById() {
        int idPedido = criarPedido(criarCliente());
        int idMouse = criarMouse();

        int idItem = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(String.format("""
            {
                "idPedido": %d,
                "idMouse": %d,
                "quantidade": 1,
                "precoUnitario": 200.0,
                "precoTotal": 200.0
            }
            """, idPedido, idMouse))
        .when().post("/itenspedido")
        .then().statusCode(201)
        .extract().path("id");

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when().get("/itenspedido/" + idItem)
        .then().statusCode(200)
        .body("id", is(idItem));
    }

    @Test
    public void testUpdateItemDoPedido() {
        int idPedido = criarPedido(criarCliente());
        int idMouse = criarMouse();

        int idItem = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(String.format("""
            {
                "idPedido": %d,
                "idMouse": %d,
                "quantidade": 1,
                "precoUnitario": 100.0,
                "precoTotal": 100.0
            }
            """, idPedido, idMouse))
        .when().post("/itenspedido")
        .then().statusCode(201)
        .extract().path("id");

        String jsonUpdate = String.format("""
        {
            "idPedido": %d,
            "idMouse": %d,
            "quantidade": 3,
            "precoUnitario": 100.0,
            "precoTotal": 300.0
        }
        """, idPedido, idMouse);

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(jsonUpdate)
        .when().put("/itenspedido/" + idItem)
        .then().statusCode(204);

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when().get("/itenspedido/" + idItem)
        .then().statusCode(200)
        .body("quantidade", is(3));
    }

    @Test
    public void testDeleteItemDoPedido() {
        int idPedido = criarPedido(criarCliente());
        int idMouse = criarMouse();

        int idItem = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(String.format("""
            {
                "idPedido": %d,
                "idMouse": %d,
                "quantidade": 1,
                "precoUnitario": 150.0,
                "precoTotal": 150.0
            }
            """, idPedido, idMouse))
        .when().post("/itenspedido")
        .then().statusCode(201)
        .extract().path("id");

        given()
            .header("Authorization", "Bearer " + TOKEN_FIXO)
        .when().delete("/itenspedido/" + idItem)
        .then().statusCode(204);
    }

    @Test
    public void testCreateItemDoPedidoInvalid() {
        int idPedido = criarPedido(criarCliente());
        int idMouse = criarMouse();

        String json = String.format("""
        {
            "idPedido": %d,
            "idMouse": %d,
            "precoUnitario": 100.0,
            "precoTotal": 100.0
        }
        """, idPedido, idMouse);

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + TOKEN_FIXO)
            .body(json)
        .when().post("/itenspedido")
        .then().statusCode(400);
    }
}
