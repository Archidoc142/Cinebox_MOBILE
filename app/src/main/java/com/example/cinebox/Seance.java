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

package com.example.cinebox;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class Seance
{
    public static ArrayList<Seance> seancesArrayList = new ArrayList<>();
    private int id;
    private String dateTime;
    private Film film;
    private String salle_siege;
    private String salle_ecran;

    public Seance(int id, String dateTimeStr, int filmId, String salle_siege, String salle_ecran)
    {
        this.id = id;
        this.film = Film.FilmOnArrayList.get(filmId - 1);

        this.dateTime = dateTimeStr.substring(11, 16);

        this.salle_siege = salle_siege;
        this.salle_ecran = salle_ecran;
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
        return dateTime;
    }

    /**
     * @param dateTime
     * @param filmTitre
     * @return  Id de la séance en fonction de la date et du titre du film
     */
    public static int getIdFromDateAndTitre(String dateTime, String filmTitre) {
        String formattedTime = null;
        try {
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = originalFormat.parse(dateTime);

            DateFormat targetFormat = new SimpleDateFormat("HH:mm");
            formattedTime = targetFormat.format(date);

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
