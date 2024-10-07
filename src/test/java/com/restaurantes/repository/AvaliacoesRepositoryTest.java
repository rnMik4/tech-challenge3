package com.restaurantes.repository;

import com.restaurantes.entity.Avaliacoes;
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
public class AvaliacoesRepositoryTest {
    @Mock
    private AvaliacoesRepository repository;

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
    class CadastrarAvaliacoes {

        @Test
        void devePermitirCadastrarAvaliacao() {
            var avaliacao = gerarAvaliacao();
            var idRestaurante = new Random().nextLong();
            var idReserva = new Random().nextLong();
            avaliacao.setIdRestaurante(idRestaurante);
            avaliacao.setIdReserva(idReserva);
            when(repository.save(any(Avaliacoes.class))).thenReturn(avaliacao);
            var result = repository.save(avaliacao);

            assertThat(result).isNotNull();
            verify(repository, times(1)).save(any(Avaliacoes.class));
        }

    }

    @Nested
    class RemoverAvaliacoes {
        @Test
        void devePermitirExcluirAvaliacao() {
            var id = new Random().nextLong();
            doNothing().when(repository).deleteById(any(Long.class));

            repository.deleteById(id);

            verify(repository, times(1)).deleteById(any(Long.class));
        }
    }

    @Nested
    class BuscarAvaliacoes {
        @Test
        void devePermitirConsultarAvalilacaoPeloId() {
            var id = new Random().nextLong();
            var avaliacao = gerarAvaliacao();
            avaliacao.setId(id);

            when(repository.findById(any(Long.class)))
                    .thenReturn(Optional.of(avaliacao));

            var resultOpcional = repository.findById(id);

            assertThat(resultOpcional)
                    .isPresent()
                    .contains(avaliacao);

            resultOpcional.ifPresent(result -> {
                assertThat(result.getId()).isEqualTo(id);
            });

            verify(repository, times(1)).findById(any(Long.class));
        }
    }


    private Avaliacoes gerarAvaliacao(){

        return Avaliacoes.builder()
                .avaliacao(10)
                .comentario("comida maravilhosa!")
                .build();
    }

}
