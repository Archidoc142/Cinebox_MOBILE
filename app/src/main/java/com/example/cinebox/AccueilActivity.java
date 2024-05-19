package com.example.cinebox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;

import com.bumptech.glide.util.Util;

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
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        RecyclerView filmsRecycler = findViewById(R.id.filmRecycler),
                snacksRecycler = findViewById(R.id.snackRecycler);

        filmsRecycler.setNestedScrollingEnabled(false);
        snacksRecycler.setNestedScrollingEnabled(false);

        Utilisateur user = Utilisateur.getInstance();

        Toast.makeText(this, "Bienvenue " + Utilisateur.getInstance().getNom(), Toast.LENGTH_SHORT).show();

        new Thread(new Runnable() {
            @Override
            public void run()
            {
                APIRequests.getFilms();
                APIRequests.getSnacks();

                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        AccueilAdapter accueilAdapter = new AccueilAdapter();

                        AccueilAdapter.FilmsAdapterAccueil filmAdapter = accueilAdapter.new FilmsAdapterAccueil(AccueilActivity.this);
                        AccueilAdapter.GrignotinesAdapterAccueil snackAdapter = accueilAdapter.new GrignotinesAdapterAccueil(AccueilActivity.this);

                        GridLayoutManager filmLayoutManager = new GridLayoutManager(AccueilActivity.this, 2),
                                snackLayoutManager = new GridLayoutManager(AccueilActivity.this, 2);

                        filmsRecycler.setAdapter(filmAdapter);
                        filmsRecycler.setLayoutManager(filmLayoutManager);

                        snacksRecycler.setAdapter(snackAdapter);
                        snacksRecycler.setLayoutManager(snackLayoutManager);
                    }
                });
            }
        }).start();
    }
}