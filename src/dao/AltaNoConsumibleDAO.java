/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.ConexionDB;
import modelo.HerramientaNoConsumibleModelo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.TipoNoConsumibleModelo;

/**
 *
 * @author carde
 */
public class AltaNoConsumibleDAO {
    private final Connection conexion;
    
    public AltaNoConsumibleDAO(Connection conexion){
        this.conexion = conexion;
    }
    
    public List <HerramientaNoConsumibleModelo> listar(List<TipoNoConsumibleModelo>tipoNoConsumible) {
        List<HerramientaNoConsumibleModelo> lista = new ArrayList<>();
        String sql = "SELECT * FROM MaterialNoConsumible";
        try (
            Connection conn = ConexionDB.getConexion();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
        
            while (rs.next()) {
                //int tipMat = rs.getInt("tipMat");
                HerramientaNoConsumibleModelo item = new HerramientaNoConsumibleModelo();
                //lista.add(new HerramientaNoConsumibleModelo(
                    item.setIdUTNG(rs.getString("idUTNG"));
                    item.setIdEst(rs.getString("idEst"));
                    item.setDesMatNoCon(rs.getString("desMatNoCon"));
                    item.setNumAct(rs.getString("numAct"));
                    item.setNumSer(rs.getString("numSer"));
                    item.setMarca(rs.getString("marca"));
                    item.setModelo(rs.getString("modelo"));
                    item.setCosto(rs.getDouble("costo"));
                    item.setTipMat(rs.getInt("tipMat"));
                    
                    lista.add(item);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public void altaNoConsumible(HerramientaNoConsumibleModelo h){
        String sql = "INSERT INTO MaterialNoConsumible(idUTNG,idEst,desMatNoCon,numAct,numSer,marca,modelo,costo,tipMat) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try(
            Connection conn = ConexionDB.getConexion();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, h.getIdUTNG());
            stmt.setString(2, h.getIdEst());
            stmt.setString(3, h.getDesMatNoCon());
            stmt.setString(4, h.getNumAct());
            stmt.setString(5, h.getNumSer());
            stmt.setString(6, h.getMarca());
            stmt.setString(7, h.getModelo());
            stmt.setDouble(8, h.getCosto());
            stmt.setInt(9, h.getTipMat());
            
            System.out.println("Consulta SQL: "+ stmt.toString());
            stmt.executeUpdate();
            
            if (h == null) {
                throw new IllegalArgumentException("El objeto herramienta no puede ser nulo");
            }
            
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public boolean existeIdUTNG(String idUTNG) throws SQLException{
        String sql = "SELECT COUNT(*) FROM MaterialNoConsumible WHERE idUTNG = ?";
        
        try(PreparedStatement stmt = conexion.prepareStatement(sql)){
            stmt.setString(1, idUTNG);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                return rs.getInt(1)>0;
            }
            
            return false;
        }
    }
    
    public boolean existeIdEstatal(String idEst) throws SQLException{
        String sql = "SELECT COUNT(*) FROM MaterialNoConsumible WHERE idEst = ?";
        
        try(PreparedStatement stmt = conexion.prepareStatement(sql)){
            stmt.setString(1, idEst);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                return rs.getInt(1)>0;
            }
            
            return false;
        }
    }
}
