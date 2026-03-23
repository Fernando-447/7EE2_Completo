/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;

/**
 *
 * @author Mr.Robot
 */
public class Prestamo {
    private int cvePreMat;
    public String numTraDoc;
    private String nomCom;
    private String numConAlu;
    private byte cveAdm;
    private String nomComAdm;
    private String gruAlu;
    private String mat;
    private String obs;
    private LocalDate fecSal;
    private LocalDate fecEnt;

    //public Prestamo() {
    //}

    public Prestamo() {
        this.cvePreMat = cvePreMat;
        this.numTraDoc = numTraDoc;
        this.nomCom = nomCom;
        this.numConAlu = numConAlu;
        this.cveAdm = cveAdm;
        this.nomComAdm = nomComAdm;
        this.gruAlu = gruAlu;
        this.mat = mat;
        this.obs = obs;
        this.fecSal = fecSal;
        this.fecEnt = fecEnt;
    }
    
    // Setters

    public void setCvePreMat(int cvePreMat) {
        this.cvePreMat = cvePreMat;
    }

    public void setNumTraDoc(String numTraDoc) {
        this.numTraDoc = numTraDoc;
    }

    public void setNumConAlu(String numConAlu) {
        this.numConAlu = numConAlu;
    }

    public void setCveAdm(byte cveAdm) {
        this.cveAdm = cveAdm;
    }

    public String getNomComAdm() {
        return nomComAdm;
    }

    public void setNomComAdm(String nomComAdm) {
        this.nomComAdm = nomComAdm;
    }

    public void setGruAlu(String gruAlu) {
        this.gruAlu = gruAlu;
    }

    public void setMat(String mat) {
        this.mat = mat;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public void setFecSal(LocalDate fecSal) {
        this.fecSal = fecSal;
    }

    public void setFecEnt(LocalDate fecEnt) {
        this.fecEnt = fecEnt;
    }
    
    // Getters

    public int getCvePreMat() {
        return cvePreMat;
    }

    public String getNumTraDoc() {
        return numTraDoc;
    }

    public String getNomCom() {
        return nomCom;
    }

    public void setNomCom(String nomCom) {
        this.nomCom = nomCom;
    }

    public String getNumConAlu() {
        return numConAlu;
    }

    public byte getCveAdm() {
        return cveAdm;
    }

    public String getGruAlu() {
        return gruAlu;
    }

    public String getMat() {
        return mat;
    }

    public String getObs() {
        return obs;
    }

    public LocalDate getFecSal() {
        return fecSal;
    }

    public LocalDate getFecEnt() {
        return fecEnt;
    }

}
