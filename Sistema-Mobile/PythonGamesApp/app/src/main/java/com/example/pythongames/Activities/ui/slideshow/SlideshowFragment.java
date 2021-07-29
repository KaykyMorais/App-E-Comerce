package com.example.pythongames.Activities.ui.slideshow;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pythongames.Activities.DatePickerFragment;
import com.example.pythongames.Activities.Jogos;
import com.example.pythongames.Adapters.MeuAdapterJogos;
import com.example.pythongames.Apis.ConfigRetrofit;
import com.example.pythongames.Apis.TbProduto;
import com.example.pythongames.Dtos.DtoJogos;
import com.example.pythongames.R;
import com.example.pythongames.databinding.FragmentSlideshowBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SlideshowFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private SlideshowViewModel slideshowViewModel;
    private FragmentSlideshowBinding binding;
    Spinner spinnerJogo, spinnerPlat, spinnerDurac;
    Retrofit retrofit;
    TbProduto tbProduto;
    ImageView wallpaper;
    ArrayAdapter adapter, adapter2, adapter3;
    Button btnData, btnAgenda;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    TextView valor, data;
    String usuario, jogo, dataTeste, duracao, plataforma;
    DtoJogos dtoJogos;
    ArrayList<DtoJogos> arrayList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        spinnerDurac = root.findViewById(R.id.spinnerDurac);
        spinnerPlat = root.findViewById(R.id.spinnerPlat);
        spinnerJogo = root.findViewById(R.id.spinnerJogo);
        wallpaper = root.findViewById(R.id.wallpaper);
        valor = root.findViewById(R.id.valor);
        btnData = root.findViewById(R.id.btnData);
        data = root.findViewById(R.id.data);
        btnAgenda = root.findViewById(R.id.btnAgenda);

        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valor.setText("R$: 50");
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.setTargetFragment(SlideshowFragment.this, 0);
                datePicker.show(getFragmentManager(), "Date Picker");
            }
        });

        btnAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agendaTeste();
            }
        });

        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/tcc-3deec.appspot.com/o/WhatsApp%20Image%202021-05-19%20at%2023.36.40%20(1).jpeg?alt=media&token=adaebe24-6a2f-49f5-b300-0e97355dc29f").into(wallpaper);

        carregaDurac();
        carregaJogos();
        carregaPlataformas();

        return root;
    }

    private void agendaTeste() {
        retrofit = ConfigRetrofit.getRetrofit();
        tbProduto = retrofit.create(TbProduto.class);

        jogo = spinnerJogo.getSelectedItem().toString();
        dataTeste = data.getText().toString();
        duracao = spinnerDurac.getSelectedItem().toString();
        plataforma = spinnerPlat.getSelectedItem().toString();
        usuario = auth.getCurrentUser().getEmail();

        Call<Boolean> call = tbProduto.apiTestes(jogo, dataTeste, duracao, plataforma, usuario, 50);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Toast.makeText(SlideshowFragment.this.getContext(), "Teste agendado!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.d("falja", t.getMessage());
                Toast.makeText(SlideshowFragment.this.getContext(), "Falha ao agendar teste!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void carregaDurac() {
        List<String> lista = new ArrayList<>();
        lista.add("1h");
        lista.add("1h30");
        lista.add("2h");

        adapter3 = new ArrayAdapter(SlideshowFragment.this.getContext(), android.R.layout.simple_list_item_1, lista);

        spinnerDurac.setAdapter(adapter3);

    }

    private void carregaPlataformas() {
        List<String> lista = new ArrayList<>();
        lista.add("PS5");
        lista.add("PS4");
        lista.add("Xbox One");
        lista.add("Xbox Series X");
        lista.add("PC");
        lista.add("Nintendo Switch");

        adapter2 = new ArrayAdapter(SlideshowFragment.this.getContext(), android.R.layout.simple_list_item_1, lista);

        spinnerPlat.setAdapter(adapter2);
    }

    private void carregaJogos() {
        retrofit = ConfigRetrofit.getRetrofit();
        tbProduto = retrofit.create(TbProduto.class);
        dtoJogos = new DtoJogos();

        Call<ArrayList<DtoJogos>> call = tbProduto.apiJogos();
        call.enqueue(new Callback<ArrayList<DtoJogos>>() {
            @Override
            public void onResponse(Call<ArrayList<DtoJogos>> call, Response<ArrayList<DtoJogos>> response) {
                arrayList = response.body();

                adapter = new ArrayAdapter(SlideshowFragment.this.getContext(), android.R.layout.simple_list_item_1, arrayList);

                spinnerJogo.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<DtoJogos>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String dataAtual = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(c.getTime());
        data.setText(dataAtual);
    }
}