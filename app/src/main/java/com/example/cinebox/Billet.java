package com.example.cinebox;

public class Billet {
    private int id;
    private String seance;
    private String film;
    private String date_heure_achat;
    private float montant_achat;
    private String type_billet;

    public Billet(int id, String seance, String film, String date_heure_achat, float montant_achat, String type_billet) {
        this.id = id;
        this.seance = seance;
        this.film = film;
        this.date_heure_achat = date_heure_achat;
        this.montant_achat = montant_achat;
        this.type_billet = type_billet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeance() {
        return seance;
    }

    public void setSeance(String seance) {
        this.seance = seance;
    }

    public String getFilm() {
        return film;
    }

    public void setFilm(String film) {
        this.film = film;
    }

    public String getDate_heure_achat() {
        return date_heure_achat;
    }

    public void setDate_heure_achat(String date_heure_achat) {
        this.date_heure_achat = date_heure_achat;
    }

    public float getMontant_achat() {
        return montant_achat;
    }

    public void setMontant_achat(float montant_achat) {
        this.montant_achat = montant_achat;
    }

    public String getType_billet() {
        return type_billet;
    }

    public void setType_billet(String type_billet) {
        this.type_billet = type_billet;
    }
}
