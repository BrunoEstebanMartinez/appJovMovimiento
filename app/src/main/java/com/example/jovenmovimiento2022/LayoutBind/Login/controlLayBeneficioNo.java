package com.example.jovenmovimiento2022.LayoutBind.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jovenmovimiento2022.Dialogs.Alerts.forConsultaCurp;
import com.example.jovenmovimiento2022.R;
import com.example.jovenmovimiento2022.interfaces.navigate;


public class controlLayBeneficioNo extends Activity implements navigate{

    public Button principal_qr;
    protected String stateResponse;
    //retrive the session user
    protected String SessionC;
    protected TextView SessionCurp;
    forConsultaCurp alertedisNull = new forConsultaCurp(this);
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onSetScreen();
        viewNavintent();
        SessionCurp = findViewById(R.id.getCurp);
        Bundle Session = this.getIntent().getExtras();
        SessionC = Session.getString("CurpSendSession");
        SessionCurp.setText(SessionC);


    }


    public void onSetScreen() {
        setContentView(R.layout.statusbeneficio);
    }


    public void viewNavintent(){
        viewComponents(principal_qr, R.id.cargarFotos).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(controlLayBeneficioNo.this, QreadLayHelper.class);
                Bundle Session = new Bundle();
                Session.putString("Session", SessionC);
                intent.putExtras(Session);
                startActivity(intent);
            }
        });
    }

    @Override
    public Button viewComponents(Button Component, int idreference) {
        return findViewById(idreference);
    }

    public void onBackPressed(){

    }

}
