/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.ConexionDB;
import modelo.AlumnoModelo;
import java.sql.*;

/**
 *
 * @author nataly
 */
public class AlumnoDAO {

    ConexionDB conn = new ConexionDB();
    Connection conexion = conn.getConexion();

    public boolean insertarAlumno(AlumnoModelo alumno) {
        String sql = "INSERT INTO alumnos (nombre, apellido_paterno, apellido_materno, no_control) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, alumno.getNombre());
            stmt.setString(2, alumno.getApellidoPaterno());
            stmt.setString(3, alumno.getApellidoMaterno());
            stmt.setInt(4, alumno.getNoControl());

            int filasInsertadas = stmt.executeUpdate();
            return filasInsertadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

    
    
    
