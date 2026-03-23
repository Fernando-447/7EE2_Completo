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
import modelo.*;
import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;


public class ModificarNoConsumibleDAO {
    public HerramientaNoConsumibleModelo extraerNoConsumible(String idUTNG){
        
        String sql = "SELECT idUTNG,idEst,desMatNoCon,numAct,numSer,marca,modelo,costo,estMatNoCon, tmnc.nomTip "
                + "FROM MaterialNoConsumible as mnc, TipMatNoCon as tmnc "
                + "Where mnc.tipMat = tmnc.idTipNoCon AND idUTNG = ?";
        
        try (
            Connection conn = ConexionDB.getConexion();
            PreparedStatement st = conn.prepareStatement(sql)){
            
            st.setString(1, idUTNG);
            
            try (ResultSet rs = st.executeQuery()){
                if (rs.next()) {
  
                    HerramientaNoConsumibleModelo h = new HerramientaNoConsumibleModelo();
                    h.setIdUTNG(idUTNG);
                    h.setIdEst(rs.getString("idEst"));
                    h.setDesMatNoCon(rs.getString("desMatNoCon"));
                    h.setNumAct(rs.getString("numAct"));
                    h.setNumSer(rs.getString("numSer"));
                    h.setMarca(rs.getString("marca"));
                    h.setModelo(rs.getString("modelo"));
                    h.setCosto(Double.parseDouble(rs.getString("costo")));
                    h.setEstMatNoCon(rs.getString("estMatNoCon"));
                    h.setNomTip(rs.getString("tmnc.nomTip"));
                    
                    
                    return h;
                } 
            }    
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
    
    
    public void actualizarNoCon(String idUTNG,String desMatNoCon,String numAct, String numSer, String marca, String modelo, Double costo, String estado){
        String sql = "UPDATE MaterialNoConsumible SET desMatNoCon = ?, numAct = ?, numSer = ?, marca = ?, modelo = ?, costo = ?, estMatNoCon = ? WHERE idUTNG = ?";
        try (Connection conn = ConexionDB.getConexion();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, desMatNoCon);
            ps.setString(2, numAct);
            ps.setString(3, numSer);
            ps.setString(4, marca);
            ps.setString(5, modelo);
            ps.setDouble(6, costo);
            ps.setString(7, estado);
            ps.setString(8, idUTNG);
            ps.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}

