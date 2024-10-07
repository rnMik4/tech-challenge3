package com.restaurantes.service;

import com.restaurantes.DTO.AvaliacoesReqDTO;
import com.restaurantes.DTO.MesasReqDTO;
import com.restaurantes.entity.Avaliacoes;
import com.restaurantes.entity.MesasRestaurante;
import com.restaurantes.exceptions.ResourceNotFoundException;
import com.restaurantes.repository.AvaliacoesRepository;
import com.restaurantes.service.impl.AvaliacoesServiceImpl;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class AvaliacoesServiceTest {
    @Mock
    private AvaliacoesRepository repository;
    private AvaliacoesService service;


    AutoCloseable mock;

    @BeforeEach
    void setUp() {

        mock = MockitoAnnotations.openMocks(this);
        service = new AvaliacoesServiceImpl(repository);
    }

    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class RegistrarAvaliacoes {

        @Test
        void devePermitirRegistrarAvaliacao() {
            var avaliacao = gerarAvaliacoes();
            when(repository.save(any(Avaliacoes.class))).thenReturn(avaliacao);

            AvaliacoesReqDTO dto = new AvaliacoesReqDTO(avaliacao.getAvaliacao(),
                    avaliacao.getComentario(),
                    avaliacao.getIdReserva(),
                    avaliacao.getIdRestaurante());

            var avaliacaoRegistrada = service.createAvaliacao(dto);

            assertThat(avaliacaoRegistrada)
                    .isInstanceOf(Avaliacoes.class)
                    .isNotNull();


            verify(repository, times(1)).save(any(Avaliacoes.class));
        }
    }

    @Nested
    class ConsultarAvaliacoes {
        @Test
        void devePermitirConsultarAvaliacoesPeloId() {
            var id = new Random().nextLong();
            var avaliacao = gerarAvaliacoes();
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

    @Nested
    class AlterarAvaliacoes {
        @Test
        void devePermitirAlterarAvaliacao() {
            var id = new Random().nextLong();
            var avaliacaoAntiga = gerarAvaliacoes();
            avaliacaoAntiga.setId(id);

            var avaliacaoNova = new Avaliacoes();
            avaliacaoNova.setId(avaliacaoAntiga.getId());
            avaliacaoNova.setAvaliacao(8);
            avaliacaoNova.setComentario("Quase perfeito");
            avaliacaoNova.setIdReserva(avaliacaoAntiga.getIdReserva());
            avaliacaoNova.setIdRestaurante(avaliacaoAntiga.getIdRestaurante());

            AvaliacoesReqDTO dto = new AvaliacoesReqDTO(
                    avaliacaoNova.getAvaliacao(),
                    avaliacaoNova.getComentario(),
                    avaliacaoNova.getIdReserva(),
                    avaliacaoNova.getIdRestaurante()
            );

            when(repository.findById(id)).thenReturn(Optional.of(avaliacaoAntiga));
            when(repository.save(any(Avaliacoes.class))).thenReturn(avaliacaoNova);

            var avaliacaoObtida = service.updateAvaliacao(dto, id);

            assertThat(avaliacaoObtida).isInstanceOf(Avaliacoes.class).isNotNull();
            assertThat(avaliacaoObtida.getId()).isEqualTo(avaliacaoNova.getId());

            verify(repository, times(1)).findById(id);
            verify(repository, times(1)).save(avaliacaoNova);


        }

    }
    private Avaliacoes gerarAvaliacoes(){

        return Avaliacoes.builder()
                .idReserva(new Random().nextLong())
                .comentario("Bão demais Sô")
                .avaliacao(10)
                .idRestaurante(new Random().nextLong())
                .build();
    }


}
