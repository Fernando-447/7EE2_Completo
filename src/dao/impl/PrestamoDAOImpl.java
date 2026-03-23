/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dao.impl;

import conexion.ConexionDB;
import dao.interfaces.IPrestamoDAO;
import java.util.ArrayList;
import java.util.List;
import modelo.Prestamo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.interfaces.IPrestamoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Prestamo;

/**
 *
 * @author Mr.Robot
 */
public class PrestamoDAOImpl implements IPrestamoDAO {
    @Override
    public List<Prestamo> listar() {
        List<Prestamo> lista = new ArrayList<>();
        
        String sql = "SELECT * FROM PrestamoMaterial ORDER BY cvePreMat";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = ConexionDB.getConexion();
            ps  = con.prepareStatement(sql);
            rs  = ps.executeQuery();
            
            while (rs.next()) {
                lista.add(mapPrestamo(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al solicitar lista de prestamos:  " + e.getMessage());
        } finally {
            cerrar(rs);
            cerrar(ps);
            cerrar(con);
        }
        return lista;
    }
    
    @Override
    public List<Prestamo> buscarPorNoVale(String id) {
        String sql = "SELECT * FROM PrestamoMaterial WHERE cvePreMat = ?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Prestamo> lista = new ArrayList<>();

        try {
            con = ConexionDB.getConexion();
            ps  = con.prepareStatement(sql);
            ps.setString(1, id);
            rs  = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapPrestamo(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("Error al buscar por cvePreMat: " + e.getMessage());
            
        } finally {
            cerrar(rs);
            cerrar(ps);
            cerrar(con);
        }
        return lista;
    }
    
    @Override
    public List<Prestamo> buscarPorNoTra(String id) {
        String sql = "SELECT * FROM PrestamoMaterial WHERE numTraDoc = ?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Prestamo> lista = new ArrayList<>();

        try {
            con = ConexionDB.getConexion();
            ps  = con.prepareStatement(sql);
            ps.setString(1, id);
            rs  = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapPrestamo(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("Error al buscar por numTraDoc: " + e.getMessage());
            
        } finally {
            cerrar(rs);
            cerrar(ps);
            cerrar(con);
        }
        return lista;
    }

    @Override
    public List<Prestamo> buscarPorNoCon(String id) {
        String sql = "SELECT * FROM PrestamoMaterial WHERE numConAlu = ?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Prestamo> lista = new ArrayList<>();

        try {
            con = ConexionDB.getConexion();
            ps  = con.prepareStatement(sql);
            ps.setString(1, id);
            rs  = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapPrestamo(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("Error al buscar por numConAlu: " + e.getMessage());
            
        } finally {
            cerrar(rs);
            cerrar(ps);
            cerrar(con);
        }
        return lista;
    }
    
    private Prestamo mapPrestamo(ResultSet rs) {
        Prestamo p = null;
        
        try {
            p = new Prestamo();
            p.setCvePreMat(rs.getInt("cvePreMat"));
            p.setNumTraDoc(rs.getString("numTraDoc"));
            p.setNumConAlu(rs.getString("numConAlu"));
            p.setCveAdm(rs.getByte("cveAdm"));
            p.setGruAlu(rs.getString("gruAlu"));
            p.setMat(rs.getString("mat"));
            p.setObs(rs.getString("obs"));
            p.setFecSal(rs.getDate("fecSal").toLocalDate());
            if(rs.getDate("fecEnt") != null) {
                 p.setFecEnt(rs.getDate("fecEnt").toLocalDate());
            }
                       
        } catch (SQLException e) {
            System.out.println("Error al mapear el prestamo: " + e.getMessage());
        }
        return p;
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