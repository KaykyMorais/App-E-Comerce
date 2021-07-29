package com.example.pythongames.Dtos;

public class DtoCarrinho {
    public String nomeItem, formaPagamento;
    public int precoEntrega, precoTeste, precoItens, total;


    public DtoCarrinho(String nomeItem, String formaPagamento, int precoEntrega, int precoTeste, int precoItens, int total) {
        this.nomeItem = nomeItem;
        this.formaPagamento = formaPagamento;
        this.precoEntrega = precoEntrega;
        this.precoTeste = precoTeste;
        this.precoItens = precoItens;
        this.total = total;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getNomeItem() {
        return nomeItem;
    }

    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }

    public int getPrecoEntrega() {
        return precoEntrega;
    }

    public void setPrecoEntrega(int precoEntrega) {
        this.precoEntrega = precoEntrega;
    }

    public int getPrecoTeste() {
        return precoTeste;
    }

    public void setPrecoTeste(int precoTeste) {
        this.precoTeste = precoTeste;
    }

    public int getPrecoItens() {
        return precoItens;
    }

    public void setPrecoItens(int precoItens) {
        this.precoItens = precoItens;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
