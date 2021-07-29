package com.example.pythongames.Dtos;

import java.io.Serializable;

public class Recomendados implements Serializable {
    private String nome, descricao, categoria, idImagem;
    private int preco;

    public Recomendados(String nome, String descricao, String categoria, int preco, String idImagem) {
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.idImagem = idImagem;
        this.preco = preco;

    }

    public Recomendados() {

    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getPreco() {
        return preco;
    }

    public void setPreco(int preco) {
        this.preco = preco;
    }

    public String getIdImagem() {
        return idImagem;
    }

    public void setIdImagem(String idImagem) {
        this.idImagem = idImagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }

}
