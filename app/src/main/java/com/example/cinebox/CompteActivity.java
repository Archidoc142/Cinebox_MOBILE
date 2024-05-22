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

import org.w3c.dom.Text;

public class CompteActivity extends AppCompatActivity implements RecyclerViewInterface, View.OnClickListener {
    private Integer[] id = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    private String[] date = {"2023/01/01", "2023/02/01", "2023/03/01", "2023/04/01", "2023/05/01", "2023/06/01", "2023/01/01", "2023/02/01", "2023/03/01", "2023/04/01", "2023/05/01", "2023/06/01"};
    private double[] montant = {10.5, 20.0, 15.75, 30.0, 25.5, 50.0, 10.5, 20.0, 15.75, 30.0, 25.5, 50.0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte);

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
            listNav.setVisibility(View.INVISIBLE);
            cartNav.setVisibility(View.INVISIBLE);
        }

        connexion.setOnClickListener(this);
        films.setOnClickListener(this);
        grignotines.setOnClickListener(this);
        tarifs.setOnClickListener(this);
        listNav.setOnClickListener(this);

        //Utilisateur user = Utilisateur.getInstance();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerAchats);
        TextView nomUtilisateur = (TextView) findViewById(R.id.nomUtilisateur);
        TextView prenomUser = (TextView) findViewById(R.id.prenomUser);
        TextView nomUser = (TextView) findViewById(R.id.nomUser);
        TextView courrielUser = (TextView) findViewById(R.id.courrielUser);
        TextView phoneUser = (TextView) findViewById(R.id.phoneUser);

        HistoriqueAchatAdapter myAdapter = new HistoriqueAchatAdapter(this, id, date, montant, this);

        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*nomUtilisateur.setText(user.getNomUtilisateur());
        prenomUser.setText(user.getPrenom());
        nomUser.setText(user.getNom());
        courrielUser.setText(user.getCourriel());
        phoneUser.setText(user.getTelephone());*/
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(CompteActivity.this, ConsulterAchatActivity.class);

        //intent.inputExtra....

        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.filmsNav) {
            Intent intent = new Intent(CompteActivity.this, FilmsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.grignotinesNav) {
            Intent intent = new Intent(CompteActivity.this, GrignotinesActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.tarifsNav) {
            Intent intent = new Intent(CompteActivity.this, TarifsActivity.class);
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
                Intent intent = new Intent(CompteActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
    }
}
