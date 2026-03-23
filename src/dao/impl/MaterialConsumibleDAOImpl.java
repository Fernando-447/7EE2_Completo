/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.impl;

import conexion.ConexionDB;
import dao.interfaces.IMaterialConsumibleDAO;
import java.util.ArrayList;
import java.util.List;
import modelo.MaterialConsumible;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Mr.Robot
 */
public class MaterialConsumibleDAOImpl implements IMaterialConsumibleDAO {

    @Override
    public List<MaterialConsumible> listar() {
        List<MaterialConsumible> lista = new ArrayList<>();
        
        String sql = "SELECT * FROM MaterialConsumible ORDER BY cveMatCon";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = ConexionDB.getConexion();
            ps  = con.prepareStatement(sql);
            rs  = ps.executeQuery();
            
            while (rs.next()) {
                lista.add(mapMaterialConsumible(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al solicitar la lista de prestamos:  " + e.getMessage());
        } finally {
            cerrar(rs);
            cerrar(ps);
            cerrar(con);
        }
        return lista;
    }

    @Override
    public MaterialConsumible buscarPorIdLoc(String id) {
        String sql = "SELECT * FROM MaterialConsumible WHERE idLoc = ?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        MaterialConsumible m = null;

        try {
            con = ConexionDB.getConexion();
            ps  = con.prepareStatement(sql);
            ps.setString(1, id);
            rs  = ps.executeQuery();

            if (rs.next()) {
                m = mapMaterialConsumible(rs);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al buscar por idLoc: " + e.getMessage());
            
        } finally {
            cerrar(rs);
            cerrar(ps);
            cerrar(con);
        }
        return m;
    }
    
    @Override
    public MaterialConsumible buscarPorCveMatCon(String cve) {
        String sql = "SELECT * FROM MaterialConsumible WHERE cveMatCon = ?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        MaterialConsumible m = null;

        try {
            con = ConexionDB.getConexion();
            ps  = con.prepareStatement(sql);
            ps.setString(1, cve);
            rs  = ps.executeQuery();

            if (rs.next()) {
                m = mapMaterialConsumible(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar por cveMatCon: " + e.getMessage());

        } finally {
            cerrar(rs);
            cerrar(ps);
            cerrar(con);
        }
        return m;
    }
    
    private MaterialConsumible mapMaterialConsumible(ResultSet rs) {
        MaterialConsumible m = null;
        
        try {
            m = new MaterialConsumible();
            m.setCveMatCon(rs.getString("cveMatCon"));
            m.setIdLoc(rs.getString("idLoc"));
            m.setDesMatCon(rs.getString("desMatCon"));
            m.setEstMatCon(MaterialConsumible.EstadoMaterial.fromBD(rs.getString("estMatCon")));
            m.setDisposicion(MaterialConsumible.DisposicionMaterial.fromBD(rs.getString("disposicion")));
            m.setTipMat(rs.getInt("tipMat"));
        } catch (SQLException e) {
            System.out.println("Error al mapear el material consumible: " + e.getMessage());
        }
        return m;
    }
    
    private void cerrar(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}