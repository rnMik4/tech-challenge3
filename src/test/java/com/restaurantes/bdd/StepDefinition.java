package com.restaurantes.bdd;


import com.restaurantes.entity.Usuario;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.hasKey;

public class StepDefinition {

    private Response response;
    private Usuario usuarioResposta;
    private final String ENDPOINT_API_USUARIO = "http://localhost:8080/usuario";


    @Quando("cadastrar um novo usuario")
    public Usuario cadastrar_um_novo_usuario() {
        var usuarioRequest = gerarUsuario();
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(usuarioRequest)
                .when()
                .post(ENDPOINT_API_USUARIO);
        return response.then().extract().body().as(Usuario.class);
    }

    @Entao("usuario registrado com sucesso")
    public void o_usuario_registrado_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Dado("que um usuario ja foi cadastrado")
    public void o_usuario_ja_foi_cadastrado() {
        usuarioResposta= cadastrar_um_novo_usuario();

    }

    @Quando("efetuar a busca do usuario")
    public void efetuar_a_busca_do_usuario() {
        response = when().get(ENDPOINT_API_USUARIO+ "/{id}", usuarioResposta.getId());
    }

    @Quando("usuario exibido com sucesso")
    public void o_usuario_exibido_com_sucesso() {
        response.then()
                    .body("$", hasKey("nomeCompleto"));
    }


    private Usuario gerarUsuario(){
        return Usuario.builder()
                .nomeCompleto("Junit Test")
                .email("teste@email.com")
                .senha("123456")
                .telefone("11912345678")
                .build();
    }
}
