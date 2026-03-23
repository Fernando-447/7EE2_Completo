/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 *
 * @author carde
 */
import conexion.ConexionDB;
import dao.DevolucionPrestamoDAO;
import dao.EliminarPrestamoDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import vista.prestamo.VistaListaPrestamos;

public class DevolucionPrestamoControlador {
    private VistaListaPrestamos vista;
    private EliminarPrestamoDAO daoPre;
    private ControladorVistaListaMaterial list;
    
    public DevolucionPrestamoControlador(VistaListaPrestamos vista){
        this.vista = vista;
    }
    
    public int extraerCvePre(VistaListaPrestamos vista){
        //VistaListaPrestamos vistaPre = new VistaListaPrestamos();
        Object cve = null;
        int cvePre = 0;
        
        //Obtener fila seleccionada
        int fila = vista.getTblPrestamos().getSelectedRow();
        
        //Verificar que haya fila seleccionada
        if(fila >= 0){
            cve = vista.getTblPrestamos().getValueAt(fila, 0);
            cvePre = Integer.parseInt(cve.toString());
            return cvePre;
        }
        
        return cvePre;
    }
    
    public String extraerFecha(){
        LocalDateTime ahora = LocalDateTime.now();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaHoraFormateada = ahora.format(formatter);
        
        return fechaHoraFormateada;
    }
    
    public void regresarPrestamo(int cvePre, String fecha) throws SQLException{
        Connection conn = ConexionDB.getConexion();
        DevolucionPrestamoDAO daoPre = new DevolucionPrestamoDAO(conn);
        
        cambiarDisposicionNC(cvePre);
        cambiarDisposicionC(cvePre);
        
        daoPre.devolucionPrestamo(cvePre,fecha);
        JOptionPane.showMessageDialog(vista, "Prestamo devuelto");
        
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
