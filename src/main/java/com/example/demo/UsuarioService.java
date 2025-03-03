package com.example.demo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.UsuarioRepository;
import com.example.demo.CidadaoRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private CidadaoRepository cidadaoRepository;

    // Cadastro do usuário
    public Usuario cadastrarUsuario(Usuario usuario) {
        // Verifica se o cidadão existe
        if (!cidadaoRepository.existsById(usuario.getId())) {
            throw new IllegalArgumentException("Cidadão não encontrado");
        }
        // Garante que só haja um cadastro por ID
        if (usuarioRepository.existsById(usuario.getId())) {
            throw new IllegalArgumentException("Usuário já cadastrado para este cidadão");
        }
        usuario.setTentativasFalhas(0);
        usuario.setBloqueado(false);
        usuario.setForcarTrocaSenha(false);
        usuario.setDataUltimoLogin(null);
        // Em produção, criptografar a senha com BCrypt, por exemplo.
        return usuarioRepository.save(usuario);
    }

    // Autenticação do usuário
    public boolean autenticar(String username, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
        if (usuarioOpt.isEmpty()) {
            return false;
        }
        Usuario usuario = usuarioOpt.get();
        
        if (usuario.isBloqueado()) {
            throw new RuntimeException("Usuário bloqueado");
        }
        
        // Aqui, a comparação simples; lembre de utilizar um encoder em produção
        if (usuario.getSenha().equals(senha)) {
            // Se já passou mais de 30 dias desde o último login, forçar troca de senha
            if (usuario.getDataUltimoLogin() != null) {
                long dias = ChronoUnit.DAYS.between(usuario.getDataUltimoLogin(), LocalDateTime.now());
                if (dias > 30) {
                    usuario.setForcarTrocaSenha(true);
                } else {
                    usuario.setForcarTrocaSenha(false);
                }
            }
            usuario.setTentativasFalhas(0);
            usuario.setDataUltimoLogin(LocalDateTime.now());
            usuarioRepository.save(usuario);
            return true;
        } else {
            // Incrementa tentativas e bloqueia após 3 falhas
            usuario.setTentativasFalhas(usuario.getTentativasFalhas() + 1);
            if (usuario.getTentativasFalhas() >= 3) {
                usuario.setBloqueado(true);
            }
            usuarioRepository.save(usuario);
            return false;
        }
    }

    // Troca de senha
    public void trocarSenha(String username, String senhaAtual, String novaSenha) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        
        if (!usuario.getSenha().equals(senhaAtual)) {
            throw new IllegalArgumentException("Senha atual incorreta");
        }
        usuario.setSenha(novaSenha);
        usuario.setForcarTrocaSenha(false);
        usuarioRepository.save(usuario);
    }
    
    // Desbloqueio de usuário
    public void desbloquearUsuario(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        usuario.setBloqueado(false);
        usuario.setTentativasFalhas(0);
        usuarioRepository.save(usuario);
    }
}
