/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import conexion.ConexionDB;
import java.sql.*;
import modelo.DocenteModelo;

/**
 *
 * @author nataly
 */
public class DocenteDAO {
    ConexionDB conn = new ConexionDB();
    Connection conexion=conn.getConexion();
    
    // Insertar un docente nuevo
    public boolean insertarDocente(DocenteModelo docente) {
        String sql = "INSERT INTO Docente (nombre, apellidoPaterno, apellidoMaterno) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, docente.getNombre());
            stmt.setString(2, docente.getApellidoPaterno());
            stmt.setString(3, docente.getApellidoMaterno());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
