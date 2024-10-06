package com.restaurantes.service;

import com.restaurantes.DTO.UsuarioReqDTO;
import com.restaurantes.entity.Usuario;
import com.restaurantes.exceptions.ResourceNotFoundException;
import com.restaurantes.repository.UsuarioRepository;
import com.restaurantes.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    private UsuarioService service;

    AutoCloseable mock;

    @BeforeEach
    void setUp() {

        mock = MockitoAnnotations.openMocks(this);
        service = new UsuarioServiceImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }


    @Test
    void devePermitirRegistrarUsuario(){
        var usuario = gerarUsuario();
        usuario.setId(new Random().nextLong());
        when(repository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioReqDTO dto = new UsuarioReqDTO(usuario.getNomeCompleto(), usuario.getEmail(), usuario.getSenha(), usuario.getTelefone());

        var usuarioRegistrado = service.novoUsuario(dto);

        assertThat(usuarioRegistrado)
                .isInstanceOf(Usuario.class)
                .isNotNull();

        assertThat(usuarioRegistrado.getNomeCompleto()).isEqualTo(usuario.getNomeCompleto());
        assertThat(usuarioRegistrado.getEmail()).isEqualTo(usuario.getEmail());

        verify(repository, times(1)).save(any(Usuario.class));
    }

    @Test
    void devePermitirConsultarUsuarioPeloId(){
        var id = new Random().nextLong();
        var usuario = gerarUsuario();
        usuario.setId(id);

        when(repository.findById(any(Long.class)))
                .thenReturn(Optional.of(usuario));

        var resultOpcional = repository.findById(id);

        assertThat(resultOpcional)
                .isPresent()
                .contains(usuario);

        resultOpcional.ifPresent(result -> {
            assertThat(result.getId()).isEqualTo(id);
        });

        verify(repository, times(1)).findById(any(Long.class));
    }

    @Test
    void devePermitirAlterarUsuario(){
        var id = new Random().nextLong();
        var usuarioAntigo = gerarUsuario();
        usuarioAntigo.setId(id);

        var usuarioNovo = new Usuario();
        usuarioNovo.setNomeCompleto(usuarioAntigo.getNomeCompleto());
        usuarioNovo.setEmail(usuarioAntigo.getEmail());
        usuarioNovo.setSenha(usuarioAntigo.getSenha());
        usuarioNovo.setId(usuarioAntigo.getId());
        usuarioNovo.setTelefone("11980332233");
        UsuarioReqDTO usuarioNovoDTO = new UsuarioReqDTO(usuarioNovo.getNomeCompleto(),
                usuarioNovo.getEmail(), usuarioNovo.getSenha(), usuarioNovo.getTelefone());

        when(repository.findById(id)).thenReturn(Optional.of(usuarioAntigo));
        when(repository.save(any(Usuario.class))).thenAnswer(i -> i.getArgument(0));



        var usuarioObtido = service.updateUsuario(usuarioNovoDTO, id);

        assertThat(usuarioObtido).isInstanceOf(Usuario.class).isNotNull();
        assertThat(usuarioObtido.getId()).isEqualTo(usuarioNovo.getId());

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(usuarioNovo);


    }

    @Test
    void devePermitirConsultarUsuario(){
        var usuario = gerarUsuario();
        var id = new Random().nextLong();
        usuario.setId(id);
        when(repository.findById(id))
                .thenReturn(Optional.of(usuario));

        var usuarioObtido = service.getUsuario(id);

        assertThat(usuarioObtido)
                .isEqualTo(usuario);

        verify(repository, times(1)).findById(any(Long.class));
    }

    @Test
    void deveGerarExcecaoQuandoNaoEncontrado(){
        var id = new Random().nextLong();
        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getUsuario(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Usuário não encontrado");

        verify(repository, times(1)).findById(id);

    }



    private Usuario gerarUsuario(){
        return Usuario.builder()
                .nomeCompleto("Junit Test")
                .email("teste@email.com")
                .senha("123456")
                .telefone("11912345678")
                .build();
    }


}
