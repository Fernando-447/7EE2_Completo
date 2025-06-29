/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author nataly
 */
public class HerramientasRotasModelo {
   private String nombre; 
   private String ubicacion;
   private String descripcion; 
   private byte[]imagenHeramienta;
   
   //ConstructorVacio
   public HerramientasRotasModelo(){};
   
   
   //Constructor

    public HerramientasRotasModelo(String nombre, String ubicacion, String descripcion, byte[] imagenHeramienta) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.imagenHeramienta = imagenHeramienta;
    }
//Getters setters 
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getImagenHeramienta() {
        return imagenHeramienta;
    }

    public void setImagenHeramienta(byte[] imagenHeramienta) {
        this.imagenHeramienta = imagenHeramienta;
    }
   
   
   
}
