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

        APIRequests.getSnacks();

        RecyclerView recyclerView = findViewById(R.id.recycler);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        GrignotinesAdapter adapter = new GrignotinesAdapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }
}