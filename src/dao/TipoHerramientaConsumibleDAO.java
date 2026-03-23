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
import java.util.List;
import java.util.ArrayList;
import modelo.TipoConsumibleModelo;
import java.sql.*;
import javax.swing.JOptionPane;
import modelo.TipoNoConsumibleModelo;


public class TipoHerramientaConsumibleDAO {
    //private ConexionDB conn = new ConexionDB();
    ConexionDB conexionDB = new ConexionDB();
    Connection conexion = conexionDB.getConexion();
    
    //Método para obtener los tipos de herramientas consumibles 

    public List<TipoConsumibleModelo> listar() throws SQLException {
        List<TipoConsumibleModelo> lista = new ArrayList<>();
        
        //Consulta
        String sql = "SELECT * "
                + "FROM TipMatCon";
        
        try (
            Connection conn = ConexionDB.getConexion();
            
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery())
            {
            
                while (rs.next()){
                    
                    lista.add(new TipoConsumibleModelo(
                            rs.getInt("idTipCon"),
                            rs.getString("nomTip")
                    ));
                    
   
                }
                
        } catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar tipos: "+ e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
        }
                
        System.out.println("Registros encontrados: " + lista.size());
        lista.forEach(tipo -> System.out.println("ID: " + tipo.getIdTipCon() + ", Nombre: " + tipo.getNomTip()));
        return lista;
        
        
    }
  
    public TipoConsumibleModelo registrarTipo(TipoConsumibleModelo nuevoTipo) throws SQLException {
        String sql = "INSERT INTO TipMatCon (nomTip) VALUES (?)";
        
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nuevoTipo.getNomTip());
            stmt.execute();
            
            //Obtener el ID generado automáticamente
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()){
                    nuevoTipo.setIdTipCon(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("No se pudo obtener el ID generado");
                }
            }
            
            return nuevoTipo;
        }
    }
    
}
