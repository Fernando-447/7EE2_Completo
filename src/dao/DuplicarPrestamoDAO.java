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

public class DuplicarPrestamoDAO {
    private final Connection conexion;
    
    private List<String> listaNoConsumible = new ArrayList<>();
    private List<String> listaConsumible = new ArrayList<>();
    private Object stmt;
    
    public DuplicarPrestamoDAO (Connection conexion){
        this.conexion = conexion;
    }
    
    public List<String> obtenerAdminNomCom() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT cveAdm, CONCAT( nomAdm,' ',apePat,' ',apeMat) AS nomCom FROM Administrador";

        try (PreparedStatement stmt = conexion.prepareStatement(sql); 
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String admin = rs.getString("cveAdm")+ "-" + rs.getString("nomCom");
                lista.add(admin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
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
        
        String sql = "SELECT pm.cvePreMat,concat(d.nom,' ',d.apePat,' ',d.apeMat) as nomCom, concat(admin.nomAdm,' ',admin.apePat,' ',admin.apeMat) as nomComAdmin,pm.numConAlu,pm.gruAlu,pm.mat,pm.obs "
                + "FROM PrestamoMaterial as pm Inner Join Docente as d On pm.numTraDoc = d.numTraDoc "
                + "Inner Join Administrador as admin On pm.cveAdm = admin.cveAdm "
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
                    p.setNomComAdm(rs.getString("nomComAdmin"));
                    System.out.println("Nombre extraído"+p.getNomComAdm());
                    p.setGruAlu(rs.getString("gruAlu"));
                    p.setMat(rs.getString("mat"));
                    p.setObs(rs.getString("obs"));
                    
                    ResultSetMetaData meta = rs.getMetaData();
                    for(int i = 1; i <= meta.getColumnCount(); i++){
                        System.out.println(i+": "+meta.getColumnName(i)+" = "+rs.getString(i));
                    }
                    
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
    
    public void registrarPrestamo(Prestamo p, int cvePre, byte cveAdm, String numConAlu, String gruAlu, String mat, String numTraDoc) throws SQLException{     
        
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
    
    public boolean existePrestamo(int cvePre) throws SQLException {
        String sql = "Select COUNT(*) FROM PrestamoMaterial WHERE cvePreMat = ?";
        
        try(Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, cvePre);
            
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1)>0;
        }
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
        //List<String> listaNoConsumible = new ArrayList<>();
        
        String sql = "SELECT m.cveMatCon, t.nomTip FROM MaterialConsumible as m, TipMatCon as t WHERE m.cveMatCon = ? AND m.tipMat = t.idTipCon";
        
        try (
            Connection conexion = ConexionDB.getConexion();
            PreparedStatement stmt = conexion.prepareStatement(sql)) {
  
            if(cveMatCon== null){
                stmt.setNull(1, Types.VARCHAR);
                
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
    
    
}
