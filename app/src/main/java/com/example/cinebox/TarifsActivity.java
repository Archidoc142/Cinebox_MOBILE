package com.example.cinebox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class TarifsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarifs);

        new Thread(new Runnable() {
            @Override
            public void run()
            {
                APIRequests.getTarifs();

                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        RecyclerView recyclerView = findViewById(R.id.recycler_tarifs);
                       // GridLayoutManager layoutManager = new GridLayoutManager(TarifsActivity.this, 1);
                        TarifsAdapter adapter = new TarifsAdapter(TarifsActivity.this);
                       // recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);

                        recyclerView.setLayoutManager(new LinearLayoutManager(TarifsActivity.this));

                        //recyclerView.setLayoutManager(layoutManager);
                    }
                });
            }
        }).start();
    }
}