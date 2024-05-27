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
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinebox.R;
import com.example.cinebox.RecyclerViewInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public HistoriqueAchatAdapter(Context context, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        updateData();
    }

    private void updateData()
    {
        int size = Achat.HistoriqueAchats.size();
        id = new Integer[size];
        date = new String[size];
        montant = new double[size];

        for (int i = 0; i < size; i++) {
            Achat achat = Achat.HistoriqueAchats.get(i);
            id[i] = achat.getId();

            /*
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateFormat = null;
            try {
                dateFormat = inputFormat.parse(achat.getDate());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            String formattedDate = null;
            // Extraire data
            formattedDate = outputFormat.format(dateFormat);
            date[i] = formattedDate;*/
            date[i] = achat.getDate();
            montant[i] = achat.getMontantFinal();
        }

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


        holder.montantAchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ConsulterAchatActivity.class);

                intent.putExtra("idAchat", position);

                context.startActivity(intent);
            }
        });
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
