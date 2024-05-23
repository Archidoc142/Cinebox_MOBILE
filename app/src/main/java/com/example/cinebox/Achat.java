/****************************************
 * Fichier : HistoriqueAchat
 * Auteur : Antoine Auger
 * Fonctionnalité : N/A
 * Date : 14 mai 2024
 * Vérification :
 * Date Nom Approuvé
 * =========================================================
 * Historique de modifications :
 * Date Nom Description
 * =========================================================
 *
 * ****************************************/

package com.example.cinebox;

import java.util.ArrayList;

public class Achat
{
    private static double TPS = 0.05;
    private static double TVQ = 0.09975;

    public static ArrayList<Achat> HistoriqueAchats = new ArrayList<Achat>();

    private ArrayList<Billet> billetsAchat;
    private ArrayList<Grignotine> grignotinesAchat;

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

    public Achat(ArrayList<Billet> billets, ArrayList<Grignotine> grignotines)
    {
        billetsAchat = billets;
        grignotinesAchat = grignotines;
    }

    // constructeur importation depuis BD
    public Achat(int id, String date, double montantBrut, double tps, double tvq, double montantFinal)
    {
        this.id = id;
        this.date = date;
        this.montantBrut = montantBrut;
        this.tps = tps;
        this.tvq = tvq;
        this.montantFinal = montantFinal;
    }

    /*
    public Achat() {
        id = 0;
        date = null;
        montantBrut = 0;
    }*/

    public Achat(String date, float montantBrut) {
        this.date = date;
        this.montantBrut = montantBrut;
        this.tps = montantBrut * TPS;
        this.tvq = montantBrut * TVQ;
        this.montantFinal = this.tps + this.tvq;
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
}
