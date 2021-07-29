package com.example.pythongames.Dtos;

public class DtoTesteGame {
    private String nm_jogo, dt_jogo, duracao, plataforma, usuario;
    private int id_teste, valor;

    public DtoTesteGame(int id_teste, String nm_jogo, String dt_jogo, String duracao, String plataforma, String usuario, int valor) {
        this.id_teste = id_teste;
        this.nm_jogo = nm_jogo;
        this.dt_jogo = dt_jogo;
        this.duracao = duracao;
        this.plataforma = plataforma;
        this.usuario = usuario;
        this.valor = valor;
    }

    public int getId_teste() {
        return id_teste;
    }

    public void setId_teste(int id_teste) {
        this.id_teste = id_teste;
    }

    public String getNm_jogo() {
        return nm_jogo;
    }

    public void setNm_jogo(String nm_jogo) {
        this.nm_jogo = nm_jogo;
    }

    public String getDt_jogo() {
        return dt_jogo;
    }

    public void setDt_jogo(String dt_jogo) {
        this.dt_jogo = dt_jogo;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
