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

public class GrignotinesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grignotines);

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
        GrignotinesAdapter adapter = new GrignotinesAdapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void populateListApi() throws IOException, JSONException {
        if (Grignotine.GrignotineOnArrayList.size() == 0) {
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

                    JSONArray movies = json.getJSONArray("data");

                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject movie = movies.getJSONObject(i);

                        int id = movie.getInt("id");
                        String marque = movie.getString("marque");
                        String categorie = movie.getString("categorie");
                        String format = movie.getString("format");
                        double prix_vente = movie.getDouble("prix_vente");
                        String qte_disponible = movie.getString("qte_disponible");
                        String image = movie.getString("image");

                        Grignotine.GrignotineOnArrayList.add(new Grignotine(id, marque, categorie, format, prix_vente, qte_disponible, image));
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}