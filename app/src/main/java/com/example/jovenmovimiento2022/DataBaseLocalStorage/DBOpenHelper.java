/*
 *  File: DBOpenHelper.java
 *  Function: Connection methods to local Database in SQLite
 *  Autores: Ing. Bruno Esteban Martínez Millán e Ing. Silverio Baltazar Barrientos Zarate.
 *  Copyright 2022 Gobierno del Estado de México
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
        JOVLOCAL.execSQL(Estructure_localSession.CREAR_TABLA_SES_LOCAL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase JOVLOCAL, int oldVersion, int newVersion) {
        JOVLOCAL.execSQL(Estructure_person.ELIMINAR_TABLA_PERSON);
        JOVLOCAL.execSQL(Estructure_personImages.ELIMINAR_TABLA_PERSONIM);
        JOVLOCAL.execSQL(Estructure_Session.ELIMINAR_TABLA_PERSONSES);
        JOVLOCAL.execSQL(Estructure_localSession.ELIMINAR_TABLA_PERSONSES);
    }

        // Tables info images person

        public void inFetchDataImages(
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
            contentValues.put(Estructure_personImages.IDENT_INE_ADVER, inden_ine_ad);
            contentValues.put(Estructure_personImages.IDENT_INE_REVER, inden_ine_rev);
            contentValues.put(Estructure_personImages.BENEF_FUR, FUR_image);
            database.insert(Estructure_personImages.TABLA_PERSONIM, null, contentValues);
    }

    public void inFetchDataImagesLocal(String curp_per_i,
                                       String comprobante_i,
                                       String curp_ph_i,
                                       String acta_i,
                                       String inden_ine_rever_i,
                                       String inden_ine_adver_i,
                                       String benef_fur_i,
                                       SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Estructure_personImages.CURP_PER, curp_per_i);
        contentValues.put(Estructure_personImages.COMPROBANTE_ES, comprobante_i);
        contentValues.put(Estructure_personImages.CURP_PH, curp_ph_i);
        contentValues.put(Estructure_personImages.ACTA_NAC, acta_i);
        contentValues.put(Estructure_personImages.IDENT_INE_ADVER, inden_ine_adver_i);
        contentValues.put(Estructure_personImages.IDENT_INE_REVER, inden_ine_rever_i);
        contentValues.put(Estructure_personImages.BENEF_FUR, benef_fur_i);
        database.insert(Estructure_personImagesLocal.TABLA_PERSONIM_LOCAL, null, contentValues);
    }

    //


    //Tables info person

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

    public void inFetchDataLocalSession(String curp_per_s,
                                 String apell_p_s,
                                 String apell_m_s,
                                 String nombres_s,
                                 String genero_s,
                                 String fecha_nac_s,
                                 String nac_s,
                                 String benef_s,
                                 String exte_s,
                                 String nonac_s,
                                 SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Estructure_personLocal.CURP_PER_LOCAL, curp_per_s);
        contentValues.put(Estructure_personLocal.PATERNO_A_LOCAL, apell_p_s);
        contentValues.put(Estructure_personLocal.MATERNO_A_LOCAL, apell_m_s);
        contentValues.put(Estructure_personLocal.NOMBRES_PER_LOCAL, nombres_s);
        contentValues.put(Estructure_personLocal.GENERO_PER_LOCAL, genero_s);
        contentValues.put(Estructure_personLocal.FECHANA_PER_LOCAL, fecha_nac_s);
        contentValues.put(Estructure_personLocal.STAT_NAC_LOCAL, nac_s);
        contentValues.put(Estructure_personLocal.STAT_BENEF_LOCAL, benef_s);
        contentValues.put(Estructure_personLocal.STAT_EXIST_LOCAL, exte_s);
        contentValues.put(Estructure_personLocal.STAT_NONAC_LOCAL, nonac_s);
        database.insert(Estructure_personLocal.TABLA_PERSON_LOCAL, null, contentValues);
    }

    //


    // Tables sessions
    public void inSessionPerson(String myCurp, int rnd, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Estructure_Session.CURP_PH, myCurp);
        contentValues.put(Estructure_Session.RANDOM_NUMBER, rnd);
        database.insert(Estructure_Session.TABLA_PERSONSES, null, contentValues);
    }

    public void inSessionPersonLocal(String myCurp_local, int rnd_local, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Estructure_localSession.CURP_PH_LOCAL, myCurp_local);
        contentValues.put(Estructure_localSession.INT_RAND_LOCAL, rnd_local);
        database.insert(Estructure_localSession.TABLA_LOCALSES, null, contentValues);
    }
   //

    public Cursor retriveAllinfoPerson(SQLiteDatabase database){
        String[] Columns = {Estructure_person.CURP_PER,
                Estructure_person.PATERNO_A,
                Estructure_person.MATERNO_A,
                Estructure_person.NOMBRES_PER,
                Estructure_person.GENERO_PER,
                Estructure_person.FECHANA_PER,
                Estructure_person.STAT_NAC,
                Estructure_person.STAT_NONAC};
                return database.query(Estructure_person.TABLA_PERSON, Columns, null, null, null, null, null);
    }

    public Cursor retriveAllinfoPersonLocal(SQLiteDatabase database){
        String[] Columns = {Estructure_personLocal.CURP_PER_LOCAL,
                Estructure_personLocal.PATERNO_A_LOCAL,
                Estructure_personLocal.MATERNO_A_LOCAL,
                Estructure_personLocal.NOMBRES_PER_LOCAL,
                Estructure_personLocal.GENERO_PER_LOCAL,
                Estructure_personLocal.FECHANA_PER_LOCAL,
                Estructure_personLocal.STAT_NAC_LOCAL,
                Estructure_personLocal.STAT_NONAC_LOCAL};
            return database.query(Estructure_personLocal.TABLA_PERSON_LOCAL, Columns, null, null, null, null, null);
    }

    public Cursor retriveAllinfoPersonImages(SQLiteDatabase database){
        String[] Columns = {Estructure_personImages.CURP_PER,
                Estructure_personImages.COMPROBANTE_ES,
                Estructure_personImages.CURP_PH,
                Estructure_personImages.ACTA_NAC,
                Estructure_personImages.IDENT_INE_REVER,
                Estructure_personImages.IDENT_INE_ADVER,
                Estructure_personImages.BENEF_FUR};
        return database.query(Estructure_personImages.TABLA_PERSONIM, Columns, null, null, null, null, null);
    }

    public Cursor retriveAllinfoPersonImagesLocal(SQLiteDatabase database){
        String[] Columns = {
                Estructure_personImagesLocal.COMPROBANTE_ES_LOCAL,
                Estructure_personImagesLocal.CURP_PH_LOCAL,
                Estructure_personImagesLocal.ACTA_NAC_LOCAL,
                Estructure_personImagesLocal.IDENT_INE_REVER_LOCAL,
                Estructure_personImagesLocal.IDENT_INE_ADVER_LOCAL,
                Estructure_personImagesLocal.BENEF_FUR_LOCAL
        };
        return database.query(Estructure_personImagesLocal.TABLA_PERSONIM_LOCAL, Columns, null, null, null, null, null);
    }


    public Cursor retriveSessionsLocal(SQLiteDatabase database){
        String[] Columns = {Estructure_localSession.CURP_PH_LOCAL};
        return database.query(Estructure_localSession.TABLA_LOCALSES, Columns, null, null, null, null, null);
    }

    //Revision

    public Cursor retriveSessions(SQLiteDatabase database){
        String[] Columns = {Estructure_Session.CURP_PH};
        return database.query(Estructure_Session.TABLA_PERSONSES, Columns, null, null, null, null, null);
     }

     //Revision

    public Cursor retriveNumberOfSession(SQLiteDatabase database){
        String[] Columns = {Estructure_Session.RANDOM_NUMBER, Estructure_Session.CURP_PH};
        return database.query(Estructure_Session.TABLA_PERSONSES, Columns, null, null, null, null, null);
    }

    public Cursor retriveNumberOfSessionLocal(SQLiteDatabase database){
        String[] Columns = {Estructure_localSession.INT_RAND_LOCAL, Estructure_localSession.CURP_PH_LOCAL};
        return database.query(Estructure_localSession.TABLA_LOCALSES, Columns, null, null,null, null, null);
    }

}
