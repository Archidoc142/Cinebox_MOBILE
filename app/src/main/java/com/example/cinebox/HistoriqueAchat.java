package com.example.cinebox;

import java.util.ArrayList;

public class HistoriqueAchat {
    public static ArrayList<HistoriqueAchat> HistoriqueAchatOnArrayList = new ArrayList<HistoriqueAchat>();

    public int id;
    public String date;
    public float montant;

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
