/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author carde
 */
import conexion.ConexionDB;
//import modelo.*;
import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
import modelo.TipoConsumibleModelo;
import modelo.HerramientaConsumibleModelo;


public class ModificarConsumibleDAO {
    
    public HerramientaConsumibleModelo extraerConsumible(String cveMatCon){
        
        String sql = "SELECT cveMatCon,idLoc,desMatCon,estMatCon, tmc.nomTip "
                + "FROM MaterialConsumible as mc, TipMatCon as tmc "
                + "Where mc.tipMat = tmc.idTipCon AND cveMatCon = ?";
        
        try (
            Connection conn = ConexionDB.getConexion();
            PreparedStatement st = conn.prepareStatement(sql)){
            
            st.setString(1, cveMatCon);
            
            try (ResultSet rs = st.executeQuery()){
                if (rs.next()) {
  
                    HerramientaConsumibleModelo h = new HerramientaConsumibleModelo();
                    h.setCveMatCon(cveMatCon);
                    h.setIdLoc(rs.getString("idLoc"));
                    h.setDesMatCon(rs.getString("desMatCon"));
                    h.setEstMatCon(rs.getString("estMatCon"));
                    h.setNomTip(rs.getString("tmc.nomTip"));
                    
                    
                    return h;
                } 
            }    
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
    
    
    public void actualizarCon(String cveMatCon,String idLoc, String estado, String desc){
        String sql = "UPDATE MaterialConsumible SET idLoc = ?, desMatCon=?, estMatCon = ? WHERE cveMatCon = ?";
        try (Connection conn = ConexionDB.getConexion();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, idLoc);
            ps.setString(2, desc);
            ps.setString(3, estado);
            ps.setString(4, cveMatCon);
            ps.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}

