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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFromBDToMemory();

        //Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        Intent intent = new Intent(MainActivity.this, TarifsActivity.class);
        startActivity(intent);
    }

    private void loadFromBDToMemory() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateLists();
    }
}