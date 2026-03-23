/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.ConexionDB;
import static java.lang.System.out;
import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import modelo.MatPreCon;
import modelo.MatPreNoCon;
import modelo.Prestamo;
import vista.DashBoard;

/**
 *
 * @author carde
 */
public class AgregarPrestamoMaterialDAO {
    //private final Connection conexion;
    private List<String> listaNoConsumible = new ArrayList<>();
    private List<String> listaConsumible = new ArrayList<>();
    //private DashBoard vista;
    
    public AgregarPrestamoMaterialDAO (){
    
    }
    
    public List<String> obtenerMaterialNoConsumible(String idUTNG) {
        //List<String> listaNoConsumible = new ArrayList<>();
        
        String sql = "SELECT m.idUTNG, t.nomTip FROM MaterialNoConsumible as m, TipMatNoCon as t WHERE m.idUTNG = ? AND m.tipMat = t.idTipNoCon";
        
        try (
            Connection conexion = ConexionDB.getConexion();
            PreparedStatement stmt = conexion.prepareStatement(sql)) {
  
            if(idUTNG == null){
                stmt.setNull(1, Types.VARCHAR);
                System.out.println("No idUTNG");
            } else {
                stmt.setString(1, idUTNG);
                System.out.println("[DAO] Consulta SQL: "+ stmt.toString());
            }
            
            try (ResultSet rs = stmt.executeQuery()){
                
                while (rs.next()) {
                
                String registro = rs.getString("idUTNG")+ "-" + rs.getString("nomTip");
                
                listaNoConsumible.add(registro);
                System.out.println("[DAO] Registro añadido: "+ registro);
                }
            }
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaNoConsumible;
    }
    
    public List<String> obtenerMaterialConsumible(String cveMatCon) {
        //List<String> lista = new ArrayList<>();
        String sql = "SELECT m.cveMatCon, t.nomTip FROM MaterialConsumible as m, TipMatCon as t WHERE m.cveMatCon = ? AND m.tipMat = t.idTipCon";
        
        try (
            Connection conexion = ConexionDB.getConexion();
            PreparedStatement stmt = conexion.prepareStatement(sql)) {
  
            if(cveMatCon == null){
                stmt.setNull(1, Types.VARCHAR);
                System.out.println("No cve");
            } else {
                stmt.setString(1, cveMatCon);
                System.out.println("[DAO] Consulta SQL: "+ stmt.toString());
            }
            
            try (ResultSet rs = stmt.executeQuery()){
                
                while (rs.next()) {
                
                String registro = rs.getString("cveMatCon")+ "-" + rs.getString("nomTip");
                
                listaConsumible.add(registro);
                System.out.println("[DAO] Registro añadido: "+ registro);
                }
            }
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaConsumible;
    }

    public void limpiarListaNoConsumible(){
        listaNoConsumible.clear();
    }
    
    public void limpiarListaConsumible(){
        listaConsumible.clear();
    }
    
    public void registrarPrestamo(Prestamo p, int cvePre, byte cveAdm,String numConAlu, String gruAlu, String mat, String numTraDoc) throws SQLException{     
        
        String sql = "INSERT into PrestamoMaterial (cvePreMat,numTraDoc,numConAlu,cveAdm,gruAlu,mat) VALUES (?,?,?,?,?,?)";
        //String cvePre = '';
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, cvePre);
            stmt.setString(2, numTraDoc);
            stmt.setString(3, numConAlu);
            stmt.setByte(4, cveAdm);
            stmt.setString(5, gruAlu);
            stmt.setString(6, mat);
            stmt.execute();
            
        }
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
}
