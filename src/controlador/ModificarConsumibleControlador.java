/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 *
 * @author carde
 */
import DAO.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
//import modelo.*;
import vista.buscarMaterial.VistaListaMaterial;
import vista.Herramientas.vistaModificarConsumible;
import modelo.HerramientaConsumibleModelo;
import modelo.TipoConsumibleModelo;
import dao.ModificarConsumibleDAO;
import dao.TipoHerramientaConsumibleDAO;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class ModificarConsumibleControlador {
    //private VistaListaMaterial vista;
    private vistaModificarConsumible vistaMc = new vistaModificarConsumible();
    private ModificarConsumibleDAO cdao = new ModificarConsumibleDAO();
    
    public ModificarConsumibleControlador(vistaModificarConsumible vistaMc){
        this.vistaMc = vistaMc;
    }
    
    public String extraerCveMatCon(VistaListaMaterial vistaMat){
        //VistaListaPrestamos vistaPre = new VistaListaPrestamos();
        Object mat = null;
        String cveMatCon = "";
        
        //Obtener fila seleccionada
        int fila = vistaMat.getTblMaterial().getSelectedRow();
        
        //Verificar que haya fila seleccionada
        if(fila >= 0){
            mat = vistaMat.getTblMaterial().getValueAt(fila, 0);
            cveMatCon = mat.toString();
            return cveMatCon;
        }
        
        return cveMatCon;
    }
    
    public void cargarSeleccionadoCon(String cveMatCon){
        ModificarConsumibleDAO modiCon = new ModificarConsumibleDAO();
        
        HerramientaConsumibleModelo h = modiCon.extraerConsumible(cveMatCon);
        
        vistaModificarConsumible vistaMC = new vistaModificarConsumible();
        
        vistaMc.getCveMatCon().setText(h.getCveMatCon());
        vistaMc.getIdLocal().setText(h.getIdLoc());
        vistaMc.getDescripcion().setText(h.getDesMatCon());
        //vistaMc.getCmbEstado().setSelectedItem(h.setEstMatCon(c);
        vistaMc.getNomTip().setText(h.getNomTip());
        
        String estado = h.getEstMatCon();
        
        switch (estado){
            case "Bueno":
            vistaMc.getCmbEstado().setSelectedIndex(1);
            break;
            
            case "Regular":
            vistaMc.getCmbEstado().setSelectedIndex(2);
            break;
            
            case "Desgastado":
                vistaMc.getCmbEstado().setSelectedIndex(3);
            break;
            
            case "Dañado":
                vistaMc.getCmbEstado().setSelectedIndex(4);
            break; 
        }
    }
    
    public void modificar(String cveMatCon,String idLoc, String estado, String desc){
        ModificarConsumibleDAO modiCon = new ModificarConsumibleDAO();
        modiCon.actualizarCon(cveMatCon,idLoc, estado, desc);
        JOptionPane.showMessageDialog(vistaMc, "Material consumible modificado con éxito");
        vistaMc.dispose();
        
    }
}

