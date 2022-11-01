package com.example.jovenmovimiento2022.DataBaseLocalStorage;

public class Estructure_personImagesLocal {
    public static final String TABLA_PERSONIM_LOCAL = "JOVMOVI_IMAGES_LOCAL";
    public static final String CURP_PER_LOCAL = "CURP_PER_LOCAL";
    public static final String COMPROBANTE_ES_LOCAL = "COMPROBANTE_ES_LOCAL";
    public static final String CURP_PH_LOCAL = "CURP_PH_LOCAL";
    public static final String ACTA_NAC_LOCAL = "ACTA_NAC_LOCAL";
    public static final String IDENT_INE_REVER_LOCAL = "IDENT_INE_REVER_LOCAL";
    public static final String IDENT_INE_ADVER_LOCAL = "IDENT_INE_ADVER_LOCAL";
    public static final String BENEF_FUR_LOCAL = "BENEF_FUR_LOCAL";


    public static final String CREAR_TABLA_PERSONIM_LOCAL = "create table "+TABLA_PERSONIM_LOCAL+"("
            +CURP_PER_LOCAL+" VARCHAR, "+COMPROBANTE_ES_LOCAL+" TEXT, "+CURP_PH_LOCAL+" TEXT, "+ACTA_NAC_LOCAL+" TEXT, " +IDENT_INE_REVER_LOCAL+" TEXT, "
            +IDENT_INE_ADVER_LOCAL+" TEXT, "+BENEF_FUR_LOCAL+" TEXT)";

    public static final String ELIMINAR_TABLA_PERSONIM_LOCAL = "drop table if exists" + TABLA_PERSONIM_LOCAL;
}
