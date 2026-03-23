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

    //ConexionDB conn = new ConexionDB();
    //Connection conexion = conn.getConexion();
    ConexionDB conexionDB = new ConexionDB();
    Connection conexion = conexionDB.getConexion();
    //public DocenteDAO() {
      //  try {
        //    ConexionDB conn = new ConexionDB(); // Crear instancia de ConexionDB
          //  this.conexion = conn.getConexion(); // Obtener la conexión
            //if (this.conexion == null) {
    //            throw new SQLException("No se pudo establecer la conexión a la base de datos");
      //      }
        //} catch (SQLException e) {
          //  e.printStackTrace();  // Manejamos el error de conexión
            // Puedes agregar más lógica para manejar el fallo de la conexión aquí, como lanzar una excepción personalizada.
        //}
    //}

    // Insertar un docente nuevo
    public boolean insertarDocente(DocenteModelo docente) {
        String sql = "INSERT INTO Docente (numTraDoc ,nom, apePat, apeMat) VALUES (?,?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, docente.getNumTraDoc());
            stmt.setString(2, docente.getNombre());
            stmt.setString(3, docente.getApellidoPaterno());
            stmt.setString(4, docente.getApellidoMaterno());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todos los docentes
    public List<DocenteModelo> obtenerTodosLosDocentes() {
        List<DocenteModelo> lista = new ArrayList<>();
        String sql = "SELECT numTraDoc, nom, apePat, apeMat FROM docente";

        try (PreparedStatement stmt = conexion.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                DocenteModelo docente = new DocenteModelo();
                docente.setNumTraDoc(rs.getInt("numTraDoc"));
                docente.setNombre(rs.getString("nom"));
                docente.setApellidoPaterno(rs.getString("apePat"));
                docente.setApellidoMaterno(rs.getString("apeMat"));
                lista.add(docente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public List<String> obtenerDocenteNomCom() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT numTraDoc, CONCAT( nom,' ',apePat,' ',apeMat) AS nomCom FROM docente";

        try (PreparedStatement stmt = conexion.prepareStatement(sql); 
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String docente = rs.getString("numTraDoc")+ "-" + rs.getString("nomCom");
                lista.add(docente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Obtener docente por ID
    public DocenteModelo obtenerDocentePorId(int numTraDoc) {
        String sql = "SELECT numTraDoc, nom, apePat, apeMat FROM Docente WHERE numTraDoc = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, numTraDoc);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                DocenteModelo docente = new DocenteModelo();
                docente.setNumTraDoc(rs.getInt("numTraDoc"));
                docente.setNombre(rs.getString("nom"));
                docente.setApellidoPaterno(rs.getString("apePat"));
                docente.setApellidoMaterno(rs.getString("apeMat"));
                return docente;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Actualizar docente por ID
    public boolean actualizarDocentePorId(int numTraDoc, DocenteModelo docenteActualizado) {
        String sql = "UPDATE Docente SET nom = ?, apePat = ?, apeMat = ? WHERE numTraDoc = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, docenteActualizado.getNombre());
            stmt.setString(2, docenteActualizado.getApellidoPaterno());
            stmt.setString(3, docenteActualizado.getApellidoMaterno());
            stmt.setInt(4, numTraDoc);

            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar docente por ID
    public boolean eliminarDocentePorId(int numTraDoc) {
        String sql = "DELETE FROM Docente WHERE numTraDoc = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, numTraDoc);
            int filasEliminadas = stmt.executeUpdate();
            return filasEliminadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
