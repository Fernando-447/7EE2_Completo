/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.ConexionDB;
import modelo.AlumnoModelo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nataly
 */
public class AlumnoDAO {

    ConexionDB conn = new ConexionDB();
    Connection conexion = conn.getConexion();

    public boolean insertarAlumno(AlumnoModelo alumno) {
        String sql = "INSERT INTO alumno (nomAlu, apePatAlu, apeMatAlu, numConAlu) VALUES (?, ?, ?, ?)";

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
    
    // Leer todos los alumnos
    public List<AlumnoModelo> obtenerTodosLosAlumnos() {
        List<AlumnoModelo> lista = new ArrayList<>();
        String sql = "SELECT * FROM alumno";
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                AlumnoModelo alumno = new AlumnoModelo();
                alumno.setNombre(rs.getString("nomAlu"));
                alumno.setApellidoPaterno(rs.getString("apePatAlu"));
                alumno.setApellidoMaterno(rs.getString("apeMatAlu"));
                alumno.setNoControl(rs.getInt("numConAlu"));
                lista.add(alumno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Leer uno por número de control
    public AlumnoModelo obtenerAlumnoPorNoControl(int noControl) {
        String sql = "SELECT * FROM alumno WHERE numConAlu = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, noControl);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    AlumnoModelo alumno = new AlumnoModelo();
                    alumno.setNombre(rs.getString("nomAlu"));
                    alumno.setApellidoPaterno(rs.getString("apePatAlu"));
                    alumno.setApellidoMaterno(rs.getString("apeMatAlu"));
                    alumno.setNoControl(rs.getInt("numConAlu"));
                    return alumno;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Actualizar por número de control (lo recibe como parámetro)
    public boolean actualizarAlumnoPorNoControl(int noControl, AlumnoModelo alumnoActualizado) {
        String sql = "UPDATE alumno SET nomAlu = ?, apePatAlu = ?, apeMatAlu = ? WHERE numConAlu = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, alumnoActualizado.getNombre());
            stmt.setString(2, alumnoActualizado.getApellidoPaterno());
            stmt.setString(3, alumnoActualizado.getApellidoMaterno());
            stmt.setInt(4, noControl); // el nocontrol que se quiere actualizar
            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar por número de control
    public boolean eliminarAlumnoPorNoControl(int noControl) {
        String sql = "DELETE FROM alumno WHERE numConAlu = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, noControl);
            int filasEliminadas = stmt.executeUpdate();
            return filasEliminadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

    
    
    
