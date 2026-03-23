/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 *
 * @author otero
 */
import conexion.ConexionDB;
import vista.DashBoard;
import dao.ModificarPrestamoDAO;
import java.sql.Connection;
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
import vista.prestamo.VistaListaPrestamos;
import vista.prestamo.VistaModificarPrestamo;

public class ModificarPrestamosControlador {
    private VistaModificarPrestamo vista;
    //private AgregarPrestamoMaterialDAO pre;
    //private String idUTNG;
    //private String cveMatCon;
    private final List<String> listaNoConsumible = new ArrayList<>();
    private final List<String> listaConsumible = new ArrayList<>();
    private Connection conexion;
    //private int cvePre;
    //private ModificarPrestamoDAO modiDAO;

    public ModificarPrestamosControlador(VistaModificarPrestamo vista){
        this.vista = vista;
    }
    
    public void cargarTodosDatos(int cvePre){
        //VistaListaPrestamos vistaPre = new VistaListaPrestamos();
        colocarPrestamoEnPantalla(cvePre);
        cargarDatosPrestamo(cvePre);
    }
    
    public void cargarDatosPrestamo(int cvePre){
        System.out.println("[Controlador] Cargando datos para cvePre"+cvePre);
        this.cargarListaPreCon(cvePre);
        this.cargarListaPreNoCon(cvePre);
    }
    
    
    public int extraerCvePre(VistaListaPrestamos vistaPre){
        Object cve = null;
        int cvePre = 0;
        
        int fila = vistaPre.getTblPrestamos().getSelectedRow();
        
        if(fila >= 0 ){
            cve = vistaPre.getTblPrestamos().getValueAt(fila, 0);
            cvePre = Integer.parseInt(cve.toString());
            return cvePre;
        }
        return cvePre;
    }
    
    public String extraerIdUTNG(VistaModificarPrestamo vistaModi){
        String idUTNG = "";
        
        int fila = vistaModi.getTblMatNoCon().getSelectedRow();
        
        if(fila >= 0 ){
            idUTNG = vistaModi.getTblMatNoCon().getValueAt(fila, 0).toString();
            
            return idUTNG;
        }
        return idUTNG;
    }
    
    public String extraerCveMatCon(VistaModificarPrestamo vistaModi){
        String cveMatCon = "";
        
        int fila = vistaModi.getTblMatCon().getSelectedRow();
        
        if(fila >= 0 ){
            cveMatCon = vistaModi.getTblMatCon().getValueAt(fila, 0).toString();
            
            return cveMatCon;
        }
        return cveMatCon;
    }
    
    public void cargarListaPreNoCon(int cvePre){
        System.out.println("[Controlador] Valor de cvePre recibido antes del dao "+cvePre);
        DefaultTableModel model = (DefaultTableModel) vista.getTblMatNoCon().getModel();
        
        ModificarPrestamoDAO modiPre = new ModificarPrestamoDAO(conexion);
        List<String> listNoConsumible = modiPre.obtenerMatNoCon(cvePre);
        
        if(listNoConsumible != null){
            for(String c : listNoConsumible){
                String [] partes = c.split("-");
            
                model.addRow(new Object []{partes[0], partes[1]});
              
                System.out.println("[Debug] Fila agregada: "+ partes[0] + "|" + partes[1]);
            }
        }
        
        vista.getTblMatNoCon().setModel(model);
        vista.getTblMatNoCon().repaint();
        
    }
    
    public void cargarListaPreCon(int cvePre){
        DefaultTableModel model = (DefaultTableModel) vista.getTblMatCon().getModel();
        model.setRowCount(0);
        
        ModificarPrestamoDAO modiPre = new ModificarPrestamoDAO(conexion);
        List<String> listConsumible = modiPre.obtenerMaterialConsumible(cvePre);
        
        if(listConsumible != null){
            for(String c : listConsumible){
                String [] partes = c.split("-");
            
                if(partes.length >= 2){
                    model.addRow(new Object []{partes[0], partes[1]});
              
                    System.out.println("[Debug] Fila agregada: "+ partes[0] + "|" + partes[1]);

                }
                            
            }
        }
        
        //vista.getTblMatNoCon().setModel(model);
        vista.getTblMatCon().repaint();
        
    }
    
    public void cargarListaNoConsumible(String idUTNG, int cvePre) throws SQLException{
        
        DefaultTableModel model = (DefaultTableModel) vista.getTblMatNoCon().getModel();
        model.setRowCount(0);
        
        if(existeNoConsumibleEnTabla(model, idUTNG)){
            JOptionPane.showMessageDialog(null, "!El ID " + idUTNG +" ya esta registrado", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        ModificarPrestamoDAO preDAO = new ModificarPrestamoDAO(conexion);
        
        List<String> nuevoNoConsumible = preDAO.obtenerMatNoCon(cvePre);
         
         
        //Llenar el modelo con los datos
        if(listaNoConsumible != null){
            for(String c : nuevoNoConsumible){
                String [] partes = c.split("-");
            
                model.addRow(new Object []{partes[0], partes[1]});
              
                
                System.out.println("[Debug] Fila agregada: "+ partes[0] + "|" + partes[1]);
            }
        }
        
        vista.getTblMatNoCon().setModel(model);
        vista.getTblMatNoCon().repaint();
        
    }
    
    public void agregarNoCon(String idUTGN, int cvePre) throws SQLException{
        ModificarPrestamoDAO preDAO = new ModificarPrestamoDAO(conexion);
        preDAO.guardarNoConsumible(idUTGN, cvePre);
        preDAO.cambiarDisposicionNC(idUTGN);
        
    }
    
    public void agregarCon(String cveMatCon, int cvePre) throws SQLException{
        ModificarPrestamoDAO preDAO = new ModificarPrestamoDAO(conexion);
        preDAO.guardarConsumible(cveMatCon, cvePre);
        preDAO.cambiarDisposicionC(cveMatCon);
    }
    
    public void cargarListaConsumible(String cveMatCon, int cvePre) throws SQLException{
        
        DefaultTableModel model = (DefaultTableModel) vista.getTblMatCon().getModel();
        model.setRowCount(0);
        
        if(existeConsumibleEnTabla(model, cveMatCon)){
            JOptionPane.showMessageDialog(null, "!La clave " + cveMatCon +" ya esta registrado", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        ModificarPrestamoDAO preDAO = new ModificarPrestamoDAO(conexion);
        
        
        List<String> nuevoConsumible = preDAO.obtenerMaterialConsumible(cvePre);
        
        //Llenar el modelo con los datos
        if(listaConsumible != null){
            for(String c : nuevoConsumible){
                String [] partes = c.split("-");
            
                model.addRow(new Object []{partes[0], partes[1]});
                
                System.out.println("[Debug] Fila agregada: "+ partes[0] + "|" + partes[1]);
            }
        }
        
        vista.getTblMatCon().setModel(model);
        vista.getTblMatCon().repaint();
       
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
    
    public void colocarPrestamoEnPantalla (int cvePre){
        ModificarPrestamoDAO modiDAO = new ModificarPrestamoDAO(conexion);
        Prestamo p = modiDAO.extraerPrestamo(cvePre);
       
        if(p == null){
            System.out.println("No se encontre el prestamo con clave: "+cvePre);
            return;
        }
            
            vista.getCvePre().setText(p.getCvePreMat()+"");
            vista.getDocente().setText(p.getNomCom());
            vista.getNumConAlu().setText(p.getNumConAlu());
            vista.getCveAdm().setText(String.valueOf(p.getCveAdm()));
            vista.getGrupo().setText(p.getGruAlu());
            vista.getMateria().setText(p.getMat());
            vista.getObs().setText(p.getObs());
    }
   
    public void eliminarNC(String idUTNG, int cvePre) throws SQLException{
        ModificarPrestamoDAO modiPre = new ModificarPrestamoDAO(conexion);
        modiPre.eliminarNoCon(idUTNG, cvePre);
        modiPre.cambiarDisponibleNoCon(idUTNG);
    }
    
    public void eliminarC(String cveMatCon, int cvePre) throws SQLException{
        ModificarPrestamoDAO modiPre = new ModificarPrestamoDAO(conexion);
        modiPre.eliminarCon(cveMatCon, cvePre);
        modiPre.cambiarDisponibleCon(cveMatCon);
    }
    
    public void guardarCambios(String grupo, String mat, String obs, int cvePre) throws SQLException{
        ModificarPrestamoDAO modiPre = new ModificarPrestamoDAO(conexion);
        modiPre.guardarCambios(cvePre, grupo, mat, obs);
        JOptionPane.showMessageDialog(vista, "Prestamo modificado exitosamente");
    }
}



