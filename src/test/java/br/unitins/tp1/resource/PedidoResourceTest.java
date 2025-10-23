package br.unitins.tp1.resource;

import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
public class PedidoResourceTest {

    private static final String TOKEN_FIXO = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ1bml0aW5zLWp3dCIsInN1YiI6ImpvYW8iLCJncm91cHMiOlsiQWRtIl0sImV4cCI6MTc0OTc1MzEzMiwiaWF0IjoxNzQ5NjY2NzMyLCJqdGkiOiIzYmVmYTMyZS1kNDYxLTQ3NjItOThmNi1jZjIyY2M2MWZjYTQifQ.jpQfaHY9qzJ37ypaSBXv9xW3FWbEK_lj4gM20fnZCrGebsTXAGL6tefem488Eo2inK7OPPPVVK6-7Jv9uUUIqWeuJVW2C31NVDFCX9yO5qfd6bY4kNNiQD4qkojQ0b_nC_vJuayNe0pE1Z8B8JfTN4gDu53R2SqsSjilTM_0mnINc1u3UI_JeXicilnw9mmxwmnNzZC14EAGCFvwncjvXwA-4JEcbrOYhwERDakeBbJXKfGN7teYtbiUwB0-6qrqcj8C4Zcu_mzfStq5Yv4VWibKRW93oEONAasVlvc1DnQcDbFW--bCZzKIRitKy6dVPYCT2hpSQFSf47wh-YK4DQ";

    // Cria um ClienteFisico e retorna o id criado (int)
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
                .when().post("/clientes/fisicos")  // plural corrigido
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
