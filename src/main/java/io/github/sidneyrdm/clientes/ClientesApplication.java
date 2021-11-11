package io.github.sidneyrdm.clientes;

import io.github.sidneyrdm.clientes.model.entity.Usuario;
import io.github.sidneyrdm.clientes.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ClientesApplication {

    @Bean
    public CommandLineRunner commandLineRunner(@Autowired UsuarioRepository usuarioRepository){
        return args -> {
            Usuario usuario = new Usuario();
            usuario.setUsername("admin");
            usuario.setPassword("q1w2e3r4");

            if(!usuarioRepository.existsByUsername("admin")) {
                usuarioRepository.save(usuario);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(ClientesApplication.class, args);
    }
}
