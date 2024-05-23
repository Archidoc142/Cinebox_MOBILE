package com.example.cinebox;

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
    private LocalDateTime dateTime;
    private Film film;

    public Seance(int id, String dateTimeStr, Film film)
    {
        this.id = id;
        this.film = film;

        this.dateTime = LocalDateTime.parse(dateTimeStr);
    }

    public int getId()
    {
        return id;
    }

    public LocalDateTime getDateTime()
    {
        return dateTime;
    }

    public Film getFilm()
    {
        return film;
    }
}
