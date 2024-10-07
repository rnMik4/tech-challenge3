package com.restaurantes.controller;

import com.restaurantes.entity.Usuario;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.hasKey;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class UsuarioControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup(){
        RestAssured.port=port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    }

    @Nested
    class CriarUsuario{
        @Test
        void devePermitirCriarUsuario(){
            var usuario = gerarUsuario();

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(usuario)
            .when()
                    .post("/usuario")
            .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("$",hasKey("nomeCompleto"));



        }
    }

    @Nested
    class BuscarUsuario{
        @Test
        void devePermitirConsultarUsuarioPeloId(){
            var id = 1L;
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
                    .get("/usuario/{id}", id)
            .then()
                    .statusCode(HttpStatus.OK.value());
        }

        @Test
        void deveGerarExcecaoBuscarUsuarioIdNaoExiste(){
            var id = 10L;
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
                    .get("/usuario/{id}", id)
            .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
        }
    }

    @Nested
    class AtualizarUsuario{
        @Test
        void devePermitirAtualizarUsuarioPeloId(){
            var id = 1L;
            var usuario = gerarUsuario();
            usuario.setId(id);
            usuario.setTelefone("11999999999");

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(usuario)
            .when()
                    .put("/usuario/{id}", id)
            .then()
                    .statusCode(HttpStatus.ACCEPTED.value());
        }
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
