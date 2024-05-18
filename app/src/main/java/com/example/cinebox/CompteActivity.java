package com.example.cinebox;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CompteActivity extends AppCompatActivity implements RecyclerViewInterface {
    private Integer[] id = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    private String[] date = {"2023/01/01", "2023/02/01", "2023/03/01", "2023/04/01", "2023/05/01", "2023/06/01", "2023/01/01", "2023/02/01", "2023/03/01", "2023/04/01", "2023/05/01", "2023/06/01"};
    private double[] montant = {10.5, 20.0, 15.75, 30.0, 25.5, 50.0, 10.5, 20.0, 15.75, 30.0, 25.5, 50.0};

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerAchats);

        HistoriqueAchatAdapter myAdapter = new HistoriqueAchatAdapter(this, id, date, montant, this);

        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(CompteActivity.this, ConsulterAchatActivity.class);

        //intent.inputExtra....

        startActivity(intent);
    }
}
