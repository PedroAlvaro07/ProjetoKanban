package com.kanban.projetoKanban.controler;

import com.kanban.projetoKanban.model.Usuario;
import com.kanban.projetoKanban.security.Token;
import com.kanban.projetoKanban.service.UserServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/autenticar")
public class ControleAutenticacao {

    @Autowired
    private UserServico userServico;

    @Autowired
    private Token token;

    @PostMapping("/registro")
    public ResponseEntity<?> sign(@RequestBody Usuario user) {
        userServico.registrar(user);
        return ResponseEntity.ok("Usuário registrado com sucesso.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciais) {
        String username = credenciais.get("username");
        String password = credenciais.get("password");

        Optional<Usuario> user = userServico.autenticacao(username, password);
        if (user.isPresent()) {
            String generatedToken = token.GerarToken(user.get());
            Map<String, String> response = new HashMap<>();
            response.put("token", generatedToken);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).body("Credenciais inválidas");
    }

}
