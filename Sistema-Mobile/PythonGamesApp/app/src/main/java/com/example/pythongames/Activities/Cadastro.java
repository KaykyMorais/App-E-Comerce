package com.example.pythongames.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pythongames.Activities.ui.gallery.GalleryFragment;
import com.example.pythongames.Apis.ConfigRetrofit;
import com.example.pythongames.Apis.TbProduto;
import com.example.pythongames.Dtos.Cliente;
import com.example.pythongames.Dtos.Produtos;
import com.example.pythongames.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class Cadastro extends AppCompatActivity {
    EditText editNome, editCpf, editEmail, editSenha;
    Button btnCadastra;
    FirebaseAuth auth;
    ImageView wallpaper;
    FirebaseDatabase db;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editNome = findViewById(R.id.editNome);
        editCpf = findViewById(R.id.editCpf);
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);

        btnCadastra = findViewById(R.id.btnCadastra);
        wallpaper = findViewById(R.id.wallpaper);

        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/tcc-3deec.appspot.com/o/WhatsApp%20Image%202021-05-19%20at%2023.36.40%20(1).jpeg?alt=media&token=adaebe24-6a2f-49f5-b300-0e97355dc29f").into(wallpaper);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        ref = db.getReference().child("Usuários");

        mascaras();

        btnCadastra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editCpf.length() < 11) {
                    Toast.makeText(Cadastro.this, "CPF inválido, tente novamente!", Toast.LENGTH_SHORT).show();
                } else {
                    if (editSenha.length() < 6) {
                        Toast.makeText(Cadastro.this, "Senha inválida, tente novamente!", Toast.LENGTH_SHORT).show();
                    } else {
                        criaUsuarioFirebase();
                        Intent intent = new Intent(Cadastro.this, Login.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void mascaras() {
        SimpleMaskFormatter simpleMaskCpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher maskCpf = new MaskTextWatcher(editCpf, simpleMaskCpf);
        editCpf.addTextChangedListener(maskCpf);

    }

    private void criaUsuarioFirebase() {
        auth.createUserWithEmailAndPassword(editEmail.getText().toString(), editSenha.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Cadastro.this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Cadastro.this, "Falha ao cadastrar usuário, tente novamente!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        Cliente cliente = new Cliente(editNome.getText().toString(), editCpf.getText().toString(), editEmail.getText().toString());

        ref.child(editNome.getText().toString()).setValue(cliente).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("TAG", "Task finished");
                } else {
                    Log.d("TAG", "Failed to finish task");
                }
            }
        });

    }

    public void onBackPressed() {
    }
}