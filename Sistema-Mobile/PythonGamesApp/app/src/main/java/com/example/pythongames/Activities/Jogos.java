package com.example.pythongames.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.pythongames.Activities.ui.home.HomeFragment;
import com.example.pythongames.Adapters.MeuAdapter;
import com.example.pythongames.Adapters.MeuAdapterJogos;
import com.example.pythongames.Apis.ConfigRetrofit;
import com.example.pythongames.Apis.TbProduto;
import com.example.pythongames.Dtos.DtoJogos;
import com.example.pythongames.Dtos.Produtos;
import com.example.pythongames.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Jogos extends AppCompatActivity implements MeuAdapterJogos.OnProdListener {
    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;
    MeuAdapterJogos adapterJogos;
    ArrayList<DtoJogos> arrayList = new ArrayList<>();
    Retrofit retrofit;
    TbProduto tbProduto;
    DtoJogos dtoJogos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogos);

        rv = findViewById(R.id.rv);

        carregaJogos();
    }

    private void carregaJogos() {
        retrofit = ConfigRetrofit.getRetrofit();
        tbProduto = retrofit.create(TbProduto.class);

        Call<ArrayList<DtoJogos>> call = tbProduto.apiJogos();
        call.enqueue(new Callback<ArrayList<DtoJogos>>() {
            @Override
            public void onResponse(Call<ArrayList<DtoJogos>> call, Response<ArrayList<DtoJogos>> response) {
                arrayList = response.body();

                layoutManager = new LinearLayoutManager(Jogos.this);
                adapterJogos = new MeuAdapterJogos(arrayList, Jogos.this);

                rv.setAdapter(adapterJogos);
                rv.setLayoutManager(layoutManager);
            }

            @Override
            public void onFailure(Call<ArrayList<DtoJogos>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onProdClick(int position) {
        dtoJogos = (DtoJogos) arrayList.get(position);

        Intent intent = new Intent(Jogos.this, DetalheJogo.class);
        intent.putExtra("jogos", dtoJogos);
        startActivity(intent);
    }
}