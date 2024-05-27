/****************************************
 * Fichier : Films
 * Auteur : Antoine Auger
 * Fonctionnalité : MFi2, MFi3
 * Date : 14 mai 2024
 * Vérification :
 * Date Nom Approuvé
 * =========================================================
 * Historique de modifications :
 * Date Nom Description
 * =========================================================****************************************/

package com.example.cinebox.Film;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cinebox.Accueil.AccueilActivity;
import com.example.cinebox.Compte.CompteActivity;
import com.example.cinebox.Compte.LoginActivity;
import com.example.cinebox.Compte.Utilisateur;
import com.example.cinebox.Grignotine.GrignotinesActivity;
import com.example.cinebox.Panier.PanierActivity;
import com.example.cinebox.R;
import com.example.cinebox.Tarif.TarifsActivity;

public class FilmsActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_films);

        View nav = findViewById(R.id.nav);

        TextView grignotines = nav.findViewById(R.id.grignotinesNav);
        TextView tarifs = nav.findViewById(R.id.tarifsNav);
        TextView connexion = nav.findViewById(R.id.connexionNav);
        ImageView imageUser = nav.findViewById(R.id.imageProfil);
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
            imageUser.setVisibility(View.INVISIBLE);
            //listNav.setVisibility(View.INVISIBLE);
            cartNav.setVisibility(View.INVISIBLE);
        }

        connexion.setOnClickListener(this);
        grignotines.setOnClickListener(this);
        tarifs.setOnClickListener(this);
        listNav.setOnClickListener(this);
        imageUser.setOnClickListener(this);
        cartNav.setOnClickListener(this);
        mainTitle.setOnClickListener(this);

        RecyclerView recyclerView = findViewById(R.id.recycler);
        GridLayoutManager layoutManager = new GridLayoutManager(FilmsActivity.this, 2);
        FilmsAdapter adapter = new FilmsAdapter(FilmsActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.grignotinesNav) {
            Intent intent = new Intent(FilmsActivity.this, GrignotinesActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.tarifsNav) {
            Intent intent = new Intent(FilmsActivity.this, TarifsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.cartNav) {
            Intent intent = new Intent(FilmsActivity.this, PanierActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.mainTitle) {
            Intent intent = new Intent(FilmsActivity.this, AccueilActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.imageProfil) {
            Intent intent = new Intent(FilmsActivity.this, CompteActivity.class);
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
                finish();
            }

            Intent intent = new Intent(FilmsActivity.this, LoginActivity.class);
            startActivity(intent);

        }
    }
}