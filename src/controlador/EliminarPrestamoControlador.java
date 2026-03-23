/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 *
 * @author carde
 */

import java.sql.Connection;
import conexion.ConexionDB;
import vista.prestamo.VistaListaPrestamos;
import dao.EliminarPrestamoDAO;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import controlador.ControladorVistaListaPrestamos;
import java.util.List;

public class EliminarPrestamoControlador {
    private VistaListaPrestamos vistaPre;
    private EliminarPrestamoDAO daoPre;
    private ControladorVistaListaPrestamos list;
    
    public EliminarPrestamoControlador(VistaListaPrestamos vistaPre){
        this.vistaPre = vistaPre;
    }
    
    public int extraerCvePre(VistaListaPrestamos vistaPre){
        //VistaListaPrestamos vistaPre = new VistaListaPrestamos();
        Object cve = null;
        int cvePre = 0;
        
        //Obtener fila seleccionada
        int fila = vistaPre.getTblPrestamos().getSelectedRow();
        
        //Verificar que haya fila seleccionada
        if(fila >= 0){
            cve = vistaPre.getTblPrestamos().getValueAt(fila, 0);
            cvePre = Integer.parseInt(cve.toString());
            return cvePre;
        }
        
        return cvePre;
    }
    
    public void eliminarPrestamo() throws SQLException{
        Connection conn = ConexionDB.getConexion();
        EliminarPrestamoDAO daoPre = new EliminarPrestamoDAO(conn);
        int cvePre = extraerCvePre(vistaPre);
        System.out.println("CvePrestamo: "+cvePre);
        
        cambiarDisposicionNC(cvePre);
        cambiarDisposicionC(cvePre);
        
        daoPre.eliminarPrestamo(cvePre);
        JOptionPane.showMessageDialog(vistaPre, "Prestamo eliminado completamente");
        
        ControladorVistaListaPrestamos list = new ControladorVistaListaPrestamos(vistaPre);
        list.cargarPrestamos();
        
    }
    
    public void cambiarDisposicionNC(int cvePre) throws SQLException{
        //Obtener lista actual
        Connection conn = ConexionDB.getConexion();
        EliminarPrestamoDAO eliPre = new EliminarPrestamoDAO(conn);
        List<String> dispo = eliPre.obtenerMaterialNoConsumible(cvePre);
        
        //Validar si la lista no está vacía
        if(dispo == null || dispo.isEmpty()){
            System.out.println("No se encontraron materiales para el préstamo: "+cvePre);
            return;
        }
        
        //Procesar cada material
        for(String idUTNG : dispo){
            if(idUTNG != null && !idUTNG.trim().isEmpty()){
                eliPre.cambiarDisposicionNC(idUTNG);
            }
            
        }

    }
    
    public void cambiarDisposicionC(int cvePre) throws SQLException {
        //Obtener lista actual
        Connection conn = ConexionDB.getConexion();
        EliminarPrestamoDAO eliPre = new EliminarPrestamoDAO(conn);
        List<String> dispo = eliPre.obtenerMaterialConsumible(cvePre);
        
        //Validar si la lista no está vacía
        if(dispo == null || dispo.isEmpty()){
            System.out.println("No se encontraron materiales para el préstamo: "+cvePre);
            return;
        }
        
        //Procesar cada material
        for(String cveMatCon : dispo){
            if(cveMatCon != null && !cveMatCon.trim().isEmpty()){
                eliPre.cambiarDisposicionC(cveMatCon);
            }
            
        }
    }
}
