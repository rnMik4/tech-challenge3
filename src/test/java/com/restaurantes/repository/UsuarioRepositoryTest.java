package com.restaurantes.repository;

import com.restaurantes.entity.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

    @Mock
    private UsuarioRepository repository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class CadastraUsuario {

        @Test
        void devePermitirCadastrarUsuario() {
            var usuario = gerarUsuario();
            when(repository.save(any(Usuario.class))).thenReturn(usuario);
            var result = repository.save(usuario);

            assertThat(result).isNotNull();
            verify(repository, times(1)).save(any(Usuario.class));
        }

    }

    @Nested
    class ExcluirUsuario {
        @Test
        void devePermitirExcluirUsuario() {
            var id = new Random().nextLong();
            doNothing().when(repository).deleteById(any(Long.class));

            repository.deleteById(id);

            verify(repository, times(1)).deleteById(any(Long.class));
        }
    }



    private Usuario gerarUsuario(){
        return Usuario.builder()
                .nomeCompleto("Junit Test")
                .email("teste@email.com")
                .build();
    }
}
