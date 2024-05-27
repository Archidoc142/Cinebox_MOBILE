/****************************************
 * Fichier : Achat.java
 * Auteur : Antoine Auger, Hicham Abekiri, Arthur Andrianjafisolo
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

package com.example.cinebox.Achat;

import android.content.Context;

import com.example.cinebox.APIRequests;
import com.example.cinebox.Billet.Billet;
import com.example.cinebox.Grignotine.GrignotineQuantite;
import com.example.cinebox.Panier.Panier;
import com.example.cinebox.SQLiteManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Achat
{
    /**
     * Tableau dynamique contenant tous les achats de l'utilisateur connecté
     */
    public static ArrayList<Achat> HistoriqueAchats = new ArrayList<Achat>();

    /**
     * L'ID à utiliser lors de l'ajout d'un nouvel achat (initialisé depuis l'API)
     */
    private static int nextAchatId = 0;

    /**
     * Les billets de l'achat
     */
    private ArrayList<Billet> billetsAchat;

    /**
     * Les grignotines de l'achat
     */
    private ArrayList<GrignotineQuantite> grignotinesAchat;

    /**
     * ID de l'achat
     */
    private int id;

    /**
     * Date de l'achat
     */
    private String date;

    /**
     * Montant brut de l'achat
     */
    private double montantBrut;

    /**
     * Taux TPS de l'achat
     */
    private double tps;

    /**
     * Taux TVQ de l'achat
     */
    private double tvq;

    /**
     * Montant final de l'achat
     */
    private double montantFinal;

    /**
     * Création d'un achat à partir du contenu du panier ou un achat importé
     */
    public Achat(boolean fromPanier)
    {
        if(fromPanier)
        {
            if(!Panier.isEmpty())
            {
                billetsAchat = Panier.Billet_PanierList;
                grignotinesAchat = Panier.Snack_PanierList;

                this.id = nextAchatId;
                this.date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
                this.montantBrut = Panier.getTotal();
                this.tps = Panier.getTPS();
                this.tvq = Panier.getTVQ();
                this.montantFinal = Panier.getTotalFinal();
            }
        }
        else
        {
            this.billetsAchat = null;
            this.grignotinesAchat = null;
            this.id = 0;
            this.date = null;
            this.montantBrut = 0;
            this.tps = 0;
            this.tvq = 0;
            this.montantFinal = 0;
        }
    }

    public Achat(Context context)
    {
        if(!Panier.isEmpty())
        {
            billetsAchat = Panier.Billet_PanierList;
            grignotinesAchat = Panier.Snack_PanierList;

            this.id = nextAchatId;
            this.date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
            this.montantBrut = Panier.getTotal();
            this.tps = Panier.getTPS();
            this.tvq = Panier.getTVQ();
            this.montantFinal = Panier.getTotalFinal();

            SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(context);
            sqLiteManager.insertAchat(this);

            nextAchatId++;
        }
    }

    /**
     * Création d'un achat importé depuis la BD
     * @param id ID de l'achat
     * @param date Date de l'achat
     * @param montantBrut Montant de l'achat
     * @param tps Taux TPS de l'achat
     * @param tvq Taux TVQ de l'achat
     * @param montantFinal Montant final de l'achat
     * @param billetsAchat Billets de l'achat
     * @param grignotinesAchat Grignotines de l'achat avec leurs quantités
     */
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


    public Achat(int id, String date, double montantFinal) {
        this.id = id;
        this.date = date;
        this.montantBrut = 0;   //pas besoin du montant brut
        this.tps = 0;       //Pas besoin des taxes
        this.tvq = 0;       //Pas besoin des taxes
        this.montantFinal = montantFinal;
    }

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
        return Double.parseDouble(String.format("%.2f", montantBrut));
    }

    public double getTps() {
        return Double.parseDouble(String.format("%.2f", tps));
    }

    public double getTvq() {
        return Double.parseDouble(String.format("%.2f", tvq));
    }

    public double getMontantFinal() {
        return Double.parseDouble(String.format("%.2f", montantFinal));
    }

    public ArrayList<Billet> getBilletsAchat() {
        return billetsAchat;
    }
    public void addBilletsAchat(ArrayList<Billet> billet) {
        this.billetsAchat = billet;
    }

    public ArrayList<GrignotineQuantite> getGrignotinesAchat() {
        return grignotinesAchat;
    }
    public void addGrignotinesAchat(ArrayList<GrignotineQuantite> grignotineQuantite) {
        this.grignotinesAchat = grignotineQuantite;
    }
    public static int getNextId()
    {
        return nextAchatId;
    }

    public static void setNextAchatId(int id)
    {
        nextAchatId = id;
    }

    /**
     * Met à jour l'ID du prochain achat
     */
    public static void incrementNextAchatId()
    {
        nextAchatId++;
    }

    /**
     * Insère l'achat dans la BD et l'envoie ensuite au serveur via l'API
     * @param context Contexte de l'application
     */
    public void envoyerAchat(Context context) throws AchatUnsuccessfulException {
        SQLiteManager sql = SQLiteManager.instanceOfDatabase(context);
        sql.insertAchatFromPanier(this);

        APIRequests.AchatRunnable achatRunnable = new APIRequests.AchatRunnable();

        Thread thread = new Thread(achatRunnable);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (achatRunnable.successful()) {
            incrementNextAchatId();
            Achat.HistoriqueAchats.add(this);
            Billet.incrementNextBilletId(Panier.Billet_PanierList.size());
            Panier.Billet_PanierList.clear();
            Panier.Snack_PanierList.clear();
        } else {
            sql.deleteAchat(this);
            throw new AchatUnsuccessfulException();
        }
    }

    /**
     * @param jsonObject object JSON contenant les informations de l'achat (billets, grignotines, ect)
     * @return Achat object from a JSON object
     */
    public static Achat loadFromJSON(JSONObject jsonObject)
    {
        Achat achat = new Achat(false);
        achat.id = jsonObject.optInt("no_vente");
        achat.date = jsonObject.optString("date_facturation");
        achat.montantBrut = jsonObject.optDouble("total_brut");
        achat.tps = jsonObject.optDouble("tps");
        achat.tvq = jsonObject.optDouble("tvq");
        achat.montantFinal = Double.parseDouble(jsonObject.optString("total_final").replace(",", "."));

        // Initialisation des ArrayList
        achat.billetsAchat = new ArrayList<>();
        achat.grignotinesAchat = new ArrayList<>();

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
