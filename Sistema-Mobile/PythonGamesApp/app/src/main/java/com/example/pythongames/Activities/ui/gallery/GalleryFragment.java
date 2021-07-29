package com.example.pythongames.Activities.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pythongames.Activities.Carrinho;
import com.example.pythongames.Activities.TesteGames;
import com.example.pythongames.Adapters.MeuAdapterCarrinho;
import com.example.pythongames.Adapters.MeuAdapterTestes;
import com.example.pythongames.Apis.ConfigRetrofit;
import com.example.pythongames.Apis.TbProduto;
import com.example.pythongames.Dtos.Cliente;
import com.example.pythongames.Dtos.DtoTesteGame;
import com.example.pythongames.Dtos.Historico;
import com.example.pythongames.R;
import com.example.pythongames.databinding.FragmentGalleryBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GalleryFragment extends Fragment implements MeuAdapterTestes.OnProdListener {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;
    ListView lv;
    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<DtoTesteGame> arrayList;
    ArrayList<Historico> historico;
    Retrofit retrofit;
    TbProduto tbProduto;
    FirebaseAuth auth;
    String usuario;
    MeuAdapterTestes adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        rv = root.findViewById(R.id.rv);
        lv = root.findViewById(R.id.lv);

        pegaDados();
        pegaHistorico();

        return root;
    }

    private void pegaHistorico() {
        auth = FirebaseAuth.getInstance();
        retrofit = ConfigRetrofit.getRetrofit();
        tbProduto = retrofit.create(TbProduto.class);
        usuario = auth.getCurrentUser().getEmail();
        Call<ArrayList<Historico>> call = tbProduto.apiConsultaHistorico(usuario);
        call.enqueue(new Callback<ArrayList<Historico>>() {
            @Override
            public void onResponse(Call<ArrayList<Historico>> call, Response<ArrayList<Historico>> response) {
                historico = response.body();
                ArrayAdapter adapter = new ArrayAdapter(GalleryFragment.this.getContext(), android.R.layout.simple_list_item_1, historico);

                lv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Historico>> call, Throwable t) {

            }
        });
    }

    private void pegaDados() {
        auth = FirebaseAuth.getInstance();
        retrofit = ConfigRetrofit.getRetrofit();
        tbProduto = retrofit.create(TbProduto.class);
        usuario = auth.getCurrentUser().getEmail();
        Call<ArrayList<DtoTesteGame>> call = tbProduto.apiConsultaTeste(usuario);
        call.enqueue(new Callback<ArrayList<DtoTesteGame>>() {
            @Override
            public void onResponse(Call<ArrayList<DtoTesteGame>> call, Response<ArrayList<DtoTesteGame>> response) {
                arrayList = response.body();

                adapter = new MeuAdapterTestes(arrayList, GalleryFragment.this);
                layoutManager = new LinearLayoutManager(GalleryFragment.this.getContext());
                rv.setLayoutManager(layoutManager);
                rv.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<ArrayList<DtoTesteGame>> call, Throwable t) {
                Log.d("erradao", t.getMessage());
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onProdClick(int position) {

    }
}