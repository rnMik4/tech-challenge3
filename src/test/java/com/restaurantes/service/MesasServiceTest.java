package com.restaurantes.service;

import com.restaurantes.DTO.MesasReqDTO;
import com.restaurantes.entity.MesasRestaurante;
import com.restaurantes.exceptions.ResourceNotFoundException;
import com.restaurantes.repository.MesasRepository;
import com.restaurantes.service.impl.MesasServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class MesasServiceTest {
    @Mock
    private MesasRepository repository;

    private MesasService service;

    AutoCloseable mock;

    @BeforeEach
    void setUp() {

        mock = MockitoAnnotations.openMocks(this);
        service = new MesasServiceImpl(repository);
    }

    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void devePermitirRegistrarMesa(){
        var mesa = gerarMesas();
        when(repository.save(any(MesasRestaurante.class))).thenReturn(mesa);

        MesasReqDTO dto = new MesasReqDTO(mesa.getLugares(), mesa.getIdRestaurante(), mesa.getNomeMesa());

        var mesaRegistrada = service.addMesa(dto);

        assertThat(mesaRegistrada)
                .isInstanceOf(MesasRestaurante.class)
                .isNotNull();

        assertThat(mesaRegistrada.getNomeMesa()).isEqualTo(mesa.getNomeMesa());
        assertThat(mesaRegistrada.getLugares()).isEqualTo(mesa.getLugares());
        assertThat(mesaRegistrada.getIdRestaurante()).isEqualTo(mesa.getIdRestaurante());

        verify(repository, times(1)).save(any(MesasRestaurante.class));
    }

    @Test
    void devePermitirConsultarMesasPeloId(){
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

    @Test
    void devePermitirAlterarMesa(){
        var id = new Random().nextLong();
        var mesaAntiga = gerarMesas();
        mesaAntiga.setId(id);

        var mesaNova = new MesasRestaurante();
        mesaNova.setNomeMesa(mesaAntiga.getNomeMesa());
        mesaNova.setLugares(mesaAntiga.getLugares());
        mesaNova.setId(mesaAntiga.getId());
        mesaNova.setLugares(mesaAntiga.getLugares());

        MesasReqDTO dto = new MesasReqDTO(mesaNova.getLugares(), mesaNova.getIdRestaurante(), mesaNova.getNomeMesa());

        when(repository.findById(id)).thenReturn(Optional.of(mesaAntiga));
        when(repository.save(any(MesasRestaurante.class))).thenAnswer(i -> i.getArgument(0));



        var mesaObtida = service.updateMesa(dto, id);

        assertThat(mesaObtida).isInstanceOf(MesasRestaurante.class).isNotNull();
        assertThat(mesaObtida.getId()).isEqualTo(mesaNova.getId());

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(mesaNova);


    }

    @Test
    void devePermitirConsultarMesa(){
        var mesa = gerarMesas();
        var id = new Random().nextLong();
        mesa.setId(id);
        when(repository.findById(id))
                .thenReturn(Optional.of(mesa));

        var mesaObtida = service.getMesaById(id);

        assertThat(mesaObtida)
                .isEqualTo(mesa);

        verify(repository, times(1)).findById(any(Long.class));
    }

    @Test
    void deveGerarExcecaoQuandoNaoEncontrado(){
        var id = new Random().nextLong();
        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getMesaById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Mesa não encontrada");

        verify(repository, times(1)).findById(id);

    }



    private MesasRestaurante gerarMesas(){

        return MesasRestaurante.builder()
                .nomeMesa("Varanda")
                .lugares(8)
                .build();
    }


}
