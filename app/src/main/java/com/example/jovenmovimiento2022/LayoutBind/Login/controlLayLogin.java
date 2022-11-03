
/*
 *  Autor:    Ing. en I. Bruno Esteban Martínez Millán
 *  Copyright 2022 Gobierno del Estado de México
 */

package com.example.jovenmovimiento2022.LayoutBind.Login;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jovenmovimiento2022.Controllers.methodsOn;
import com.example.jovenmovimiento2022.DataBaseLocalStorage.Estructure_Session;
import com.example.jovenmovimiento2022.DataBaseLocalStorage.Estructure_person;
import com.example.jovenmovimiento2022.DataBaseLocalStorage.Estructure_personImages;
import com.example.jovenmovimiento2022.Dialogs.Alerts.forConsultaCurp;
import com.example.jovenmovimiento2022.customscann.CustomScannerActivity;
import com.example.jovenmovimiento2022.interfaces.methodServer;
import com.example.jovenmovimiento2022.interfaces.navigate;
import com.example.jovenmovimiento2022.DataBaseLocalStorage.DBOpenHelper;

import com.example.jovenmovimiento2022.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class controlLayLogin extends Activity implements navigate, methodServer {


    //Search person for info in table -> RESPONSE THE PIN
    private static final String URL = "http://187.216.191.87:8060/api/onLogonTestWithPIN/";
    //Get session person -> RESPONSE THE CURP
    private static final String URLSg = "http://187.216.191.87:8060/api/onLogonTestWithCURPRPIN/";
    //Post Info person
    private static final String URLIn = "http://187.216.191.87:8060/api/altaPersonBen";
    //Post info person images
    private static final String URLIm = "http://187.216.191.87:8060/api/altaPersonImage";
    //Post info person session
    private static final String URLSp = "http://187.216.191.87:8060/api/altaPersonSession";
    //Requested
    private static final String Identity = "GET";
    private static final String IdentityAux = "POST";

    //

    protected static Context context;
    Button Login, login_to_principal;
    TextView pass;
    String currentPIN, selfPin, selfCURP, currentCURP;
    //
    forConsultaCurp alertedisNull = new forConsultaCurp(this);
    AlertDialog.Builder testingQRCurp;
    AlertDialog Indications;
    protected String stateResponse;
    private ProgressDialog dialogoUp;
    methodsOn methods = new methodsOn();
    //Open localDataBase
    DBOpenHelper dbOpenHelper;
    //Testings
    protected JSONObject json;
    String SessionCurp, SessionPIN, SessionifExist;
    String CURPLocal;
    boolean isConnected;
    //Delayed task
    protected Handler IntStatusTask = new Handler();

    String curp_per, compro_i, curp_i, acta_i, inden_re_i, inden_ad_i, benef_fu_i;
    String nombre_s, ApellP_s, ApellM_s, Curp_s, Genero_s, fecha_s, Paises_s, Noest_s;
    String curp_Session;
    int passPIN;
    //Auxiliars responses
    protected String sessionAccount = "non";
    protected String sessionAccountSuccess = "is";

    String TestingPass;
    int number;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controlLayLogin.context = getApplicationContext();

        dbOpenHelper = new DBOpenHelper(this);
        testingQRCurp = new AlertDialog.Builder(this);
        onSetScreen();

        findViewById(R.id.PINED);
        Login = findViewById(R.id.cargarFotos);
        pass = findViewById(R.id.PINED);
        TestingPass = pass.getText().toString();

        //Login Validator options
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor ifSessionCount = dbOpenHelper.retriveNumberOfSession(database);
        ifSessionCount.moveToFirst();
        //Revision

       if(ifSessionCount.getCount() <= 0){
            alertedisNull.alertisNull("Status: PIN", "Al iniciar tu registro la aplicación te asignará un PIN de inicio. Asegurate de guardarlo para cualquier aclaración", false, "Continuar", true);
        }else if(ifSessionCount.getCount() >= 1){
           IntStatusTask.postDelayed(taskActivityNetwork, 0);
       }
        ifSessionCount.close();
        //

        //
        database = dbOpenHelper.getReadableDatabase();
        //Only for session start
        Cursor lastPinSession = dbOpenHelper.retriveNumberOfSession(database);
        while (lastPinSession.moveToNext()) {
            TextView pass;
            pass = findViewById(R.id.PINED);
            pass.setText(lastPinSession.getString(lastPinSession.getColumnIndexOrThrow(Estructure_Session.RANDOM_NUMBER)));
        }
        lastPinSession.close();

        //Revision

        database = dbOpenHelper.getReadableDatabase();
        Cursor images = dbOpenHelper.retriveAllinfoPersonImages(database);
        Cursor Info = dbOpenHelper.retriveAllinfoPerson(database);
        Cursor PINUNIQUE = dbOpenHelper.retriveNumberOfSession(database);


        while (images.moveToNext()) {

            curp_per = images.getString(images.getColumnIndexOrThrow(Estructure_personImages.CURP_PER));
            compro_i = images.getString(images.getColumnIndexOrThrow(Estructure_personImages.COMPROBANTE_ES));
            curp_i = images.getString(images.getColumnIndexOrThrow(Estructure_personImages.CURP_PH));
            acta_i = images.getString(images.getColumnIndexOrThrow(Estructure_personImages.ACTA_NAC));
            inden_re_i = images.getString(images.getColumnIndexOrThrow(Estructure_personImages.IDENT_INE_REVER));
            inden_ad_i = images.getString(images.getColumnIndexOrThrow(Estructure_personImages.IDENT_INE_ADVER));
            benef_fu_i = images.getString(images.getColumnIndexOrThrow(Estructure_personImages.BENEF_FUR));
        }
        images.close();

        while (Info.moveToNext()) {

            nombre_s = Info.getString(Info.getColumnIndexOrThrow(Estructure_person.NOMBRES_PER));
            ApellP_s = Info.getString(Info.getColumnIndexOrThrow(Estructure_person.PATERNO_A));
            ApellM_s = Info.getString(Info.getColumnIndexOrThrow(Estructure_person.MATERNO_A));
            Curp_s = Info.getString(Info.getColumnIndexOrThrow(Estructure_person.CURP_PER));
            Paises_s = Info.getString(Info.getColumnIndexOrThrow(Estructure_person.STAT_NAC));
            Genero_s = Info.getString(Info.getColumnIndexOrThrow(Estructure_person.GENERO_PER));
            fecha_s = Info.getString(Info.getColumnIndexOrThrow(Estructure_person.FECHANA_PER));
            Noest_s = Info.getString(Info.getColumnIndexOrThrow(Estructure_person.STAT_NONAC));
        }
        Info.close();

        TextView TEst;
        TEst =findViewById(R.id.benef_fu_i_tested);
        TEst.setText(TestingPass);
        while(PINUNIQUE.moveToNext()){
            curp_Session = PINUNIQUE.getString(PINUNIQUE.getColumnIndexOrThrow(Estructure_Session.CURP_PH));
            passPIN = PINUNIQUE.getInt(PINUNIQUE.getColumnIndexOrThrow(Estructure_Session.RANDOM_NUMBER));
        }
        PINUNIQUE.close();

                /*
                String curp_per, compro_i, curp_i, acta_i, inden_re_i, inden_ad_i, benef_fu_i;
                String nombre_s, ApellP_s, ApellM_s, Curp_s, Genero_s, fecha_s, Paises_s, Noest_s;
                */
    }


    Runnable taskActivityNetwork = new Runnable() {
        @Override
        public void run() {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnected();
            if (!isConnected) {
                SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
                Cursor onlyReceiveMessage = dbOpenHelper.retriveNumberOfSession(database);
                onlyReceiveMessage.moveToFirst();
                if(onlyReceiveMessage.getCount() <=0){
                    alertedisNull.alertisNull("Estatus: Sin conexión", "Sus datos se guardarán localmente. Conectate a una red para registrarte correctamente.", false, "Continuar", true);
                }else{
                    alertedisNull.alertisNull("Estatus: Sin conexión", "Conectate a una red para saber si eres beneficiario o no.", false, "Continuar", true);
                }
                        } else {

                    new LOCALSTORAGE().execute();
            }
            IntStatusTask.postDelayed(this, 20000);
        }
    };


    public void onSetScreen() {
        setContentView(R.layout.login);
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor ifSomething = dbOpenHelper.retriveNumberOfSession(database);
        ifSomething.moveToFirst();
        if (ifSomething.getCount() <= 0) {
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
            testingQRCurp.setNegativeButton("Saltar", (dialog, which) -> testingQRCurp.setCancelable(true));
            Indications = testingQRCurp.create();
            Indications.show();
            viewNavintent();
        } else {
            //alertedisNull.alertisNull("Indicaciones", "Asegurate de tener TODOS tus documentos e información a la mano para evitar que tus datos se pierdan. ", false, "Continuar", true);
            //Revision -> task duplicate -> similar logic handler
            Handler timeForLogin = new Handler();
            timeForLogin.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Login.setEnabled(false);
                    alertedisNull.alertisNull("Estatus", "Reinicia la aplicación. Código de inicio: " + selfPin + Curp_s, false, "Continuar", true);
                    IntStatusTask.removeCallbacks(taskActivityNetwork);
                }
            }, 20000);

            pass = findViewById(R.id.PINED);
            selfPin = findPin(currentPIN);
            selfCURP = findCurp(currentCURP);

            viewNavIntentisFinalLogon();

        }
    }

    public void viewNavintent() {
        viewComponents(login_to_principal, R.id.cargarFotos).setOnClickListener(view -> {
            //Initialize ZXING Library
            IntentIntegrator integrator = new IntentIntegrator(controlLayLogin.this);
            integrator.setCaptureActivity(CustomScannerActivity.class);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            integrator.setCameraId(0);
            integrator.setBeepEnabled(false);
            integrator.initiateScan();
        });
    }

    public void viewNavIntentisFinalLogon(){
         viewComponents(login_to_principal, R.id.cargarFotos).setOnClickListener(view -> {
             new onLogon().execute();
        });
    }

    @Override
    public Button viewComponents(Button Component, int idreference) {
        return findViewById(idreference);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                alertedisNull.alertisNull("Algo salió mal", "El resultado no fue el esperado. Solo código de barras", false, "Continuar", true);
            } else {
                //Revision
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnected();
                if(!isConnected){
                    stateResponse = result.getContents();
                    new LOCALSTORE().execute();
                }else{
                    stateResponse = result.getContents();
                    new LOGIN().execute();
                }
                //
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //

    public class LOCALSTORAGE extends AsyncTask<String, String, String> {

        @Override
        protected void onCancelled() {
            alertedisNull.alertisNull("Estatus: Datos", "Inicia tu pre-registro", false, "Continuar", true);

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogoUp = new ProgressDialog(controlLayLogin.this);
            dialogoUp.setMessage("Comprobando ...");
            dialogoUp.setIndeterminate(false);
            dialogoUp.setCancelable(false);
            dialogoUp.show();
            //Request validations = 3
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Thread.sleep(1000);
                SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
                Cursor sessions = dbOpenHelper.retriveSessions(database);
                Cursor images = dbOpenHelper.retriveAllinfoPersonImages(database);
                Cursor Info = dbOpenHelper.retriveAllinfoPerson(database);
                sessions.moveToFirst();
                images.moveToFirst();
                Info.moveToFirst();

                if (sessions.getCount() <= 0 || Info.getCount() <= 0 || images.getCount() <= 0) {
                    new LOCALSTORAGE().cancel(true);
                    dialogoUp.dismiss();
                }
                else {
                    try {
                        onPOSTIfNotInfoPerson(URLIn, IdentityAux);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        onPostIfNotInfoPersonImages(URLIm, IdentityAux);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        onPostifNotSessionPerson(URLSp, IdentityAux);
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            dialogoUp.dismiss();
            IntStatusTask.removeCallbacks(taskActivityNetwork);
            //alertedisNull.alertisNull("Status: Datos", "Tus datos fueron guardados. Puedes cerrar la aplicación", false, "Continuar", true);
        }

    }

    public class LOGIN extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogoUp = new ProgressDialog(controlLayLogin.this);
            dialogoUp.setMessage("Iniciando...");
            dialogoUp.setIndeterminate(false);
            dialogoUp.setCancelable(false);
            dialogoUp.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                GET(URL + stateResponse, Identity);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialogoUp.dismiss();
            // try null reference response
                SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
                Cursor ifInfo = dbOpenHelper.retriveNumberOfSession(database);
                ifInfo.moveToFirst();

                    Intent intent = new Intent(controlLayLogin.this, controlLayBeneficioNo.class);
                    Bundle bundle = new Bundle();
                    //Create a Key for local session
                    bundle.putString("CurpSendSession", SessionPIN);
                    bundle.putString("CurpSendSessionF", stateResponse);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    IntStatusTask.removeCallbacks(taskActivityNetwork);

        }
    }

    public class onLogon extends AsyncTask<String, String, String>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogoUp = new ProgressDialog(controlLayLogin.this);
            dialogoUp.setMessage("Iniciando sesión ...");
            dialogoUp.setIndeterminate(false);
            dialogoUp.setCancelable(false);
            dialogoUp.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                GETAuthPIN(URLSg + selfPin , Identity);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialogoUp.dismiss();
            SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
            Cursor getIfNotCache = dbOpenHelper.retriveNumberOfSession(database);
            getIfNotCache.moveToFirst();
            if(sessionAccountSuccess.equals(SessionPIN)){
                if(getIfNotCache.getCount() <= 0){
                    Intent intent = new Intent(controlLayLogin.this, controlLayLogin.class);
                    startActivity(intent);
                    alertedisNull.alertisNull("Status: Datos", "0011: Ya eres beneficiario. Cierra o desinstala la app si hiciste tu registro", false, null, true);
                           }
            }else{
                Intent intent = new Intent(controlLayLogin.this, QreadLayHelper.class);
                Bundle bundle = new Bundle();
                bundle.putString("CurpSendSession", SessionPIN);
                intent.putExtras(bundle);
                startActivity(intent);
            }
            //
            if(sessionAccount.equals(SessionPIN)){
                Intent intent = new Intent(controlLayLogin.this, controlLayLogin.class);
                startActivity(intent);
                alertedisNull.alertisNull("Algo salió mal", "Contacta al administrador", false, null, true);
                          }else{
                Intent intent = new Intent(controlLayLogin.this, controlLayBeneficioNo.class);
                Bundle bundle = new Bundle();
                bundle.putString("CurpSendSession", SessionPIN);
                bundle.putString("CurpSendSessionCurp", SessionifExist);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            Intent intent = new Intent(controlLayLogin.this, controlLayBeneficioNo.class);
            Bundle bundle = new Bundle();
            bundle.putString("CurpSendSession", Curp_s);
            bundle.putString("CurpSendSessionF", Curp_s);
            intent.putExtras(bundle);
            startActivity(intent);

            IntStatusTask.removeCallbacks(taskActivityNetwork);
        }
    }


        public class LOCALSTORE extends AsyncTask<String, String, String>{

            @Override
            protected void onCancelled() {
                super.onCancelled();
                Intent intent = new Intent(controlLayLogin.this, controlLayLogin.class);
                Bundle bundle = new Bundle();
                //Create a Key for local session
                bundle.putString("CurpSendSession", stateResponse);
                bundle.putString("CurpSendSessionF", stateResponse);
                intent.putExtras(bundle);
                Button isMessage = (Button) findViewById(R.id.cargarFotos);
                isMessage.setEnabled(true);
                isMessage.setText("");
                startActivity(intent);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialogoUp = new ProgressDialog(controlLayLogin.this);
                dialogoUp.setMessage("Iniciando localmente ...");
                dialogoUp.setIndeterminate(false);
                dialogoUp.setCancelable(false);
                dialogoUp.show();
            }

            @Override
            protected String doInBackground(String... strings) {
                SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
                Cursor ifLocal = dbOpenHelper.retriveNumberOfSession(database);
                ifLocal.moveToFirst();
                if(ifLocal.getCount() >=1){
                    new LOCALSTORE().cancel(true);
                    dialogoUp.dismiss();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                dialogoUp.dismiss();
                Intent intent = new Intent(controlLayLogin.this, controlLayBeneficioNo.class);
                Bundle bundle = new Bundle();
                //Create a Key for local session
                bundle.putString("CurpSendSession", stateResponse);
                bundle.putString("CurpSendSessionF", stateResponse);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }


    public void onPOSTIfNotInfoPerson(String URL, String Identity) throws Exception {
        List<NameValuePair> params = new ArrayList<>();

        try {
            params.add(new BasicNameValuePair("curpPerson", Curp_s));
            params.add(new BasicNameValuePair("apellidoP", ApellP_s));
            params.add(new BasicNameValuePair("apellidoM", ApellM_s));
            params.add(new BasicNameValuePair("nombrePerson", nombre_s));
            params.add(new BasicNameValuePair("generoPerson", Genero_s));
            params.add(new BasicNameValuePair("fechaBirPerson", fecha_s));
            params.add(new BasicNameValuePair("paisPerson", Paises_s));
            params.add(new BasicNameValuePair("noEstPerson", Noest_s));
            // 8 parametros
            JSONObject json = methods.Requested(URL, Identity, params);
            Log.d("Creando respuesta ...", json.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onPostIfNotInfoPersonImages(String URL, String Identity) throws Exception {
        List<NameValuePair> params = new ArrayList<>();
        try {
            params.add(new BasicNameValuePair("curp_per", curp_per));
            params.add(new BasicNameValuePair("comprobante_es", compro_i));
            params.add(new BasicNameValuePair("curp_ph", curp_i));
            params.add(new BasicNameValuePair("acta_nac", acta_i));
            params.add(new BasicNameValuePair("indent_ine_adver", inden_ad_i));
            params.add(new BasicNameValuePair("indent_ine_rever", inden_re_i));
            params.add(new BasicNameValuePair("benef_fur", benef_fu_i));
            // 7 paramtros

            JSONObject json = methods.Requested(URL, Identity, params);
            Log.d("Creando respuesta ...", json.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onPostifNotSessionPerson(String URL, String Identity) throws Exception{
        List<NameValuePair> params = new ArrayList<>();
        try {
            params.add(new BasicNameValuePair("curp_per", Curp_s));
            params.add(new BasicNameValuePair("pin_benef", Integer.toString(passPIN)));

            JSONObject json = methods.Requested(URL, Identity, params);
            Log.d("Creando respuesta ...", json.toString());

        } catch (Exception e) {
            e.printStackTrace();
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
            SessionPIN = json.getString("pin_benef");
            Log.d("Creando respuesta ...", json.toString());
            Log.d("Creando respuesta ...", SessionPIN);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //GET CURP
    public void POSTAuth(String URL, String Identity, int PIN) throws Exception {

        List<NameValuePair> params = new ArrayList<>();
        try {
            params.add(new BasicNameValuePair("curp_per", stateResponse));
            params.add(new BasicNameValuePair("pin_benef", Integer.toString(PIN)));
            json = methods.Requested(URL, Identity, params);

            Log.d("Test de respuesta...", json.toString());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // Revision

    public void GETAuth(String URL, String Identity) throws Exception{
        List<NameValuePair> params = new ArrayList<>();
        try {
            String CURPLocal = null;
            SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
            Cursor ifCurp = dbOpenHelper.retriveNumberOfSession(database);
            while(ifCurp.moveToNext()){
                CURPLocal = ifCurp.getString((ifCurp.getColumnIndexOrThrow(Estructure_Session.CURP_PH)));
            }
            ifCurp.close();
            params.add(new BasicNameValuePair("curpPerson", CURPLocal));
            json = methods.Requested(URL, Identity, params);
            //SessionifExist = json.getJSONObject("0").getString("pin_benef");
            SessionPIN = json.getString("pin_benef");
            Log.d("Creando respuesta ...", json.toString());
            //Log.d("Creando respuesta ...", SessionifExist);
            Log.d("Creando respuesta ...", SessionPIN);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GETAuthPIN(String URL, String Identity) throws Exception{
        List<NameValuePair> params = new ArrayList<>();
        try {
            int PIN = 0;
            SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
            Cursor ifCurp = dbOpenHelper.retriveNumberOfSession(database);
            while(ifCurp.moveToNext()){
                PIN = ifCurp.getInt((ifCurp.getColumnIndexOrThrow(Estructure_Session.RANDOM_NUMBER)));
            }
            ifCurp.close();
            params.add(new BasicNameValuePair("curpPerson", Integer.toString(PIN)));
            json = methods.Requested(URL, Identity, params);
            SessionifExist = json.getJSONObject("0").getString("curp_per");
            SessionPIN = json.getString("pin_benef");
            Log.d("Creando respuesta ...", json.toString());
            Log.d("Creando respuesta ...", SessionifExist);
            Log.d("Creando respuesta ...", SessionPIN);

        } catch (Exception e) {
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

    public String findCurp(String PINCurp){
        SQLiteDatabase findPinn = dbOpenHelper.getReadableDatabase();
        Cursor pin = dbOpenHelper.retriveNumberOfSession(findPinn);
        while(pin.moveToNext()){
            PINCurp = pin.getString(pin.getColumnIndexOrThrow(Estructure_Session.CURP_PH));
        }
        pin.close();
        return PINCurp;
    }
}

