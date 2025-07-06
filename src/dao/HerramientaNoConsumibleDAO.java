/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.ConexionDB;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import modelo.HerramientasRotasModelo;
import java.sql.*;
/**
 *
 * @author nataly
 */
public class HerramientaNoConsumibleDAO{
    
    ConexionDB conn=new ConexionDB();
    public Connection conexion=conn.getConexion();
   
     // Método para obtener todas las herramientas rotas
    public List<HerramientasRotasModelo> obtenerTodasHerramientasNoConsumiblesRotas() {
        List<HerramientasRotasModelo> lista = new ArrayList<>();
        String sql = "SELECT nombre, ubicacion, descripcion,estado , idHerramienta FROM herramientaconsumible WHERE estado='ROTO'";

        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                HerramientasRotasModelo herramienta = new HerramientasRotasModelo();
                herramienta.setNombre(rs.getString("nombre"));
                herramienta.setUbicacion(rs.getString("ubicacion"));
                herramienta.setDescripcion(rs.getString("descripcion"));
                
                herramienta.setEstado(rs.getString("estado"));
                herramienta.setId(rs.getInt("idHerramienta"));
                lista.add(herramienta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
    
    
    

