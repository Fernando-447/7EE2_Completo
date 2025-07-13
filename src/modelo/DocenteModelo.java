/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author nataly
 */
public class DocenteModelo {
    private int numTraDoc;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;

    public int getNumTraDoc() {
        return numTraDoc;
    }

    public void setNumTraDoc(int numTraDoc) {
        this.numTraDoc = numTraDoc;
    }

   
    
//constructor
    public DocenteModelo(String nombre, String apellidoPaterno, String apellidoMaterno, int numTraDoc) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.numTraDoc = numTraDoc;
    }
    public DocenteModelo(){};
//getter
    public String getNombre() {
        return nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }
//setter
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }
    
    
    
    
}
