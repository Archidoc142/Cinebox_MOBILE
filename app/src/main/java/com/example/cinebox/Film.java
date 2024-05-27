/****************************************
 * Fichier : Film
 * Auteur : Antoine Auger
 * Fonctionnalité : N/A
 * Date : 16 mai 2024
 * Vérification :
 * Date Nom Approuvé
 * =========================================================
 * Historique de modifications :
 * Date     Nom     Description
 * =========================================================
 * 26/05/2023   Arthur  Ajout getIdFromTitre()
 * ****************************************/

package com.example.cinebox;

import org.json.JSONArray;

import java.util.ArrayList;

public class Film {
    /**
     * Liste statique de tous les films du cinéma
     */
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

    /**
     * Constructeur d'un film
     *
     * @param id ID du film
     * @param titre Titre du film
     * @param duration Durée du film (préformaté en HhM)
     * @param description Description du film
     * @param date_de_sortie Date de sortie du film
     * @param date_fin_diffusion Date de fin de diffusion du film
     * @param categorie Catégorie du film
     * @param realisateur Réalisateur du film
     * @param image_affiche URL de l'affiche du film
     * @param etat_film État du film (en salle, indisponible, bientôt disponible)
     */
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public String getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_affiche() {
        return image_affiche;
    }

    /**
     * Récupérer un objet film avec son titre
     * @param titre Titre du film
     * @return Id du film selon son titre
     */
    public static int getIdFromTitre(String titre) {
        for (Film film : FilmOnArrayList) {
            if (film.getTitre().equalsIgnoreCase(titre)) {
                return film.getId();
            }
        }
        throw new IllegalArgumentException("Film not found for title: " + titre);
    }
}
