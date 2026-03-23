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
import dao.DuplicarPrestamoDAO;
import vista.DashBoard;
import dao.ModificarPrestamoDAO;
import java.awt.Container;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.HerramientaConsumibleModelo;
import modelo.MatPreCon;
import modelo.MatPreNoCon;
import modelo.MaterialConsumible;
import modelo.Prestamo;
import vista.prestamo.VistaDuplicarPrestamo;
import vista.prestamo.VistaListaPrestamos;

public class DuplicarPrestamoControlador {
    private VistaDuplicarPrestamo vista;
    private final List<String> listaNoConsumible = new ArrayList<>();
    private final List<String> listaConsumible = new ArrayList<>();
    private Connection conexion;
    
    public DuplicarPrestamoControlador(VistaDuplicarPrestamo vista){
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
    
    public String extraerIdUTNG(VistaDuplicarPrestamo vistaModi){
        String idUTNG = "";
        
        int fila = vistaModi.getTblMatNoCon().getSelectedRow();
        
        if(fila >= 0 ){
            idUTNG = vistaModi.getTblMatNoCon().getValueAt(fila, 0).toString();
            
            return idUTNG;
        }
        return idUTNG;
    }
    
    public String extraerCveMatCon(VistaDuplicarPrestamo vistaModi){
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
        
        String docenteAsignado = p.getNomCom();
        String admin = p.getNomComAdm();
        System.out.println("Variable admin: "+admin);
        
            vista.getCvePre().setText(p.getCvePreMat()+"");
            vista.getCmbDocente().setSelectedItem(docenteAsignado);
            vista.getNumConAlu().setText(p.getNumConAlu());
            vista.getCmbAdmin().setSelectedItem(admin);
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
    
    public void agregarPrestamo(int cvePre, byte cveAdm, String numConAlu, String gruAlu, String mat, String numTraDoc, String obs) throws SQLException{
        DuplicarPrestamoDAO presDAO = new DuplicarPrestamoDAO(conexion);
        boolean existe = presDAO.existePrestamo(cvePre);
        
        if(existe == true){
            JOptionPane.showMessageDialog(vista, 
                    "Error: La clave de préstamo ya existe. \nPor favor, ingrese una clave única.",
                    "Clave duplicada",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
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

        vista.setVisible(false);
        
    }
    
    public void cargarListaNoConsumible(String idUTNG){
        
        DefaultTableModel model = (DefaultTableModel) vista.getTblMatNoCon().getModel();
        
        if(existeNoConsumibleEnTabla(model, idUTNG)){
            JOptionPane.showMessageDialog(null, "!El ID " + idUTNG +" ya esta registrado", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        //vista.getTblNoConsumible().getModel();
        //model.setColumnIdentifiers(new String []{"ID UTNG","Nombre"});
        
        DuplicarPrestamoDAO preDAO = new DuplicarPrestamoDAO(conexion);
        List<String> nuevoNoConsumible = preDAO.obtenerMaterialNoConsumible(idUTNG);
        System.out.println("Modelo en controlador: "+model);
        System.out.println("Modelo en vista: "+vista.getTblMatNoCon().getModel());
        
        //Llenar el modelo con los datos
        if(nuevoNoConsumible != null && !nuevoNoConsumible.isEmpty()){
            String c = nuevoNoConsumible.get(0);
            String [] partes = c.split("-");
            
            if(partes.length >=2 ){
                    model.addRow(new Object []{partes[0], partes[1]});
              
                    System.out.println("[Debug] Fila agregada: "+ partes[0] + "|" + partes[1]);
                
                }
            
            
            
        } else {
            JOptionPane.showMessageDialog(vista, "El ID aun no esta dado de alta en la base de datos");
        }
     
        
    } 
   
    public void cargarListaConsumible(String cveMatCon){
        
        DefaultTableModel model = (DefaultTableModel) vista.getTblMatCon().getModel();
        
        if(existeConsumibleEnTabla(model, cveMatCon)){
            JOptionPane.showMessageDialog(null, "!El ID " + cveMatCon +" ya esta registrado", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        
        DuplicarPrestamoDAO preDAO = new DuplicarPrestamoDAO(conexion);
        List<String> nuevoConsumible = preDAO.obtenerMaterialConsumible(cveMatCon);
        System.out.println("Modelo en controlador: "+model);
        System.out.println("Modelo en vista: "+vista.getTblMatNoCon().getModel());
        
        //Llenar el modelo con los datos
        if(nuevoConsumible != null && !nuevoConsumible.isEmpty()){
            String c = nuevoConsumible.get(0);
            String [] partes = c.split("-");
            
            if(partes.length >=2 ){
                    model.addRow(new Object []{partes[0], partes[1]});
              
                    System.out.println("[Debug] Fila agregada: "+ partes[0] + "|" + partes[1]);
                
                }

        } else {
            JOptionPane.showMessageDialog(vista, "El ID aun no esta dado de alta en la base de datos");
        }
     
        
    }
    
    public void eliminarNoCon(){
        DefaultTableModel model = (DefaultTableModel) vista.getTblMatNoCon().getModel();
        int filaSeleccionada = vista.getTblMatNoCon().getSelectedRow();
        
        if(filaSeleccionada != -1){
            model.removeRow(filaSeleccionada);
        } else {
            JOptionPane.showMessageDialog(vista, "Seleccione el material a eliminar");
        }
    }
    
    public void eliminarCon(){
        DefaultTableModel model = (DefaultTableModel) vista.getTblMatCon().getModel();
        int filaSeleccionada = vista.getTblMatCon().getSelectedRow();
        
        if(filaSeleccionada != -1){
            model.removeRow(filaSeleccionada);
        } else {
            JOptionPane.showMessageDialog(vista, "Seleccione el material a eliminar");
        }
    }
   
}



