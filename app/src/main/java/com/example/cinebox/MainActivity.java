package com.example.cinebox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFromBDToMemory();
    }

    private void loadFromBDToMemory() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateLists();
    }
}