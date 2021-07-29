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
import com.example.pythongames.Dtos.DtoJogos;
import com.example.pythongames.Dtos.Produtos;
import com.example.pythongames.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetalheJogo extends AppCompatActivity {
    TextView txtNome, txtDesc, txtCateg, exclui, adiciona, quantidade, txtTotal;
    Button btnAdd;
    DtoJogos dtoJogos;
    ImageView imgProd;
    Retrofit retrofit;
    TbProduto tbProduto;
    int quant = 1;
    int preco = 0;
    int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        btnAdd = findViewById(R.id.btnAdd);
        txtNome = findViewById(R.id.txtNome);
        txtDesc = findViewById(R.id.txtDesc);
        exclui = findViewById(R.id.exclui);
        adiciona = findViewById(R.id.adiciona);
        quantidade = findViewById(R.id.quantidade);
        txtTotal = findViewById(R.id.txtTotal);
        imgProd = findViewById(R.id.imgProd);
        txtCateg = findViewById(R.id.txtCateg);

        pegaItem();

        adiciona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quant++;
                quantidade.setText(String.valueOf(quant));
                total = preco * quant;
                txtTotal.setText(String.valueOf(total));
            }
        });

        exclui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quant > 1) {
                    quant--;
                    quantidade.setText(String.valueOf(quant));
                    total = preco * quant;
                    txtTotal.setText(String.valueOf(total));
                } else {
                    Toast.makeText(DetalheJogo.this, "Selecione ao menos 1", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionaItem();
                Intent intent = new Intent(DetalheJogo.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void pegaItem() {
        Intent intent = getIntent();
        dtoJogos = (DtoJogos) intent.getSerializableExtra("jogos");

        txtNome.setText(dtoJogos.getNome());
        txtDesc.setText(dtoJogos.getDescricao());
        Picasso.get().load(dtoJogos.getIdImagem()).into(imgProd);
        txtCateg.setText(dtoJogos.getCategoria());
        preco = dtoJogos.getPreco();
        txtTotal.setText(String.valueOf(preco));
    }

    private void adicionaItem() {
        retrofit = ConfigRetrofit.getRetrofit();
        tbProduto = retrofit.create(TbProduto.class);

        String nome = txtNome.getText().toString();
        int quant = Integer.valueOf(quantidade.getText().toString());
        int preco = Integer.valueOf(txtTotal.getText().toString());
        String id_img = dtoJogos.getIdImagem();

        Call<Boolean> call = tbProduto.apiCarrinho(nome, quant, preco, id_img);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(DetalheJogo.this, "Produto adicionados com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("erros", "não foi");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(DetalheJogo.this, "erro" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("erro", t.getMessage());
            }
        });

    }

}