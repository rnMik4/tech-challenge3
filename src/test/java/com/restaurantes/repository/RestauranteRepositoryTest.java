package com.restaurantes.repository;

import com.restaurantes.entity.Restaurante;
import com.restaurantes.entity.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RestauranteRepositoryTest {
    @Mock
    private RestauranteRepository restauranteRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirCadastrarRestaurante(){
        var restaurante = gerarRestaurante();
        when(restauranteRepository.save(any(Restaurante.class))).thenReturn(restaurante);
        var result = restauranteRepository.save(restaurante);

        assertThat(result).isNotNull();
        verify(restauranteRepository, times(1)).save(any(Restaurante.class));
    }



    @Test
    void devePermitirExcluirRestaurante(){
        var id = new Random().nextLong();
        doNothing().when(restauranteRepository).deleteById(any(Long.class));

        restauranteRepository.deleteById(id);

        verify(restauranteRepository, times(1)).deleteById(any(Long.class));
    }


    @Test
    void devePermitirConsultarRestaurantePeloId(){
        var id = new Random().nextLong();
        var restaurante = gerarRestaurante();
        restaurante.setId(id);

        when(restauranteRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(restaurante));

        var resultOpcional = restauranteRepository.findById(id);

        assertThat(resultOpcional)
                .isPresent()
                .contains(restaurante);

        resultOpcional.ifPresent(result -> {
            assertThat(result.getId()).isEqualTo(id);
        });

        verify(restauranteRepository, times(1)).findById(any(Long.class));
    }

    private Usuario gerarDonoRestaurante(){
        return Usuario.builder()
                .nomeCompleto("Junit Test")
                .email("teste@email.com")
                .build();
    }

    private Restaurante gerarRestaurante(){
        return Restaurante.builder()
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
                .dono(gerarDonoRestaurante())
                .build();
    }

}
