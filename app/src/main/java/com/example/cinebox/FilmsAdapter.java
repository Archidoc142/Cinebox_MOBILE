/****************************************
 * Fichier : FilmsAdapter
 * Auteur : Antoine Auger
 * Fonctionnalité : N/A
 * Date : 14 mai 2024
 * Vérification :
 * Date Nom Approuvé
 * =========================================================
 * Historique de modifications :
 * Date Nom Description
 * =========================================================****************************************/

package com.example.cinebox;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.MyViewHolder> {

    private Integer id[];
    private String nom[];
    private String images[];
    private Context context;

    public FilmsAdapter(Context context) {
        ArrayList<Integer> idArray = new ArrayList<>();
        ArrayList<String> nameArray = new ArrayList<>();
        ArrayList<String> imagesArray = new ArrayList<>();

        for(Film f: Film.FilmOnArrayList) {
            idArray.add(f.getId());
            nameArray.add(f.getTitre());
            imagesArray.add(f.getImage_affiche());
        }

        this.context = context;
        this.id = idArray.toArray(new Integer[idArray.size()]);
        this.nom = nameArray.toArray(new String[nameArray.size()]);
        this.images = imagesArray.toArray(new String[imagesArray.size()]);
    }

    @NonNull
    @Override
    public FilmsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.film_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmsAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.id.setText(String.valueOf(id[position]));
        holder.nom.setText(nom[position]);

        Glide.with(context)
                .load(images[position])
                .error(R.drawable.image_not_found)
                .into(holder.imageView);

        /**
         * Cliquer sur l'image d'un film permet d'accéder à ses informations
         */
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FilmActivity.class);
                intent.putExtra("id", id[position] - 1);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id.length;
    }

    /**
     * Les films qui ne sont pas dans la liste se voit retiré
     * @param list
     */
    public void filter(ArrayList<String> list) {
        ArrayList<Integer> idArray = new ArrayList<>();
        ArrayList<String> nomArray = new ArrayList<>();
        ArrayList<String> imagesArray = new ArrayList<>();

        for (Film f : Film.FilmOnArrayList)
        {
            if (list.contains(f.getTitre())) {
                idArray.add(f.getId());
                nomArray.add(f.getTitre());
                imagesArray.add(f.getImage_affiche());
            }
        }

        this.id = idArray.toArray(new Integer[idArray.size()]);
        this.nom = nomArray.toArray(new String[nomArray.size()]);
        this.images = imagesArray.toArray(new String[imagesArray.size()]);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id, nom;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.text_id);
            nom = itemView.findViewById(R.id.nom);
            imageView = itemView.findViewById(R.id.imageInstanceFilm);
        }
    }
}
