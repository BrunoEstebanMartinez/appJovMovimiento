/*
 *  File:     Estructure_personImages.java
 *  Function: Model table JOVMOVI_IMAGES in local storage database
 *  Autores:  Ing. Bruno Esteban Martínez Millán e Ing. Silverio Baltazar Barrientos Zarate.
 *  Copyright 2022 Gobierno del Estado de México
 *
 *
 */
package com.example.jovenmovimiento2022.DataBaseLocalStorage;

public class Estructure_personImages {
    public static final String TABLA_PERSONIM = "JOVMOVI_IMAGES";
    public static final String CURP_PER = "CURP_PER";
    public static final String COMPROBANTE_ES = "COMPROBANTE_ES";
    public static final String CURP_PH = "CURP_PH";
    public static final String ACTA_NAC = "ACTA_NAC";
    public static final String IDENT_INE_REVER = "IDENT_INE_REVER";
    public static final String IDENT_INE_ADVER = "IDENT_INE_ADVER";
    public static final String BENEF_FUR = "BENEF_FUR";


    public static final String CREAR_TABLA_PERSONIM = "create table "+TABLA_PERSONIM+"("
            +CURP_PER+" VARCHAR, "+COMPROBANTE_ES+" TEXT, "+CURP_PH+" TEXT, "+ACTA_NAC+" TEXT, " +IDENT_INE_REVER+" TEXT, "
            +IDENT_INE_ADVER+" TEXT, "+BENEF_FUR+" TEXT)";

    public static final String ELIMINAR_TABLA_PERSONIM = "drop table if exists" + TABLA_PERSONIM;


}
