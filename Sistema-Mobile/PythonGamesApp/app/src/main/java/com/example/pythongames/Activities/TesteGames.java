package com.example.pythongames.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pythongames.Activities.ui.slideshow.SlideshowFragment;
import com.example.pythongames.Apis.ConfigRetrofit;
import com.example.pythongames.Apis.TbProduto;
import com.example.pythongames.Dtos.DtoJogos;
import com.example.pythongames.R;
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

public class TesteGames extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Spinner spinnerJogo, spinnerPlat, spinnerDurac;
    Retrofit retrofit;
    TbProduto tbProduto;
    ImageView wallpaper;
    ArrayAdapter adapter, adapter2, adapter3;
    Button btnData, btnAgenda;
    TextView valor, data;
    String usuario, jogo, dataTeste, duracao, plataforma;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    ArrayList<DtoJogos> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_games);

        spinnerDurac = findViewById(R.id.spinnerDurac);
        spinnerPlat = findViewById(R.id.spinnerPlat);
        spinnerJogo = findViewById(R.id.spinnerJogo);
        wallpaper = findViewById(R.id.wallpaper);
        valor = findViewById(R.id.valor);
        btnData = findViewById(R.id.btnData);
        data = findViewById(R.id.data);
        btnAgenda = findViewById(R.id.btnAgenda);

        btnAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agendaTeste();
            }
        });

        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valor.setText("R$: 50");
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(TesteGames.this.getSupportFragmentManager(), "Date Picker");
            }
        });

        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/tcc-3deec.appspot.com/o/WhatsApp%20Image%202021-05-19%20at%2023.36.40%20(1).jpeg?alt=media&token=adaebe24-6a2f-49f5-b300-0e97355dc29f").into(wallpaper);

        carregaDurac();
        carregaJogos();
        carregaPlataformas();
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
                Toast.makeText(TesteGames.this, "Teste agendado!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.d("falha teste", t.getMessage());
                Toast.makeText(TesteGames.this, "Falha ao agendar teste!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void carregaDurac() {
        List<String> lista = new ArrayList<>();
        lista.add("1h");
        lista.add("1h30");
        lista.add("2h");

        adapter3 = new ArrayAdapter(TesteGames.this, android.R.layout.simple_list_item_1, lista);

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

        adapter2 = new ArrayAdapter(TesteGames.this, android.R.layout.simple_list_item_1, lista);

        spinnerPlat.setAdapter(adapter2);
    }

    private void carregaJogos() {
        retrofit = ConfigRetrofit.getRetrofit();
        tbProduto = retrofit.create(TbProduto.class);

        Call<ArrayList<DtoJogos>> call = tbProduto.apiJogos();
        call.enqueue(new Callback<ArrayList<DtoJogos>>() {
            @Override
            public void onResponse(Call<ArrayList<DtoJogos>> call, Response<ArrayList<DtoJogos>> response) {
                arrayList = response.body();

                adapter = new ArrayAdapter(TesteGames.this, android.R.layout.simple_list_item_1, arrayList);

                spinnerJogo.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<DtoJogos>> call, Throwable t) {

            }
        });

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