/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carde
 */
public class DevolucionPrestamoDAO {
    
    private final java.sql.Connection conexion;
    
    public DevolucionPrestamoDAO(Connection conexion){
        this.conexion = conexion;
    }
    
    public void devolucionPrestamo(int cvePre, String fecha) throws SQLException {
        String sql = "UPDATE PrestamoMaterial SET fecEnt = ? WHERE cvePreMat = ?";
        
        try(
            Connection conn = ConexionDB.getConexion();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, fecha);
            stmt.setInt(2, cvePre);
            stmt.executeUpdate();
            
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    
    public List<String> obtenerMaterialNoConsumible(int cvePre) {
        List<String> listaNoConsumible = new ArrayList<>();
        
        String sql = "SELECT cveMatNoCon FROM MatPreNoCon WHERE cvePre = ? ";
        
        try (
            Connection conexion = ConexionDB.getConexion();
            PreparedStatement stmt = conexion.prepareStatement(sql)) {
  
                stmt.setInt(1, cvePre);
                
            try (ResultSet rs = stmt.executeQuery()){
                
                while (rs.next()) {
                    
                String idUTNG = rs.getString("cveMatNoCon");
                
                listaNoConsumible.add(idUTNG);
                }
            }
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaNoConsumible;
    }
    
    public List<String> obtenerMaterialConsumible(int cvePre) {
        List<String> listaConsumible = new ArrayList<>();
        String sql = "SELECT cveMatCon FROM MatPreCon WHERE cvePre = ? ";
        
        try (
            Connection conexion = ConexionDB.getConexion();
            PreparedStatement stmt = conexion.prepareStatement(sql)) {

                stmt.setInt(1, cvePre);
                System.out.println("[DAO] Consulta SQL: "+ stmt.toString());

            try (ResultSet rs = stmt.executeQuery()){
                
                while (rs.next()) {
                
                String cveMatCon = rs.getString("cveMatCon");
                
                listaConsumible.add(cveMatCon);
                }
            }
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaConsumible;
    }
    
    public void cambiarDisposicionNC(String idUTNG) throws SQLException{
        //String estado = "\"No disponible\"";
        String sql = "UPDATE MaterialNoConsumible SET disposicion = 'Disponible' "
                + "Where idUTNG = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            //stmt.setString(1, estado);
            stmt.setString(1, idUTNG);
            stmt.execute();
            
        } 
    }
    
    public void cambiarDisposicionC(String cveMatCon) throws SQLException{
        //String estado = "\"No disponible\"";
        String sql = "UPDATE MaterialConsumible SET disposicion = 'Disponible' "
                + "Where cveMatCon = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            //stmt.setString(1, estado);
            stmt.setString(1, cveMatCon);
            stmt.execute();
            
        } 
    }
}
