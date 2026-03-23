/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import conexion.ConexionDB;
import java.sql.SQLException;
import dao.BajaDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.MaterialNoConsumible;
import vista.buscarMaterial.VistaListaMaterial;

/**
 *
 * @author carde
 */
public class BajaControlador {
    private final VistaListaMaterial vista;
    private final BajaDAO herramientaDAO;
    
    public BajaControlador(VistaListaMaterial vista) throws SQLException{
        this.vista = vista;
        this.herramientaDAO = new BajaDAO(ConexionDB.getConexion());
        configurarEventos();
    }
    
    private void configurarEventos(){
        vista.getBtnEliminar().addActionListener(e -> eliminarMaterial());
    }
    
    public void eliminarMaterial(){
        try {
            if(vista.isConsumibleSelected()){
                eliminarConsumible();
            } else if(vista.isNoConsumibleSelected()){
                eliminarNoConsumible();
            } else {
                vista.mostrarMensaje("Seleccione un tipo de material");
            }
        } catch (SQLException ex){
            vista.mostrarMensaje("Error al eliminar: "+ex.getMessage());
        }
    }
    
    private void eliminarConsumible() throws SQLException {
        String clave = vista.getClaveSeleccionada();
        if(clave == null){
            vista.mostrarMensaje("Seleccione un material de la tabla");
            return;
        }
        
        vista.mostrarConfirmacionEliminar(() -> {
            try {
                
                boolean eliminado = herramientaDAO.eliminarConsumible(clave);
                    if(eliminado){
                        vista.mostrarMensaje("Consumible eliminado correctamente");
                        actualizarTabla();
                    }
                
            } catch (SQLException e){
                vista.mostrarMensaje("Error al eliminar consumible: "+ e.getMessage());
            }
        });
    }
    
    private void eliminarNoConsumible() throws SQLException {
        String idUTNG = vista.getClaveSeleccionada();
        if(idUTNG == null){
            vista.mostrarMensaje("Seleccione un material de la tabla");
            System.out.println("Clave enviada para eliminar: "+ idUTNG + "'");

            return;
        }
        
        vista.mostrarConfirmacionEliminar(() -> {
            try {
                boolean eliminado = herramientaDAO.eliminarNoConsumible(idUTNG);
                if(eliminado){
                    vista.mostrarMensaje("No consumible eliminado correctamente");
                    actualizarTabla();
                }
            } catch (SQLException e){
                vista.mostrarMensaje("Error al eliminar no consumible: "+ e.getMessage());
            }
        });
    }
    
    private void actualizarTabla(){
        //Pendiente
    }
    
}
