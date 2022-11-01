/*
 *  File:     methodsOn.java
 *  Function: Connection and Decode responses in JSON format logic
 *  Autores:    Ing. Bruno Esteban Martínez Millán e Ing. Silverio Baltazar Barrientos Zarate.
 *  Copyright 2022 Gobierno del Estado de México
 *
 *
 */


package com.example.jovenmovimiento2022.Controllers;


import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;


import org.apache.http.NameValuePair;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class methodsOn {

    protected InputStream IS = null;
    protected JSONObject OBJUP = null;
    static String jsonResp = "";


    static String html = "";
    static String textBody = "";

    //Construct void
    public methodsOn() {
    }


    public JSONObject Requested(String URL, String identifier, List<NameValuePair> params) throws IOException {


        //Adecuando la respuesta JSON

        switch (identifier) {
            case "POST":
                try {
                    DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
                    HttpPost post = new HttpPost(URL);
                    post.setEntity(new UrlEncodedFormEntity(params));
                    HttpResponse response = defaultHttpClient.execute(post);
                    HttpEntity entity = response.getEntity();
                    IS = entity.getContent();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            case "GET":
                DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
                String parameterString = URLEncodedUtils.format(params, "utf-8");

                URL += "?" + parameterString;

                HttpGet get = new HttpGet(URL);

                HttpResponse response = defaultHttpClient.execute(get);
                HttpEntity entity = response.getEntity();
                IS = entity.getContent();
        }


        //Parseando objeto JSON a String
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    IS, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            IS.close();
            jsonResp = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }


        try {
            OBJUP = new JSONObject(jsonResp);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return OBJUP;
    }


}




