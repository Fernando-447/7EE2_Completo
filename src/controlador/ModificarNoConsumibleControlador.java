/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;
import DAO.*;
import dao.ModificarNoConsumibleDAO;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import vista.buscarMaterial.VistaListaMaterial;
import vista.Herramientas.vistaModificarNoConsumible;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author otero
 */

public class ModificarNoConsumibleControlador {
    //private VistaListaMaterial vista;                 //Esto fue lo Agregado 
    
    //private vistaModificarNoConsumible vistaMnc = new vistaModificarNoConsumible();
    private vistaModificarNoConsumible vistaMnc = new vistaModificarNoConsumible();
    private ModificarNoConsumibleDAO cdao = new ModificarNoConsumibleDAO();
    
    
    public ModificarNoConsumibleControlador(vistaModificarNoConsumible vistaMnc){
        this.vistaMnc = vistaMnc;
    }
    
    public String extraeridUTNG(VistaListaMaterial vistaMat){
        //VistaListaPrestamos vistaPre = new VistaListaPrestamos();
        Object mat = null;
        String idUTNG = "";
        
        //Obtener fila seleccionada
        int fila = vistaMat.getTblMaterial().getSelectedRow();
        
        //Verificar que haya fila seleccionada
        if(fila >= 0){
            mat = vistaMat.getTblMaterial().getValueAt(fila, 0);
            idUTNG = mat.toString();
            return idUTNG;
        }
        
        return idUTNG;
    }
    
    public void cargarSeleccionadoNoCon(String idUTNG){
        ModificarNoConsumibleDAO modiCon = new ModificarNoConsumibleDAO();
        
        HerramientaNoConsumibleModelo h = modiCon.extraerNoConsumible(idUTNG);
        
        vistaModificarNoConsumible vistaMNC = new vistaModificarNoConsumible();
        vistaMnc.getNomTip().setText(h.getNomTip());
        vistaMnc.getIdUTNG().setText(h.getIdUTNG());
        vistaMnc.getIdEstatal().setText(h.getIdEst());
        vistaMnc.getDescripcion().setText(h.getDesMatNoCon());
        vistaMnc.getNumActivo().setText(h.getNumAct());
        vistaMnc.getNumSerie().setText(h.getNumSer());
        vistaMnc.getMarca().setText(h.getMarca());
        vistaMnc.getModelo().setText(h.getModelo());
        
        
        String estado = h.getEstMatNoCon();
        
        switch (estado){
            case "Bueno":
            vistaMnc.getCmbEstado().setSelectedIndex(1);
            break;
            
            case "Regular":
            vistaMnc.getCmbEstado().setSelectedIndex(2);
            break;
            
            case "Desgastado":
                vistaMnc.getCmbEstado().setSelectedIndex(3);
            break;
            
            case "Dañado":
                vistaMnc.getCmbEstado().setSelectedIndex(4);
            break; 
        }
        
        vistaMnc.getCosto().setText(h.getCosto()+"");
        
    }
    
    public void modificar(String idUTNG,String desMatNoCon,String numActivo,String numSerie,String marca,String modelo,Double costo,String estado){
        ModificarNoConsumibleDAO modiNoCon = new ModificarNoConsumibleDAO();
        modiNoCon.actualizarNoCon(idUTNG, desMatNoCon, numActivo, numSerie, marca, modelo, costo, estado);
        JOptionPane.showMessageDialog(vistaMnc, "Material no consumible modificado con éxito");
        vistaMnc.dispose();
       
    }
}

