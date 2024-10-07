package com.restaurantes.repository;

import com.restaurantes.entity.MesasRestaurante;
import com.restaurantes.entity.Reservas;
import com.restaurantes.entity.Restaurante;
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

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class ReservasRepositoryTest {
    @Mock
    private ReservasRepository repository;

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
    class SalvarReserva {

        @Test
        void devePermitirSalvarReserva() {
            var reserva = gerarReserva();
            when(repository.save(any(Reservas.class))).thenReturn(reserva);
            var result = repository.save(reserva);

            assertThat(result).isNotNull();
            verify(repository, times(1)).save(any(Reservas.class));
        }
    }

    @Nested
    class ExcluirReserva {

        @Test
        void devePermitirExcluir() {
            var id = new Random().nextLong();
            doNothing().when(repository).deleteById(any(Long.class));

            repository.deleteById(id);

            verify(repository, times(1)).deleteById(any(Long.class));
        }

    }

    @Nested
    class BuscarReserva {
        @Test
        void devePermitirConsultarReservaPeloId() {
            var id = new Random().nextLong();
            var reserva = gerarReserva();
            reserva.setId(id);

            when(repository.findById(any(Long.class)))
                    .thenReturn(Optional.of(reserva));

            var resultOpcional = repository.findById(id);

            assertThat(resultOpcional)
                    .isPresent()
                    .contains(reserva);

            resultOpcional.ifPresent(result -> {
                assertThat(result.getId()).isEqualTo(id);
            });

            verify(repository, times(1)).findById(any(Long.class));
        }
    }

    private Reservas gerarReserva(){
        return Reservas.builder()
                .dataHoraReserva(LocalDateTime.now())
                .statusReserva("Reservado")
                .restaurante(gerarRestaurante())
                .usuario(gerarUsuario())
                .mesa(gerarMesas())
                .build();
    }


    private MesasRestaurante gerarMesas(){

        return MesasRestaurante.builder()
                .nomeMesa("Varanda")
                .lugares(8)
                .build();
    }

    private Usuario gerarUsuario(){

        return Usuario.builder()
                .id(new Random().nextLong())
                .email("email@email.com")
                .nomeCompleto("Tony Stark")
                .build();
    }

    private Restaurante gerarRestaurante(){

        return Restaurante.builder()
                .id(new Random().nextLong())
                .nomeRestaurante("J Unit Foods SA")
                .cep("00000000")
                .cnpj("12345678901234")
                .uf("SP")
                .cidade("Guarulhos")
                .bairro("Centro")
                .pais("Brasil")
                .logradouro("Rua da amargura")
                .numero("1245")
                .tipoCozinha("Italiana")
                .horarioAbertura(10)
                .horarioFechamento(23)
                .telefone("11999998888")
                .dono(gerarUsuario())
                .build();
    }



}
