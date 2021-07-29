package com.example.pythongames.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pythongames.Dtos.DtoItemCarrinho;
import com.example.pythongames.Dtos.Produtos;
import com.example.pythongames.R;

import java.util.ArrayList;

public class MeuAdapterCarrinho extends RecyclerView.Adapter<MeuAdapterCarrinho.MeuHolder> {
    private ArrayList<DtoItemCarrinho> dtoItemCarrinhos;
    private OnItensListener onItensListener;

    public MeuAdapterCarrinho(ArrayList<DtoItemCarrinho> dtoItemCarrinhos, OnItensListener onItensListener) {
        this.dtoItemCarrinhos = dtoItemCarrinhos;
        this.onItensListener = onItensListener;
    }

    @NonNull
    @Override
    public MeuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.meu_item_carrinho, parent, false);
        MeuHolder holder = new MeuHolder(itemLista, onItensListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MeuHolder holder, int position) {
        holder.txtNome.setText(dtoItemCarrinhos.get(position).getNm_item());
        holder.txtPreco.setText("R$: " + dtoItemCarrinhos.get(position).getPreco_item());
        holder.txtQuant.setText("x" + dtoItemCarrinhos.get(position).getQt_item());

    }

    @Override
    public int getItemCount() {
        return dtoItemCarrinhos.size();
    }

    class MeuHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView txtNome, txtPreco, txtQuant;
        OnItensListener onItensListener;

        public MeuHolder(@NonNull View itemView, OnItensListener onItensListener) {
            super(itemView);
            this.onItensListener = onItensListener;
            cv = (CardView) itemView.findViewById(R.id.cv);
            txtNome = (TextView) itemView.findViewById(R.id.txtNome);
            txtPreco = (TextView) itemView.findViewById(R.id.txtPreco);
            txtQuant = (TextView) itemView.findViewById(R.id.txtQuant);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItensListener.onItensClick(getAdapterPosition());
        }
    }

    public interface OnItensListener {
        void onItensClick(int position);
    }

}
