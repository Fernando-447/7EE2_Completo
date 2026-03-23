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
public class HerramientaNoConsumibleRotasDAO {
    
    ConexionDB conn = new ConexionDB();
    public Connection conexion = conn.getConexion();
    //private Connection conexion;

    // Método para obtener todas las herramientas rotas
    public List<HerramientasRotasModelo> obtenerTodasHerramientasNoConsumiblesRotas() { 
        List<HerramientasRotasModelo> lista = new ArrayList<>();

        // NUEVA CONSULTA CON INNER JOIN
        String sql = "SELECT t.nomTip AS nombre, m.desMatNoCon AS descripcion, "
                + "m.estMatNoCon AS estado, m.idUTNG AS idHerramienta "
                + "FROM MaterialNoConsumible m "
                + "INNER JOIN TipMatNoCon t ON m.tipMat = t.idTipNoCon "
                + "WHERE m.estMatNoCon = 'Dañado'";

        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

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
