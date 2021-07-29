package com.example.pythongames.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pythongames.Dtos.DtoTesteGame;
import com.example.pythongames.Dtos.Produtos;
import com.example.pythongames.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MeuAdapterTestes extends RecyclerView.Adapter<MeuAdapterTestes.MeuHolder> {
    private ArrayList<DtoTesteGame> testeGames;
    private OnProdListener onProdListener;

    public MeuAdapterTestes(ArrayList<DtoTesteGame> testeGames, OnProdListener onProdListener) {
        this.testeGames = testeGames;
        this.onProdListener = onProdListener;
    }

    @NonNull
    @Override
    public MeuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.meu_item_teste, parent, false);
        MeuHolder holder = new MeuHolder(itemLista, onProdListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MeuHolder holder, int position) {
        holder.txtNome.setText(testeGames.get(position).getNm_jogo());
        holder.txtData.setText("Data: " + testeGames.get(position).getDt_jogo());
        holder.txtDurac.setText("Duração: " + testeGames.get(position).getDuracao());
        holder.txtPlataforma.setText("Plataforma: " + testeGames.get(position).getPlataforma());
        holder.txtPreco.setText(String.valueOf("Valor: " + testeGames.get(position).getValor()));

    }

    @Override
    public int getItemCount() {
        return testeGames.size();
    }

    class MeuHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView txtNome, txtData, txtDurac, txtPlataforma, txtPreco;
        ImageView imgProd;
        OnProdListener onProdListener;

        public MeuHolder(@NonNull View itemView, OnProdListener onProdListener) {
            super(itemView);
            this.onProdListener = onProdListener;
            cv = (CardView) itemView.findViewById(R.id.cv);
            txtNome = (TextView) itemView.findViewById(R.id.txtNome);
            txtData = (TextView) itemView.findViewById(R.id.txtData);
            txtDurac = (TextView) itemView.findViewById(R.id.txtDurac);
            txtPlataforma = (TextView) itemView.findViewById(R.id.txtPlataforma);
            txtPreco = (TextView) itemView.findViewById(R.id.txtPreco);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onProdListener.onProdClick(getAdapterPosition());
        }
    }

    public interface OnProdListener {
        void onProdClick(int position);
    }

}