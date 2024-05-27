/****************************************
 * Fichier : APIRequest.java
 * Auteur : Hicham, Arthur, Amélie, Antoine
 * Fonctionnalité : Cette class permet de centraliser les requêtes à l'API
 * Date : 17 mai 2024
 *
 * Vérification :
 * Date     Nom     Approuvé
 * =========================================================
 *
 *
 * Historique de modifications :
 * Date     Nom     Description
 * =========================================================
 * 17/05/2024   Hicham  Création du fichier APIRequests
 *                      Ajout requêtes pour authentificatione
 * 20/05/2024   Amélie  Création de la requête pour les tarifs
 * 22/05/2024   Arthur  Début Ajout getAchat()
 * 25/05/2024   Arthur  Ajout postClientUpdate()
 * ****************************************/

package com.example.cinebox;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;


public class APIRequests
{
    private static final String apiURL = "http://20.121.131.16/api/";
    /**
     * URL de l'API pour obtenir la liste des films en JSON
     */
    private static final String getFilmsURL = apiURL + "films";
    /**
     *  URL de l'API pour obtenir la liste des grignotines en JSON
     */
    private static final String getSnacksURL = apiURL + "snacks";
    private static final String postLoginURL = apiURL + "token";
    private static final String getUserURL = apiURL + "user";
    /**
     * URL de l'API pout ajouter un compte client dans la BD
     */
    private static final String addUserURL = apiURL + "client/ajout";
    private static final String postClientUpdateURL = apiURL + "client/update";
    private static final String getTarifsURL = apiURL + "tarifs";
    private static final String getHistoriqueAchatURL = apiURL + "ventes";
    private static final String getNextAchatIdURL = apiURL + "vente/nextId";
    private static final String postVenteAjoutURL = apiURL + "vente/ajout";
    /**
     * URL de l'API pour obtenir la liste des séances en JSON
     */
    private static final String getSeances = apiURL + "seances";
    /**
     * URL de l'API pour obtenir la nouvelle valeur de nextBilletId
     */
    private static final String getNextBilletIdURL = apiURL + "billet/nextId";

    public static class TokenValidRunnable implements Runnable
    {
        private volatile boolean valid;

        @Override
        public void run()
        {
            valid = isTokenValid();
        }

        public boolean isValid()
        {
            return valid;
        }
    }

    public static class AchatRunnable implements Runnable
    {
        private volatile boolean result;

        @Override
        public void run()
        {
            result = postVente();
        }

        public boolean successful()
        {
            return result;
        }
    }

    /**
     * Popule la liste FilmOnArrayList de la class Film
     */
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
                    response = fixJSON(response);
                    JSONObject json = new JSONObject(response.toString());

                    JSONArray movies = json.getJSONArray("data");

                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject movie = movies.getJSONObject(i);

                        int id = movie.getInt("id");
                        String titre = movie.getString("titre");
                        String duration = movie.getString("duration");
                        String description = movie.getString("description");
                        String date_de_sortie = movie.getString("date_de_sortie");
                        String date_fin_diffusion = movie.getString("date_fin_diffusion");
                        String categorie = movie.getString("categorie");
                        String realisateur = movie.getString("realisateur");
                        String image_affiche = movie.getString("image_affiche");
                        String etat_film = movie.getString("id_etat_film");

                        Film.FilmOnArrayList.add(new Film(id, titre, duration, description, date_de_sortie, date_fin_diffusion, categorie, realisateur, image_affiche, etat_film));
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Popule la liste seancesArrayList de la class Seance
     */
    public static void getSeances()
    {
        if (Film.FilmOnArrayList.size() != 0) {
            try {
                URL obj = new URL(getSeances);
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

                    response = fixJSON(response);
                    JSONObject json = new JSONObject(response.toString());

                    JSONArray sessions = json.getJSONArray("data");

                    for (int i = 0; i < sessions.length(); i++) {
                        JSONObject session = sessions.getJSONObject(i);

                        int idS = session.getInt("id");
                        int filmId = session.getInt("id_film");
                        String hour = session.getString("date_heure");
                        String siege = session.getString("salle_siege");
                        String ecran = session.getString("salle_ecran");
                        int noSalle = session.getInt("no_salle");

                        Seance.seancesArrayList.add(new Seance(idS, hour, filmId, siege, ecran, noSalle));
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Popule la liste GrignotineOnArrayList de la class Grignotine
     * @param context
     */
    public static void getSnacks(Context context)
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
                    response = fixJSON(response);
                    JSONObject json = new JSONObject(response.toString());

                    JSONArray snacks = json.getJSONArray("data");

                    for (int i = 0; i < snacks.length(); i++) {
                        JSONObject snack = snacks.getJSONObject(i);

                        int id = snack.getInt("id");
                        String marque = snack.getString("marque");
                        String categorie = snack.getString("categorie");
                        String format = snack.getString("format");
                        double prix_vente = snack.getDouble("prix_vente");
                        int qte_disponible = snack.getInt("qte_disponible");
                        String image = snack.getString("image");

                        Grignotine.GrignotineOnArrayList.add(new Grignotine(id, marque, categorie, format, prix_vente, qte_disponible, image));
                        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(context);
                        sqLiteManager.insertSnacks();
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static boolean postLoginUser(String mail, String pwd, Context context)
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
                        System.out.println(inputLine);
                        response.append(inputLine);
                    }
                    in.close();
                    System.out.println(response.toString());
                    response = fixJSON(response);
                    JSONObject json = new JSONObject(response.toString());
                    String token = json.getString("token");
                    getUser(token, context);

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

    private static void getUser(String token, Context context)
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
                    System.out.println(inputLine);
                    response.append(inputLine);
                }
                in.close();
                System.out.println(response);
                response = fixJSON(response);
                JSONObject userJ = new JSONObject(response.toString());

                int id = userJ.getInt("id");

                String nom = userJ.getString("nom_famille");
                String prenom = userJ.getString("prenom");
                String nomUtilisateur = userJ.getString("name");
                String courriel = userJ.getString("email");
                String telephone = userJ.getString("telephone");

                Utilisateur.initUser(context, token, id, nom, prenom, nomUtilisateur, courriel, telephone, null);
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

    public static boolean isTokenValid() {
        if (Utilisateur.getInstance() != null) {
            try {
                URL obj = new URL(getUserURL);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization", "Bearer " + Utilisateur.getInstance().getToken());

                int responseCode = con.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return true;
                }

                return false;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return false;
        }
    }

    public static void getNextAchatId()
    {
        try
        {
            URL obj = new URL(getNextAchatIdURL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK)
            {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null)
                {
                    System.out.println(inputLine);
                    response.append(inputLine);
                }
                in.close();

                response = fixJSON(response);
                JSONObject json = new JSONObject(response.toString());

                int id = json.getInt("id");
                Achat.setNextAchatId(id);
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Incrémente la variable nextBilletId de la class Billet
     */
    public static void getNextBilletId()
    {
        try
        {
            URL obj = new URL(getNextBilletIdURL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK)
            {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null)
                {
                    System.out.println(inputLine);
                    response.append(inputLine);
                }
                in.close();

                response = fixJSON(response);
                JSONObject json = new JSONObject(response.toString());

                int id = json.getInt("id");
                Billet.setNextBilletId(id);
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    public static void getTarifs()
    {
        if (Tarif.TarifOnArrayList.size() == 0){
            try {
                URL obj = new URL(getTarifsURL);
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
                    response = fixJSON(response);
                    JSONObject json = new JSONObject(response.toString());

                    JSONArray tarifs = json.getJSONArray("data");

                    for (int i = 0; i < tarifs.length(); i++) {
                        JSONObject tarif = tarifs.getJSONObject(i);

                        int id = tarif.getInt("id_tarif");
                        String categorie = tarif.getString("categorie");
                        double prix = tarif.getDouble("prix");
                        String description = tarif.getString("description");

                        Tarif.TarifOnArrayList.add(new Tarif(id, categorie, prix, description));
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Ajoute un utilisateur à la BD avec les informations de body
     * @param body
     * @return
     */
    public static boolean addUser(JSONObject body) {
        try {
            URL obj = new URL(addUserURL);
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

            if (responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR || responseCode == HttpURLConnection.HTTP_OK) {
                return true;
            } else {
                System.out.println("POST request not worked");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param token
     * @param context
     *
     * Fonction add Achat in static HistoriqueAchats arrayList
     */
    public static void getAchats(String token, Context context)
    {
        Achat.HistoriqueAchats.clear();

        try
        {
            URL obj = new URL(getHistoriqueAchatURL);
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
                response = fixJSON(response);
                JSONObject json = new JSONObject(response.toString());

                JSONArray achats = json.getJSONArray("data");

                for (int i = 0; i < achats.length(); i++) {
                    JSONObject achat = achats.getJSONObject(i);

                    Achat.HistoriqueAchats.add(Achat.loadFromJSON(achat));
                }
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

    public static boolean postVente()
    {
        if(!Panier.isEmpty() && Utilisateur.getInstance() != null)
        {
            try
            {
                JSONObject body = new JSONObject();

                JSONObject billets = new JSONObject();
                JSONObject grignotines = new JSONObject();

                if (!Panier.Billet_PanierList.isEmpty())
                {
                    int i = 0;
                    for (Billet b : Panier.Billet_PanierList)
                    {
                        JSONObject billet = new JSONObject();
                        billet.put("id_tarif", b.getTarif().getId());
                        billet.put("id_seance", b.getSeance().getId());

                        billets.put(Integer.toString(i), billet);
                        i++;
                    }

                    body.put("billets", billets);
                }

                if (!Panier.Snack_PanierList.isEmpty())
                {
                    int i = 0;
                    for (GrignotineQuantite g : Panier.Snack_PanierList)
                    {
                        JSONObject grignotineQte = new JSONObject();
                        grignotineQte.put("id_grignotine", g.getGrignotine().getId());
                        grignotineQte.put("quantite", g.getQuantite());

                        grignotines.put(Integer.toString(i), grignotineQte);
                        i++;
                    }

                    body.put("grignotines", grignotines);
                }

                URL obj = new URL(postVenteAjoutURL);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestProperty("Authorization", "Bearer " + Utilisateur.getInstance().getToken());

                con.setDoOutput(true);

                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                writer.write(body.toString());
                writer.flush();
                writer.close();

                System.out.println("sending achat...");

                int responseCode = con.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK)
                {
                    return true;
                }

                return false;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * @return boolean value
     *
     * cette fonction permet de mettre à jour les information utlisateur vers l'API depuis l'application
     */
    public static boolean postUserUpdate() {
        if (Utilisateur.getInstance() != null) {
            Utilisateur user = Utilisateur.getInstance();
            try {

                //Remplacé par fonction toJSON() in Utilisateur object
                /*JSONObject body = new JSONObject();

                body.put("id_client", user.getId());
                body.put("nom_utilisateur", user.getNomUtilisateur());
                body.put("nom_famille", user.getNom());
                body.put("prenom", user.getPrenom());
                body.put("email", user.getCourriel());
                body.put("telephone", user.getTelephone());*/

                URL obj = new URL(postClientUpdateURL);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.setRequestProperty("Accept", "application/json");
                con.setDoOutput(true);

                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                writer.write(user.toJSON().toString());
                writer.flush();
                writer.close();

                int responseCode = con.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return true;
                } else {
                    System.out.println("POST request not worked");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public static StringBuffer fixJSON(StringBuffer response)
    {
        // Pourquoi.
        String lastChar = response.substring(response.length() - 1);
        if(lastChar != "}")
        {
            response.append("}");
        }
        
        return response;
    }
}
