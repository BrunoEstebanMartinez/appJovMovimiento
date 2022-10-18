
/*
 *  File:     controlLayLogin.java
 *  Function: Login Logic
 *  Autor:    Ing. Bruno Esteban Martínez Millán
 *  Copyright 2022 Gobierno del Estado de México
 *
 *
 */

package com.example.jovenmovimiento2022.LayoutBind.Login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jovenmovimiento2022.Controllers.methodsOn;
import com.example.jovenmovimiento2022.DataBaseLocalStorage.Estructure_Session;
import com.example.jovenmovimiento2022.Dialogs.Alerts.forConsultaCurp;
import com.example.jovenmovimiento2022.customscann.CustomScannerActivity;
import com.example.jovenmovimiento2022.interfaces.methodServer;
import com.example.jovenmovimiento2022.interfaces.navigate;
import com.example.jovenmovimiento2022.DataBaseLocalStorage.DBOpenHelper;


import com.example.jovenmovimiento2022.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.w3c.dom.Text;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class controlLayLogin extends Activity implements navigate, methodServer {

    private static final String URL = "http://187.216.191.87:8060/api/onLogonTesting/";
    private static final String Identity = "GET";

    Button Login, login_to_principal;
    TextView pass;
    String currentPIN, selfPin;
    //
    forConsultaCurp alertedisNull = new forConsultaCurp(this);
    AlertDialog.Builder testingQRCurp;
    AlertDialog Indications;
    protected String stateResponse;
    private ProgressDialog dialogoUp;
    public boolean flag = false;
    methodsOn methods = new methodsOn();
    //Open localDataBase
    DBOpenHelper dbOpenHelper;

    //

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Context
        dbOpenHelper = new DBOpenHelper(this);
        testingQRCurp = new AlertDialog.Builder(this);
        onSetScreen();

        findViewById(R.id.PINED);
        Login = findViewById(R.id.cargarFotos);

        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor ifSessionCount = dbOpenHelper.retriveNumberOfSession(database);
        ifSessionCount.moveToLast();

        if(ifSessionCount.getCount() >= 3){
            Login.setEnabled(false);
        }
        ifSessionCount.close();
        //
        SQLiteDatabase database2 = dbOpenHelper.getReadableDatabase();
        Cursor lastPinSession = dbOpenHelper.retriveNumberOfSession(database2);
        while(lastPinSession.moveToNext()){
            TextView pass;
            pass = findViewById(R.id.PINED);
            pass.setText(lastPinSession.getString(lastPinSession.getColumnIndexOrThrow(Estructure_Session.RANDOM_NUMBER)));
        }
        lastPinSession.close();
    }


    public void onSetScreen(){
        setContentView(R.layout.login);
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor ifSomething = dbOpenHelper.retriveNumberOfSession(database);
        ifSomething.moveToFirst();
        if(ifSomething.getCount() <= 0) {
            alertedisNull.alertisNull("Indicaciones", "Asegurate de tener TODOS tus documentos e información a la mano para evitar que tus datos se pierdan. ", false, "Continuar", true);
            alertedisNull.alertisNull("Indicaciones", "Al presionar el boton 'Ingresar', deberás escanear el código de barras impreso en la parte posterior izquierda de tu documento CURP y después el QR correspondiente.", false, "Continuar", true);
            testingQRCurp.setTitle("Antes de comenzar");
            testingQRCurp.setMessage("Prueba el código QR impreso en tu documento físico (CURP). Si el lector tarda por mas de 20 segundos, tu código no es legible, sin embargo, puedes escanear directamente un documento digital. Si estás seguro de que tu código es legible, puede saltar esta prueba");
            testingQRCurp.setPositiveButton("Probar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                        Intent onsetQRTesting = new Intent(controlLayLogin.this, testingUserQr.class);
                        startActivity(onsetQRTesting);
                }
            });
            testingQRCurp.setNegativeButton("Saltar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    testingQRCurp.setCancelable(true);
                }
            });
            Indications = testingQRCurp.create();
            Indications.show();

            viewNavintent();
        }else{
            Handler timeForLogin = new Handler();
            timeForLogin.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Login.setEnabled(false);
                    alertedisNull.alertisNull("Estatus", "Reinicia la aplicación. Código de inicio: " + selfPin, false, "Continuar", true);
                }
            }, 20000);
            pass = findViewById(R.id.PINED);
            selfPin = findPin(currentPIN);
            if(selfPin.equals(findPin(currentPIN))){
                viewComponents(login_to_principal, R.id.cargarFotos).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Intent moveToPrincipal = new Intent(controlLayLogin.this, QreadLayHelper.class);
                        startActivity(moveToPrincipal);
                    }
                });
            }else{
                alertedisNull.alertisNull("PIN incorrecto", "Contacta al administrador " + selfPin, false, "Continuar", true);
            }

        }
    }


    public void viewNavintent(){
            viewComponents(login_to_principal, R.id.cargarFotos).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    //Initialize ZXING Library
                    IntentIntegrator integrator = new IntentIntegrator(controlLayLogin.this);
                    integrator.setCaptureActivity(CustomScannerActivity.class);
                    integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                    integrator.setCameraId(0);
                    integrator.setBeepEnabled(false);
                    integrator.initiateScan();
                }
            });
        }





    @Override
    public Button viewComponents(Button Component, int idreference) {
        return findViewById(idreference);

    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if (result.getContents() == null){
                alertedisNull.alertisNull("Algo salió mal", "El resultado no fue el esperado. Solo código de barras", false, "Continuar", true);
            }
            else{

                        stateResponse = result.getContents();
                        new LOGIN().execute();

            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }


    }

    public class LOGIN extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogoUp = new ProgressDialog(controlLayLogin.this);
            dialogoUp.setMessage("Iniciando sesion");
            dialogoUp.setIndeterminate(false);
            dialogoUp.setCancelable(false);
            dialogoUp.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            try {
              GET(URL + stateResponse, Identity);
                //Generate randnumber with min and max numbers
                int max = 6000;
                int min = 1;
                int PIN = new Random().nextInt((max - min) + 1) + min;
                regSessionPerson(stateResponse, PIN);
            } catch (Exception e) {
                e.printStackTrace();
                Intent intent = new Intent(controlLayLogin.this, controlLayLogin.class);
                startActivity(intent);
                alertedisNull.alertisNull("Algo salió mal", "El resultado no fue el esperado", false, "Continuar", true);;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialogoUp.dismiss();
            alertedisNull.alertisNull("Iniciado", "Status: Alta", false, "Continuar", true);
            Intent intent = new Intent(controlLayLogin.this, controlLayBeneficioNo.class);
            Bundle bundle = new Bundle();
            //Create a Key for local session
            bundle.putString("CurpSendSession", stateResponse);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }




    @Override
    public void POST(String URL, String Identity) throws Exception {

    }

    @Override
    public void GET(String URL, String Identity) throws Exception {
        List<NameValuePair> params = new ArrayList<>();
        try{
            params.add(new BasicNameValuePair("curpPerson",  stateResponse));
            JSONObject json  =  methods.Requested(URL, Identity, params);
            Log.d("Creando respuesta ...", json.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void PUT(String URL, String Identity) throws Exception {

    }

    public String findPin(String PINMatch){
        SQLiteDatabase findPinn = dbOpenHelper.getReadableDatabase();
        Cursor pin = dbOpenHelper.retriveNumberOfSession(findPinn);
        while(pin.moveToNext()){
         PINMatch = pin.getString(pin.getColumnIndexOrThrow(Estructure_Session.RANDOM_NUMBER));
        }
        pin.close();

        return PINMatch;
    }

    public void regSessionPerson(String keyCurp, int randNumb){
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        dbOpenHelper.inSessionPerson(keyCurp, randNumb, database);
        dbOpenHelper.close();
    }

}
