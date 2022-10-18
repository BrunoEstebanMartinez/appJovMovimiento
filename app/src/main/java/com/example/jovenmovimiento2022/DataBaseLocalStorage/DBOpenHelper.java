/*
 *  Autor:    Ing. Bruno Esteban Martínez Millán
 *  Copyright 2022 Gobierno del Estado de México
 *
 *
 */

package com.example.jovenmovimiento2022.DataBaseLocalStorage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class DBOpenHelper extends SQLiteOpenHelper{

    public DBOpenHelper(Context context) {
        super(context, DBVersion_DBName.DATABASENAME, null, DBVersion_DBName.VERSIONDATABASE);
    }


    @Override
    public void onCreate(SQLiteDatabase JOVLOCAL) {
        JOVLOCAL.execSQL(Estructure_person.CREAR_TABLA_PERSON);
        JOVLOCAL.execSQL(Estructure_personImages.CREAR_TABLA_PERSONIM);
        JOVLOCAL.execSQL(Estructure_Session.CREAR_TABLA_SES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase JOVLOCAL, int oldVersion, int newVersion) {
        JOVLOCAL.execSQL(Estructure_person.ELIMINAR_TABLA_PERSON);
        JOVLOCAL.execSQL(Estructure_personImages.ELIMINAR_TABLA_PERSONIM);
        JOVLOCAL.execSQL(Estructure_Session.ELIMINAR_TABLA_PERSONSES);
    }

        public void inFetchData(
                            String curp_per,
                            String comprobante_es,
                            String curp_phh,
                            String acta_nac,
                            String inden_ine_rev,
                            String inden_ine_ad,
                            String FUR_image,
                            SQLiteDatabase database){
            ContentValues contentValues = new ContentValues();
            contentValues.put(Estructure_personImages.CURP_PER, curp_per);
            contentValues.put(Estructure_personImages.COMPROBANTE_ES, comprobante_es);
            contentValues.put(Estructure_personImages.CURP_PH, curp_phh);
            contentValues.put(Estructure_personImages.ACTA_NAC, acta_nac);
            contentValues.put(Estructure_personImages.IDENT_INE_REVER, inden_ine_rev);
            contentValues.put(Estructure_personImages.IDENT_INE_ADVER, inden_ine_ad);
            contentValues.put(Estructure_personImages.BENEF_FUR, FUR_image);
            database.insert(Estructure_personImages.TABLA_PERSONIM, null, contentValues);
    }


    public void inFetchDataLocal(String curp_per,
                                 String apell_p,
                                 String apell_m,
                                 String nombres,
                                 String genero,
                                 String fecha_nac,
                                 String nac,
                                 String benef,
                                 String exte,
                                 String nonac,
                                 SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Estructure_person.CURP_PER, curp_per);
        contentValues.put(Estructure_person.PATERNO_A, apell_p);
        contentValues.put(Estructure_person.MATERNO_A, apell_m);
        contentValues.put(Estructure_person.NOMBRES_PER, nombres);
        contentValues.put(Estructure_person.GENERO_PER, genero);
        contentValues.put(Estructure_person.FECHANA_PER, fecha_nac);
        contentValues.put(Estructure_person.STAT_NAC, nac);
        contentValues.put(Estructure_person.STAT_BENEF, benef);
        contentValues.put(Estructure_person.STAT_EXIST, exte);
        contentValues.put(Estructure_person.STAT_NONAC, nonac);
        database.insert(Estructure_person.TABLA_PERSON, null, contentValues);

    }

    public Cursor retriveAllinfoPerson(SQLiteDatabase database){
        String[] Columns = {Estructure_person.CURP_PER, Estructure_person.PATERNO_A, Estructure_person.MATERNO_A, Estructure_person.NOMBRES_PER, Estructure_person.GENERO_PER,
                Estructure_person.FECHANA_PER, Estructure_person.STAT_NAC, Estructure_person.STAT_NONAC};
                return database.query(Estructure_person.TABLA_PERSON, Columns, null, null, null, null, null);
    }
    public void inSessionPerson(String myCurp, int rnd, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Estructure_Session.CURP_PH, myCurp);
        contentValues.put(Estructure_Session.RANDOM_NUMBER, rnd);
        database.insert(Estructure_Session.TABLA_PERSONSES, null, contentValues);
    }

    public Cursor retriveDataCurpphh(SQLiteDatabase database){
        String[] Vistas = {Estructure_personImages.CURP_PH};
        return database.query(Estructure_personImages.TABLA_PERSONIM, Vistas, null, null, null, null, "DESC");
    }

    public Cursor retriveSessions(SQLiteDatabase database){
        String[] Columns = {Estructure_Session.CURP_PH};
        return database.query(Estructure_Session.CREAR_TABLA_SES, Columns, null, null, null, null, null);
     }

    public Cursor retriveNumberOfSession(SQLiteDatabase database){
        String[] Columns = {Estructure_Session.RANDOM_NUMBER};
        return database.query(Estructure_Session.TABLA_PERSONSES, Columns, null, null, null, null, null);
    }

}
