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
import conexion.ConexionDB;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.util.Types;

public class EliminarPrestamoDAO {
    
    private final java.sql.Connection conexion;
    
    public EliminarPrestamoDAO(Connection conexion){
        this.conexion = conexion;
    }
    
    public void eliminarPrestamo(int cvePre) throws SQLException {
        String sql = "DELETE FROM PrestamoMaterial WHERE cvePreMat = ?";
        
        try(
            Connection conn = ConexionDB.getConexion();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, cvePre);
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
