package com.example.pythongames.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pythongames.Dtos.DtoJogos;
import com.example.pythongames.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MeuAdapterJogos extends RecyclerView.Adapter<MeuAdapterJogos.MeuHolder> {
    private ArrayList<DtoJogos> jogos;
    private OnProdListener onProdListener;

    public MeuAdapterJogos(ArrayList<DtoJogos> jogos, OnProdListener onProdListener) {
        this.jogos = jogos;
        this.onProdListener = onProdListener;
    }

    @NonNull
    @Override
    public MeuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.meu_item, parent, false);
        MeuHolder holder = new MeuHolder(itemLista, onProdListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MeuHolder holder, int position) {
        holder.txtNome.setText(jogos.get(position).getNome());
        holder.txtPreco.setText("R$: " + jogos.get(position).getPreco());
        Picasso.get().load(jogos.get(position).getIdImagem()).into(holder.imgProd);

    }

    @Override
    public int getItemCount() {
        return jogos.size();
    }

    class MeuHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView txtNome, txtPreco;
        ImageView imgProd;
        OnProdListener onProdListener;

        public MeuHolder(@NonNull View itemView, OnProdListener onProdListener) {
            super(itemView);
            this.onProdListener = onProdListener;
            cv = (CardView) itemView.findViewById(R.id.cv);
            txtNome = (TextView) itemView.findViewById(R.id.txtNome);
            txtPreco = (TextView) itemView.findViewById(R.id.txtPreco);
            imgProd = (ImageView) itemView.findViewById(R.id.imgProd);

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
