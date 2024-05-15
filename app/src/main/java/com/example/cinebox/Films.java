package com.example.cinebox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Films extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_films);

        String api = "https://genshin.jmp.blue/characters";

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("api", "Response: " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("api", "Error: " + error.getMessage());
            }
        });

        queue.add(stringRequest);

        // int filmsArray = 0;

        // RecyclerView recyclerView = findViewById(R.id.recycler);
        // GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        // recyclerView.setLayoutManager(layoutManager);
        // FilmsAdapter adapter = new FilmsAdapter(this, filmsArray);
        // recyclerView.setHasFixedSize(true);
        // recyclerView.setAdapter(adapter);
    }
}