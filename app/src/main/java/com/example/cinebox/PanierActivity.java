package com.example.cinebox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PanierActivity extends AppCompatActivity {
    TextView videtxt, temptxt;
    LinearLayout bottomPanier;
    Button payer, vider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);

        videtxt = findViewById(R.id.panierVide);
        temptxt = findViewById(R.id.txt_panierTemp);

        bottomPanier = findViewById(R.id.bottomPanier);

        payer = findViewById(R.id.btn_payerPanier);
        vider = findViewById(R.id.btn_viderPanier);

        //(int id, String marque, String categorie, String format, double prix_vente, String qte_disponible, String image) {

        Grignotine g = new Grignotine(1, "marque", "categorie", "format", 12.3, "100", "une image");
      //  Panier.Snack_PanierList.add(g);

        if(Panier.Snack_PanierList.isEmpty() && Panier.Billet_PanierList.isEmpty()) {
            temptxt.setVisibility(View.INVISIBLE);
            bottomPanier.setVisibility(View.GONE);

            videtxt.setVisibility(View.VISIBLE);
        } else {
            temptxt.setVisibility(View.VISIBLE);
            bottomPanier.setVisibility(View.VISIBLE);

            videtxt.setVisibility(View.GONE);

            RecyclerView recyclerView = findViewById(R.id.recycler_panier);
            PanierAdapter adapter = new PanierAdapter(PanierActivity.this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(PanierActivity.this));

            payer.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Panier.payerPanier();
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
}