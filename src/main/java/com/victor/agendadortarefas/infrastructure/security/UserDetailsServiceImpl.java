package com.victor.agendadortarefas.infrastructure.security;


import com.victor.agendadortarefas.bussines.dto.UsuarioDTO;
import com.victor.agendadortarefas.infrastructure.security.client.UsuarioClient;
import com.victor.usuario.infrastructure.entity.Usuario;
import com.victor.usuario.infrastructure.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl {

    @Autowired
    private UsuarioClient client;

    public UserDetails loadUserByUsername(String email, String token){

        UsuarioDTO usuarioDTO = client.buscarUsuarioPorEmail(email, token);

        return User
                .withUsername(usuarioDTO.getEmail()) // Define o nome de usuário como o e-mail
                .password(usuarioDTO.getSenha()) // Define a senha do usuário
                .build(); // Constrói o objeto UserDetails
    }
}
