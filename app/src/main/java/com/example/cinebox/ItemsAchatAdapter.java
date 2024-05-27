/****************************************
 * Fichier : ItemAchatAdapter.java
 * Auteur : Arthur Andrianjafisolo
 * Fonctionnalité : Adapter pour chaque item dans les recycler view du détail de fatcure (billet comme grignotines)
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

package com.example.cinebox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsAchatAdapter extends RecyclerView.Adapter<ItemsAchatAdapter.ItemsAchatViewHolder> {

    private String[] nom;
    private String[] categorie;
    private Integer[] qte;
    private double[] prixUnitaire;
    private Context context;
    private RecyclerViewInterface recyclerViewInterface;

    /**
     * @param context
     * @param nom
     * @param categorie
     * @param qte
     * @param prixUnitaire
     * @param recyclerViewInterface
     *
     * Constructeur pour les billets acheté comme pour les grignotines acheté
     */
    public ItemsAchatAdapter(Context context, String[] nom, String[] categorie, Integer[] qte, double[] prixUnitaire, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.nom = nom;
        this.categorie = categorie;
        this.qte = qte;
        this.prixUnitaire = prixUnitaire;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ItemsAchatAdapter.ItemsAchatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_achat_row, parent, false);
        return new ItemsAchatViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAchatAdapter.ItemsAchatViewHolder holder, int position) {
        holder.nom.setText("- "+nom[position]);
        holder.categorie.setText(" ("+categorie[position]+") ");
        holder.qte.setText('x'+String.valueOf(qte[position]));
        holder.montantTot.setText(String.valueOf(prixUnitaire[position] * qte[position])+'$');
    }

    @Override
    public int getItemCount() {
        return nom.length;
    }

    public static class ItemsAchatViewHolder extends RecyclerView.ViewHolder {
        TextView nom;
        TextView categorie;
        TextView qte;
        TextView montantTot;

        public ItemsAchatViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            nom = itemView.findViewById(R.id.itemNom);
            categorie = itemView.findViewById(R.id.itemCategorie);
            qte = itemView.findViewById(R.id.itemQte);
            montantTot = itemView.findViewById(R.id.itemMontant);

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
