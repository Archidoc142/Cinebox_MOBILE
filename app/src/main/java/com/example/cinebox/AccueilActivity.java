package com.example.cinebox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AccueilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try
        {
            populateFilms();
            populateSnacks();

            RecyclerView filmsRecycler = findViewById(R.id.filmRecycler),
                         snacksRecycler = findViewById(R.id.snackRecycler);

            GridLayoutManager filmLayoutManager = new GridLayoutManager(this, 2),
                              snackLayoutManager = new GridLayoutManager(this, 2);


            FilmsAdapter filmAdapter = new FilmsAdapter(this);
            filmsRecycler.setHasFixedSize(true);
            filmsRecycler.setAdapter(filmAdapter);
            filmsRecycler.setLayoutManager(filmLayoutManager);

            GrignotinesAdapter snackAdapter = new GrignotinesAdapter(this);
            snacksRecycler.setHasFixedSize(true);
            snacksRecycler.setAdapter(snackAdapter);
            snacksRecycler.setLayoutManager(snackLayoutManager);
        }
        catch (IOException | JSONException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void populateFilms() throws IOException, JSONException {
        if (Film.FilmOnArrayList.size() == 0) {
            try {
                URL obj = new URL(getString(R.string.api_url) + "films");
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    JSONObject json = new JSONObject(response.toString());

                    JSONArray movies = json.getJSONArray("data");

                    int lim;
                    if(movies.length() > 4)
                        lim = 4;
                    else
                        lim = movies.length();

                    for (int i = 0; i < lim; i++) {
                        JSONObject movie = movies.getJSONObject(i);

                        int id = movie.getInt("id");
                        String titre = movie.getString("titre");
                        int duration = movie.getInt("duration");
                        String description = movie.getString("description");
                        String date_de_sortie = movie.getString("date_de_sortie");
                        String date_fin_diffusion = movie.getString("date_fin_diffusion");
                        String categorie = movie.getString("categorie");
                        String realisateur = movie.getString("realisateur");
                        String image_affiche = movie.getString("image_affiche");

                        Film.FilmOnArrayList.add(new Film(id, titre, duration, description, date_de_sortie, date_fin_diffusion, categorie, realisateur, image_affiche));
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void populateSnacks() throws IOException, JSONException
    {
        if (Grignotine.GrignotineOnArrayList.size() == 0){
            try {
                URL obj = new URL(getString(R.string.api_url) + "snacks");
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    JSONObject json = new JSONObject(response.toString());

                    JSONArray snacks = json.getJSONArray("data");

                    int lim;

                    if(snacks.length() > 4)
                        lim = 4;
                    else
                        lim = snacks.length();

                    for (int i = 0; i < lim; i++) {
                        JSONObject snack = snacks.getJSONObject(i);

                        int id = snack.getInt("id");
                        String marque = snack.getString("marque");
                        String categorie = snack.getString("categorie");
                        String format = snack.getString("format");
                        double prix_vente = snack.getDouble("prix_vente");
                        String qte_disponible = snack.getString("qte_disponible");
                        String image = snack.getString("image");

                        Grignotine.GrignotineOnArrayList.add(new Grignotine(id, marque, categorie, format, prix_vente, qte_disponible, image));
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}