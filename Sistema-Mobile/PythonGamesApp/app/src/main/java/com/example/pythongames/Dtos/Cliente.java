package com.example.pythongames.Dtos;

public class Cliente {

    private String nm_cli, cpf_cli, email_cliente;

    public Cliente(String nm_cli, String cpf_cli, String email_cliente) {
        this.nm_cli = nm_cli;
        this.cpf_cli = cpf_cli;
        this.email_cliente = email_cliente;
    }

    public Cliente() {

    }

    public String getNm_cli() {
        return nm_cli;
    }

    public void setNm_cli(String nm_cli) {
        this.nm_cli = nm_cli;
    }

    public String getCpf_cli() {
        return cpf_cli;
    }

    public void setCpf_cli(String cpf_cli) {
        this.cpf_cli = cpf_cli;
    }

    public String getEmail_cliente() {
        return email_cliente;
    }

    public void setEmail_cliente(String email_cliente) {
        this.email_cliente = email_cliente;
    }
}
