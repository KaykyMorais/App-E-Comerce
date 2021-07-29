package com.example.pythongames.Dtos;

import java.io.Serializable;

public class DtoItemCarrinho implements Serializable {
    private String nm_item, id_img;
    private int id_item, qt_item, preco_item;

    public DtoItemCarrinho(int id_item, String nm_item, int qt_item, int preco_item, String id_img) {
        this.nm_item = nm_item;
        this.id_item = id_item;
        this.qt_item = qt_item;
        this.preco_item = preco_item;
        this.id_img = id_img;
    }

    public DtoItemCarrinho() {

    }

    public String getNm_item() {
        return nm_item;
    }

    public void setNm_item(String nm_item) {
        this.nm_item = nm_item;
    }

    public int getId_item() {
        return id_item;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

    public int getQt_item() {
        return qt_item;
    }

    public void setQt_item(int qt_item) {
        this.qt_item = qt_item;
    }

    public int getPreco_item() {
        return preco_item;
    }

    public void setPreco_item(int preco_item) {
        this.preco_item = preco_item;
    }

    public String getId_img() {
        return id_img;
    }

    public void setId_img(String id_img) {
        this.id_img = id_img;
    }
}
