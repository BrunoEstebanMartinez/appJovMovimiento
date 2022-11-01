package com.example.jovenmovimiento2022.DataBaseLocalStorage;


// Sin registrar
public class Estructure_personLocal {

    public static final String TABLA_PERSON_LOCAL= "JOVMOVI_BENEF_LOCAL";
    public static final String CURP_PER_LOCAL = "CURP_PER";
    public static final String PATERNO_A_LOCAL = "MATERNO_P";
    public static final String MATERNO_A_LOCAL = "MATERNO_A";
    public static final String NOMBRES_PER_LOCAL = "NOMBRES_PER";
    public static final String GENERO_PER_LOCAL = "GENERO_PER";
    public static final String FECHANA_PER_LOCAL = "FECHANA_PER";
    public static final String STAT_NAC_LOCAL = "STAT_NAC";
    public static final String STAT_BENEF_LOCAL = "STAT_BENEF";
    public static final String STAT_EXIST_LOCAL = "STAT_EXIST";
    public static final String STAT_NONAC_LOCAL = "STAT_NONAC";

    public static final String CREAR_TABLA_PERSON_LOCAL = "create table "+TABLA_PERSON_LOCAL+"(BENEF_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            +CURP_PER_LOCAL+" VARCHAR, "+PATERNO_A_LOCAL+" VARCHAR, "+MATERNO_A_LOCAL+" VARCHAR, "+NOMBRES_PER_LOCAL+" VARCHAR, " +GENERO_PER_LOCAL+" VARCHAR, "
            +FECHANA_PER_LOCAL+" TEXT, "+STAT_NAC_LOCAL+" VARCHAR, "+STAT_BENEF_LOCAL+" VARCHAR, "+STAT_EXIST_LOCAL+" VARCHAR, "+STAT_NONAC_LOCAL+" VARCHAR)";

    public static final String ELIMINAR_TABLA_PERSON_LOCAL = "drop table if exists" + TABLA_PERSON_LOCAL;


}
