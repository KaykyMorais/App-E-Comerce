package com.example.pythongames.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pythongames.Apis.ConfigRetrofit;
import com.example.pythongames.Apis.TbProduto;
import com.example.pythongames.Dtos.DtoItemCarrinho;
import com.example.pythongames.Dtos.Produtos;
import com.example.pythongames.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetalhesCarrinho extends AppCompatActivity {
    TextView txtNome, txtDesc, txtCateg, exclui, adiciona, quantidade, txtTotal;
    Button btnAdd, btnRemove;
    DtoItemCarrinho itemCarrinho;
    ImageView imgProd;
    Retrofit retrofit;
    TbProduto tbProduto;
    int preco = 0;
    int total = 0;
    int quant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_carrinho);

        btnAdd = findViewById(R.id.btnAdd);
        btnRemove = findViewById(R.id.btnRemove);

        txtNome = findViewById(R.id.txtNome);
        txtDesc = findViewById(R.id.txtDesc);
        exclui = findViewById(R.id.exclui);
        adiciona = findViewById(R.id.adiciona);
        quantidade = findViewById(R.id.quantidade);
        txtTotal = findViewById(R.id.txtTotal);
        imgProd = findViewById(R.id.imgProd);
        txtCateg = findViewById(R.id.txtCateg);

        pegaItemCarrinho();

        adiciona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quant = Integer.valueOf(quantidade.getText().toString());
                quant++;
                quantidade.setText(String.valueOf(quant));
                total = preco * quant;
                txtTotal.setText(String.valueOf(total));
            }
        });

        exclui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(quantidade.getText().toString()) > 1) {
                    quant = Integer.valueOf(quantidade.getText().toString());
                    quant--;
                    quantidade.setText(String.valueOf(quant));
                    total = preco / quant;

                    txtTotal.setText(String.valueOf(total));
                } else {
                    Toast.makeText(DetalhesCarrinho.this, "Selecione ao menos 1", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizaItem();
                Intent intent = new Intent(DetalhesCarrinho.this, Carrinho.class);
                startActivity(intent);
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluiItem();
                Intent intent = new Intent(DetalhesCarrinho.this, Carrinho.class);
                startActivity(intent);
            }
        });

    }

    private void pegaItemCarrinho() {
        Intent intent = getIntent();

        itemCarrinho = (DtoItemCarrinho) intent.getSerializableExtra("itens");
        txtNome.setText(itemCarrinho.getNm_item());
        quantidade.setText(String.valueOf(itemCarrinho.getQt_item()));
        Picasso.get().load(itemCarrinho.getId_img()).into(imgProd);
        preco = itemCarrinho.getPreco_item();
        txtTotal.setText(String.valueOf(preco));
    }

    private void atualizaItem() {
        retrofit = ConfigRetrofit.getRetrofit();
        tbProduto = retrofit.create(TbProduto.class);

        int id_item = itemCarrinho.getId_item();
        int qt_item = Integer.valueOf(quantidade.getText().toString());
        int preco_item = Integer.valueOf(txtTotal.getText().toString());

        Call<Boolean> call = tbProduto.apiUpdate(qt_item, preco_item, id_item);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Toast.makeText(DetalhesCarrinho.this, "Alterações concluídas!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.d("update falha", t.getMessage());
                Toast.makeText(DetalhesCarrinho.this, "Erro", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void excluiItem() {
        retrofit = ConfigRetrofit.getRetrofit();
        tbProduto = retrofit.create(TbProduto.class);

        int id_item = itemCarrinho.getId_item();
        Call<Boolean> call = tbProduto.apiExclui(id_item);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Toast.makeText(DetalhesCarrinho.this, "Item excluído com sucesso!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.d("Erro", t.getMessage());
            }
        });

    }
}