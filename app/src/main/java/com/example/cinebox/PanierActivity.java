package com.example.cinebox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PanierActivity extends AppCompatActivity implements View.OnClickListener {
    TextView videtxt, temptxt;
    LinearLayout bottomPanier;
    Button payer, vider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);

        View nav = findViewById(R.id.nav);

        TextView films = nav.findViewById(R.id.filmsNav);
        TextView grignotines = nav.findViewById(R.id.grignotinesNav);
        TextView tarifs = nav.findViewById(R.id.tarifsNav);
        TextView connexion = nav.findViewById(R.id.connexionNav);
        ImageView imageUser = nav.findViewById(R.id.imageInstanceFilm);
        ImageView listNav = nav.findViewById(R.id.listNav);
        ImageView cartNav = nav.findViewById(R.id.cartNav);

        if (Utilisateur.getInstance() != null) {
            connexion.setText("Se déconnecter");
            imageUser.setImageBitmap(Utilisateur.getInstance().getImage());
        } else {
            imageUser.setVisibility(View.INVISIBLE);
            listNav.setVisibility(View.INVISIBLE);
            cartNav.setVisibility(View.INVISIBLE);
        }

        connexion.setOnClickListener(this);
        films.setOnClickListener(this);
        grignotines.setOnClickListener(this);
        tarifs.setOnClickListener(this);
        listNav.setOnClickListener(this);

        videtxt = findViewById(R.id.panierVide);
        temptxt = findViewById(R.id.txt_panierTemp);

        bottomPanier = findViewById(R.id.bottomPanier);

        payer = findViewById(R.id.btn_payerPanier);
        vider = findViewById(R.id.btn_viderPanier);

        //(int id, String marque, String categorie, String format, double prix_vente, String qte_disponible, String image) {

        Grignotine g = new Grignotine(1, "marque", "categorie", "format", 12.3, "100", "une image");
        GrignotineQuantite gq = new GrignotineQuantite(g, 2);
        Panier.Snack_PanierList.add(gq);

        if(Panier.isEmpty()) {
            temptxt.setVisibility(View.INVISIBLE);
            bottomPanier.setVisibility(View.GONE);

            videtxt.setVisibility(View.VISIBLE);
        } else {
            temptxt.setVisibility(View.VISIBLE);
            bottomPanier.setVisibility(View.VISIBLE);

            videtxt.setVisibility(View.GONE);

            //RecyclerView recyclerView = findViewById(R.id.recycler_panier);
            //PanierAdapter adapter = new PanierAdapter(PanierActivity.this);
            //recyclerView.setAdapter(adapter);
            //recyclerView.setLayoutManager(new LinearLayoutManager(PanierActivity.this));

            payer.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Panier.payerPanier(PanierActivity.this);
                }
            });

            vider.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Panier.viderPanier();
                    // finish();
                    // startActivity(getIntent());
                    recreate();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.filmsNav) {
            Intent intent = new Intent(PanierActivity.this, FilmsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.grignotinesNav) {
            Intent intent = new Intent(PanierActivity.this, GrignotinesActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.tarifsNav) {
            Intent intent = new Intent(PanierActivity.this, TarifsActivity.class);
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
                Intent intent = new Intent(PanierActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
    }
}