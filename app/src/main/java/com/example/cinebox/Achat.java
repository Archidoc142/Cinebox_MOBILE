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
 *
 * ****************************************/

package com.example.cinebox;

import android.content.Context;

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
     * Création d'un achat à partir du contenu du panier
     */
    public Achat()
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
    public void envoyerAchat(Context context) throws AchatUnsuccessfulException
    {
        SQLiteManager sql = SQLiteManager.instanceOfDatabase(context);
        sql.insertAchatFromPanier(this);

        APIRequests.AchatRunnable achatRunnable = new APIRequests.AchatRunnable();

        Thread thread = new Thread(achatRunnable);
        thread.start();

        try
        {
            thread.join();
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }

        if(achatRunnable.successful())
        {
            incrementNextAchatId();
            Billet.incrementNextBilletId(Panier.Billet_PanierList.size());
            Panier.Billet_PanierList.clear();
            Panier.Snack_PanierList.clear();
        }
        else
        {
            sql.deleteAchat(this);
            throw new AchatUnsuccessfulException();
        }


        /**

        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    if(APIRequests.postVente())
                    {
                        incrementNextAchatId();
                        Billet.incrementNextBilletId(Panier.Billet_PanierList.size());
                        Panier.Billet_PanierList.clear();
                        Panier.Snack_PanierList.clear();
                    }
                }
                catch(AchatUnsuccessfulException e)
                {

                }

            }
        }).start();*/
    }
}
