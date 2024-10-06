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
import com.restaurantes.service.impl.ReservasServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ReservasServiceTest {

    @Mock
    private ReservasRepository repository;

    @Mock
    MesasRepository mesasRepository;

    @Mock
    UsuarioRepository usuarioRepository;

    @Mock
    RestauranteRepository restauranteRepository;

    private ReservasService service;

    private UsuarioService usuarioService;

    private MesasService mesasService;

    private RestauranteService restauranteService;



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
        when(restauranteRepository.save(any(Restaurante.class)))
                .thenAnswer(i -> i.getArgument(0));

        RestauranteReqDTO restauranteReqDTO = new RestauranteReqDTO(restaurante.getNomeRestaurante(),
                restaurante.getTipoCozinha(),
                restaurante.getLogradouro(),
                restaurante.getBairro(),
                restaurante.getNumero(),
                restaurante.getCidade(),
                restaurante.getUf(),
                restaurante.getPais(),
                restaurante.getCep(),
                restaurante.getTelefone(),
                restaurante.getCnpj(),
                restaurante.getDono().getId(),
                restaurante.getHorarioAbertura(),
                restaurante.getHorarioFechamento());
        var restauranteRegistrado = restauranteService.createRestaurante(restauranteReqDTO);

        //inserir mock mesa
        var mesa = gerarMesas();
        mesa.setIdRestaurante(restauranteRegistrado.getId());
        when(mesasRepository.save(any(MesasRestaurante.class)))
                .thenAnswer(i -> i.getArgument(0));

        MesasReqDTO mesadto = new MesasReqDTO(mesa.getLugares(), mesa.getIdRestaurante(), mesa.getNomeMesa());

        var mesaRegistrada = mesasService.addMesa(mesadto);

        //inserir mock usuario
        var usuario = gerarUsuario();
        when(usuarioRepository.save(any(Usuario.class)))
                .thenAnswer(i -> i.getArgument(0));

        UsuarioReqDTO usuariodto = new UsuarioReqDTO(usuario.getNomeCompleto(), usuario.getEmail(), usuario.getSenha(), usuario.getTelefone());

        var usuarioRegistrado = usuarioService.novoUsuario(usuariodto);

        //mock reserva
        var reserva = gerarReserva();
        reserva.setRestaurante(restauranteRegistrado);
        reserva.setUsuario(usuarioRegistrado);
        reserva.setMesa(mesaRegistrada);
        when(repository.save(any(Reservas.class)))
                .thenAnswer(i -> i.getArgument(0));


        ReservasDTO dto = new ReservasDTO(
                reserva.getDataHoraReserva(),
                reserva.getDataHoraAtualizacao(),
                reserva.getStatusReserva(),
                reserva.getRestaurante().getId(),
                reserva.getMesa().getId(),
                reserva.getUsuario().getId());

        var reservaRegistrada = service.addReserva(dto);

        assertThat(reservaRegistrada)
                .isInstanceOf(MesasRestaurante.class)
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
        var id = new Random().nextLong();
        var reservaAntiga = gerarReserva();
        reservaAntiga.setId(id);

        var reservaNova = new Reservas();
        reservaNova.setId(id);
        reservaNova.setStatusReserva(reservaAntiga.getStatusReserva());
        reservaNova.setRestaurante(reservaAntiga.getRestaurante());
        reservaNova.setMesa(reservaAntiga.getMesa());
        reservaNova.setUsuario(reservaAntiga.getUsuario());


        ReservasDTO dto = new ReservasDTO(
                reservaNova.getDataHoraReserva(),
                reservaNova.getDataHoraAtualizacao(),
                reservaNova.getStatusReserva(),
                reservaNova.getRestaurante().getId(),
                reservaNova.getMesa().getId(),
                reservaNova.getUsuario().getId()
        );

        when(repository.findById(id)).thenReturn(Optional.of(reservaAntiga));
        when(repository.save(any(Reservas.class))).thenAnswer(i -> i.getArgument(0));



        var reservaObtida = service.updateReserva(dto, id);

        assertThat(reservaObtida).isInstanceOf(Reservas.class).isNotNull();
        assertThat(reservaObtida.getId()).isEqualTo(reservaAntiga.getId());

        verify(repository, times(1)).findById(id);
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
                .restaurante(gerarRestaurante())
                .usuario(gerarUsuario())
                .mesa(gerarMesas())
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
