package com.example.cinebox;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TarifsAdapter  extends RecyclerView.Adapter<TarifsAdapter.MyViewHolder> {

    private Integer id[];
    private String categorie[];
    private String prix[];
    private String description[];
    private Context context;

    public TarifsAdapter(Context context) {
        ArrayList<Integer> idArray = new ArrayList<>();
        ArrayList<String> categorieArray = new ArrayList<>();
        ArrayList<String> prixArray = new ArrayList<>();
        ArrayList<String> descriptionArray = new ArrayList<>();

        for(Tarif t: Tarif.TarifOnArrayList) {
            idArray.add(t.getId());
            categorieArray.add(t.getCategorie());
            prixArray.add(String.valueOf(t.getPrix()));
            descriptionArray.add(t.getDescription());
        }

        this.context = context;
        this.id = idArray.toArray(new Integer[idArray.size()]);
        this.categorie = categorieArray.toArray(new String[categorieArray.size()]);
        this.prix = prixArray.toArray(new String[prixArray.size()]);
        this.description = descriptionArray.toArray(new String[descriptionArray.size()]);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.tarif_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //holder.id.setText(String.valueOf(id[position]));
        holder.categorie.setText(categorie[position]);
        //holder.prix.setText(String.format("%.2f", prix[position] + "$"));
        holder.prix.setText(prix[position] + "$");
        holder.description.setText(description[position]);
    }

    @Override
    public int getItemCount() {
        return id.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView categorie, prix, description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            categorie = itemView.findViewById(R.id.tarifNom);
            prix = itemView.findViewById(R.id.tarifPrix);
            description = itemView.findViewById(R.id.tarifDesc);
        }
    }
}
