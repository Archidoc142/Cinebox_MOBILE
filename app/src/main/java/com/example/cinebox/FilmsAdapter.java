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

public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.MyViewHolder> {

    private int id[];
    private String nom[];
    private String images[];
    private Context context;

    public FilmsAdapter(Context context, int id[], String nom[], String images[]) {
        this.context = context;
        this.id = id;
        this.nom = nom;
        this.images = images;
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

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Film.class);
                intent.putExtra("id", id[position]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id.length;
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
