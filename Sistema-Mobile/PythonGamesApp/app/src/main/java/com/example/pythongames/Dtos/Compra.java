package com.example.pythongames.Dtos;

import java.util.List;

public class Compra {
    private int preco_total;
    private String nm_cliente, dt_compra, hr_compra, forma_pagamento;

    public Compra(int preco_total, String nm_cliente, String dt_compra, String hr_compra, String forma_pagamento) {
        this.preco_total = preco_total;
        this.nm_cliente = nm_cliente;
        this.dt_compra = dt_compra;
        this.hr_compra = hr_compra;
        this.forma_pagamento = forma_pagamento;
    }

    public Compra() {

    }

    public int getPreco_total() {
        return preco_total;
    }

    public void setPreco_total(int preco_total) {
        this.preco_total = preco_total;
    }

    public String getNm_cliente() {
        return nm_cliente;
    }

    public void setNm_cliente(String nm_cliente) {
        this.nm_cliente = nm_cliente;
    }

    public String getDt_compra() {
        return dt_compra;
    }

    public void setDt_compra(String dt_compra) {
        this.dt_compra = dt_compra;
    }

    public String getHr_compra() {
        return hr_compra;
    }

    public void setHr_compra(String hr_compra) {
        this.hr_compra = hr_compra;
    }

    public String getForma_pagamento() {
        return forma_pagamento;
    }

    public void setForma_pagamento(String forma_pagamento) {
        this.forma_pagamento = forma_pagamento;
    }
}


