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
import dao.AltaConsumibleDAO;
import dao.TipoHerramientaConsumibleDAO;
import java.util.List;
import modelo.TipoConsumibleModelo;
import vista.Herramientas.vistaAltaHerramientaConsumible;
import vista.DashBoard;
import javax.swing.DefaultComboBoxModel;
import java.sql.SQLException;
import modelo.HerramientaConsumibleModelo;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AltaConsumibleControlador {
    private vistaAltaHerramientaConsumible vista;
    private DashBoard vista2;
    private TipoHerramientaConsumibleDAO tdao = new TipoHerramientaConsumibleDAO();
    private AltaConsumibleDAO adao;
    private List<TipoConsumibleModelo> listaTipoConsumible;
    private List<HerramientaConsumibleModelo> listaHerramientaConsumible;
    //private TipoHerramientaConsumibleDAO tipoDAO;
    
    public AltaConsumibleControlador(vistaAltaHerramientaConsumible vista){
        this.vista = vista;
    }
    
    public void cargarTipos(){
        
        try{
            //Obtener datos del DAO
            listaTipoConsumible = tdao.listar();
        
            //Verificar si hay datos
            if(listaTipoConsumible == null || listaTipoConsumible.isEmpty()){
                JOptionPane.showMessageDialog(vista,"No hay material registrado", "Advertencia", JOptionPane.WARNING_MESSAGE);
            
                return;
            }
            
            DefaultComboBoxModel<TipoConsumibleModelo> model = new DefaultComboBoxModel<>();
            for(TipoConsumibleModelo tipo : listaTipoConsumible){
            model.addElement(tipo);
            }
             vista.getCmbConsumibles().setModel(model);
             //vista2.getCmbConsumible().setModel(model);
            
        } catch (Exception e){
            manejarErrorCargaTipos(e);
        }
        
    }
    
    public void manejarErrorCargaTipos(Exception e){
        JOptionPane.showMessageDialog(vista, "Error al cargar tipos: "+ e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        
        e.printStackTrace();
    }
    
    public void registrarHerramienta(HerramientaConsumibleModelo herramienta) throws SQLException{
        
        Object seleccion = vista.getTipoConsumibleSeleccionado();
        
        if(seleccion == null || seleccion.toString().trim().isEmpty()){
            throw new IllegalArgumentException("Debe seleccionar o ingresar un tipo");
        }
        
        String nomTip;
        TipoConsumibleModelo tipo;
        
        //Caso 1: el usuario escoge un ítem existente
        if(seleccion instanceof TipoConsumibleModelo) {
            tipo = (TipoConsumibleModelo) seleccion;
        }
        
        //Caso 2: el usuario escribió texto
        else {
            nomTip = seleccion.toString().trim();
            
            //Buscar si ya existe
            tipo = buscarTipoEnLista(nomTip);
            
            //Si no existe, registrar nuevo
            if(tipo == null){
                tipo = new TipoConsumibleModelo(0, "");
                tipo.setNomTip(nomTip);
                tipo = tdao.registrarTipo(tipo);
                
                //Actualizar ComboBox
                actualizarComboBoxTipos();
            }
        }
        
        //Obtener texto del ComboBox
        String tipoIngresado = vista.getTextoComboBoxTipo();
        
        TipoConsumibleModelo seleccionado = (TipoConsumibleModelo)vista.getTipoConsumibleSeleccionado();
        if(seleccionado == null){
            throw new IllegalArgumentException("Debe seleccionar un tipo de material");
        }
        
        //Asignar el tipo a la herramienta
        herramienta.setTipMat(tipo.getIdTipCon());
        
        String estado = (String)vista.getCmbEstado().getSelectedItem();
        if(estado == null || estado.equals("Seleccione el estado")){
            throw new IllegalArgumentException("Debe seleccionar un estado válido");
        }
        
        int tipMat = seleccionado.getIdTipCon();
        //Obtener datos de la vista y asignarlos al módelo
        herramienta.setCveMatCon(vista.getCveMatCon());
        herramienta.setIdLoc(vista.getIdLocal());
        herramienta.setDesMatCon(vista.getDescripcion());
        herramienta.setEstMatCon(estado);
        herramienta.setTipMat(tipMat);
        
        try {
            validarCveMatCon(herramienta);
            
            try(
                Connection conn = ConexionDB.getConexion()){
                AltaConsumibleDAO dao = new AltaConsumibleDAO(conn);
                dao.altaConsumible(herramienta);
                JOptionPane.showMessageDialog(null, "¡Registro exitoso!");
                
            }
            
        } catch (IllegalArgumentException | SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private TipoConsumibleModelo buscarTipoEnLista(String nomTip) throws SQLException{
        //Obtener lista actual
        List<TipoConsumibleModelo> tipos = tdao.listar();
        
        //Buscar coincidencia
        for(TipoConsumibleModelo tipo : tipos){
            if(tipo.getNomTip().equalsIgnoreCase(nomTip)){
                return tipo;
            }
        }
        
        return null;
    }
    
    private TipoConsumibleModelo registrarNuevoTipo(String nomTip){
        //Validar nombre
        if(nomTip == null || nomTip.trim().isEmpty()){
            throw new IllegalArgumentException("El nombre del tipo no puede estar vacío");
        }
        
        //Crear y guardar nuevo tipo
        TipoConsumibleModelo nuevoTipo = new TipoConsumibleModelo(0, "");
        nuevoTipo.setNomTip(nomTip.trim());
        
        try {
            TipoConsumibleModelo tipoRegistrado = tdao.registrarTipo(nuevoTipo);
            
            //Actualizar ComboBox
            actualizarComboBoxTipos();
            
            return tipoRegistrado;
            
        } catch (SQLException e){
            throw new RuntimeException("Error al registriar nuevo tipo", e);
        }
    }
    
    private void actualizarComboBoxTipos() {
        try {
            List<TipoConsumibleModelo> tipos = tdao.listar();
            DefaultComboBoxModel<TipoConsumibleModelo> model = new DefaultComboBoxModel<>();
            
            for(TipoConsumibleModelo tipo : tipos) {
                model.addElement(tipo);
            }
            
            vista.getCmbConsumibles().setModel(model);
            vista.getCmbConsumibles().setSelectedItem(null);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    public void validarCveMatCon(HerramientaConsumibleModelo herramienta) throws SQLException{
       
        //Validar objeto
        if(herramienta == null){
            throw new IllegalArgumentException("La clave no puede estar vacía");
        }
        
        //Validar ID vacío
        if(herramienta.getCveMatCon() == null || herramienta.getCveMatCon().trim().isEmpty()){
            throw new IllegalArgumentException("La clave no puede estar vacía");
        }
        
        //Validar duplicado
        try(Connection conn = ConexionDB.getConexion()){
            AltaConsumibleDAO dao = new AltaConsumibleDAO(conn);
            
            if(dao.existeCveMatCon(herramienta.getCveMatCon())){
                throw new IllegalArgumentException("La clave ya está registada en el sistema");    
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw new IllegalArgumentException("Error al validar la clave en la base de datos", e);
        }
    }
}
