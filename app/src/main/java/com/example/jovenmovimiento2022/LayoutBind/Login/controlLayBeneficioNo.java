package com.example.jovenmovimiento2022.LayoutBind.Login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jovenmovimiento2022.DataBaseLocalStorage.DBOpenHelper;
import com.example.jovenmovimiento2022.DataBaseLocalStorage.Estructure_Session;
import com.example.jovenmovimiento2022.Dialogs.Alerts.forConsultaCurp;
import com.example.jovenmovimiento2022.Controllers.methodsOn;
import com.example.jovenmovimiento2022.interfaces.methodServer;
import com.example.jovenmovimiento2022.R;
import com.example.jovenmovimiento2022.interfaces.navigate;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.constraintlayout.widget.ConstraintLayout;



public class controlLayBeneficioNo extends Activity implements navigate, methodServer{


    methodsOn methods = new methodsOn();
    forConsultaCurp alertedisNull = new forConsultaCurp(this);
    private ProgressDialog dialogoUp;
    DBOpenHelper dbOpenHelper;

    public Button principal_qr;
    protected String stateResponse;
    //retrive the session user
    protected String SessionC, SessionF, SessionServeJson, SessionLocal;
    protected TextView SessionCurp;
    protected TextView SessionLeyen;

    protected String URL = "http://187.216.191.87:8060/api/altaPersonSession";
    protected String URLrg = "http://187.216.191.87:8060/api/altaRegPerson";
    protected String Identity = "POST";
    protected String valuated = "non";
    protected String valueIsSucces = "is";
    protected String leyendaIsAccess = "Eres beneficiario";
    protected String leyendaIsNotAccess = "No eres beneficiario";
    protected String leyendaBConsul = "Consultar";
    protected String leyendaCurpQR = "Estas dado de alta. Solo podrás consultar tus datos";
    protected String leyendaIsInServer = "Tu registro fue exitoso. Puedes desinstalar o cerrar la aplicación.";
    protected String leyendaisLocal = "Tu registro se guardará localmente. Captura el código QR impreso o digitalizado en tu documento";
    protected String leyendaisLocalReg = "Sesión local. Solo podrás consultar tus datos";
    protected boolean isConnected;
    protected static Context context;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onSetScreen();
        viewNavintent();

        controlLayBeneficioNo.context = getApplicationContext();
        dbOpenHelper = new DBOpenHelper(this);

        SessionCurp = findViewById(R.id.getCurp);
        //SessionLeyen = findViewById(R.id.leyenda);

        Bundle Session = this.getIntent().getExtras();

        SessionC = Session.getString("CurpSendSession");
        SessionF = Session.getString("CurpSendSessionF");
        SessionLocal = Session.getString("CurpSendLocal");
        SessionServeJson = Session.getString("CurpSendSessionCurp");


        //Revision

        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor getIfNotCache = dbOpenHelper.retriveNumberOfSession(database);
        getIfNotCache.moveToFirst();

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnected();

        if(isConnected){
            try{
                if (SessionC.equals(valuated)) {
                    ConstraintLayout backColorifBenef = (ConstraintLayout) findViewById(R.id.benefic);
                    backColorifBenef.setBackgroundResource(R.drawable.esqu_yellow_notreg);
                    TextView leyendaAccess = (TextView) findViewById(R.id.Bne);
                    leyendaAccess.setText(leyendaIsNotAccess);
                }else if(SessionC.equals(valueIsSucces)){
                    if (getIfNotCache.getCount() <= 0) {
                        ConstraintLayout backColorifBenef = (ConstraintLayout) findViewById(R.id.benefic);
                        backColorifBenef.setBackgroundResource(R.drawable.esqu_shadow_button);
                        SessionCurp.setText(SessionServeJson);
                        TextView leyendaAccess = (TextView) findViewById(R.id.Bne);
                        TextView leyendaAccessQR = (TextView) findViewById(R.id.leyenda);
                        leyendaAccess.setText(leyendaIsAccess);
                        leyendaAccessQR.setText(leyendaIsInServer);
                        Button leyendaisBlocked = (Button) findViewById(R.id.cargarFotos);
                        leyendaisBlocked.setEnabled(false);
                    }
                }else{
                    ConstraintLayout backColorifBenef = (ConstraintLayout) findViewById(R.id.benefic);
                    backColorifBenef.setBackgroundResource(R.drawable.esqu_shadow_button);
                    SessionCurp.setText(SessionServeJson);
                    TextView leyendaAccess = (TextView) findViewById(R.id.Bne);
                    TextView leyendaAccessQR = (TextView) findViewById(R.id.leyenda);
                    leyendaAccess.setText(leyendaIsAccess);
                    leyendaAccessQR.setText(leyendaCurpQR);
                    Button leyendaisConsulta = (Button) findViewById(R.id.cargarFotos);
                    leyendaisConsulta.setText(leyendaBConsul);
                    alertedisNull.alertisNull("Estatus: Sesión", "Datos completados", false, "Continuar", true);
                }

            }catch(Exception e){
                Intent intent = new Intent(controlLayBeneficioNo.this, controlLayLogin.class);
                startActivity(intent);
            }
        }else{
            try{
                if (SessionC.equals(SessionF)) {
                    ConstraintLayout backColorifBenef = (ConstraintLayout) findViewById(R.id.benefic);
                    backColorifBenef.setBackgroundResource(R.drawable.esqu_shadow_button);
                    SessionCurp.setText(SessionC);
                    TextView leyendaAccess = (TextView) findViewById(R.id.Bne);
                    TextView leyendaAccessQr = (TextView) findViewById(R.id.leyenda);
                    leyendaAccess.setText(leyendaIsAccess);
                    if (getIfNotCache.getCount() <= 0) {
                        leyendaAccessQr.setText(leyendaisLocal);
                    } else {
                        leyendaAccessQr.setText(leyendaisLocalReg);
                        Button consulta = (Button) findViewById(R.id.cargarFotos);
                        consulta.setText(leyendaBConsul);
                    }
                }
            }catch(Exception e){
                Intent intent = new Intent(controlLayBeneficioNo.this, controlLayLogin.class);
                startActivity(intent);
            }

        }
    }


    public void onSetScreen() {
        setContentView(R.layout.statusbeneficio);
    }

    public void viewNavintent(){
        viewComponents(principal_qr, R.id.cargarFotos).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
                Cursor ifSomething = dbOpenHelper.retriveNumberOfSession(database);
                if(ifSomething.getCount() <= 0){
                    new POSTPINPERSON().execute();
                }
                ifSomething.close();
                Intent intent= new Intent(controlLayBeneficioNo.this, QreadLayHelper.class);
                Bundle Session = new Bundle();
                Session.putString("Session", SessionC);
                intent.putExtras(Session);
                startActivity(intent);

            }
        });
    }

    public class POSTPINPERSON extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogoUp = new ProgressDialog(controlLayBeneficioNo.this);
            dialogoUp.setMessage("Creando usuario...");
            dialogoUp.setIndeterminate(false);
            dialogoUp.setCancelable(false);
            dialogoUp.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                int max = 6000;
                int min = 1;
                int PIN = new Random().nextInt((max - min) + 1) + min;
                regSessionPerson(SessionF, PIN);
                regSessionLocal(SessionF, PIN);
                POST(URL, Identity);

            } catch (Exception e) {
                e.printStackTrace();

            }
            try{
                POSTREGBENEF(URLrg, Identity);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialogoUp.dismiss();
        }
    }

    @Override
    public Button viewComponents(Button Component, int idreference) {
        return findViewById(idreference);
    }


    @Override
    public void POST(String URL, String Identity) throws Exception {
        List<NameValuePair> params = new ArrayList<>();
        int PIN = 0;
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor PINED  = dbOpenHelper.retriveNumberOfSession(database);
        while(PINED.moveToNext()){
            PIN = PINED.getInt(PINED.getColumnIndexOrThrow(Estructure_Session.RANDOM_NUMBER));
        }
        PINED.close();
        try {
            params.add(new BasicNameValuePair("curp_per", SessionF));
            params.add(new BasicNameValuePair("pin_benef", Integer.toString(PIN)));
            JSONObject json = methods.Requested(URL, Identity, params);

            Log.d("Test de respuesta...", json.toString());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void POSTREGBENEF(String URL, String Identity){
        List<NameValuePair> params = new ArrayList<>();
        try {
            params.add(new BasicNameValuePair("curp_per", SessionF));
            JSONObject json = methods.Requested(URL, Identity, params);
            Log.d("Test de respuesta...", json.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void GET(String URL, String Identity) throws Exception {

    }

    @Override
    public void PUT(String URL, String Identity) throws Exception {

    }

    public void regSessionPerson(String keyCurp, int randNumb){
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        dbOpenHelper.inSessionPerson(keyCurp, randNumb, database);
        dbOpenHelper.close();
    }

    public void regSessionLocal(String keyCurpLocal, int randNumbLocal){
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        dbOpenHelper.inSessionPersonLocal(keyCurpLocal, randNumbLocal, database);
        dbOpenHelper.close();
    }

    public void onBackPressed(){

    }
}
