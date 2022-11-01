
/*
 *  File:     keyGen.java
 *  Function: Context of local key for Web Service RENAPO tutorial
 *  Autores:  Ing. Bruno Esteban Martínez Millán e Ing. Silverio Baltazar Barrientos Zarate.
 *  Copyright 2022 Gobierno del Estado de México
 *
 *
 */


package com.example.jovenmovimiento2022.Models;

public class keyGen {
    //keygen parameters

    protected String URLKeyGen, passwordSet;

    //Constructor with keygen parameters

    //Null Constructor
    public keyGen(){}

    public void KeySet(String URLKeyGen, String passwordSet){
        System.setProperty("javax.net.ssl.trustStore", this.URLKeyGen = URLKeyGen);
        System.setProperty("javax.net.ssl.trustStorePassword", this.passwordSet = passwordSet);
    }
}
