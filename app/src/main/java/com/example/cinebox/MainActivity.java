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

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFromBDToMemory();

        /*
            Juste un test pour voir si l'utilisateur peut se reconnecter automatiquement ou pas.
            La page d'accueil resterait toujours la page initiale peu importe le type d'utilisateur.
         */

        /*if(Utilisateur.loggedIn(this))
        {
            intent = new Intent(MainActivity.this, AccueilActivity.class);
        }*//*
        else
        {
            //intent = new Intent(MainActivity.this, LoginActivity.class);

            intent = new Intent(MainActivity.this, LoginActivity.class);
        }*/

        finish();

        Intent intent = new Intent(MainActivity.this, AccueilActivity.class);

        startActivity(intent);

    }

    private void loadFromBDToMemory()
    {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateLists();

        new Thread(new Runnable() {
            @Override
            public void run() {
                APIRequests.getNextAchatId();
            }
        }).start();
    }
}