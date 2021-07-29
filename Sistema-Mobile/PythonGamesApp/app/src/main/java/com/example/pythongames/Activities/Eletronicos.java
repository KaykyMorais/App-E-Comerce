package com.example.pythongames.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.pythongames.Adapters.MeuAdapterConsoles;
import com.example.pythongames.Adapters.MeuAdapterEletronicos;
import com.example.pythongames.Apis.ConfigRetrofit;
import com.example.pythongames.Apis.TbProduto;
import com.example.pythongames.Dtos.DtoConsoles;
import com.example.pythongames.Dtos.DtoEletronicos;
import com.example.pythongames.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Eletronicos extends AppCompatActivity implements MeuAdapterEletronicos.OnProdListener {
    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;
    MeuAdapterEletronicos adapterEletronicos;
    ArrayList<DtoEletronicos> arrayList = new ArrayList<>();
    Retrofit retrofit;
    TbProduto tbProduto;
    DtoEletronicos dtoEletronicos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eletronicos);

        rv = findViewById(R.id.rv);

        carregaEletronicos();
    }

    private void carregaEletronicos() {
        retrofit = ConfigRetrofit.getRetrofit();
        tbProduto = retrofit.create(TbProduto.class);

        Call<ArrayList<DtoEletronicos>> call = tbProduto.apiEletronicos();
        call.enqueue(new Callback<ArrayList<DtoEletronicos>>() {
            @Override
            public void onResponse(Call<ArrayList<DtoEletronicos>> call, Response<ArrayList<DtoEletronicos>> response) {
                arrayList = response.body();

                layoutManager = new LinearLayoutManager(Eletronicos.this);
                adapterEletronicos = new MeuAdapterEletronicos(arrayList, Eletronicos.this);

                rv.setAdapter(adapterEletronicos);
                rv.setLayoutManager(layoutManager);
            }

            @Override
            public void onFailure(Call<ArrayList<DtoEletronicos>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onProdClick(int position) {
        dtoEletronicos = (DtoEletronicos) arrayList.get(position);

        Intent intent = new Intent(Eletronicos.this, DetalheEletronicos.class);
        intent.putExtra("jogos", dtoEletronicos);
        startActivity(intent);
    }
}