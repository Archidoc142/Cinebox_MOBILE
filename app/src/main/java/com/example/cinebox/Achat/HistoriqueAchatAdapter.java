/****************************************
 * Fichier : HistoriqueAchatAdapter.java
 * Auteur : Arthur Andrianjafisolo
 * Fonctionnalité : Adapter pour recycle view de l'historique d'achat
 * Date : 18/05/2023
 *
 * Vérification :
 * Date     Nom     Approuvé
 * =========================================================
 *
 *
 * Historique de modifications :
 * Date     Nom     Description
 * =========================================================
 *
 * ****************************************/

package com.example.cinebox.Achat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinebox.R;
import com.example.cinebox.RecyclerViewInterface;

public class HistoriqueAchatAdapter extends RecyclerView.Adapter<HistoriqueAchatAdapter.HistoriqueAchatViewHolder> {
    private Integer[] id;
    private String[] date;
    private double[] montant;
    private Context context;
    private RecyclerViewInterface recyclerViewInterface;

    public HistoriqueAchatAdapter(Context context, Integer[] id, String[] date, double[] montant, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.id = id;
        this.date = date;
        this.montant = montant;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public HistoriqueAchatAdapter.HistoriqueAchatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.achat_row, parent, false);
        return new HistoriqueAchatAdapter.HistoriqueAchatViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoriqueAchatAdapter.HistoriqueAchatViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.dateAchat.setText("- " + date[position]);
        holder.montantAchat.setText(String.valueOf(montant[position])+'$');
    }

    @Override
    public int getItemCount() {
        return id.length;
    }

    public static class HistoriqueAchatViewHolder extends RecyclerView.ViewHolder {
        TextView dateAchat;
        TextView montantAchat;

        public HistoriqueAchatViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            dateAchat = itemView.findViewById(R.id.dateAchat);
            montantAchat = itemView.findViewById(R.id.montantAchat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
