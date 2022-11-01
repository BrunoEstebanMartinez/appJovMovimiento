package com.example.jovenmovimiento2022.Models;

/*
   *  File:     DatosConsultaCurp.java
   *  Function: Testing de WsCurp
   *  Autores:    Ing. Bruno Esteban Martínez Millán e Ing. Silverio Baltazar Barrientos Zarate.
   *  Copyright 2022 Gobierno del Estado de México
   *
   *
*/


import java.io.Serializable;

public class DatosConsultaCurp implements Serializable {

    //Input Parameters
    protected int tipoTransaccion;
    protected String usuario,
                     password,
                     cveCurp,
                     direccionIp;

    //Output parameters

    protected String apellido1,
                     apellido2,
                     nombres,
                     sexo,
                     fechNac,
                     cveEntidadNac,
                     nacionalidad,
                     AnioReg,
                     numEntidadReg,
                     cveMunicipioReg,
                     cveEntidadEmisora;




    //Null Constructor
    public DatosConsultaCurp(){}

    //Constructor with Input Parameters
    public DatosConsultaCurp(
            int tipoTransaccion,
            String usuario,
            String password,
            String direccionIp,
            String cveCurp){
                            this.tipoTransaccion = tipoTransaccion;
                            this.usuario = usuario;
                            this.password = password;
                            this.direccionIp = direccionIp;
                            this.cveCurp = cveCurp;
                            }
    //Constructor with Ouput Parameters
    public DatosConsultaCurp(
            String apellido1,
            String apellido2,
            String nombres,
            String sexo,
            String fechNac,
            String cveEntidadNac,
            String nacionalidad,
            String AnioReg,
            String numEntidadReg,
            String cveMunicipioReg,
            String cveEntidadEmisora
                            ){
                            this.apellido1 = apellido1;
                            this.apellido2 = apellido2;
                            this.nombres = nombres;
                            this.sexo = sexo;
                            this.fechNac = fechNac;
                            this.cveEntidadNac = cveEntidadNac;
                            this.nacionalidad = nacionalidad;
                            this.AnioReg = AnioReg;
                            this.numEntidadReg = numEntidadReg;
                            this.cveMunicipioReg = cveMunicipioReg;
                            this.cveEntidadEmisora = cveEntidadEmisora;
                            }
    //Constructor with all parameters
    public DatosConsultaCurp(
            int tipoTransaccion,
            String usuario,
            String password,
            String direccionIp,
            String cveCurp,
            String apellido1,
            String apellido2,
            String nombres,
            String sexo,
            String fechNac,
            String cveEntidadNac,
            String nacionalidad,
            String AnioReg,
            String numEntidadReg,
            String cveMunicipioReg,
            String cveEntidadEmisora
                            ){
                        this.tipoTransaccion = tipoTransaccion;
                        this.usuario = usuario;
                        this.password = password;
                        this.direccionIp = direccionIp;
                        this.cveCurp = cveCurp;
                        this.apellido1 = apellido1;
                        this.apellido2 = apellido2;
                        this.nombres = nombres;
                        this.sexo = sexo;
                        this.fechNac = fechNac;
                        this.cveEntidadNac = cveEntidadNac;
                        this.nacionalidad = nacionalidad;
                        this.AnioReg = AnioReg;
                        this.numEntidadReg = numEntidadReg;
                        this.cveMunicipioReg = cveMunicipioReg;
                        this.cveEntidadEmisora = cveEntidadEmisora;
                            }

    public int getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(int tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCveCurp() {
        return cveCurp;
    }

    public void setCveCurp(String cveCurp) {
        this.cveCurp = cveCurp;
    }

    public String getDireccionIp() {
        return direccionIp;
    }

    public void setDireccionIp(String direccionIp) {
        this.direccionIp = direccionIp;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFechNac() {
        return fechNac;
    }

    public void setFechNac(String fechNac) {
        this.fechNac = fechNac;
    }

    public String getCveEntidadNac() {
        return cveEntidadNac;
    }

    public void setCveEntidadNac(String cveEntidadNac) {
        this.cveEntidadNac = cveEntidadNac;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getAnioReg() {
        return AnioReg;
    }

    public void setAnioReg(String anioReg) {
        AnioReg = anioReg;
    }

    public String getNumEntidadReg() {
        return numEntidadReg;
    }

    public void setNumEntidadReg(String numEntidadReg) {
        this.numEntidadReg = numEntidadReg;
    }

    public String getCveMunicipioReg() {
        return cveMunicipioReg;
    }

    public void setCveMunicipioReg(String cveMunicipioReg) {
        this.cveMunicipioReg = cveMunicipioReg;
    }

    public String getCveEntidadEmisora() {
        return cveEntidadEmisora;
    }

    public void setCveEntidadEmisora(String cveEntidadEmisora) {
        this.cveEntidadEmisora = cveEntidadEmisora;
    }
}
