/****************************************
 * Fichier : PanierAdapter
 * Auteur : Amélie Bergeron
 * Fonctionnalité : Supprimer un élément du panier
 * Date : 14 mai 2024
 * Vérification :
 * Date Nom Approuvé
 * =========================================================
 * Historique de modifications :
 * 20 mai 2024, Amélie Bergeron, RecyclerView fonctionnel
 * 24 mai 2024, Hicham Abekiri, Suppression d'un élément du panier fonctionnel
 * =========================================================****************************************/

package com.example.cinebox.Panier;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinebox.Billet.Billet;
import com.example.cinebox.Grignotine.GrignotineQuantite;
import com.example.cinebox.R;

import java.util.ArrayList;

public class PanierAdapter extends RecyclerView.Adapter<PanierAdapter.MyViewHolder> {
    //private  final  RecyclerViewInterface rvInt;
    //Context context;
    //ArrayList<Grignotine> snackArray_panier;
   // ArrayList<Billet> billetArray_panier;    //FILMMMMM

    private Integer id[];
    private String nom[];
    private String prix[];
    private String type[];
    private String format[];
    private Context context;


    public PanierAdapter(Context context) {
        updatePanier();

        this.context = context;
    }

    private void updatePanier()
    {
        ArrayList<Integer> idArray = new ArrayList<>();
        ArrayList<String> nomArray = new ArrayList<>();
        ArrayList<String> prixArray = new ArrayList<>();
        ArrayList<String> typeArray = new ArrayList<>();
        ArrayList<String> formatArray = new ArrayList<>();

        for (Billet b : Panier.Billet_PanierList)
        {
            idArray.add(b.getId());
            nomArray.add(b.getSeance().getFilm().getTitre());
            prixArray.add(String.valueOf(b.getMontant()));
            typeArray.add("Billet");
            formatArray.add(b.getTarif().getCategorie());
        }

        for (GrignotineQuantite g : Panier.Snack_PanierList)
        {
            idArray.add(g.getId());
            nomArray.add(g.getQuantite() + "x " + g.getGrignotine().getMarque());
            prixArray.add(String.valueOf(g.getPrixQte()));
            typeArray.add(g.getGrignotine().getCategorie());
            formatArray.add(g.getGrignotine().getFormat());
        }

        this.id = idArray.toArray(new Integer[idArray.size()]);
        this.nom = nomArray.toArray(new String[nomArray.size()]);
        this.prix = prixArray.toArray(new String[prixArray.size()]);
        this.type = typeArray.toArray(new String[typeArray.size()]);
        this.format = formatArray.toArray(new String[formatArray.size()]);
    }

    @NonNull
    @Override
    public PanierAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater infl = LayoutInflater.from(context);
        View v = infl.inflate(R.layout.panier_item, parent, false);

        return new PanierAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PanierAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        holder.nom.setText(nom[position]);
        holder.prix.setText(prix[position] + "$");
        holder.type.setText(type[position]);
        holder.format.setText(format[position]);

        holder.supp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int index = holder.getAdapterPosition();

                if(type[index] == "Billet")
                {
                    Panier.removeBillet(id[index]);
                }
                else
                {
                    Panier.removeGrignotine(id[index]);
                }

                PanierAdapter.this.updatePanier();
                PanierAdapter.this.notifyItemRemoved(index);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nom.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView supp;
        TextView nom, prix, type, format;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nom = itemView.findViewById(R.id.panierNom);
            prix = itemView.findViewById(R.id.panierPrix);
            type = itemView.findViewById(R.id.panierType);
            format = itemView.findViewById(R.id.panierFormat);

            supp = itemView.findViewById(R.id.btn_poubelle);
            supp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("delete");
                   // Panier.suppItem(itemView.getId());

                    //supprimer cet éélément
                }
            });

        }
    }

}
