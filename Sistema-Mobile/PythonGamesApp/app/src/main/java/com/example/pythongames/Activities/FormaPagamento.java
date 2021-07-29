package com.example.pythongames.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.pythongames.R;
import com.squareup.picasso.Picasso;

public class FormaPagamento extends AppCompatActivity {
    String formaPagamento = "";
    Button btnConclui;
    ImageView wallpaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forma_pagamento);

        btnConclui = findViewById(R.id.btnConclui);
        wallpaper = findViewById(R.id.wallpaper);

        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/tcc-3deec.appspot.com/o/WhatsApp%20Image%202021-05-19%20at%2023.36.40%20(1).jpeg?alt=media&token=adaebe24-6a2f-49f5-b300-0e97355dc29f").into(wallpaper);

        btnConclui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormaPagamento.this, Carrinho.class);
                intent.putExtra("formaPagamento", formaPagamento);
                startActivity(intent);
            }
        });

    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.dinheiro:
                if (checked)
                    formaPagamento = "Dinheiro";
                break;
            case R.id.cartao:
                if (checked)
                    formaPagamento = "Cartão crédito/débito";
                break;
        }
    }
}