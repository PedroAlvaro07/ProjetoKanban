package com.kanban.projetoKanban.service;

import com.kanban.projetoKanban.model.Usuario;
import com.kanban.projetoKanban.repository.UserRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServico {

    @Autowired
    private UserRepositorio userRepositorio;

    public Optional<Usuario> registrar(Usuario usuario) {
        if (userRepositorio.findByUsername(usuario.getUsername()).isEmpty()) {
            userRepositorio.save(usuario);
        } else {
            System.out.println("Usuário já existe.");
        }
        return null;
    }

    public Optional<Usuario> autenticacao(String username, String senha) {
        Optional<Usuario> user = userRepositorio.findByUsername(username);

        if (user.isPresent() && user.get().getPassword().equals(senha)) {
            return user;
        }else{
            return Optional.empty();
        }
    }
}
