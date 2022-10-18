package com.example.jovenmovimiento2022.DataBaseLocalStorage;

public class Estructure_person {
    public static final String TABLA_PERSON = "JOVMOVI_BENEF";
    public static final String CURP_PER = "CURP_PER";
    public static final String PATERNO_A = "MATERNO_P";
    public static final String MATERNO_A = "MATERNO_A";
    public static final String NOMBRES_PER = "NOMBRES_PER";
    public static final String GENERO_PER = "GENERO_PER";
    public static final String FECHANA_PER = "FECHANA_PER";
    public static final String STAT_NAC = "STAT_NAC";
    public static final String STAT_BENEF = "STAT_BENEF";
    public static final String STAT_EXIST = "STAT_EXIST";
    public static final String STAT_NONAC = "STAT_NONAC";

    public static final String CREAR_TABLA_PERSON = "create table "+TABLA_PERSON+"(BENEF_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            +CURP_PER+" VARCHAR, "+PATERNO_A+" VARCHAR, "+MATERNO_A+" VARCHAR, "+NOMBRES_PER+" VARCHAR, " +GENERO_PER+" VARCHAR, "
            +FECHANA_PER+" TEXT, "+STAT_NAC+" VARCHAR, "+STAT_BENEF+" VARCHAR, "+STAT_EXIST+" VARCHAR, "+STAT_NONAC+" VARCHAR)";

    public static final String ELIMINAR_TABLA_PERSON = "drop table if exists" + TABLA_PERSON;

}
