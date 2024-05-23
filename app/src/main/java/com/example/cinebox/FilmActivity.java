/****************************************
 * Fichier : FilmActivity
 * Auteur : Antoine Auger
 * Fonctionnalité : MFi4
 * Date : 14 mai 2024
 * Vérification :
 * Date Nom Approuvé
 * =========================================================
 * Historique de modifications :
 * Date Nom Description
 * =========================================================****************************************/

package com.example.cinebox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FilmActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);

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

        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    Intent intent = getIntent();
                    Film movie = Film.FilmOnArrayList.get(intent.getIntExtra("id", 0));

                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run() {
                            TextView title = findViewById(R.id.title);
                            TextView etat = findViewById(R.id.etat);
                            /*TextView salle = findViewById(R.id.salle);
                            TextView seance = findViewById(R.id.seance);
                            String seances = "";*/

                            /*for (int i=0; i < movie.getSeance().length(); i++) {
                                try {
                                    seances += movie.getSeance().getJSONObject(i).getString("date_heure").substring(11, 16) + "\n";
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }*/

                            title.setText(movie.getTitre());
                            etat.setText(movie.getEtat_film());
                            /*salle.setText("Siège : " + movie.getType_siege() + "\nÉcran : " + movie.getTypeEcran() + "\nDurée" +  movie.getDuration());
                            seance.setText(seances);*/
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.filmsNav) {
            Intent intent = new Intent(FilmActivity.this, FilmsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.grignotinesNav) {
            Intent intent = new Intent(FilmActivity.this, GrignotinesActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.tarifsNav) {
            Intent intent = new Intent(FilmActivity.this, TarifsActivity.class);
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
                Intent intent = new Intent(FilmActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
    }
}