package com.example.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "CAD_CIDADAO")

public class CidadaoEntity {

    @Id
    @Column(name = "ID")
    @Min(10000)
    @Max(99999)
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "bairro")
    private String bairro;

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}