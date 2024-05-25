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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ConsulterAchatActivity extends AppCompatActivity implements RecyclerViewInterface, View.OnClickListener {
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

        View nav = findViewById(R.id.nav);

        TextView films = nav.findViewById(R.id.filmsNav);
        TextView grignotines = nav.findViewById(R.id.grignotinesNav);
        TextView tarifs = nav.findViewById(R.id.tarifsNav);
        TextView connexion = nav.findViewById(R.id.connexionNav);
        ImageView imageUser = nav.findViewById(R.id.imageInstanceFilm);
        ImageView listNav = nav.findViewById(R.id.listNav);
        ImageView cartNav = nav.findViewById(R.id.cartNav);
        TextView mainTitle = nav.findViewById(R.id.mainTitle);

        if (Utilisateur.getInstance() != null) {
            connexion.setText("Se déconnecter");
            if(Utilisateur.getInstance().getImage() != null)
                imageUser.setImageBitmap(Utilisateur.getInstance().getImage());
            else
                imageUser.setImageResource(R.drawable.profil_image);
        } else {
            listNav.setVisibility(View.INVISIBLE);
            cartNav.setVisibility(View.INVISIBLE);
        }

        connexion.setOnClickListener(this);
        films.setOnClickListener(this);
        grignotines.setOnClickListener(this);
        tarifs.setOnClickListener(this);
        listNav.setOnClickListener(this);
        mainTitle.setOnClickListener(this);
        cartNav.setOnClickListener(this);
        imageUser.setOnClickListener(this);
    }
    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.filmsNav) {
            Intent intent = new Intent(ConsulterAchatActivity.this, FilmsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.grignotinesNav) {
            Intent intent = new Intent(ConsulterAchatActivity.this, GrignotinesActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.tarifsNav) {
            Intent intent = new Intent(ConsulterAchatActivity.this, TarifsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.cartNav) {
                Intent intent = new Intent(ConsulterAchatActivity.this, PanierActivity.class);
                startActivity(intent);
        } else if (v.getId() == R.id.mainTitle) {
                Intent intent = new Intent(ConsulterAchatActivity.this, AccueilActivity.class);
                startActivity(intent);
        } else if (v.getId() == R.id.imageInstanceFilm) {
            Intent intent = new Intent(ConsulterAchatActivity.this, CompteActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.listNav) {
            LinearLayout nav_elements = findViewById(R.id.nav_elements);
            if (nav_elements.getVisibility() == View.GONE) {
                nav_elements.setVisibility(View.VISIBLE);
            } else {
                nav_elements.setVisibility(View.GONE);
            }
        } else if (v.getId() == R.id.connexionNav) {
            if (Utilisateur.getInstance() != null) {
                Utilisateur.logOutUser(this);

                View nav = findViewById(R.id.nav);
                TextView connexion = nav.findViewById(R.id.connexionNav);
                connexion.setText("Se connecter");
            } else {
                Intent intent = new Intent(ConsulterAchatActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
    }
}
