/****************************************
 * Fichier : Films
 * Auteur : Antoine Auger
 * Fonctionnalité : MGr2, MGr3
 * Date : 14 mai 2024
 * Vérification :
 * Date Nom Approuvé
 * =========================================================
 * Historique de modifications :
 * Date Nom Description
 * =========================================================****************************************/

package com.example.cinebox.Grignotine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cinebox.APIRequests;
import com.example.cinebox.Accueil.AccueilActivity;
import com.example.cinebox.Compte.CompteActivity;
import com.example.cinebox.Compte.LoginActivity;
import com.example.cinebox.Compte.Utilisateur;
import com.example.cinebox.Film.FilmsActivity;
import com.example.cinebox.Panier.PanierActivity;
import com.example.cinebox.R;
import com.example.cinebox.Tarif.TarifsActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GrignotinesActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grignotines);

        View nav = findViewById(R.id.nav);

        TextView films = nav.findViewById(R.id.filmsNav);
        TextView tarifs = nav.findViewById(R.id.tarifsNav);
        TextView connexion = nav.findViewById(R.id.connexionNav);
        ImageView imageUser = nav.findViewById(R.id.imageProfil);
        ImageView listNav = nav.findViewById(R.id.listNav);
        ImageView cartNav = nav.findViewById(R.id.cartNav);
        TextView mainTitle = nav.findViewById(R.id.mainTitle);
        ImageView searchBtn = findViewById(R.id.searchBtn);

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
        films.setOnClickListener(this);
        tarifs.setOnClickListener(this);
        listNav.setOnClickListener(this);
        mainTitle.setOnClickListener(this);
        imageUser.setOnClickListener(this);
        cartNav.setOnClickListener(this);
        searchBtn.setOnClickListener(this);

        RecyclerView recyclerView = findViewById(R.id.recycler);
        GridLayoutManager layoutManager = new GridLayoutManager(GrignotinesActivity.this, 2);
        GrignotinesAdapter adapter = new GrignotinesAdapter(GrignotinesActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        ArrayList<String> list = intent.getStringArrayListExtra("list");
        if (list != null && !list.isEmpty()) {
            adapter.filter(list);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.filmsNav) {
            Intent intent = new Intent(GrignotinesActivity.this, FilmsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.tarifsNav) {
            ////////////Intent intent = new Intent(GrignotinesActivity.this, TarifsActivity.class);
            Intent intent = new Intent(GrignotinesActivity.this, TarifsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.cartNav) {
            Intent intent = new Intent(GrignotinesActivity.this, PanierActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.mainTitle) {
            Intent intent = new Intent(GrignotinesActivity.this, AccueilActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.imageProfil) {
            Intent intent = new Intent(GrignotinesActivity.this, CompteActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.searchBtn) {
            new Thread(new Runnable() {
                @Override
                public void run()
                {
                    EditText grignotine_search = findViewById(R.id.grignotine_name);
                    ArrayList<String> arrayList = APIRequests.getGrignotineByName(String.valueOf(grignotine_search.getText()));
                    Intent intent = new Intent(GrignotinesActivity.this, GrignotinesActivity.class);
                    intent.putExtra("list", arrayList);
                    startActivity(intent);
                }
            }).start();
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

            Intent intent = new Intent(GrignotinesActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}