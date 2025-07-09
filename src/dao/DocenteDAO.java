/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.DocenteModelo;

/**
 *
 * @author nataly
 */
public class DocenteDAO {

    ConexionDB conn = new ConexionDB();
    Connection conexion = conn.getConexion();

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

    public List<DocenteModelo> obtenerTodosLosDocentes() {
        List<DocenteModelo> lista = new ArrayList<>();
        String sql = "SELECT idDocentes, nombre, apellidoPaterno, apellidoMaterno FROM docente";

        try (PreparedStatement stmt = conexion.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                DocenteModelo docente = new DocenteModelo();
                docente.setIdDocente(rs.getInt("idDocentes"));
                docente.setNombre(rs.getString("nombre"));
                docente.setApellidoPaterno(rs.getString("apellidoPaterno"));
                docente.setApellidoMaterno(rs.getString("apellidoMaterno"));
                lista.add(docente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public DocenteModelo obtenerDocentePorId(int idDocente) {
        String sql = "SELECT idDocentes, nombre, apellidoPaterno, apellidoMaterno FROM Docente WHERE idDocentes = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idDocente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                DocenteModelo docente = new DocenteModelo();
                docente.setIdDocente(rs.getInt("idDocentes"));
                docente.setNombre(rs.getString("nombre"));
                docente.setApellidoPaterno(rs.getString("apellidoPaterno"));
                docente.setApellidoMaterno(rs.getString("apellidoMaterno"));
                return docente;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean actualizarDocentePorId(int idDocente, DocenteModelo docenteActualizado) {
        String sql = "UPDATE Docente SET nombre = ?, apellidoPaterno = ?, apellidoMaterno = ? WHERE idDocentes = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, docenteActualizado.getNombre());
            stmt.setString(2, docenteActualizado.getApellidoPaterno());
            stmt.setString(3, docenteActualizado.getApellidoMaterno());
            stmt.setInt(4, idDocente);

            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarDocentePorId(int idDocente) {
        String sql = "DELETE FROM Docente WHERE idDocentes = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idDocente);
            int filasEliminadas = stmt.executeUpdate();
            return filasEliminadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
