package com.example.cinebox;

import java.util.ArrayList;

public class GrignotineQuantite
{
    //public static ArrayList<GrignotineQuantite> grignotineQuantiteList = new ArrayList<>();
    private Grignotine grignotine;
    private int quantite;

    public GrignotineQuantite(Grignotine grignotine, int quantite)
    {
        this.grignotine = grignotine;
        this.quantite = quantite;
    }

    public Grignotine getGrignotine() {
        return grignotine;
    }

    public void setGrignotine(Grignotine grignotine) {
        this.grignotine = grignotine;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
