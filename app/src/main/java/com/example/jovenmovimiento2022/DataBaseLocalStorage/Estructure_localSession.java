package com.example.jovenmovimiento2022.DataBaseLocalStorage;


//Sin registrar
public class Estructure_localSession {

    public static final String TABLA_LOCALSES = "LOCAL_SESSION";
    public static final String CURP_PH_LOCAL = "CURP_PH_LOCAL";
    public static final String INT_RAND_LOCAL = "INT_RAND_LOCAL";

    public static final String CREAR_TABLA_SES_LOCAL = "create table "+TABLA_LOCALSES+"(ID_SESSIONS_LOCAL INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CURP_PH_LOCAL +" VARCHAR, "+ INT_RAND_LOCAL +" INTEGER)";


    public static final String ELIMINAR_TABLA_PERSONSES = "drop table if exists " + TABLA_LOCALSES;
}
