package com.example.jovenmovimiento2022.Dialogs.Alerts;

// Android dependencies

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

//Interfaces
import com.example.jovenmovimiento2022.interfaces.alertDialogConcept;

public class forConsultaCurp implements alertDialogConcept{

    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    private Object forConsultaCurp;

    //Null Constructor
    public forConsultaCurp(Object forConsultaCurp){
        this.forConsultaCurp = forConsultaCurp;
    }

    public forConsultaCurp() {

    }

    @Override
    public void alertisNull(String title, String Message, Boolean onCancel, String pass, Boolean okPass) {

        builder = new AlertDialog.Builder((Context) forConsultaCurp);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.setCancelable(onCancel);
        builder.setPositiveButton(pass, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                builder.setCancelable(okPass);
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }
}
