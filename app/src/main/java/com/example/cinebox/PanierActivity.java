/****************************************
 * Fichier : PanierActivity
 * Auteur : Amélie Bergeron
 * Fonctionnalité : Payer le panier, vider le panier
 * Date : 14 mai 2024
 * Vérification :
 * Date Nom Approuvé
 * =========================================================
 * Historique de modifications :
 * 20 mai 2024, Amélie Bergeron, Affichage fonctionnel des éléments du panier
 * 20 mai 2024, Amélie Bergeron, Vider le panier
 * =========================================================****************************************/

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
    TextView total, tps, tvq, vraitotal;
    LinearLayout bottomPanier;
    Button payer, vider;
    ImageView supp;

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
            //imageUser.setImageBitmap(Utilisateur.getInstance().getImage());
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
        ////////supp = nav.findViewById(R.id.btn_poubelle);

        payer = findViewById(R.id.btn_payerPanier);
        vider = findViewById(R.id.btn_viderPanier);

        if(Panier.isEmpty())
        {
            temptxt.setVisibility(View.INVISIBLE);
            bottomPanier.setVisibility(View.GONE);

            videtxt.setVisibility(View.VISIBLE);
        }
        else
        {
            temptxt.setVisibility(View.VISIBLE);
            bottomPanier.setVisibility(View.VISIBLE);

            videtxt.setVisibility(View.GONE);

            putTotal();

            RecyclerView recyclerView = findViewById(R.id.recycler_panier);

            PanierAdapter adapter = new PanierAdapter(PanierActivity.this);
            adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver()
            {
                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount)
                {
                    if(Panier.isEmpty())
                    {
                        temptxt.setVisibility(View.INVISIBLE);
                        bottomPanier.setVisibility(View.GONE);

                        videtxt.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        putTotal();
                    }
                }
            });

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(PanierActivity.this));

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
                    recreate();
                }
            });
        }
    }

    private void putTotal() {
        total = (TextView) findViewById(R.id.avanttaxe);
        total.setText("SOUS-TOTAL : " + String.format("%.2f", Panier.getTotal()) + "$");

        tps = (TextView) findViewById(R.id.tpsPanier);
        tps.setText("TPS : " + String.format("%.2f", Panier.getTPS()) + "$");

        tvq = (TextView) findViewById(R.id.tvqPanier);
        tvq.setText("TVQ : " + String.format("%.2f", Panier.getTVQ()) + "$");

        vraitotal = (TextView) findViewById(R.id.panierTotal);
        vraitotal.setText("TOTAL : " + String.format("%.2f", Panier.getTotalFinal())  + "$");
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


    //@Override
    //public void onItemClick(int position) {
    /*    vraitotal.setText(position);/*
        if (position < Panier.Billet_PanierList.size()) {
            Panier.Billet_PanierList.remove(position);
        } else {
            position -= Panier.Billet_PanierList.size();
            Panier.Snack_PanierList.remove(position);
        }*/
    //}
}