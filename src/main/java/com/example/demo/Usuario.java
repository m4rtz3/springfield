package com.example.demo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "USUARIOS")
public class Usuario {

    @Id
    private Integer id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String senha;

    @Column(name = "tentativas_falhas")
    private int tentativasFalhas;

    @Column(name = "data_ultimo_login")
    private LocalDateTime dataUltimoLogin;

    @Column
    private boolean bloqueado;

    @Column(name = "forcar_troca_senha")
    private boolean forcarTrocaSenha;

    // Getters e Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getTentativasFalhas() {
        return tentativasFalhas;
    }

    public void setTentativasFalhas(int tentativasFalhas) {
        this.tentativasFalhas = tentativasFalhas;
    }

    public LocalDateTime getDataUltimoLogin() {
        return dataUltimoLogin;
    }

    public void setDataUltimoLogin(LocalDateTime dataUltimoLogin) {
        this.dataUltimoLogin = dataUltimoLogin;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public boolean isForcarTrocaSenha() {
        return forcarTrocaSenha;
    }

    public void setForcarTrocaSenha(boolean forcarTrocaSenha) {
        this.forcarTrocaSenha = forcarTrocaSenha;
    }
}
