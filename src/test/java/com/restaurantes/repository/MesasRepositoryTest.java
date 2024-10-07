package com.restaurantes.repository;

import com.restaurantes.entity.MesasRestaurante;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class MesasRepositoryTest {

    @Mock
    private MesasRepository repository;

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
    class CadastrarMesa {

        @Test
        void devePermitirCadastrarMesa() {
            var mesa = gerarMesas();
            var idRestaurante = new Random().nextLong();
            mesa.setIdRestaurante(idRestaurante);
            when(repository.save(any(MesasRestaurante.class))).thenReturn(mesa);
            var result = repository.save(mesa);

            assertThat(result).isNotNull();
            verify(repository, times(1)).save(any(MesasRestaurante.class));
        }
    }


    @Nested
    class ConsultarMesa {
        @Test
        void devePermitirExcluirMesa() {
            var id = new Random().nextLong();
            doNothing().when(repository).deleteById(any(Long.class));

            repository.deleteById(id);

            verify(repository, times(1)).deleteById(any(Long.class));
        }


        @Test
        void devePermitirConsultarMesaPeloId() {
            var id = new Random().nextLong();
            var mesa = gerarMesas();
            mesa.setId(id);

            when(repository.findById(any(Long.class)))
                    .thenReturn(Optional.of(mesa));

            var resultOpcional = repository.findById(id);

            assertThat(resultOpcional)
                    .isPresent()
                    .contains(mesa);

            resultOpcional.ifPresent(result -> {
                assertThat(result.getId()).isEqualTo(id);
            });

            verify(repository, times(1)).findById(any(Long.class));
        }

    }

    private MesasRestaurante gerarMesas(){

        return MesasRestaurante.builder()
                .nomeMesa("Varanda")
                .lugares(8)
                .build();
    }
}
