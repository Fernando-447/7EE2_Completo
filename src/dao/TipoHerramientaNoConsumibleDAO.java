/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.ConexionDB;
import java.util.List;
import java.util.ArrayList;
import modelo.TipoNoConsumibleModelo;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author carde
 */
public class TipoHerramientaNoConsumibleDAO {
    private ConexionDB conn = new ConexionDB();
    

    // Método para obtener los tipos de herramientas no consumibles
    public List<TipoNoConsumibleModelo> listar() throws SQLException {
        List<TipoNoConsumibleModelo> lista = new ArrayList<>();

        // Consulta
        String sql = "SELECT * "
                + "FROM TipMatNoCon; ";

        try (
            Connection conn = ConexionDB.getConexion();
            
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery())
            {
            
                while (rs.next()){
                    
                    lista.add(new TipoNoConsumibleModelo(
                            rs.getInt("idTipNoCon"),
                            rs.getString("nomTip")
                    ));
                    
   
                }
                
        } catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar tipos: "+ e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
        }
                
        System.out.println("Registros encontrados: " + lista.size());
        lista.forEach(tipo -> System.out.println("ID: " + tipo.getIdTipNoCon() + ", Nombre: " + tipo.getNomTip()));
        return lista;
    }
    
    public TipoNoConsumibleModelo registrarTipo(TipoNoConsumibleModelo nuevoTipo) throws SQLException {
        String sql = "INSERT INTO TipMatNoCon (nomTip) VALUES (?)";
        
        try (Connection conn = ConexionDB.getConexion();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nuevoTipo.getNomTip());
            stmt.execute();
            
            //Obtener el ID generado automáticamente
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()){
                    nuevoTipo.setIdTipNoCon(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("No se pudo obtener el ID generado");
                }
            }
            
            return nuevoTipo;
        }
    }
}
