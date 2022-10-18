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
