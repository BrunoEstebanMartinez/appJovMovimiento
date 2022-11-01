/*
 *  File:     Estructure_Session.java
 *  Function: Model table JOVMOVI_SESSION in local storage database
 *  Autores:  Ing. Bruno Esteban Martínez Millán e Ing. Silverio Baltazar Barrientos Zarate.
 *  Copyright 2022 Gobierno del Estado de México
 *
 *
 */
package com.example.jovenmovimiento2022.DataBaseLocalStorage;

public class Estructure_Session {
    public static final String TABLA_PERSONSES = "JOVMOVI_SESSION";
    public static final String CURP_PH = "CURP_PH";
    public static final String RANDOM_NUMBER = "RANDOM_NUMBER";



    public static final String CREAR_TABLA_SES = "create table "+TABLA_PERSONSES+"(ID_SESSIONS INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CURP_PH +" VARCHAR, "+ RANDOM_NUMBER +" INTEGER)";


    public static final String ELIMINAR_TABLA_PERSONSES = "drop table if exists " + TABLA_PERSONSES;



}
