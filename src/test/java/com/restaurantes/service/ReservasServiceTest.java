package com.restaurantes.service;

import com.restaurantes.DTO.MesasReqDTO;
import com.restaurantes.DTO.ReservasDTO;
import com.restaurantes.DTO.RestauranteReqDTO;
import com.restaurantes.DTO.UsuarioReqDTO;
import com.restaurantes.entity.MesasRestaurante;
import com.restaurantes.entity.Reservas;
import com.restaurantes.entity.Restaurante;
import com.restaurantes.entity.Usuario;
import com.restaurantes.exceptions.ResourceNotFoundException;
import com.restaurantes.repository.MesasRepository;
import com.restaurantes.repository.ReservasRepository;
import com.restaurantes.repository.RestauranteRepository;
import com.restaurantes.repository.UsuarioRepository;
import com.restaurantes.service.impl.MesasServiceImpl;
import com.restaurantes.service.impl.ReservasServiceImpl;
import com.restaurantes.service.impl.RestauranteServiceImpl;
import com.restaurantes.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class ReservasServiceTest {

    @Mock
    ReservasRepository repository;

    @Mock
    MesasRepository mesasRepository;

    @Mock
    UsuarioRepository usuarioRepository;

    @Mock
    RestauranteRepository restauranteRepository;

    ReservasService service;

    UsuarioService usuarioService;

    MesasService mesasService;

    RestauranteService restauranteService;



    AutoCloseable mock;

    @BeforeEach
    void setUp() {

        mock = MockitoAnnotations.openMocks(this);
        service = new ReservasServiceImpl(repository);
    }

    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void devePermitirRegistrarReserva(){
        //inserir mock restaurante
        var restaurante = gerarRestaurante();
        restaurante.setId(new Random().nextLong());
        when(restauranteRepository.save(any(Restaurante.class))).thenReturn(restaurante);


        var restauranteRegistrado = restauranteRepository.save(restaurante);

        //inserir mock mesa
        var mesa = gerarMesas();
        mesa.setId(new Random().nextLong());
        mesa.setIdRestaurante(restauranteRegistrado.getId());
        when(mesasRepository.save(any(MesasRestaurante.class))).thenReturn(mesa);

//        MesasReqDTO mesadto = new MesasReqDTO(mesa.getLugares(), mesa.getIdRestaurante(), mesa.getNomeMesa());

        var mesaRegistrada = mesasRepository.save(mesa);

        //inserir mock usuario
        var usuario = gerarUsuario();
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

//        UsuarioReqDTO usuariodto = new UsuarioReqDTO(usuario.getNomeCompleto(), usuario.getEmail(), usuario.getSenha(), usuario.getTelefone());

        var usuarioRegistrado = usuarioRepository.save(usuario);

        //mock reserva
        var reserva = gerarReserva();
        reserva.setRestaurante(restauranteRegistrado);
        reserva.setUsuario(usuarioRegistrado);
        reserva.setMesa(mesaRegistrada);
        when(repository.save(any(Reservas.class))).thenReturn(reserva);



        var reservaRegistrada = repository.save(reserva);

        assertThat(reservaRegistrada)
                .isInstanceOf(Reservas.class)
                .isNotNull();

        assertThat(reservaRegistrada.getDataHoraReserva()).isEqualTo(reserva.getDataHoraReserva());
        assertThat(reservaRegistrada.getMesa().getId()).isEqualTo(reserva.getMesa().getId());
        assertThat(reservaRegistrada.getUsuario().getId()).isEqualTo(reserva.getUsuario().getId());
        assertThat(reservaRegistrada.getStatusReserva()).isEqualTo(reserva.getStatusReserva());
        assertThat(reservaRegistrada.getRestaurante().getId()).isEqualTo(reserva.getRestaurante().getId());

        verify(repository, times(1)).save(any(Reservas.class));
    }

    @Test
    void devePermitirConsultarReservaPeloId(){
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

    @Test
    void devePermitirAlterarReserva(){
        //inserir mock restaurante
        var restaurante = gerarRestaurante();
        restaurante.setId(new Random().nextLong());
        when(restauranteRepository.save(any(Restaurante.class))).thenReturn(restaurante);
        var restauranteRegistrado = restauranteRepository.save(restaurante);

        //inserir mock mesa
        var mesa = gerarMesas();
        mesa.setId(new Random().nextLong());
        mesa.setIdRestaurante(restauranteRegistrado.getId());
        when(mesasRepository.save(any(MesasRestaurante.class))).thenReturn(mesa);
        var mesaRegistrada = mesasRepository.save(mesa);

        //inserir mock usuario
        var usuario = gerarUsuario();
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        var usuarioRegistrado = usuarioRepository.save(usuario);

        var id = new Random().nextLong();
        var reservaAntiga = gerarReserva();
        reservaAntiga.setId(id);
        reservaAntiga.setMesa(mesaRegistrada);
        reservaAntiga.setUsuario(usuarioRegistrado);
        reservaAntiga.setRestaurante(restauranteRegistrado);
        when(repository.save(any(Reservas.class))).thenReturn(reservaAntiga);



        var reservaNova = new Reservas();
        reservaNova.setId(id);
        reservaNova.setStatusReserva(reservaAntiga.getStatusReserva());
        reservaNova.setMesa(mesaRegistrada);
        reservaNova.setRestaurante(restauranteRegistrado);
        reservaNova.setUsuario(usuarioRegistrado);
        reservaNova.setDataHoraReserva(reservaAntiga.getDataHoraReserva());
        reservaNova.setDataHoraAtualizacao(reservaAntiga.getDataHoraAtualizacao());

        when(repository.findById(any(Long.class))).thenReturn(Optional.of(reservaAntiga));
        when(repository.save(any(Reservas.class))).thenReturn(reservaNova);

        ReservasDTO dto = new ReservasDTO(
                reservaNova.getDataHoraReserva(),
                reservaNova.getDataHoraAtualizacao(),
                reservaNova.getStatusReserva(),
                reservaNova.getRestaurante().getId(),
                reservaNova.getMesa().getId(),
                reservaNova.getUsuario().getId()
        );

        var reservaObtida = repository.save(reservaNova);

        assertThat(reservaObtida).isInstanceOf(Reservas.class).isNotNull();
        assertThat(reservaObtida.getId()).isEqualTo(reservaAntiga.getId());

        verify(repository, times(1)).save(reservaNova);


    }

    @Test
    void devePermitirConsultarReserva(){
        var reserva = gerarReserva();
        var id = new Random().nextLong();
        reserva.setId(id);
        when(repository.findById(id))
                .thenReturn(Optional.of(reserva));

        var mesaObtida = service.getReservaById(id);

        assertThat(mesaObtida)
                .isEqualTo(reserva);

        verify(repository, times(1)).findById(any(Long.class));
    }

    @Test
    void deveGerarExcecaoQuandoNaoEncontrado(){
        var id = new Random().nextLong();
        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getReservaById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Reserva não encontrada");

        verify(repository, times(1)).findById(id);

    }



    private Reservas gerarReserva(){
        return Reservas.builder()
                .dataHoraReserva(LocalDateTime.now())
                .statusReserva("Reservado")
                .build();
    }


    private MesasRestaurante gerarMesas(){

        return MesasRestaurante.builder()
                .id(new Random().nextLong())
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

    private Usuario gerarDonoRestaurante(){
        return Usuario.builder()
                .nomeCompleto("Junit Test")
                .email("teste@email.com")
                .build();
    }



}
