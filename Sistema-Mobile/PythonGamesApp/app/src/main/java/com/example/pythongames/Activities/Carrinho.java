package com.example.pythongames.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pythongames.Activities.ui.home.HomeFragment;
import com.example.pythongames.Adapters.MeuAdapterCarrinho;
import com.example.pythongames.Apis.ConfigRetrofit;
import com.example.pythongames.Apis.TbProduto;
import com.example.pythongames.Dtos.Compra;
import com.example.pythongames.Dtos.DtoItemCarrinho;
import com.example.pythongames.Dtos.Produtos;
import com.example.pythongames.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Carrinho extends AppCompatActivity implements MeuAdapterCarrinho.OnItensListener {
    RecyclerView lv;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<DtoItemCarrinho> itemCarrinho;
    DtoItemCarrinho ic;
    TextView total, itensSelecionados, entrega, addItem, formaPag;
    MeuAdapterCarrinho adapter;
    Retrofit retrofit;
    TbProduto tbProduto;
    ImageView imgTeste;
    int totalItens = 0;
    int preco = 0;
    int entregs = 25;
    int totalTudo;
    Compra compra = new Compra();
    FirebaseAuth auth;
    Button btnFinaliza;
    CardView btnTesteGame, formaPagamento;
    String usuario, dt, horaFormatada;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        lv = findViewById(R.id.lv);
        total = findViewById(R.id.total);
        formaPagamento = findViewById(R.id.formaPagamento);
        itemCarrinho = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        addItem = findViewById(R.id.addItem);
        btnFinaliza = findViewById(R.id.btnFinaliza);
        itensSelecionados = findViewById(R.id.itensSelecionados);
        entrega = findViewById(R.id.entrega);
        imgTeste = findViewById(R.id.imgTeste);
        btnTesteGame = findViewById(R.id.btnTesteGame);
        formaPag = findViewById(R.id.formaPag);

        Picasso.get().load("https://mityk.com.br/wp-content/uploads/2021/05/share.png").into(imgTeste);

        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        dt = formatador.format(data);

        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatadorHora = DateTimeFormatter.ofPattern("HH:mm:ss");
        horaFormatada = formatadorHora.format(agora);

        Intent intent = getIntent();
        String formap = intent.getStringExtra("formaPagamento");
        formaPag.setText(formap);

        formaPagamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Carrinho.this, FormaPagamento.class);
                startActivity(intent);
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Carrinho.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnFinaliza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizaCompra();
                Intent intent = new Intent(Carrinho.this, MainActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(Carrinho.this).toBundle());
            }
        });

        btnTesteGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Carrinho.this, TesteGames.class);
                startActivity(intent);
            }
        });

        lv.removeAllViews();

        pegaItensCarrinho();

    }

    private void finalizaCompra() {
        retrofit = ConfigRetrofit.getRetrofit();
        tbProduto = retrofit.create(TbProduto.class);
        preco = totalTudo;
        usuario = auth.getCurrentUser().getEmail();

        Call<Boolean> call = tbProduto.apiCompra(preco, usuario, dt, horaFormatada, formaPag.getText().toString());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Carrinho.this, "Compra finalizada com sucesso!", Toast.LENGTH_LONG).show();
                    Log.d("foi", "foi");
                } else {
                    Toast.makeText(Carrinho.this, "Erro ao finalizar a compra!", Toast.LENGTH_SHORT).show();
                    Log.d("não foi", "não foi");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(Carrinho.this, "erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("erro", t.getMessage());
            }
        });
    }

    private void mostraTotal() {
        for (DtoItemCarrinho dtoItemCarrinho : itemCarrinho) {
            totalItens = totalItens + dtoItemCarrinho.getPreco_item();

            itensSelecionados.setText(String.valueOf(totalItens));
            totalTudo = Integer.valueOf(itensSelecionados.getText().toString()) + entregs;
            itensSelecionados.setText("R$: " + String.valueOf(totalItens));
            total.setText("R$: " + String.valueOf(totalTudo));
            compra.setPreco_total(totalTudo);

        }
    }

    private void pegaItensCarrinho() {
        retrofit = ConfigRetrofit.getRetrofit();
        tbProduto = retrofit.create(TbProduto.class);

        Call<ArrayList<DtoItemCarrinho>> call = tbProduto.apiCarrinhoLista();
        call.enqueue(new Callback<ArrayList<DtoItemCarrinho>>() {
            @Override
            public void onResponse(Call<ArrayList<DtoItemCarrinho>> call, Response<ArrayList<DtoItemCarrinho>> response) {
                itemCarrinho = response.body();
                adapter = new MeuAdapterCarrinho(itemCarrinho, Carrinho.this);
                layoutManager = new LinearLayoutManager(Carrinho.this);
                lv.setLayoutManager(layoutManager);
                lv.setAdapter(adapter);
                mostraTotal();
            }

            @Override
            public void onFailure(Call<ArrayList<DtoItemCarrinho>> call, Throwable t) {
                Toast.makeText(Carrinho.this, "Erro ao carregar carrinho!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onItensClick(int position) {
        ic = (DtoItemCarrinho) itemCarrinho.get(position);

        Intent intent = new Intent(Carrinho.this, DetalhesCarrinho.class);
        intent.putExtra("itens", ic);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }
}