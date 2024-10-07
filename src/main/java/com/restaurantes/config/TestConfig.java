package com.restaurantes.config;

import com.restaurantes.entity.Usuario;
import com.restaurantes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
//@Profile("test")
public class TestConfig implements CommandLineRunner {
    @Autowired
    private UsuarioRepository usuarioRepository;


        @Override
        public void run(String... args) throws Exception {
            Usuario usuario = new Usuario();
            usuario.setNomeCompleto("Papa Capim");
            usuario.setEmail("papacim@gmail.com");


            usuarioRepository.save(usuario);
        }

    }

