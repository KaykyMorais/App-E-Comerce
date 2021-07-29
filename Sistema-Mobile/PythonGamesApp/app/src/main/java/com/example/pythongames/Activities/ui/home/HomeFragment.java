package com.example.pythongames.Activities.ui.home;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pythongames.Activities.Carrinho;
import com.example.pythongames.Activities.Console;
import com.example.pythongames.Activities.Detalhes;
import com.example.pythongames.Activities.Eletronicos;
import com.example.pythongames.Activities.Jogos;
import com.example.pythongames.Activities.Login;
import com.example.pythongames.Activities.TesteGames;
import com.example.pythongames.Adapters.MeuAdapterCarrinho;
import com.example.pythongames.Apis.ConfigRetrofit;
import com.example.pythongames.Apis.TbProduto;
import com.example.pythongames.Dtos.DtoItemCarrinho;
import com.example.pythongames.Dtos.EmAlta;
import com.example.pythongames.Dtos.Produtos;
import com.example.pythongames.Adapters.MeuAdapter;
import com.example.pythongames.Dtos.Recomendados;
import com.example.pythongames.R;
import com.example.pythongames.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment implements MeuAdapter.OnProdListener {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    RecyclerView recycler, recycler2, recycler3;
    RecyclerView.LayoutManager layoutManager, layoutManager2, layoutManager3;
    CardView cardJogos, cardConsoles, cardEletronicos, btnTesteGame, btnCarrinho, logout, btnPromocao;
    ArrayList<Produtos> produto;
    ArrayList<Recomendados> recomendado;
    ArrayList<EmAlta> emAlta;
    Produtos prods;
    FirebaseAuth auth;
    ImageView wallpaper, wallpaper2;
    Retrofit retrofit;
    TbProduto tbProduto;
    MeuAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recycler = root.findViewById(R.id.recycler);
        recycler2 = root.findViewById(R.id.recycler2);
        recycler3 = root.findViewById(R.id.recycler3);

        cardJogos = root.findViewById(R.id.cardJogos);
        cardConsoles = root.findViewById(R.id.cardConsoles);
        cardEletronicos = root.findViewById(R.id.cardEletronicos);

        btnTesteGame = root.findViewById(R.id.btnTesteGame);
        btnCarrinho = root.findViewById(R.id.btnCarrinho);
        logout = root.findViewById(R.id.logout);
        btnPromocao = root.findViewById(R.id.btnPromocao);

        wallpaper = root.findViewById(R.id.wallpaper);
        wallpaper2 = root.findViewById(R.id.wallpaper2);

        auth = FirebaseAuth.getInstance();

        btnTesteGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeFragment.this.getActivity(), TesteGames.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(HomeFragment.this.getActivity()).toBundle());
            }
        });

        btnCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeFragment.this.getActivity(), Carrinho.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(HomeFragment.this.getActivity()).toBundle());
            }
        });

        cardJogos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeFragment.this.getActivity(), Jogos.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(HomeFragment.this.getActivity()).toBundle());
            }
        });

        cardConsoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeFragment.this.getActivity(), Console.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(HomeFragment.this.getActivity()).toBundle());
            }
        });

        cardEletronicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeFragment.this.getActivity(), Eletronicos.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(HomeFragment.this.getActivity()).toBundle());
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(HomeFragment.this.getActivity(), Login.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(HomeFragment.this.getActivity()).toBundle());
            }
        });

        btnPromocao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://linktr.ee/thegrowthtree")));
            }
        });

        Picasso.get().load("https://image.api.playstation.com/vulcan/ap/rnd/202101/2921/x64hEmgvhgxpXc9z9hpyLAyQ.jpg").into(wallpaper);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/tcc-3deec.appspot.com/o/logo.png?alt=media&token=97b3515d-4500-4147-900d-5c1d3995d0a1").into(wallpaper2);

        pegaItensCarrinho();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onProdClick(int position) {
        prods = (Produtos) produto.get(position);

        Intent intent = new Intent(HomeFragment.this.getActivity(), Detalhes.class);
        intent.putExtra("produto", prods);
        startActivity(intent);
    }

    private void pegaItensCarrinho() {
        retrofit = ConfigRetrofit.getRetrofit();
        tbProduto = retrofit.create(TbProduto.class);

        Call<ArrayList<Produtos>> call = tbProduto.apiProdutos();
        call.enqueue(new Callback<ArrayList<Produtos>>() {
            @Override
            public void onResponse(Call<ArrayList<Produtos>> call, Response<ArrayList<Produtos>> response) {
                produto = response.body();
                adapter = new MeuAdapter(produto, HomeFragment.this);

                layoutManager = new LinearLayoutManager(HomeFragment.this.getActivity(), RecyclerView.HORIZONTAL, false);
                recycler.setLayoutManager(layoutManager);
                recycler.setAdapter(adapter);

                layoutManager2 = new LinearLayoutManager(HomeFragment.this.getActivity(), RecyclerView.HORIZONTAL, false);
                recycler2.setLayoutManager(layoutManager2);
                recycler2.setAdapter(adapter);

                layoutManager3 = new LinearLayoutManager(HomeFragment.this.getActivity());
                recycler3.setLayoutManager(layoutManager3);
                recycler3.setAdapter(adapter);

                recycler3.setNestedScrollingEnabled(false);
            }

            @Override
            public void onFailure(Call<ArrayList<Produtos>> call, Throwable t) {

            }
        });

    }

}