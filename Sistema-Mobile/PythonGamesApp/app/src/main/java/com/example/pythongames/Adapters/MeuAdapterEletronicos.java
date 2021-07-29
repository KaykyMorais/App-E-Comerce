package com.example.pythongames.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pythongames.Dtos.DtoConsoles;
import com.example.pythongames.Dtos.DtoEletronicos;
import com.example.pythongames.Dtos.DtoJogos;
import com.example.pythongames.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MeuAdapterEletronicos extends RecyclerView.Adapter<MeuAdapterEletronicos.MeuHolder> {
    private ArrayList<DtoEletronicos> eletronicos;
    private OnProdListener onProdListener;

    public MeuAdapterEletronicos(ArrayList<DtoEletronicos> eletronicos, OnProdListener onProdListener) {
        this.eletronicos = eletronicos;
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
        holder.txtNome.setText(eletronicos.get(position).getNome());
        holder.txtPreco.setText("R$: " + eletronicos.get(position).getPreco());
        Picasso.get().load(eletronicos.get(position).getIdImagem()).into(holder.imgProd);

    }

    @Override
    public int getItemCount() {
        return eletronicos.size();
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
