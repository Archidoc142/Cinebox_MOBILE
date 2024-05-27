/****************************************
 * Fichier : GrignotinesAdapter
 * Auteur : Antoine Auger et Amélie Bergeron
 * Fonctionnalité : Ajouter une grignotine au panier en cliquant sur son nom
 * Date : 14 mai 2024
 * Vérification :
 * Date Nom Approuvé
 * =========================================================
 * Historique de modifications :
 * 24 mai 2024, Amélie Bergeron, Ajout d'une grignotine dans le panier (+ mise à jour de sa quantité dans la liste du panier)
 * Date Nom Description
 * =========================================================****************************************/

package com.example.cinebox;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GrignotinesAdapter extends RecyclerView.Adapter<GrignotinesAdapter.MyViewHolder> {

    private Integer id[];
    private String nom[];
    private String images[];
    private Context context;

    public GrignotinesAdapter(Context context) {
        ArrayList<Integer> idArray = new ArrayList<>();
        ArrayList<String> nameArray = new ArrayList<>();
        ArrayList<String> imagesArray = new ArrayList<>();

        for(Grignotine g: Grignotine.GrignotineOnArrayList) {
            idArray.add(g.getId());
            nameArray.add(g.getCategorie() + " (" + g.getFormat() + ")");
            imagesArray.add(g.getImage());
        }

        this.context = context;
        this.id = idArray.toArray(new Integer[idArray.size()]);
        this.nom = nameArray.toArray(new String[nameArray.size()]);
        this.images = imagesArray.toArray(new String[imagesArray.size()]);
    }

    @NonNull
    @Override
    public GrignotinesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.grignotine_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GrignotinesAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.id.setText(String.valueOf(id[position]));
        holder.nom.setText(nom[position]);

        Glide.with(context)
                .load(images[position])
                .error(R.drawable.image_not_found)
                .into(holder.imageView);

        /**
         * Cliquer sur le nom d'une grignotine permet de l'ajouter au panier
         */
        holder.nom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int oldsize = Panier.Snack_PanierList.size();
                int oldqte = 0;
                int indexGQ = 0;
                boolean dansListe = false;

                for(GrignotineQuantite gq : Panier.Snack_PanierList) {
                    if(gq.getGrignotine().equals(Grignotine.GrignotineOnArrayList.get(position))) {
                        dansListe = true;
                        indexGQ = Panier.Snack_PanierList.indexOf(gq);
                    }
                }

                if(dansListe) {
                    oldqte = Panier.Snack_PanierList.get(indexGQ).getQuantite();
                    Panier.Snack_PanierList.get(indexGQ).setQuantite(Panier.Snack_PanierList.get(indexGQ).getQuantite() + 1);
                } else {
                    Panier.Snack_PanierList.add(new GrignotineQuantite(Grignotine.GrignotineOnArrayList.get(position), 1));
                }

                if(Panier.Snack_PanierList.size() == (oldsize + 1)  || Panier.Snack_PanierList.get(indexGQ).getQuantite() == (oldqte + 1)) {
                    Toast.makeText(context, "La grignotine a été ajoutée au panier.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "La grignotine n'a pas été ajoutée au panier.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return id.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        Button nom;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.text_id);
            nom = itemView.findViewById(R.id.nom);
            imageView = itemView.findViewById(R.id.imageInstanceGrignotine);
        }
    }
}
