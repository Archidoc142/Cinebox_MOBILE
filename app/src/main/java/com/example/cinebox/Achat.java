/****************************************
 * Fichier : Achat.java
 * Auteur : Antoine Auger
 * Fonctionnalité : N/A
 * Date : 14 mai 2024
 * Vérification :
 * Date     Nom     Approuvé
 * =========================================================
 *
 * Historique de modifications :
 * Date     Nom     Description
 * =========================================================
 * 22/05/2024   Arthur  Ajout des lists billetsAchat et grignotinesAchat au constructeur pour importation depuis BD
 * 25/05/2024   Arthur  Ajout loadFromJSON()
 * ****************************************/

package com.example.cinebox;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Achat
{
    public static ArrayList<Achat> HistoriqueAchats = new ArrayList<Achat>();
    private static int nextAchatId = 0;

    private ArrayList<Billet> billetsAchat;
    private ArrayList<GrignotineQuantite> grignotinesAchat;

    private int id;
    private String date;
    private double montantBrut;
    private double tps;
    private double tvq;
    private double montantFinal;

    public static void ajouterAchatPanier()
    {
        Achat achatPanier = new Achat(Panier.Billet_PanierList, Panier.Snack_PanierList);
    }

    public Achat() {
        this.billetsAchat = null;
        this.grignotinesAchat = null;
        this.id = 0;
        this.date = null;
        this.montantBrut = 0;
        this.tps = 0;
        this.tvq = 0;
        this.montantFinal = 0;
    }

    public Achat(Context context)
    {
        if(!Panier.isEmpty())
        {
            billetsAchat = Panier.Billet_PanierList;
            grignotinesAchat = Panier.Snack_PanierList;

            this.id = nextAchatId;
            this.date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            this.montantBrut = Panier.getTotal();
            this.tps = Panier.getTPS();
            this.tvq = Panier.getTVQ();
            this.montantFinal = Panier.getTotalFinal();

            SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(context);
            sqLiteManager.insertAchat(this);

            nextAchatId++;
        }
    }

    public Achat(ArrayList<Billet> billets, ArrayList<GrignotineQuantite> grignotinesqte)
    {
        billetsAchat = billets;
        grignotinesAchat = grignotinesqte;
    }

    // constructeur importation depuis BD
    public Achat(int id, String date, double montantBrut, double tps, double tvq, double montantFinal, ArrayList<Billet> billetsAchat, ArrayList<GrignotineQuantite> grignotinesAchat)
    {
        this.id = id;
        this.date = date;
        this.montantBrut = montantBrut;
        this.tps = tps;
        this.tvq = tvq;
        this.montantFinal = montantFinal;
        this.billetsAchat = billetsAchat;
        this.grignotinesAchat = grignotinesAchat;
    }

    /*
    public Achat() {
        id = 0;
        date = null;
        montantBrut = 0;
    }*/

    /*
    public Achat(String date, float montantBrut) {
        this.date = date;
        this.montantBrut = montantBrut;
        this.tps = montantBrut * TPS;
        this.tvq = montantBrut * TVQ;
        this.montantFinal = this.tps + this.tvq;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getmontantBrut() {
        return montantBrut;
    }

    public void setmontantBrut(float montantBrut) {
        this.montantBrut = montantBrut;
    }

    public double getTps() {
        return tps;
    }

    public double getTvq() {
        return tvq;
    }

    public double getMontantFinal() {
        return montantFinal;
    }

    public ArrayList<Billet> getBilletsAchat() {
        return billetsAchat;
    }

    public ArrayList<GrignotineQuantite> getGrignotinesAchat() {
        return grignotinesAchat;
    }

    public static int getNextId()
    {
        return nextAchatId;
    }

    public static void setNextAchatId(int id)
    {
        nextAchatId = id;
    }

    public static void incrementNextAchatId()
    {
        nextAchatId++;
    }

    public void envoyerAchat(Context context)
    {
        SQLiteManager sql = SQLiteManager.instanceOfDatabase(context);
        sql.insertAchatFromPanier(this);

        new Thread(new Runnable() {
            @Override
            public void run()
            {
                System.out.println("posting");
                if(APIRequests.postVente())
                {
                    System.out.println("post worked!");

                    incrementNextAchatId();
                    Panier.Billet_PanierList.clear();
                    Panier.Snack_PanierList.clear();
                }
            }
        }).start();


    }
    public static Achat loadFromJSON(JSONObject jsonObject) {
        Achat achat = new Achat();
        achat.id = jsonObject.optInt("no_vente");
        achat.date = jsonObject.optString("date_facturation");
        achat.montantBrut = jsonObject.optDouble("total_brut");
        achat.tps = jsonObject.optDouble("tps");
        achat.tvq = jsonObject.optDouble("tvq");
        achat.montantFinal = Double.parseDouble(jsonObject.optString("total_final"));

        JSONArray billetsArray = jsonObject.optJSONArray("billets");
        if (billetsArray != null) {
            for (int i = 0; i < billetsArray.length(); i++) {
                JSONObject billetObject = billetsArray.optJSONObject(i);
                if (billetObject != null) {
                    Billet billet = Billet.loadFromJSON(billetObject);
                    achat.billetsAchat.add(billet);
                }
            }
        }

        JSONArray grignotinesArray = jsonObject.optJSONArray("grignotines");
        if (grignotinesArray != null) {
            for (int i = 0; i < grignotinesArray.length(); i++) {
                JSONObject grignotineObject = grignotinesArray.optJSONObject(i);
                if (grignotineObject != null) {
                    GrignotineQuantite grignotineQuantite = GrignotineQuantite.loadFromJSON(grignotineObject);
                    achat.grignotinesAchat.add(grignotineQuantite);
                }
            }
        }

        return achat;
    }
}
