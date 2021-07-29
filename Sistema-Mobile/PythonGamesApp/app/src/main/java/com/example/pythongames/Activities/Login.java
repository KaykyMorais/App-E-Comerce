package com.example.pythongames.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pythongames.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class Login extends AppCompatActivity {
    EditText editEmail, editSenha;
    TextView txtCad;
    CardView cardView;
    FirebaseAuth auth;
    ImageView wallpaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        cardView = findViewById(R.id.cardView);
        txtCad = findViewById(R.id.txtCad);
        wallpaper = findViewById(R.id.wallpaper);

        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/tcc-3deec.appspot.com/o/WhatsApp%20Image%202021-05-19%20at%2023.36.40%20(1).jpeg?alt=media&token=adaebe24-6a2f-49f5-b300-0e97355dc29f").into(wallpaper);

        txtCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Cadastro.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(Login.this).toBundle());
            }
        });

        auth = FirebaseAuth.getInstance();

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editEmail.length() <= 0 && editSenha.length() <= 0) {
                    Toast.makeText(Login.this, "Preencha os campos para prosseguir", Toast.LENGTH_SHORT).show();
                } else {
                    login();
                }
            }
        });

    }

    private void login() {
        if (editEmail.length() <= 0 && editSenha.length() < 0) {
            Toast.makeText(Login.this, "Preencha os campos para prosseguir!", Toast.LENGTH_SHORT).show();
        } else {
            auth.signInWithEmailAndPassword(editEmail.getText().toString(), editSenha.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Login.this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = auth.getCurrentUser();
                                updateUI(user);
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Login.this, "Email ou senha inválidos, tente novamente", Toast.LENGTH_SHORT).show();
                                editEmail.setText("");
                                editSenha.setText("");
                                updateUI(null);
                            }
                        }
                    });
        }
    }

    private void updateUI(FirebaseUser user) {
    }

    @Override
    public void onBackPressed() {

    }

}