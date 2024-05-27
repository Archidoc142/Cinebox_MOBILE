/****************************************
 * Fichier : AccueilActivity.java
 * Auteur : Hicham Abekiri
 * Fonctionnalité : Page d'accueil (MAcc0)
 * Date : 17 mai 2024
 *
 * Vérification :
 * Date     Nom     Approuvé
 * =========================================================
 *
 *
 * Historique de modifications :
 * Date     Nom     Description
 * =========================================================
 * 17/05/2024   Hicham  Création de l'activité Accueil
 * 22/05/2024   Arthur  Ajout lien vers page de gestion du compte
 *
 * ****************************************/

package com.example.cinebox.Accueil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cinebox.APIRequests;
import com.example.cinebox.Compte.CompteActivity;
import com.example.cinebox.Film.FilmsActivity;
import com.example.cinebox.Grignotine.GrignotinesActivity;
import com.example.cinebox.Compte.LoginActivity;
import com.example.cinebox.Panier.PanierActivity;
import com.example.cinebox.R;
import com.example.cinebox.Tarif.TarifsActivity;
import com.example.cinebox.Compte.Utilisateur;

public class AccueilActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        View nav = findViewById(R.id.nav);

        TextView films = nav.findViewById(R.id.filmsNav);
        TextView grignotines = nav.findViewById(R.id.grignotinesNav);
        TextView tarifs = nav.findViewById(R.id.tarifsNav);
        TextView connexion = nav.findViewById(R.id.connexionNav);
        ImageView imageUser = nav.findViewById(R.id.imageProfil);
        ImageView listNav = nav.findViewById(R.id.listNav);
        ImageView cartNav = nav.findViewById(R.id.cartNav);

        if (Utilisateur.getInstance() != null) {
            connexion.setText("Se déconnecter");

            //Si aucune image de profil défini alors on mets l'avatar par défaut
            if(Utilisateur.getInstance().getImage() != null)
                imageUser.setImageBitmap(Utilisateur.getInstance().getImage());
            else
                imageUser.setImageResource(R.drawable.profil_image);
        } else {
            imageUser.setImageBitmap(null);
            imageUser.setVisibility(View.INVISIBLE);
            //listNav.setVisibility(View.INVISIBLE);
            cartNav.setVisibility(View.INVISIBLE);
        }

        connexion.setOnClickListener(this);
        films.setOnClickListener(this);
        grignotines.setOnClickListener(this);
        tarifs.setOnClickListener(this);
        listNav.setOnClickListener(this);
        imageUser.setOnClickListener(this);
        cartNav.setOnClickListener(this);

        RecyclerView filmsRecycler = findViewById(R.id.filmRecycler),
                snacksRecycler = findViewById(R.id.snackRecycler);

        filmsRecycler.setNestedScrollingEnabled(false);
        snacksRecycler.setNestedScrollingEnabled(false);

        AccueilAdapter accueilAdapter = new AccueilAdapter();

        AccueilAdapter.FilmsAdapterAccueil filmAdapter = accueilAdapter.new FilmsAdapterAccueil(AccueilActivity.this);
        AccueilAdapter.GrignotinesAdapterAccueil snackAdapter = accueilAdapter.new GrignotinesAdapterAccueil(AccueilActivity.this);

        GridLayoutManager filmLayoutManager = new GridLayoutManager(AccueilActivity.this, 2),
                snackLayoutManager = new GridLayoutManager(AccueilActivity.this, 2);

        filmsRecycler.setAdapter(filmAdapter);
        filmsRecycler.setLayoutManager(filmLayoutManager);

        snacksRecycler.setAdapter(snackAdapter);
        snacksRecycler.setLayoutManager(snackLayoutManager);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.filmsNav) {
            Intent intent = new Intent(AccueilActivity.this, FilmsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.grignotinesNav) {
            Intent intent = new Intent(AccueilActivity.this, GrignotinesActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.tarifsNav) {
            Intent intent = new Intent(AccueilActivity.this, TarifsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.imageProfil) {
            Intent intent = new Intent(AccueilActivity.this, CompteActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.cartNav) {
            Intent intent = new Intent(AccueilActivity.this, PanierActivity.class);
            startActivity(intent);
        }
        else if (v.getId() == R.id.listNav) {
            LinearLayout nav_elements = findViewById(R.id.nav_elements);
            if (nav_elements.getVisibility() == View.GONE) {
                nav_elements.setVisibility(View.VISIBLE);
            } else {
                nav_elements.setVisibility(View.GONE);
            }
        } else if (v.getId() == R.id.connexionNav) {
            if (Utilisateur.getInstance() != null)
            {
                Utilisateur.logOutUser(this);

                View nav = findViewById(R.id.nav);
                TextView connexion = nav.findViewById(R.id.connexionNav);
                connexion.setText("Se connecter");
                finish();
            }

            Intent intent = new Intent(AccueilActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}