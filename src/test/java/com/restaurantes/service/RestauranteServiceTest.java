package com.restaurantes.service;

import com.restaurantes.DTO.RestauranteReqDTO;
import com.restaurantes.DTO.UsuarioReqDTO;
import com.restaurantes.entity.Restaurante;
import com.restaurantes.entity.Usuario;
import com.restaurantes.exceptions.ResourceNotFoundException;
import com.restaurantes.repository.RestauranteRepository;
import com.restaurantes.repository.UsuarioRepository;
import com.restaurantes.service.impl.RestauranteServiceImpl;
import com.restaurantes.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
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
public class RestauranteServiceTest {

    @Mock
    private RestauranteRepository repository;

    @Mock
    private UsuarioRepository usuarioRepository;

    private RestauranteService service;

    private UsuarioService usuarioService;

    AutoCloseable mock;

    @BeforeEach
    void setUp() {

        mock = MockitoAnnotations.openMocks(this);
        service = new RestauranteServiceImpl(repository);
        usuarioService = new UsuarioServiceImpl(usuarioRepository);

    }

    void tearDown() throws Exception {
        mock.close();
    }


    @Test
    void devePermitirRegistrarRestaurante(){

        //gerar mock usuario dono
        var usuario = gerarDonoRestaurante();
        usuario.setId(new Random().nextLong());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioReqDTO usrdto = new UsuarioReqDTO(usuario.getNomeCompleto(), usuario.getEmail(), usuario.getSenha(), usuario.getTelefone());

        var donoRegistrado = usuarioService.novoUsuario(usrdto);



        var restaurante = gerarRestaurante();
        restaurante.setId(new Random().nextLong());
        when(repository.save(any(Restaurante.class))).thenReturn(restaurante);


        var restauranteRegistrado = service.addRestaurante(restaurante);

        assertThat(restauranteRegistrado)
                .isInstanceOf(Restaurante.class)
                .isNotNull();

        assertThat(restauranteRegistrado.getNomeRestaurante()).isEqualTo(restaurante.getNomeRestaurante());
        assertThat(restauranteRegistrado.getCnpj()).isEqualTo(restaurante.getCnpj());

        verify(repository, times(1)).save(any(Restaurante.class));
    }

    @Test
    void devePermitirConsultarRestaurantePeloId(){
        var id = new Random().nextLong();
        var restaurante = gerarRestaurante();
        restaurante.setId(id);

        when(repository.findById(any(Long.class)))
                .thenReturn(Optional.of(restaurante));

        var resultOpcional = repository.findById(id);

        assertThat(resultOpcional)
                .isPresent()
                .contains(restaurante);

        resultOpcional.ifPresent(result -> {
            assertThat(result.getId()).isEqualTo(id);
        });

        verify(repository, times(1)).findById(any(Long.class));
    }

    @Test
    void devePermitirAlterarRestaurante(){
        var id = new Random().nextLong();
        var restauranteAntigo = gerarRestaurante();
        restauranteAntigo.setId(id);

        var restauranteNovo = new Restaurante();
        restauranteNovo.setNomeRestaurante("Novo Nome");
        restauranteNovo.setLogradouro(restauranteAntigo.getLogradouro());
        restauranteNovo.setBairro(restauranteAntigo.getBairro());
        restauranteNovo.setNumero(restauranteAntigo.getNumero());
        restauranteNovo.setCidade(restauranteAntigo.getCidade());
        restauranteNovo.setUf(restauranteAntigo.getUf());
        restauranteNovo.setPais(restauranteAntigo.getPais());
        restauranteNovo.setCep(restauranteAntigo.getCep());
        restauranteNovo.setTelefone(restauranteAntigo.getTelefone());
        restauranteNovo.setDono(restauranteAntigo.getDono());
        restauranteNovo.setTipoCozinha(restauranteAntigo.getTipoCozinha());
        RestauranteReqDTO dto = new RestauranteReqDTO(
                restauranteNovo.getNomeRestaurante(),
                restauranteNovo.getTipoCozinha(),
                restauranteNovo.getLogradouro(),
                restauranteNovo.getBairro(),
                restauranteNovo.getNumero(),
                restauranteNovo.getCidade(),
                restauranteNovo.getUf(),
                restauranteNovo.getPais(),
                restauranteNovo.getCep(),
                restauranteNovo.getTelefone(),
                restauranteNovo.getCnpj(),
                restauranteNovo.getDono().getId(),
                restauranteNovo.getHorarioAbertura(),
                restauranteNovo.getHorarioFechamento()
        );

        when(repository.findById(id)).thenReturn(Optional.of(restauranteAntigo));
        when(repository.save(any(Restaurante.class))).thenAnswer(i -> i.getArgument(0));



        var usuarioObtido = service.updateRestaurante(dto, id);

        assertThat(usuarioObtido).isInstanceOf(Restaurante.class).isNotNull();
        assertThat(usuarioObtido.getId()).isEqualTo(restauranteAntigo.getId());

        verify(repository, times(1)).findById(id);


    }

    @Test
    void deveGerarExcecaoQuandoNaoEncontrado(){
        var id = new Random().nextLong();
        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getRestauranteById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Restaurante não encontrado");

        verify(repository, times(1)).findById(id);

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
                .senha("123456")
                .telefone("11912345678")
                .build();
    }



}
