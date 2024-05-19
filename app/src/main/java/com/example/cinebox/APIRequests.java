package com.example.cinebox;

import android.content.Context;
import android.content.res.Resources;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIRequests
{
    private static final String apiURL = "https://cinebox.taila09363.ts.net/api/";
    private static final String getFilmsURL = apiURL + "films";
    private static final String getSnacksURL = apiURL + "snacks";
    private static final String postLoginURL = apiURL + "token";
    private static final String getUserURL = apiURL + "user";

    public static void getFilms()
    {
        if (Film.FilmOnArrayList.size() == 0) {
            try {
                URL obj = new URL(getFilmsURL);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");

                System.out.println("fetch request");
                int responseCode = con.getResponseCode();

                System.out.println(responseCode);

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    System.out.println(response);
                    JSONObject json = new JSONObject(response.toString());

                    JSONArray movies = json.getJSONArray("data");

                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject movie = movies.getJSONObject(i);

                        int id = movie.getInt("id");
                        String titre = movie.getString("titre");
                        int duration = movie.getInt("duration");
                        String description = movie.getString("description");
                        String date_de_sortie = movie.getString("date_de_sortie");
                        String date_fin_diffusion = movie.getString("date_fin_diffusion");
                        String categorie = movie.getString("categorie");
                        String realisateur = movie.getString("realisateur");
                        String image_affiche = movie.getString("image_affiche");

                        Film.FilmOnArrayList.add(new Film(id, titre, duration, description, date_de_sortie, date_fin_diffusion, categorie, realisateur, image_affiche));
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void getSnacks()
    {
        if (Grignotine.GrignotineOnArrayList.size() == 0){
            try {
                URL obj = new URL(getSnacksURL);
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

                    JSONArray snacks = json.getJSONArray("data");

                    for (int i = 0; i < snacks.length(); i++) {
                        JSONObject snack = snacks.getJSONObject(i);

                        int id = snack.getInt("id");
                        String marque = snack.getString("marque");
                        String categorie = snack.getString("categorie");
                        String format = snack.getString("format");
                        double prix_vente = snack.getDouble("prix_vente");
                        String qte_disponible = snack.getString("qte_disponible");
                        String image = snack.getString("image");

                        Grignotine.GrignotineOnArrayList.add(new Grignotine(id, marque, categorie, format, prix_vente, qte_disponible, image));
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static boolean postLoginUser(String mail, String pwd)
    {
        if(Utilisateur.getInstance() == null)
        {
            try
            {
                JSONObject body = new JSONObject();
                body.put("courriel", mail);
                body.put("mot_de_passe", pwd);
                body.put("nom_token", "mobile");

                URL obj = new URL(postLoginURL);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.setRequestProperty("Accept", "application/json");
                con.setDoOutput(true);

                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                writer.write(body.toString());
                writer.flush();
                writer.close();

                int responseCode = con.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK)
                {
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    JSONObject json = new JSONObject(response.toString());

                    String token = json.getString("token");

                    addUser(token);

                    return true;
                }
                else
                {
                    System.out.println("fail: " + responseCode);
                    return false;
                }
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }
        else
        {
            return false;
        }
    }

    public static void addUser(String token)
    {
        try
        {

            URL obj = new URL(getUserURL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK)
            {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject userJ = new JSONObject(response.toString());

                int id = userJ.getInt("id");

                String nom = userJ.getString("nom_famille");
                String prenom = userJ.getString("prenom");
                String nomUtilisateur = userJ.getString("name");
                String courriel = userJ.getString("email");
                String telephone = userJ.getString("telephone");

                Utilisateur.setInstance(token, id, nom, prenom, nomUtilisateur, courriel, telephone);
            }
            else
            {
                System.out.println("fail: " + responseCode);
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
