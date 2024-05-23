package com.example.cinebox;

public class GrignotineQuantite
{
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
