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

public class FilmsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_films);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            populateListApi();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RecyclerView recyclerView = findViewById(R.id.recycler);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        FilmsAdapter adapter = new FilmsAdapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void populateListApi() throws IOException, JSONException {
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

                    for (int i = 0; i < movies.length(); i++) {
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
}