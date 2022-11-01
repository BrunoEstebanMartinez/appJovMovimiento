package com.example.jovenmovimiento2022.LayoutBind.Login;


import com.example.jovenmovimiento2022.DataBaseLocalStorage.Estructure_Session;
import com.example.jovenmovimiento2022.DataBaseLocalStorage.Estructure_person;
import com.example.jovenmovimiento2022.DataBaseLocalStorage.Estructure_personLocal;
import com.example.jovenmovimiento2022.customscann.CustomScannerActivity;
import com.example.jovenmovimiento2022.interfaces.methodServer;
import com.example.jovenmovimiento2022.Controllers.methodsOn;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jovenmovimiento2022.Dialogs.Alerts.forConsultaCurp;
import com.example.jovenmovimiento2022.Models.DatosConsultaCurp;
import com.example.jovenmovimiento2022.DataBaseLocalStorage.DBOpenHelper;
import com.example.jovenmovimiento2022.R;

import com.example.jovenmovimiento2022.interfaces.navigate;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;

public class QreadLayHelper extends Activity implements methodServer, navigate {


    private static final String URL = "http://187.216.191.87:8060/api/altaPersonBen";
    private static final String Identity = "POST";


    DBOpenHelper dbOpenHelper;
    SQLiteDatabase database;
    private ProgressDialog dialogoUp;
    TextView nombre, Curp, Genero, fecha, Paises, Noest, ApellP, ApellM;
    Button consulImage;
    forConsultaCurp alertedisNull = new forConsultaCurp(this);
    String stateResponse, curpPerson, apellidoP, apellidoM, nombrePerson, generoPerson, fechaBirPerson, paisPerson, noEstPerson;
    DatosConsultaCurp person = new DatosConsultaCurp();
    methodsOn methods = new methodsOn();
    //Session User Simulated
    protected String SessionC;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbOpenHelper = new DBOpenHelper(this);
        onSetScreen();

        nombre = findViewById(R.id.Nombre);
        ApellP = findViewById(R.id.apellP);
        ApellM = findViewById(R.id.apellM);
        Curp = findViewById(R.id.CURP);
        Genero = findViewById(R.id.Genero);
        fecha = findViewById(R.id.FechaNac);
        Paises = findViewById(R.id.paisPerson);
        Noest = findViewById(R.id.NoEst);
        //
        consulImage = findViewById(R.id.consulImages);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if (result.getContents() == null){
                alertedisNull.alertisNull("Algo salió mal", "El resultado no fue el esperado", false, "Continuar", true);
            }
            else{
                stateResponse = result.getContents();
                String [] args = stateResponse.split("\\|");
                 curpPerson = args[0];
                 apellidoP = args[2];
                 apellidoM = args[3];
                 nombrePerson   = args[4];
                 generoPerson  = args[5];
                 fechaBirPerson     = args[6];
                 paisPerson    = args[7];
                 noEstPerson = args[8];

                nombre.setText(nombrePerson);
                ApellP.setText(apellidoP);
                ApellM.setText(apellidoM);
                Curp.setText(curpPerson);
                Paises.setText(paisPerson);
                Genero.setText(generoPerson);
                fecha.setText(fechaBirPerson);
                Noest.setText(noEstPerson);

                person.setNombres(nombrePerson);
                person.setApellido1(apellidoP);
                person.setApellido2(apellidoM);
                person.setCveCurp(curpPerson);
                person.setSexo(generoPerson);
                person.setFechNac(fechaBirPerson);
                person.setNacionalidad(paisPerson);
                person.setCveEntidadNac(noEstPerson);

                new newPerson().execute();

            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void POST(String URL, String Identity) throws Exception {

        List<NameValuePair> params = new ArrayList<>();

        // TextViewpair nombre, Curp, Genero, fecha, Paises, Noest, ApellP, ApellM;
        // params String curpPerson,
        //               apellidoP,
        //               apellidoM,
        //               nombrePerson,
        //               generoPerson,
        //               fechaBirPerson,
        //               paisPerson,
        //               noEstPerson;

        try{
            params.add(new BasicNameValuePair("curpPerson",  person.getCveCurp()));
            params.add(new BasicNameValuePair("apellidoP",  person.getApellido1()));
            params.add(new BasicNameValuePair("apellidoM",  person.getApellido2()));
            params.add(new BasicNameValuePair("nombrePerson", person.getNombres()));
            params.add(new BasicNameValuePair("generoPerson",  person.getSexo()));
            params.add(new BasicNameValuePair("fechaBirPerson", person.getFechNac()));
            params.add(new BasicNameValuePair("paisPerson", person.getNacionalidad()));
            params.add(new BasicNameValuePair("noEstPerson",  person.getCveEntidadNac()));
            // 8 parametros
            JSONObject json  =  methods.Requested(URL, Identity, params);
            Log.d("Creando respuesta ...", json.toString());

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


    public class newPerson extends  AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogoUp = new ProgressDialog(QreadLayHelper.this);
            dialogoUp.setMessage("Procesando datos");
            dialogoUp.setIndeterminate(false);
            dialogoUp.setCancelable(false);
            dialogoUp.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                //Only for validations in context aplicaction (Duplicate code -> in class)
                SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
                Cursor navigateSessionApp = dbOpenHelper.retriveSessionsLocal(database);
                navigateSessionApp.moveToFirst();
                    dataPersonLocal(person.getCveCurp(),
                            person.getApellido1(),
                            person.getApellido2(),
                            person.getNombres(),
                            person.getSexo(),
                            person.getFechNac(),
                            person.getNacionalidad(),
                            "1",
                            "1",
                            person.getCveEntidadNac()
                    );
                    POST(URL, Identity);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialogoUp.dismiss();
            alertedisNull.alertisNull("Estatus: Registro", "Navegando ...", false, "Continuar", true);
            Intent intent = new Intent(QreadLayHelper.this, benefiInfo.class);
            //Create a Key for local session
            Bundle Session = new Bundle();
            Session.putString("CurpSendSession", curpPerson);
            intent.putExtras(Session);
            startActivity(intent);
            consulImage.setEnabled(false);
        }
    }

    public void onSetScreen() {
        setContentView(R.layout.statusbeneficioon);
        database = dbOpenHelper.getReadableDatabase();
        Cursor ifSomething = dbOpenHelper.retriveAllinfoPerson(database);
        Cursor ifSessionServer = dbOpenHelper.retriveNumberOfSession(database);
        ifSessionServer.moveToFirst();
        ifSomething.moveToFirst();
        if(ifSomething.getCount() <= 0){
            //Initialize ZXING Library
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setCaptureActivity(CustomScannerActivity.class);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            integrator.setCameraId(0);
            integrator.setBeepEnabled(false);
            integrator.initiateScan();
            findViewById(R.id.CerrarSession).setEnabled(false);
        }else{
                if(ifSessionServer.getCount() >= 1){
                    findViewById(R.id.CerrarSession).setEnabled(true);
                    TextView nombre, Curp, Genero, fecha, Paises, Noest, ApellP, ApellM;
                    findViewById(R.id.CerrarSession).setEnabled(true);
                    SQLiteDatabase person = dbOpenHelper.getReadableDatabase();
                    Cursor Info = dbOpenHelper.retriveAllinfoPerson(person);
                    nombre = findViewById(R.id.Nombre);
                    ApellP = findViewById(R.id.apellP);
                    ApellM = findViewById(R.id.apellM);
                    Curp = findViewById(R.id.CURP);
                    Genero = findViewById(R.id.Genero);
                    fecha = findViewById(R.id.FechaNac);
                    Paises = findViewById(R.id.paisPerson);
                    Noest = findViewById(R.id.NoEst);

                    while(Info.moveToNext()){
                        nombre.setText(ifSomething.getString(ifSomething.getColumnIndexOrThrow(Estructure_person.NOMBRES_PER)));
                        ApellP.setText(ifSomething.getString(ifSomething.getColumnIndexOrThrow(Estructure_person.PATERNO_A)));
                        ApellM.setText(ifSomething.getString(ifSomething.getColumnIndexOrThrow(Estructure_person.MATERNO_A)));
                        Curp.setText(ifSomething.getString(ifSomething.getColumnIndexOrThrow(Estructure_person.CURP_PER)));
                        Paises.setText(ifSomething.getString(ifSomething.getColumnIndexOrThrow(Estructure_person.STAT_NAC)));
                        Genero.setText(ifSomething.getString(ifSomething.getColumnIndexOrThrow(Estructure_person.GENERO_PER)));
                        fecha.setText(ifSomething.getString(ifSomething.getColumnIndexOrThrow(Estructure_person.FECHANA_PER)));
                        Noest.setText(ifSomething.getString(ifSomething.getColumnIndexOrThrow(Estructure_person.STAT_NONAC)));
                    }
                    ifSomething.close();

                }
                }


            findViewById(R.id.CerrarSession).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(QreadLayHelper.this, controlLayLogin.class);
                    Bundle keyOFFSession = new Bundle();
                    keyOFFSession.putString("keyOFF", SessionC);
                    intent.putExtras(keyOFFSession);
                    startActivity(intent);
                }
            });

            findViewById(R.id.consulImages).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(QreadLayHelper.this, benefiInfo.class);
                    //Create a Key for local session
                    Bundle Session = new Bundle();
                    Session.putString("CurpSendSession", curpPerson);
                    intent.putExtras(Session);
                    startActivity(intent);
                }
            });
        }


    public void dataPersonLocal(String curp_per,
                                String apell_p,
                                String apell_m,
                                String nombres,
                                String genero,
                                String fecha_nac,
                                String nac,
                                String benef,
                                String exte,
                                String nonac){
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        dbOpenHelper.inFetchDataLocal(curp_per, apell_p, apell_m, nombres, genero, fecha_nac, nac, benef, exte, nonac, database);
        dbOpenHelper.close();
    }

    public Button viewComponents(Button Component, int idreference) {
        return findViewById(idreference);
    }

   public void onBackPressed(){

   }
}
