package com.example.jovenmovimiento2022.LayoutBind.Login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.jovenmovimiento2022.Dialogs.Alerts.forConsultaCurp;
import com.example.jovenmovimiento2022.customscann.CustomScannerActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class testingUserQr extends Activity {

    forConsultaCurp alertedisNull = new forConsultaCurp(this);
    AlertDialog.Builder testingQRCurp;
    AlertDialog Indications;

    private static String responseTestQRCODE;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        testingQRCurp = new AlertDialog.Builder(this);

        IntentIntegrator integrator = new IntentIntegrator(testingUserQr.this);
        integrator.setCaptureActivity(CustomScannerActivity.class);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null){
            if (result.getContents() == null){
                alertedisNull.alertisNull("Algo salió mal", "El resultado no fue el esperado. Solo código de barras", false, "Continuar", true);
                Intent forWaitTwSeconds = new Intent(testingUserQr.this, controlLayLogin.class);
                startActivity(forWaitTwSeconds);
            }
            else{

                responseTestQRCODE = result.getContents();
                testingQRCurp.setTitle("Legible");
                testingQRCurp.setMessage("El resultado fue: "+responseTestQRCODE);
                testingQRCurp.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent forWaitTwSeconds = new Intent(testingUserQr.this, controlLayLogin.class);
                        startActivity(forWaitTwSeconds);
                    }
                });
                Indications = testingQRCurp.create();
                Indications.show();

            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }


    }
}



