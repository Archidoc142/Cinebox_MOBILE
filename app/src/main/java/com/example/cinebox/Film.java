/****************************************
 * Fichier : Film
 * Auteur : Antoine Auger
 * Fonctionnalité : N/A
 * Date : 16 mai 2024
 * Vérification :
 * Date Nom Approuvé
 * =========================================================
 * Historique de modifications :
 * Date Nom Description
 * =========================================================****************************************/

package com.example.cinebox;

import org.json.JSONArray;

import java.util.ArrayList;

public class Film {
    public static ArrayList<Film> FilmOnArrayList = new ArrayList<Film>();

    private int id;
    private String titre;
    private String duration;
    private String description;
    private String date_de_sortie;
    private String date_fin_diffusion;
    private String categorie;
    private String realisateur;
    private String image_affiche;
    private String etat_film;

    public Film(int id, String titre, String duration, String description, String date_de_sortie, String date_fin_diffusion, String categorie, String realisateur, String image_affiche, String etat_film) {
        this.id = id;
        this.titre = titre;
        this.duration = duration;
        this.description = description;
        this.date_de_sortie = date_de_sortie;
        this.date_fin_diffusion = date_fin_diffusion;
        this.categorie = categorie;
        this.realisateur = realisateur;
        this.image_affiche = image_affiche;
        this.etat_film = etat_film;
    }

    public String getEtat_film() {
        return etat_film;
    }

    public void setEtat_film(String etat_film) {
        this.etat_film = etat_film;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_de_sortie() {
        return date_de_sortie;
    }

    public void setDate_de_sortie(String date_de_sortie) {
        this.date_de_sortie = date_de_sortie;
    }

    public String getDate_fin_diffusion() {
        return date_fin_diffusion;
    }

    public void setDate_fin_diffusion(String date_fin_diffusion) {
        this.date_fin_diffusion = date_fin_diffusion;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getRealisateur() {
        return realisateur;
    }

    public void setRealisateur(String realisateur) {
        this.realisateur = realisateur;
    }

    public String getImage_affiche() {
        return image_affiche;
    }

    public void setImage_affiche(String image_affiche) {
        this.image_affiche = image_affiche;
    }
}
