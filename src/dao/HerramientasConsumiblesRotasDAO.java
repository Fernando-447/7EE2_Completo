/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.ConexionDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.HerramientasRotasModelo;

/**
 *
 * @author nataly
 */
public class HerramientasConsumiblesRotasDAO {
    ConexionDB conn = new ConexionDB();
    //private Connection conexion;
    Connection conexion = conn.getConexion();
    //ConexionDB conexion = new ConexionDB();
         // Método para obtener todas las herramientas rotas
public List<HerramientasRotasModelo> obtenerTodasHerramientasConsumiblesRotas() {
    List<HerramientasRotasModelo> lista = new ArrayList<>();
    
    // NUEVA CONSULTA CON INNER JOIN
    String sql = "SELECT t.nomTip AS nombre, m.desMatCon AS descripcion, " +
                 "m.estMatCon AS estado, m.cveMatCon AS idHerramienta " +
                 "FROM MaterialConsumible m " +
                 "INNER JOIN TipMatCon t ON m.tipMat = t.idTipCon " +
                 "WHERE m.estMatCon = 'Dañado'";
    
    try (Statement stmt = conexion.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            HerramientasRotasModelo herramienta = new HerramientasRotasModelo();
            herramienta.setNombre(rs.getString("nombre"));
            herramienta.setDescripcion(rs.getString("descripcion"));
            herramienta.setEstado(rs.getString("estado"));
            herramienta.setId(rs.getString("idHerramienta"));
            lista.add(herramienta);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return lista;
}
}
            

