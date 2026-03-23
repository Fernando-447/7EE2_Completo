/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.impl;

import conexion.ConexionDB;
import dao.interfaces.IMaterialNoConsumibleDAO;
import java.util.ArrayList;
import java.util.List;
import modelo.MaterialNoConsumible;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Mr.Robot
 */
public class MaterialNoConsumibleDAOImpl implements IMaterialNoConsumibleDAO {

    @Override
    public List<MaterialNoConsumible> listar() {
        List<MaterialNoConsumible> lista = new ArrayList<>();
        
        String sql = "SELECT * FROM MaterialNoConsumible ORDER BY idUTNG";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = ConexionDB.getConexion();
            ps  = con.prepareStatement(sql);
            rs  = ps.executeQuery();
            
            while (rs.next()) {
                lista.add(mapMaterialNoConsumible(rs));
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
    public MaterialNoConsumible buscarPorIdUTNG(String id) {
        String sql = "SELECT * FROM MaterialNoConsumible WHERE idUTNG = ?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        MaterialNoConsumible m = null;

        try {
            con = ConexionDB.getConexion();
            ps  = con.prepareStatement(sql);
            ps.setString(1, id);
            rs  = ps.executeQuery();

            if (rs.next()) {
                m = mapMaterialNoConsumible(rs);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al buscar por idUTNG: " + e.getMessage());
            
        } finally {
            cerrar(rs);
            cerrar(ps);
            cerrar(con);
        }
        return m;
    }

    @Override
    public MaterialNoConsumible buscarPorIdEst(String id) {
        String sql = "SELECT * FROM MaterialNoConsumible WHERE idEst = ?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        MaterialNoConsumible m = null;

        try {
            con = ConexionDB.getConexion();
            ps  = con.prepareStatement(sql);
            ps.setString(1, id);
            rs  = ps.executeQuery();

            if (rs.next()) {
                m = mapMaterialNoConsumible(rs);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al buscar por idEst: " + e.getMessage());
            
        } finally {
            cerrar(rs);
            cerrar(ps);
            cerrar(con);
        }
        return m;
    }
    
     private MaterialNoConsumible mapMaterialNoConsumible(ResultSet rs) {
        MaterialNoConsumible m = null;
        
        try {
            m = new MaterialNoConsumible();
            m.setIdUTNG(rs.getString("idUTNG"));
            m.setIdEst(rs.getString("idEst"));
            m.setDesMatNoCon(rs.getString("desMatNoCon"));
            m.setNumAct(rs.getString("numAct"));
            m.setNumSer(rs.getString("numSer"));
            m.setMarca(rs.getString("marca"));
            m.setModelo(rs.getString("modelo"));
            m.setCosto(rs.getShort("costo"));
            m.setEstMatNoCon(MaterialNoConsumible.EstadoMaterial.fromBD(rs.getString("estMatNoCon")));
            m.setDisposicion(MaterialNoConsumible.DisposicionMaterial.fromBD(rs.getString("disposicion")));
            m.setTipMat(rs.getInt("tipMat"));
        } catch (SQLException e) {
            System.out.println("Error al mapear el material no consumible: " + e.getMessage());
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
