/****************************************
 * Fichier : ConsulterAchatActivity.java
 * Auteur : Arthur Andrianjafisolo
 * Fonctionnalité : Affichage des détails de chaque facture
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

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ConsulterAchatActivity extends AppCompatActivity implements RecyclerViewInterface{
    private String[] nomMovie = {"Movie1", "Movie2", "Movie3", "Movie4", "Movie5", "Movie6"};
    private String[] categorieMovie = {"Adulte", "Adolescent", "Enfant", "Vieux", "Jeune", "Moins Jeune"};
    private Integer[] qteMovie = {1, 2, 3, 4, 5, 6};
    private double[] prixUnitaireMovie = {12.0, 10.0, 8.5, 15.0, 20.0, 5.0};
    private String[] nomSnacks = {"Popcorn", "Nachos", "Soda", "Candy", "Hotdog", "Ice Cream"};
    private String[] categorieSnacks = {"Moyen", "Petit", "Grand", "Régulier", "Mini", "Huge"};
    private Integer[] qteSnacks = {1, 2, 1, 3, 2, 1};
    private double[] prixUnitaireSnacks = {5.0, 6.0, 3.0, 4.5, 7.0, 4.0};
    RecyclerView recyclerViewFilm,
                    recyclerViewSnack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_achat);

        recyclerViewFilm = (RecyclerView) findViewById(R.id.recycleBillet);
        recyclerViewSnack = (RecyclerView) findViewById(R.id.recycleSnack);

        ItemsAchatAdapter myAdapterFilm = new ItemsAchatAdapter(this, nomMovie, categorieMovie, qteMovie, prixUnitaireMovie, this);
        ItemsAchatAdapter myAdapterSnack = new ItemsAchatAdapter(this, nomSnacks, categorieSnacks, qteSnacks, prixUnitaireSnacks, this);

        recyclerViewFilm.setAdapter(myAdapterFilm);
        recyclerViewSnack.setAdapter(myAdapterSnack);

        recyclerViewFilm.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSnack.setLayoutManager(new LinearLayoutManager(this));

    }
    @Override
    public void onItemClick(int position) {

    }
}
