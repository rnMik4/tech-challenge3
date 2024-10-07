package com.restaurantes.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurantes.DTO.UsuarioReqDTO;
import com.restaurantes.DTO.UsuarioRespDTO;
import com.restaurantes.entity.Usuario;
import com.restaurantes.exceptions.ResourceNotFoundException;
import com.restaurantes.service.GenericMapper;
import com.restaurantes.service.UsuarioService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class UsuarioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private     UsuarioService usuarioService;
    private GenericMapper mapper;

    AutoCloseable mock;

    @BeforeEach
    void setup() throws Exception {
        mock = MockitoAnnotations.openMocks(this);
        UsuarioController usuarioController = new UsuarioController(usuarioService);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController)
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class CriarUsuario{
        @Test
        void devePermitirCriarUsuario() throws Exception {
            var usuario = gerarUsuario();
            usuario.setId(new Random().nextLong());
            var usuarioReqDTO = new UsuarioReqDTO(
                    usuario.getNomeCompleto(),
                    usuario.getEmail(),
                    "1234",
                    usuario.getTelefone()
            );
            when(usuarioService.novoUsuario(usuarioReqDTO))
                    .thenAnswer((i -> i.getArgument(0)));

            mockMvc.perform(
                    post(
                            "/usuario")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(usuario))
            ).andExpect(status().isCreated());

            verify(usuarioService, times(1)).novoUsuario(any(UsuarioReqDTO.class));

        }
    }

    @Nested
    class BuscarUsuario{
        @Test
        void devePermitirConsultarUsuarioPeloId() throws Exception {
            var usuario = gerarUsuario();
            var id = new Random().nextLong();
            when(usuarioService.getUsuario(any(Long.class))).thenReturn(usuario);

            mockMvc.perform(
                    get("/usuario/{id}", id)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk());

            verify(usuarioService, times(1)).getUsuario(id);
        }
        @Test
        void deveGerarExcecaoBuscarUsuarioIdNaoExiste() throws Exception {
            var id = new Random().nextLong();
            when(usuarioService.getUsuario(id)).thenThrow(new ResourceNotFoundException("Usuário não encontrado"));

            mockMvc.perform(
                    get("/usuario/{id}", id)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isNotFound());

            verify(usuarioService, times(1)).getUsuario(id);

        }
    }

    @Nested
    class AtualizarUsuario{
        @Test
        void devePermitirAtualizarUsuarioPeloId() throws Exception {
            var id = new Random().nextLong();
            var usuario = gerarUsuario();
            usuario.setId(id);
            var usuarioReqDTO = new UsuarioReqDTO(
                    usuario.getNomeCompleto(),
                    usuario.getEmail(),
                    "123456",
                    usuario.getTelefone()
            );
            when(usuarioService.updateUsuario(any(UsuarioReqDTO.class), any(Long.class)))
            .thenReturn(usuario);
            mockMvc.perform(
                    put("/usuario/{id}", id)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(usuarioReqDTO))
            ).andExpect(status().isAccepted());

            verify(usuarioService, times(1)).updateUsuario(usuarioReqDTO, id);
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

    public static String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    private UsuarioRespDTO gerarUsuarioRespDTO(){
        return UsuarioRespDTO.builder()
                .nomeCompleto("Junit Test")
                .email("teste@email.com")
                .telefone("11912345678")
                .build();
    }


}
