/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import conexion.ConexionDB;
import java.sql.Connection;
import dao.AgregarPrestamoMaterialDAO;

/**
 *
 * @author carde
 */
import vista.DashBoard;
import dao.AgregarPrestamoMaterialDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.HerramientaConsumibleModelo;
import modelo.MatPreCon;
import modelo.MatPreNoCon;
import modelo.MaterialConsumible;
import modelo.Prestamo;

public class AgregarPrestamoControlador {
  
    private DashBoard vista;
    //private AgregarPrestamoMaterialDAO pre;
    //private String idUTNG;
    //private String cveMatCon;
    private final List<String> listaNoConsumible = new ArrayList<>();
    private final List<String> listaConsumible = new ArrayList<>();
    private ConexionDB conexion;

    public AgregarPrestamoControlador(DashBoard vista){
        this.vista = vista;
    }
    
    public void cargarListaNoConsumible(String idUTNG){
        
        DefaultTableModel model = (DefaultTableModel) vista.getTblNoConsumible().getModel();
        
        if(existeNoConsumibleEnTabla(model, idUTNG)){
            JOptionPane.showMessageDialog(null, "!El ID " + idUTNG +" ya esta registrado", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        //vista.getTblNoConsumible().getModel();
        //model.setColumnIdentifiers(new String []{"ID UTNG","Nombre"});
        
        AgregarPrestamoMaterialDAO preDAO = new AgregarPrestamoMaterialDAO();
        List<String> nuevoNoConsumible = preDAO.obtenerMaterialNoConsumible(idUTNG);
        
        //Llenar el modelo con los datos
        if(listaNoConsumible != null){
            for(String c : nuevoNoConsumible){
                String [] partes = c.split("-");
            
                model.addRow(new Object []{partes[0], partes[1]});
              
                System.out.println("[Debug] Fila agregada: "+ partes[0] + "|" + partes[1]);
            }
        }
        
        vista.getTblNoConsumible().setModel(model);
        vista.getTblNoConsumible().repaint();
        
    }
    
    public void cargarListaConsumible(String cveMatCon){
        
        DefaultTableModel model = (DefaultTableModel) vista.getTblConsumible().getModel();
        
        if(existeConsumibleEnTabla(model, cveMatCon)){
            JOptionPane.showMessageDialog(null, "!La clave " + cveMatCon +" ya esta registrado", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        //vista.getTblNoConsumible().getModel();
        //model.setColumnIdentifiers(new String []{"ID UTNG","Nombre"});
        
        AgregarPrestamoMaterialDAO preDAO = new AgregarPrestamoMaterialDAO();
        List<String> nuevoConsumible = preDAO.obtenerMaterialConsumible(cveMatCon);
        
        //Llenar el modelo con los datos
        if(listaConsumible != null){
            for(String c : nuevoConsumible){
                String [] partes = c.split("-");
            
                model.addRow(new Object []{partes[0], partes[1]});
              
                System.out.println("[Debug] Fila agregada: "+ partes[0] + "|" + partes[1]);
            }
        }
        
        vista.getTblConsumible().setModel(model);
        vista.getTblConsumible().repaint();
       
    }
    
    private boolean existeNoConsumibleEnTabla(DefaultTableModel model, String idUTNG){
        for(int i = 0; i < model.getRowCount(); i++){
            String idExistente = model.getValueAt(i, 0).toString();
            if(idExistente.equals(idUTNG)) {
               
                return true;
            }
        }
        
        return false;
    }
    
    private boolean existeConsumibleEnTabla(DefaultTableModel model, String cveMatCon){
        for(int i = 0; i < model.getRowCount(); i++){
            Object idExistente = model.getValueAt(i, 0);
            if(idExistente != null && idExistente.toString().equals(cveMatCon)) {
      
                return true;
            }
        }
        
        return false;
    }

    public void agregarPrestamo(int cvePre, byte cveAdm, String numConAlu, String gruAlu, String mat, String numTraDoc) throws SQLException{
        
        AgregarPrestamoMaterialDAO presDAO = new AgregarPrestamoMaterialDAO();
        Prestamo p = new Prestamo();
        MatPreNoCon mpnc = new MatPreNoCon();
        MatPreCon mpc = new MatPreCon();
        
        //Obtener listas
        List<String[]> noConsumibles = vista.getDatosNoConsumibles();
        List<String[]> consumibles = vista.getDatosConsumibles();
        
        //System.out.println("Valor a insertar: ["+ mpc.getCveMatCon()+"], Longitud: "+ mpc.getCveMatCon().length());
        //Crear prestamo
        presDAO.registrarPrestamo(p, cvePre, cveAdm, numConAlu, gruAlu, mat, numTraDoc);
        //int cvePre = presDAO.registrarPrestamo(p, cvePre, cveAdm, numConAlu, gruAlu, mat, numTraDoc);
        
        //Validar que haya datos MNC
        if(noConsumibles != null){
            for(String [] partes : noConsumibles){
                String idUTNG = partes[0];
                
                //presDAO.registrarPrestamo(p);
                
                presDAO.guardarNoConsumible(idUTNG, cvePre);
                presDAO.cambiarDisposicionNC(idUTNG);
                
                
            }
        } 
        
        //Validar que haya datos MNC
        if(consumibles != null){
            for(String [] item : consumibles){
                String cveMatCon = item[0];
                
                //presDAO.registrarPrestamo(p);
                
                presDAO.guardarConsumible(cveMatCon, cvePre);
                presDAO.cambiarDisposicionC(cveMatCon);
                
            }
        } 
        
        JOptionPane.showMessageDialog(null, "¡Registro exitoso!");
        AgregarPrestamoMaterialDAO preDAO = new AgregarPrestamoMaterialDAO();
        preDAO.limpiarListaConsumible();
        preDAO.limpiarListaNoConsumible();
        
        DefaultTableModel model = (DefaultTableModel) vista.getTblConsumible().getModel();
        model.setRowCount(0);
        model.setColumnCount(0);
        DefaultTableModel model2 = (DefaultTableModel) vista.getTblNoConsumible().getModel();
        model2.setRowCount(0);
        model2.setColumnCount(0);
        
    }
    
}
