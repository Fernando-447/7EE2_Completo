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
   private String descripcion;
   private String id;
   private String estado;
   
   public HerramientasRotasModelo(){};

    public HerramientasRotasModelo(String nombre, String descripcion, String id, String estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id = id;
        this.estado = estado;
    }
   
   

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
   