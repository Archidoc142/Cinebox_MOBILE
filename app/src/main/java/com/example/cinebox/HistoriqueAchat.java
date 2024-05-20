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
 * =========================================================****************************************/

package com.example.cinebox;

import java.util.ArrayList;

public class HistoriqueAchat {
    public static ArrayList<HistoriqueAchat> HistoriqueAchatOnArrayList = new ArrayList<HistoriqueAchat>();

    private int id;
    private String date;
    private float montant;

    public HistoriqueAchat(int id, String date, float montant) {
        this.id = id;
        this.date = date;
        this.montant = montant;
    }

    public HistoriqueAchat () {
        id = 0;
        date = null;
        montant = 0;
    }

    public HistoriqueAchat (String date, float montant) {
        this.date = date;
        this.montant = montant;
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

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }
}
