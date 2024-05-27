/****************************************
 * Fichier : MainActivity
 * Auteur : Antoine, Hicham, Amélie, Arthur
 * Fonctionnalité : N/A
 * Date : 14 mai 2024
 * Vérification :
 * Date Nom Approuvé
 * =========================================================
 * Historique de modifications :
 * Date Nom Description
 * =========================================================****************************************/

package com.example.cinebox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.cinebox.Accueil.AccueilActivity;
import com.example.cinebox.Billet.Seance;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                APIRequests.getFilms();
                APIRequests.getTarifs();
                APIRequests.getSeances();
                APIRequests.getNextAchatId();
                APIRequests.getSnacks(MainActivity.this);
            }}
        );

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        sqLiteManager.populateLists();

        finish();
        Intent intent = new Intent(MainActivity.this, AccueilActivity.class);

        startActivity(intent);

    }
}