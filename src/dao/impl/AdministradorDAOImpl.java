/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.impl;

import conexion.ConexionDB;
import java.util.ArrayList;
import java.util.List;
import modelo.Administrador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dao.interfaces.IAdministradorDAO;

/**
 *
 * @author Mr.Robot
 */
public class AdministradorDAOImpl implements IAdministradorDAO {
    @Override
    public List<Administrador> listar () {
        List<Administrador> lista = new ArrayList<>();
        
        String sql = "SELECT * FROM Administrador ORDER BY cveAdm";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = ConexionDB.getConexion();
            ps  = con.prepareStatement(sql);
            rs  = ps.executeQuery();
            
            while (rs.next()) {
                lista.add(mapAdministrador(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al solicitar lista de administradores:  " + e.getMessage());
        } finally {
            cerrar(rs);
            cerrar(ps);
            cerrar(con);
        }
        
        return lista;
    }
    
    @Override
    public boolean guardar(Administrador a) {
        String sql = "INSERT INTO administrador (nomAdm, apePat, apeMat, numTraAdm, correo, contraseña) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConexionDB.getConexion();
            ps  = con.prepareStatement(sql);
            ps.setString(1, a.getNomComAdm());
            ps.setString(2, a.getApePat());
            ps.setString(3, a.getApeMat());
            ps.setShort(4, a.getNumTraAdm());
            ps.setString(5, a.getCorreo());
            ps.setString(6, a.getContraseña());
            return ps.executeUpdate() == 1;
            
        } catch (SQLException e) {
            System.out.println("Error guardar(): " + e.getMessage());
            return false;
            
        } finally {
            cerrar(ps);
            cerrar(con);
        }
    }

    @Override
    public boolean editar(Administrador a) {
        String sql = "UPDATE administrador SET nomAdm = ?, apePat = ?, apeMat = ?, numTraAdm = ?, "
                   + "correo = ?, contraseña = ? WHERE cveAdm = ?";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConexionDB.getConexion();
            ps  = con.prepareStatement(sql);
            ps.setString(1, a.getNomComAdm());
            ps.setString(2, a.getApePat());
            ps.setString(3, a.getApeMat());
            ps.setShort(4, a.getNumTraAdm());
            ps.setString(5, a.getCorreo());
            ps.setString(6, a.getContraseña());
            ps.setByte(7, a.getCveAdm());
            return ps.executeUpdate() == 1;
            
        } catch (SQLException e) {
            System.out.println("Error editar(): " + e.getMessage());
            return false;
            
        } finally {
            cerrar(ps);
            cerrar(con);
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM administrador WHERE cveAdm = ?";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConexionDB.getConexion();
            ps  = con.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
            
        } catch (SQLException e) {
            System.out.println("Error eliminar(): " + e.getMessage());
            return false;
            
        } finally {
            cerrar(ps);
            cerrar(con);
        }    
    }
    
    @Override
    public Administrador buscarPorId(String num) {
    
        String sql = "SELECT * FROM Administrador Where cveAdm = ?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Administrador a = null;
        
        try {
            con = ConexionDB.getConexion();
            ps  = con.prepareStatement(sql);
            ps.setString(1, num);
            rs  = ps.executeQuery();

            if (rs.next()) {
                a = mapAdministrador(rs);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al buscar por cveAdm: " + e.getMessage());
            
        } finally {
            cerrar(rs);
            cerrar(ps);
            cerrar(con);
        }
        return a;        
    }

    @Override
    public Administrador buscarPorNoTra(String num) {
    
        String sql = "SELECT * FROM Administrador Where numTraAdm = ?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Administrador a = null;
        
        try {
            con = ConexionDB.getConexion();
            ps  = con.prepareStatement(sql);
            ps.setString(1, num);
            rs  = ps.executeQuery();

            if (rs.next()) {
                a = mapAdministrador(rs);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al buscar por número de numTraAdm: " + e.getMessage());
            
        } finally {
            cerrar(rs);
            cerrar(ps);
            cerrar(con);
        }
        return a;
    }
    
    private Administrador mapAdministrador(ResultSet rs) {
        Administrador a = null;
        
        try {
            a = new Administrador();
            a.setCveAdm(rs.getByte("cveAdm"));
            a.setNomComAdm(rs.getString("nomAdm"));
            a.setApePat(rs.getString("apePat"));
            a.setApeMat(rs.getString("apeMat"));
            a.setNumTraAdm(rs.getShort("numTraAdm"));
            a.setCorreo(rs.getString("correo"));
            a.setContraseña(rs.getString("contraseña"));
            
        } catch (SQLException e) {
            System.out.println("Error al mapear al administrador: " + e.getMessage());
        }
        return a;
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
