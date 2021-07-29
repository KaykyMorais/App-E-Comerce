package com.example.pythongames.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.pythongames.Adapters.MeuAdapterConsoles;
import com.example.pythongames.Adapters.MeuAdapterJogos;
import com.example.pythongames.Apis.ConfigRetrofit;
import com.example.pythongames.Apis.TbProduto;
import com.example.pythongames.Dtos.DtoConsoles;
import com.example.pythongames.Dtos.DtoJogos;
import com.example.pythongames.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Console extends AppCompatActivity implements MeuAdapterConsoles.OnProdListener {
    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;
    MeuAdapterConsoles adapterConsoles;
    ArrayList<DtoConsoles> arrayList = new ArrayList<>();
    Retrofit retrofit;
    TbProduto tbProduto;
    DtoConsoles dtoConsoles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_console);

        rv = findViewById(R.id.rv);

        carregaConsoles();

    }

    private void carregaConsoles() {
        retrofit = ConfigRetrofit.getRetrofit();
        tbProduto = retrofit.create(TbProduto.class);

        Call<ArrayList<DtoConsoles>> call = tbProduto.apiConsoles();
        call.enqueue(new Callback<ArrayList<DtoConsoles>>() {
            @Override
            public void onResponse(Call<ArrayList<DtoConsoles>> call, Response<ArrayList<DtoConsoles>> response) {
                arrayList = response.body();

                layoutManager = new LinearLayoutManager(Console.this);
                adapterConsoles = new MeuAdapterConsoles(arrayList, Console.this);

                rv.setAdapter(adapterConsoles);
                rv.setLayoutManager(layoutManager);
                rv.setNestedScrollingEnabled(false);
            }

            @Override
            public void onFailure(Call<ArrayList<DtoConsoles>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onProdClick(int position) {
        dtoConsoles = (DtoConsoles) arrayList.get(position);

        Intent intent = new Intent(Console.this, DetalheConsole.class);
        intent.putExtra("jogos", dtoConsoles);
        startActivity(intent);
    }
}