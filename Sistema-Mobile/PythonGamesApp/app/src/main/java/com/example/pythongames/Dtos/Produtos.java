package com.example.pythongames.Dtos;

import android.net.Uri;

import java.io.Serializable;
import java.net.URI;

public class Produtos implements Serializable {
    private String nome, descricao, categoria, idImagem;
    private int preco;

    public Produtos(String nome, String descricao, String categoria, int preco, String idImagem) {
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.preco = preco;
        this.idImagem = idImagem;
    }

    public Produtos() {

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
