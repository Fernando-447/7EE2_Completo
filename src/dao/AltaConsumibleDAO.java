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
import modelo.HerramientaConsumibleModelo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.HerramientaNoConsumibleModelo;
import modelo.TipoConsumibleModelo;
import modelo.TipoNoConsumibleModelo;


public class AltaConsumibleDAO {
    private final Connection conexion;
    
    public AltaConsumibleDAO(Connection conexion){
        this.conexion = conexion;
    }
    
    public List <HerramientaConsumibleModelo> listar(List<TipoConsumibleModelo>tipoConsumible) {
        List<HerramientaConsumibleModelo> lista = new ArrayList<>();
        String sql = "SELECT * FROM MaterialConsumible";
        try (
            Connection conn = ConexionDB.getConexion();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
        
            while (rs.next()) {
                //int tipMat = rs.getInt("tipMat");
                HerramientaConsumibleModelo item = new HerramientaConsumibleModelo();
                //lista.add(new HerramientaNoConsumibleModelo(
                    item.setCveMatCon(rs.getString("cveMatCon"));
                    item.setIdLoc(rs.getString("idLoc"));
                    item.setDesMatCon(rs.getString("desMatCon"));
                    item.setEstMatCon(rs.getString("estMatCon"));
                    item.setTipMat(rs.getInt("tipMat"));
                    
                    lista.add(item);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public void altaConsumible(HerramientaConsumibleModelo h){
        String sql = "INSERT INTO MaterialConsumible(cveMatCon,idLoc,desMatCon,estMatCon,tipMat) values (?, ?, ?, ?, ?)";
        
        try(
            Connection conn = ConexionDB.getConexion();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, h.getCveMatCon());
            stmt.setString(2, h.getIdLoc());
            stmt.setString(3, h.getDesMatCon());
            stmt.setString(4, h.getEstMatCon());            
            stmt.setInt(5, h.getTipMat());
            
            System.out.println("Consulta SQL: "+ stmt.toString());
            stmt.executeUpdate();
            
            if (h == null) {
                throw new IllegalArgumentException("El objeto herramienta no puede ser nulo");
            }
            
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public boolean existeCveMatCon(String cveMatCon) throws SQLException{
        String sql = "SELECT COUNT(*) FROM MaterialConsumible WHERE cveMatCon = ?";
        
        try(PreparedStatement stmt = conexion.prepareStatement(sql)){
            stmt.setString(1, cveMatCon);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                return rs.getInt(1)>0;
            }
            
            return false;
        }
    }
    
}
