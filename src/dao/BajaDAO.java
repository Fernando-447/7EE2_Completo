/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author carde
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import conexion.ConexionDB;
import java.sql.SQLException;

public class BajaDAO {
    private final java.sql.Connection conexion;
    
    public BajaDAO(Connection conexion){
        this.conexion = conexion;
    }
    
    public boolean eliminarConsumible(String clave) throws SQLException {
        String sql = "DELETE FROM MaterialConsumible WHERE cveMatCon = ?";
        
        try(
            Connection conn = ConexionDB.getConexion();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, clave);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean eliminarNoConsumible(String idUTNG) throws SQLException {
        String sql = "DELETE FROM MaterialNoConsumible WHERE idUTNG = ? ";
        
        try(Connection conn = ConexionDB.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setString(1, idUTNG);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
