/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Mr.Robot
 */
public class Administrador {
    private byte cveAdm;
    private String nomComAdm;
    private String apePat = "Sin apelllido";
    private String apeMat = "Sin apellido";
    private short numTraAdm;
    private String correo;
    private String contraseña;
    
    // Constructores

    public Administrador() {
    }

    public Administrador(byte cveAdm, String nomComAdm, short numTraAdm, String correo, String contraseña) {
        this.cveAdm = cveAdm;
        this.nomComAdm = nomComAdm;
        this.numTraAdm = numTraAdm;
        this.correo = correo;
        this.contraseña = contraseña;
    }
    
    // Setters

    public void setCveAdm(byte cveAdm) {
        this.cveAdm = cveAdm;
    }

    public void setNomComAdm(String nomComAdm) {
        this.nomComAdm = nomComAdm;
    }

    public void setApePat(String apePat) {
        this.apePat = apePat;
    }

    public void setApeMat(String apeMat) {
        this.apeMat = apeMat;
    }

    public void setNumTraAdm(short numTraAdm) {
        this.numTraAdm = numTraAdm;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
    // Getters

    public byte getCveAdm() {
        return cveAdm;
    }

    public String getNomComAdm() {
        return nomComAdm;
    }

    public String getApePat() {
        return apePat;
    }

    public String getApeMat() {
        return apeMat;
    }

    public short getNumTraAdm() {
        return numTraAdm;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    
}
