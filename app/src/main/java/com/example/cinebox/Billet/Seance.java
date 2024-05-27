/****************************************
 * Fichier : Seance.java
 * Auteur : Hicham, Antoine, Arthur
 * Fonctionnalité : Classe qui représente une séance pour un film
 * Date : 23/05/2024
 *
 * Vérification :
 * Date     Nom     Approuvé
 * =========================================================
 *
 *
 * Historique de modifications :
 * Date     Nom     Description
 * =========================================================
 * 23/05/2024  Hicham  Création de la classe Séance
 * 25/05/2024  Antoine, Hicham    Modifications pour utiliser dans FilmActivity et Panier
 * 26/05/2024   Arthur  Ajout getIdFromDateAndTitre()
 * ****************************************/

package com.example.cinebox.Billet;

import com.example.cinebox.Film.Film;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Seance
{
    /**
     * Tableau contenant toutes les séances du cinéma
     */
    public static ArrayList<Seance> seancesArrayList = new ArrayList<>();

    private int id;
    private int noSalle;
    private String dateTime;
    private Film film;
    private String salle_siege;
    private String salle_ecran;

    /**
     * Constructeur d'une séance de film
     * @param id ID de la séance
     * @param dateTimeStr Date et heure de la séance
     * @param filmId ID du film de la séance
     * @param salle_siege Type de siège
     * @param salle_ecran Type d'écran
     * @param noSalle Numéro de salle
     */
    public Seance(int id, String dateTimeStr, int filmId, String salle_siege, String salle_ecran, int noSalle)
    {
        this.id = id;
        this.film = Film.FilmOnArrayList.get(filmId - 1);

        this.dateTime = dateTimeStr.substring(11, 16);

        this.salle_siege = salle_siege;
        this.salle_ecran = salle_ecran;
        this.noSalle = noSalle;
    }

    public int getId()
    {
        return id;
    }

    public String getDateTime()
    {
        return dateTime;
    }

    public Film getFilm()
    {
        return film;
    }

    public String getSalle_siege() {
        return salle_siege;
    }

    public void setSalle_siege(String salle_siege) {
        this.salle_siege = salle_siege;
    }

    public String getSalle_ecran() {
        return salle_ecran;
    }

    public void setSalle_ecran(String salle_ecran) {
        this.salle_ecran = salle_ecran;
    }

    @Override
    public String toString() {
        return dateTime + " | Salle " + noSalle;
    }

    /**
     * @param dateTime Date et heure de la séance
     * @param filmTitre Titre du film de la séance
     * @return  Id de la séance en fonction de la date et du titre du film
     */
    public static int getIdFromDateAndTitre(String dateTime, String filmTitre) {
        String formattedTime = null;
        try {
            // Formatter la date
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = originalFormat.parse(dateTime);

            // Extraire l'heure et les minutes
            DateFormat targetFormat = new SimpleDateFormat("HH:mm");
            formattedTime = targetFormat.format(date);

            // Afficher le résultat
            System.out.println("Heure et minutes uniquement: " + formattedTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (Seance seance : seancesArrayList) {
            if (seance.getDateTime().equals(formattedTime) && seance.getFilm().getTitre().equalsIgnoreCase(filmTitre)) {
                return seance.getId();
            }
        }
        throw new IllegalArgumentException("Seance not found for details: " + dateTime + ", " + filmTitre);
    }
}
