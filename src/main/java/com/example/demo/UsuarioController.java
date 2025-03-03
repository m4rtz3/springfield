package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Cadastro do usuário
    // POST http://localhost:8080/usuario/cadastro
    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrarUsuario(@RequestBody Usuario usuario) {
        try {
            usuarioService.cadastrarUsuario(usuario);
            return new ResponseEntity<>("Usuário cadastrado com sucesso", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Login do usuário
    // POST http://localhost:8080/usuario/login?username=xxx&senha=yyy
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String senha) {
        try {
            boolean autenticado = usuarioService.autenticar(username, senha);
            if (autenticado) {
                return new ResponseEntity<>("Login realizado com sucesso", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Falha na autenticação", HttpStatus.UNAUTHORIZED);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    // Troca de senha
    // POST http://localhost:8080/usuario/trocar-senha?username=xxx&senhaAtual=aaa&novaSenha=bbb
    @PostMapping("/trocar-senha")
    public ResponseEntity<String> trocarSenha(@RequestParam String username,
                                              @RequestParam String senhaAtual,
                                              @RequestParam String novaSenha) {
        try {
            usuarioService.trocarSenha(username, senhaAtual, novaSenha);
            return new ResponseEntity<>("Senha atualizada com sucesso", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    // Desbloqueio do usuário
    // POST http://localhost:8080/usuario/desbloquear?username=xxx
    @PostMapping("/desbloquear")
    public ResponseEntity<String> desbloquear(@RequestParam String username) {
        try {
            usuarioService.desbloquearUsuario(username);
            return new ResponseEntity<>("Usuário desbloqueado com sucesso", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
