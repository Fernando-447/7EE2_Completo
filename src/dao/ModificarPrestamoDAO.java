/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author otero
 */
//import com.sun.jdi.connect.spi.Connection;
import conexion.ConexionDB;
//import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import conexion.ConexionDB;
import static java.lang.System.out;
import java.sql.*;
import modelo.MatPreCon;
import modelo.MatPreNoCon;
import modelo.Prestamo;

public class ModificarPrestamoDAO {
    private final Connection conexion;
    
    private List<String> listaNoConsumible = new ArrayList<>();
    //private List<String> listaConsumible = new ArrayList<>();
    private Object stmt;
    
    public ModificarPrestamoDAO (Connection conexion){
    this.conexion = conexion;
}
    public List<String> obtenerMatNoCon(int cvePre){
        String sql = "Select mnc.idUTNG, tmnc.nomTip "
                + "From MatPreNoCon as mpnc Inner Join MaterialNoConsumible as mnc On mpnc.cveMatNoCon = mnc.idUTNG "
                + "Inner Join TipMatNoCon as tmnc On mnc.tipMat = tmnc.idTipNoCon "
                + "Where cvePre = ?";
     
        try (
            Connection conexion = ConexionDB.getConexion();
            PreparedStatement stmt = conexion.prepareStatement(sql)) {
  
            if(cvePre <= 0){
                stmt.setNull(1, Types.VARCHAR);
                System.out.println("No idUTNG");
            } else {
                stmt.setInt(1, cvePre);
                System.out.println("[DAO] Consulta SQL: "+ stmt.toString());
            }
            
            try (ResultSet rs = stmt.executeQuery()){
                
                while (rs.next()) {
                
                String registro = rs.getString("mnc.idUTNG")+ "-" + rs.getString("tmnc.nomTip");
                
                listaNoConsumible.add(registro);
                
                System.out.println("[DAO] Registro añadido: "+ registro);
                }
            }
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaNoConsumible;
    }
    
    public List<String> obtenerMaterialConsumible(int cvePre) {
        List<String> listaConsumible = new ArrayList<>();
        System.out.println("[DAO] Valor recivido de cvePre "+cvePre);
        String sql = "Select mc.cveMatCon, tmc.nomTip "
                + "From MatPreCon as mpc Inner Join MaterialConsumible as mc On mpc.cveMatCon = mc.cveMatCon "
                + "Inner Join TipMatCon as tmc On mc.tipMat = tmc.idTipCon "
                + "Where cvePre = ?";
        
        try (
            Connection conexion = ConexionDB.getConexion();
            PreparedStatement stmt = conexion.prepareStatement(sql)) {
  
            if(cvePre <= 0){
                stmt.setNull(1, Types.VARCHAR);
                System.out.println("No cve");
            } else {
                stmt.setInt(1, cvePre);
                System.out.println("[DAO] Consulta SQL: "+ stmt.toString());
            }
            
            try (ResultSet rs = stmt.executeQuery()){
                
                while (rs.next()) {
                
                String registro = rs.getString("mc.cveMatCon")+ "-" + rs.getString("tmc.nomTip");
                
                listaConsumible.add(registro);
                System.out.println("[DAO] Registro añadido: "+ registro);
                }
            }
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaConsumible;
    }
    
    public Prestamo extraerPrestamo(int cvePre){
        
        String sql = "SELECT pm.cvePreMat,concat(nom,' ',apePat,' ',apeMat) as nomCom, pm.numConAlu,pm.cveAdm,pm.gruAlu,pm.mat,pm.obs "
                + "FROM PrestamoMaterial as pm Inner Join Docente as d On pm.numTraDoc = d.numTraDoc "
                + "Where cvePreMat = ? ";
        
        try (
            Connection conn = ConexionDB.getConexion();
            PreparedStatement st = conn.prepareStatement(sql)){
            
            st.setInt(1, cvePre);
            
            try (ResultSet rs = st.executeQuery()){
                if (rs.next()) {
  
                    Prestamo p = new Prestamo();
                    p.setCvePreMat(cvePre);
                    p.setNomCom(rs.getString("nomCom"));
                    p.setNumConAlu(rs.getString("numConAlu"));
                    p.setCveAdm(rs.getByte("cveAdm"));
                    p.setGruAlu(rs.getString("gruAlu"));
                    p.setMat(rs.getString("mat"));
                    p.setObs(rs.getString("obs"));
                    
                    return p;
                } 
            }    
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
    
    public MatPreNoCon guardarNoConsumible(String idUTNG, int cvePre) throws SQLException{
        MatPreNoCon mpnc = new MatPreNoCon();
        System.out.println("Imprimir "+ idUTNG);
        mpnc.setCveMatNoCon(idUTNG);
        mpnc.setCvePre(cvePre);
        
        String sql = "Insert into MatPreNoCon (cveMatNoCon,cvePre) values (?,?)";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, mpnc.getCveMatNoCon().trim());
            stmt.setInt(2, mpnc.getCvePre());
            stmt.execute();
            
           System.out.append("Clave");
            return mpnc; 
        }    
      
    }
    
    public MatPreCon guardarConsumible(String cveMatCon, int cvePre) throws SQLException{
        MatPreCon mpc = new MatPreCon();
        
        mpc.setCveMatCon(cveMatCon);
        mpc.setCvePre(cvePre);
        
        System.out.println("Valor a insertar: ["+ mpc.getCveMatCon()+"], Longitud: "+ mpc.getCveMatCon().length());
        
        String sql = "Insert into MatPreCon (cveMatCon,cvePre) values (?,?)";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, mpc.getCveMatCon().trim());
            stmt.setInt(2, mpc.getCvePre());
            stmt.execute();
          
            return mpc; 
        }    
      
    }
    
    public void eliminarNoCon(String idUTNG, int cvePre) throws SQLException {
        String sql = "DELETE FROM MatPreNoCon WHERE cveMatNoCon = ? AND cvePre = ?";
        
        try(Connection conn = ConexionDB.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setString(1, idUTNG);
            stmt.setInt(2, cvePre);
            stmt.execute();
        }
    }
    
    public void eliminarCon(String cveMatCon, int cvePre) throws SQLException {
        String sql = "DELETE FROM MatPreCon WHERE cveMatCon = ? AND cvePre = ?";
        
        try(Connection conn = ConexionDB.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setString(1, cveMatCon);
            stmt.setInt(2, cvePre);
            stmt.execute();
        }
    }
    
    public void cambiarDisponibleNoCon(String idUTNG) throws SQLException {
        String sql = "UPDATE MaterialNoConsumible SET disposicion = 'Disponible' "
                + "Where idUTNG = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            //stmt.setString(1, estado);
            stmt.setString(1, idUTNG);
            stmt.execute();
            
        } 
    }
    
    public void cambiarDisponibleCon(String cveMatCon) throws SQLException {
        String sql = "UPDATE MaterialConsumible SET disposicion = 'Disponible' "
                + "Where cveMatCon = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            //stmt.setString(1, estado);
            stmt.setString(1, cveMatCon);
            stmt.execute();
            
        } 
    }
    
    public void cambiarDisposicionNC(String idUTNG) throws SQLException{
        //String estado = "\"No disponible\"";
        String sql = "UPDATE MaterialNoConsumible SET disposicion = 'No disponible' "
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
        String sql = "UPDATE MaterialConsumible SET disposicion = 'No disponible' "
                + "Where cveMatCon = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            //stmt.setString(1, estado);
            stmt.setString(1, cveMatCon);
            stmt.execute();
            
        } 
    }
    
    public boolean existeMaterialNoConsumible(String idUTNG) throws SQLException{
        String sql = "SELECT 1 FROM MaterialNoConsumible WHERE idUTNG = ?";
        
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setString(1, idUTNG);
            try(ResultSet rs = stmt.executeQuery()){
                return rs.next();
            }
            
        }
    }
    
    public void guardarCambios(int cvePre, String grupo, String mat, String obs) throws SQLException{
        String sql = "UPDATE PrestamoMaterial SET gruAlu = ?, mat = ?, obs = ? "
                + "WHERE cvePreMat = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            //stmt.setString(1, estado);
            stmt.setString(1, grupo);
            stmt.setString(2, mat);
            stmt.setString(3, obs);
            stmt.setInt(4, cvePre);
            stmt.execute();
            
        } 
    }
}
