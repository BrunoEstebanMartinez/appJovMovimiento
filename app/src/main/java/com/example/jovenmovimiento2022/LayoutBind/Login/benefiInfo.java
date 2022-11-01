package com.example.jovenmovimiento2022.LayoutBind.Login;

import android.Manifest;
import android.app.Activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jovenmovimiento2022.DataBaseLocalStorage.DBOpenHelper;
import com.example.jovenmovimiento2022.DataBaseLocalStorage.Estructure_personImages;
import com.example.jovenmovimiento2022.Controllers.methodsOn;
import com.example.jovenmovimiento2022.DataBaseLocalStorage.Estructure_personImagesLocal;
import com.example.jovenmovimiento2022.Dialogs.Alerts.forConsultaCurp;
import com.example.jovenmovimiento2022.interfaces.methodServer;
import com.example.jovenmovimiento2022.R;

import com.example.jovenmovimiento2022.interfaces.navigate;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;


public class benefiInfo extends Activity implements navigate, methodServer{

    //URL process API
    private static final String URL = "http://187.216.191.87:8060/api/altaPersonImage";
    private static final String Identity = "POST";
    //Context App and CODES permission camera
    private static Context context;
    private static final int CAMERA_PERMISSION = 100;
    private static final int CODE_REQUEST_CAMERA_STATUS_COMPROBANTE = 200;
    private static final int CODE_REQUEST_CAMERA_STATUS_CURP = 300;
    private static final int CODE_REQUEST_CAMERA_STATUS_ACTA = 400;
    private static final int CODE_REQUEST_CAMERA_STATUS_IDEAD = 500;
    private static final int CODE_REQUEST_CAMERA_STATUS_IDEREV = 600;
    private static final int CODE_REQUEST_CAMERA_STATUS_FUR = 700;
    ImageView Comprobante, CURPFOTO, ACTAFOTO, IDENADFOTO, IDENREVFOTO, FURFOTO;
    String codexPhotoComp, codexPhotoCurp, codexPhotoActa, codexPhotoIdeAd, codexPhotoIdeRev, codexPhotoFUR;
    Bitmap CompBitmap, CURPBitmap, ACTABitmap, IdenAdBitmap, IdenRevBitmap, FURBitmap;
    //Dialogs
    private ProgressDialog dialogoUp;
    forConsultaCurp alertedisNull = new forConsultaCurp(this);
    //Databases variables
    Estructure_personImages TablePersons;
    DBOpenHelper dataBaseHandler;
    methodsOn methods = new methodsOn();
    // Session User
    protected String SessionC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_fragment);
        dataBaseHandler = new DBOpenHelper(this);

        onSetScreen();
        benefiInfo.context = getApplicationContext();
        Comprobante = findViewById(R.id.Comprobantede);
        CURPFOTO = findViewById(R.id.CURPView);
        ACTAFOTO = findViewById(R.id.ActaNacView);
        IDENADFOTO = findViewById(R.id.IdenADView);
        IDENREVFOTO = findViewById(R.id.IdenREView);
        FURFOTO = findViewById(R.id.FURView);



        cameraLog(R.id.CompEst, CODE_REQUEST_CAMERA_STATUS_COMPROBANTE);
        cameraLog(R.id.CURPFOTO, CODE_REQUEST_CAMERA_STATUS_CURP);
        cameraLog(R.id.ActaFoto, CODE_REQUEST_CAMERA_STATUS_ACTA);
        cameraLog(R.id.IdenADFoto, CODE_REQUEST_CAMERA_STATUS_IDEAD);
        cameraLog(R.id.IdenREFoto, CODE_REQUEST_CAMERA_STATUS_IDEREV);
        cameraLog(R.id.FURFOTO, CODE_REQUEST_CAMERA_STATUS_FUR);


        Bundle Session = this.getIntent().getExtras();
        SessionC = Session.getString("CurpSendSession");


        findViewById(R.id.cargarFotos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Comprobante.getDrawable() == null || CURPFOTO.getDrawable() == null || ACTAFOTO.getDrawable() == null || IDENADFOTO.getDrawable() == null|| IDENREVFOTO.getDrawable() == null|| FURFOTO.getDrawable() == null){
                    alertedisNull.alertisNull("Estatus: Incompleto", "Tus fotos deben ser integras.", false, "Continuar", true);
                }else{
                    new newPhotos().execute();
                }

            }
        });

    }


    @Override
    public void onSetScreen() {
        SQLiteDatabase database = dataBaseHandler.getReadableDatabase();
        Cursor infoImages = dataBaseHandler.retriveAllinfoPersonImages(database);
        infoImages.moveToFirst();
        ImageView Comprobante, CURPFOTO, ACTAFOTO, IDENADFOTO, IDENREVFOTO, FURFOTO;

        if(infoImages.getCount() >= 1){

            Comprobante = findViewById(R.id.Comprobantede);
            CURPFOTO = findViewById(R.id.CURPView);
            ACTAFOTO = findViewById(R.id.ActaNacView);
            IDENADFOTO = findViewById(R.id.IdenADView);
            IDENREVFOTO = findViewById(R.id.IdenREView);
            FURFOTO = findViewById(R.id.FURView);

            while(infoImages.moveToNext()){
                Comprobante.setImageBitmap(getDecodedString(infoImages.getString(infoImages.getColumnIndexOrThrow(Estructure_personImages.COMPROBANTE_ES))));
                CURPFOTO.setImageBitmap(getDecodedString(infoImages.getString(infoImages.getColumnIndexOrThrow(Estructure_personImages.CURP_PH))));
                ACTAFOTO.setImageBitmap(getDecodedString(infoImages.getString(infoImages.getColumnIndexOrThrow(Estructure_personImages.ACTA_NAC))));
                IDENADFOTO.setImageBitmap(getDecodedString(infoImages.getString(infoImages.getColumnIndexOrThrow(Estructure_personImages.IDENT_INE_ADVER))));
                IDENREVFOTO.setImageBitmap(getDecodedString(infoImages.getString(infoImages.getColumnIndexOrThrow(Estructure_personImages.IDENT_INE_REVER))));
                FURFOTO.setImageBitmap(getDecodedString(infoImages.getString(infoImages.getColumnIndexOrThrow(Estructure_personImages.BENEF_FUR))));
            }
            infoImages.close();

        }
    }

    public void cameraLog(int idRefer, int CODE) {
        findViewById(idRefer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CODE);
                }
            }
        });
    }


    /*
    public void onData(){
            SQLiteDatabase database =dataBaseHandler.getReadableDatabase();
            Cursor cursor = dataBaseHandler.retriveDataCurpphh(database);
            cursor.moveToFirst();
            if(cursor.getColumnCount() == 0){
                database = dataBaseHandler.getWritableDatabase();
                dataBaseHandler.inFetchData(getEncodedString(conViewImage),
                        getEncodedString(conViewImage),
                        getEncodedString(conViewImage),
                        getEncodedString(conViewImage),
                        getEncodedString(conViewImage),
                        getEncodedString(conViewImage),
                        getEncodedString(conViewImage),
                        database);
            }
    }
*/

    // Images = CompBitmap, CURPBitmap, ACTABitmap, IdenAdBitmap, IdenRevBitmap, FURBitmap;
    // Show in imageView
    // ImageView = Comprobante, CURPFOTO, ACTAFOTO, IDENADFOTO, IDENREVFOTO, FURFOTO;

    //Serializable content
    //codexPhoto = getEncodedString(CompBitmap);
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CODE_REQUEST_CAMERA_STATUS_COMPROBANTE:
                    CompBitmap = (Bitmap) data.getExtras().get("data");
                    codexPhotoComp = getEncodedString(CompBitmap);
                    Comprobante.setImageBitmap(CompBitmap);
                    break;
                case CODE_REQUEST_CAMERA_STATUS_CURP:
                    CURPBitmap = (Bitmap) data.getExtras().get("data");
                    codexPhotoCurp = getEncodedString(CURPBitmap);
                    CURPFOTO.setImageBitmap(CURPBitmap);
                    break;
                case CODE_REQUEST_CAMERA_STATUS_ACTA:
                    ACTABitmap = (Bitmap) data.getExtras().get("data");
                    codexPhotoActa = getEncodedString(ACTABitmap);
                    ACTAFOTO.setImageBitmap(ACTABitmap);
                    break;
                case CODE_REQUEST_CAMERA_STATUS_IDEAD:
                    IdenAdBitmap = (Bitmap) data.getExtras().get("data");
                    codexPhotoIdeAd = getEncodedString(IdenAdBitmap);
                    IDENADFOTO.setImageBitmap(IdenAdBitmap);
                    break;
                case CODE_REQUEST_CAMERA_STATUS_IDEREV:
                    IdenRevBitmap = (Bitmap) data.getExtras().get("data");
                    codexPhotoIdeRev = getEncodedString(IdenRevBitmap);
                    IDENREVFOTO.setImageBitmap(IdenRevBitmap);
                    break;
                case CODE_REQUEST_CAMERA_STATUS_FUR:
                    FURBitmap = (Bitmap) data.getExtras().get("data");
                    codexPhotoFUR = getEncodedString(FURBitmap);
                    FURFOTO.setImageBitmap(FURBitmap);
                    break;
            }

        }

    }



    private String getEncodedString(Bitmap bitmap){

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG,100, os);

       /* or use below if you want 32 bit images

        bitmap.compress(Bitmap.CompressFormat.PNG, (0–100 compression), os);*/
        byte[] imageArr = os.toByteArray();

        return Base64.encodeToString(imageArr, Base64.DEFAULT);

    }

    private Bitmap getDecodedString(String bitmap){
        byte[] arr = Base64.decode(bitmap, Base64.URL_SAFE);

        Bitmap img = BitmapFactory.decodeByteArray(arr, 0, arr.length);

        return img;
    }


    @Override
    public Button viewComponents(Button Component, int idreference) {
           return null;
        }



    @Override
    public void POST(String URL, String Identity) throws Exception {
        List<NameValuePair> params = new ArrayList<>();
        try{
            params.add(new BasicNameValuePair("curp_per",  SessionC));
            params.add(new BasicNameValuePair("comprobante_es", codexPhotoComp));
            params.add(new BasicNameValuePair("curp_ph",  codexPhotoCurp));
            params.add(new BasicNameValuePair("acta_nac", codexPhotoActa));
            params.add(new BasicNameValuePair("indent_ine_adver",  codexPhotoIdeAd));
            params.add(new BasicNameValuePair("indent_ine_rever", codexPhotoIdeRev));
            params.add(new BasicNameValuePair("benef_fur", codexPhotoFUR));
            // 7 paramtros

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

    public class newPhotos extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogoUp = new ProgressDialog(benefiInfo.this);
            dialogoUp.setMessage("Procesando...");
            dialogoUp.setIndeterminate(false);
            dialogoUp.setCancelable(false);
            dialogoUp.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                SQLiteDatabase database = dataBaseHandler.getReadableDatabase();
                Cursor currentSession = dataBaseHandler.retriveNumberOfSession(database);
                currentSession.moveToFirst();
                if(currentSession.getCount() <= 0){
                   new newPhotos().cancel(true);
                    dialogoUp.dismiss();
                    Intent intent = new Intent(benefiInfo.this, benefiInfo.class);
                    startActivity(intent);
                }else{
                    inFetchDataImages(SessionC,
                            codexPhotoComp,
                            codexPhotoCurp,
                            codexPhotoActa,
                            codexPhotoIdeAd,
                            codexPhotoIdeRev,
                            codexPhotoFUR);
                    POST(URL, Identity);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialogoUp.dismiss();
            Intent intent = new Intent(benefiInfo.this, controlLayLogin.class);
            startActivity(intent);

        }
    }


    public void inFetchDataImages(String curp_per,
                                       String comprobante_es,
                                       String curp_phh,
                                       String acta_nac,
                                       String inden_ine_ad,
                                        String inden_ine_rev,
                                       String FUR_image){
        SQLiteDatabase database = dataBaseHandler.getWritableDatabase();
        dataBaseHandler.inFetchDataImages(curp_per, comprobante_es, curp_phh, acta_nac, inden_ine_ad, inden_ine_rev, FUR_image, database);
        dataBaseHandler.close();

    }

        public void onBackPressed(){

        }

}
