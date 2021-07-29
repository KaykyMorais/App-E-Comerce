package com.example.pythongames.Apis;

import com.example.pythongames.Activities.Jogos;
import com.example.pythongames.Dtos.Cliente;
import com.example.pythongames.Dtos.DtoConsoles;
import com.example.pythongames.Dtos.DtoEletronicos;
import com.example.pythongames.Dtos.DtoItemCarrinho;
import com.example.pythongames.Dtos.DtoJogos;
import com.example.pythongames.Dtos.DtoTesteGame;
import com.example.pythongames.Dtos.EmAlta;
import com.example.pythongames.Dtos.Historico;
import com.example.pythongames.Dtos.Produtos;
import com.example.pythongames.Dtos.Recomendados;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TbProduto {

    @FormUrlEncoded
    @POST("api-carrinho.php")
    Call<Boolean> apiCarrinho(
            @Field("nm_item") String nm_item,
            @Field("qt_item") int qt_item,
            @Field("preco_item") int preco_item,
            @Field("id_img") String id_img
    );

    @FormUrlEncoded
    @POST("api-exclui.php")
    Call<Boolean> apiExclui(
            @Field("id_item") int id_item
    );

    @FormUrlEncoded
    @POST("api-compra.php")
    Call<Boolean> apiCompra(
            @Field("preco_total") int preco_total,
            @Field("nm_cliente") String nm_cliente,
            @Field("dt_compra") String dt_compra,
            @Field("hr_compra") String hr_compra,
            @Field("forma_pagamento") String forma_pagamento
    );

    @FormUrlEncoded
    @POST("api-testes.php")
    Call<Boolean> apiTestes(
            @Field("nm_jogo") String nm_jogo,
            @Field("dt_jogo") String dt_jogo,
            @Field("duracao") String duracao,
            @Field("plataforma") String plataforma,
            @Field("usuario") String usuario,
            @Field("valor") int valor

    );

    @FormUrlEncoded
    @POST("api-update.php")
    Call<Boolean> apiUpdate(
            @Field("qt_item") int qt_item,
            @Field("preco_item") int preco_item,
            @Field("id_item") int id_item
    );

    @GET("api-carrinho-lista.php")
    Call<ArrayList<DtoItemCarrinho>> apiCarrinhoLista();

    @GET("api-produtos.php")
    Call<ArrayList<Produtos>> apiProdutos();

    @GET("api-eletronicos.php")
    Call<ArrayList<DtoEletronicos>> apiEletronicos();

    @GET("api-jogos.php")
    Call<ArrayList<DtoJogos>> apiJogos();

    @GET("api-consoles.php")
    Call<ArrayList<DtoConsoles>> apiConsoles();

    @GET("api-consulta.php")
    Call<ArrayList<Historico>> apiConsultaHistorico(
            @Query("nm_cliente") String nm_cliente
    );

    @GET("api-teste-lista.php")
    Call<ArrayList<DtoTesteGame>> apiConsultaTeste(
            @Query("usuario") String usuario
    );

}
