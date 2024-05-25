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

import com.bumptech.glide.Glide;

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
            listNav.setVisibility(View.INVISIBLE);
            cartNav.setVisibility(View.INVISIBLE);
        }

        connexion.setOnClickListener(this);
        films.setOnClickListener(this);
        grignotines.setOnClickListener(this);
        tarifs.setOnClickListener(this);
        listNav.setOnClickListener(this);
        imageUser.setOnClickListener(this);
        cartNav.setOnClickListener(this);
        mainTitle.setOnClickListener(this);

        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    Intent intent = getIntent();
                    Film movie = Film.FilmOnArrayList.get(intent.getIntExtra("id", 0));
                    APIRequests.getSeanceById(movie.getId());

                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run() {
                            TextView title = findViewById(R.id.title);
                            TextView etat = findViewById(R.id.etat);
                            ImageView affiche = findViewById(R.id.image);
                            TextView seanceToStr = findViewById(R.id.seance);
                            TextView salleToStr = findViewById(R.id.salle);

                            String allSessionsForMovie = "";
                            for (Seance seance : Seance.seancesArrayList) {
                                if (seance.getFilm().equals(Film.FilmOnArrayList.get(movie.getId()))) {
                                    allSessionsForMovie += seance.getDateTime().toString() + "\n";
                                }
                            }

                            title.setText(movie.getTitre());
                            etat.setText(movie.getEtat_film());
                            seanceToStr.setText(allSessionsForMovie);
                            salleToStr.setText("Siège: " + Seance.seancesArrayList.get(movie.getId()).getSalle_siege() + "\nÉcran: " + Seance.seancesArrayList.get(movie.getId()).getSalle_ecran() + "\nDurée: " + String.valueOf(movie.getDuration()));
                            Glide.with(FilmActivity.this)
                                    .load(movie.getImage_affiche())
                                    .error(R.drawable.image_not_found)
                                    .into(affiche);
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
        } else if (v.getId() == R.id.cartNav) {
            Intent intent = new Intent(FilmActivity.this, PanierActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.mainTitle) {
            Intent intent = new Intent(FilmActivity.this, AccueilActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.imageProfil) {
            Intent intent = new Intent(FilmActivity.this, CompteActivity.class);
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