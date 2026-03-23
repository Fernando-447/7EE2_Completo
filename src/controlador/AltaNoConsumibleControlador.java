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
//import static conexion.ConexionDB.getConexion;
import dao.TipoHerramientaNoConsumibleDAO;
import java.util.List;
import modelo.TipoNoConsumibleModelo;
import vista.Herramientas.vistaAltaHerramientaNoConsumible;
import javax.swing.DefaultComboBoxModel;
import java.sql.SQLException;
import modelo.HerramientaNoConsumibleModelo;

import dao.*;
import modelo.*;
import java.awt.BorderLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AltaNoConsumibleControlador {
    private vistaAltaHerramientaNoConsumible vista;
    private TipoHerramientaNoConsumibleDAO tdao = new TipoHerramientaNoConsumibleDAO();
    private AltaNoConsumibleDAO adao;
    private List<TipoNoConsumibleModelo> listaTipoNoConsumible;
    private List<HerramientaNoConsumibleModelo> listaHerramientaNoConsumible;
    
    public AltaNoConsumibleControlador(vistaAltaHerramientaNoConsumible vista){
        this.vista = vista;
    }
    
    public void cargarTipos(){
        
        try{
            //Obtener datos del DAO
            listaTipoNoConsumible = tdao.listar();
        
            //Verificar si hay datos
            if(listaTipoNoConsumible == null || listaTipoNoConsumible.isEmpty()){
                JOptionPane.showMessageDialog(vista,"No hay material registrado", "Advertencia", JOptionPane.WARNING_MESSAGE);
            
                return;
            }
            
            DefaultComboBoxModel<TipoNoConsumibleModelo> model = new DefaultComboBoxModel<>();
            for(TipoNoConsumibleModelo tipo : listaTipoNoConsumible){
            model.addElement(tipo);
            }
             vista.getCmbNoConsumibles().setModel(model);
            
        } catch (Exception e){
            manejarErrorCargaTipos(e);
        }
        
    }
     
    public void manejarErrorCargaTipos(Exception e){
        JOptionPane.showMessageDialog(vista, "Error al cargar tipos: "+ e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        
        e.printStackTrace();
    }
    
    public void registrarHerramienta(HerramientaNoConsumibleModelo herramienta) throws SQLException{
        //String nombreTipoSeleccionado = (String)vista.getCmbNoConsumibles().getSelectedItem();
        
        try{
            
            //Obtener el tipo
            TipoNoConsumibleModelo tipo = obtenerTipoSeleccionado();
            
            //Asignar datos a la herramienta
            herramienta.setIdUTNG(vista.getIdUTNG());
            herramienta.setIdEst(vista.getIdEstatal());
            herramienta.setDesMatNoCon(vista.getDescripcion());
            herramienta.setNumAct(vista.getNumActivo());
            herramienta.setNumSer(vista.getNumSerie());
            herramienta.setMarca(vista.getMarca());
            herramienta.setModelo(vista.getModelo());
            herramienta.setCosto(vista.getCosto());
            herramienta.setTipMat(tipo.getIdTipNoCon());
            
            validarIdUTNG(herramienta);
            
            try(
                Connection conn = ConexionDB.getConexion()){
                AltaNoConsumibleDAO dao = new AltaNoConsumibleDAO(conn);
                dao.altaNoConsumible(herramienta);
                JOptionPane.showMessageDialog(null, "¡Registro exitoso!");
                
            }
            
        }catch (IllegalArgumentException | SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw e;
        }
        
    }
    
    private TipoNoConsumibleModelo buscarTipoEnLista(String nomTip) throws SQLException{
        //Obtener lista actual
        List<TipoNoConsumibleModelo> tipos = tdao.listar();
        
        //Buscar coincidencia
        for(TipoNoConsumibleModelo tipo : tipos){
            if(tipo.getNomTip().equalsIgnoreCase(nomTip)){
                return tipo;
            }
        }
        
        return null;
    }
    
    private TipoNoConsumibleModelo registrarNuevoTipo(String nomTip){
        //Validar nombre
        if(nomTip == null || nomTip.trim().isEmpty()){
            throw new IllegalArgumentException("El nombre del tipo no puede estar vacío");
        }
        
        //Crear y guardar nuevo tipo
        TipoNoConsumibleModelo nuevoTipo = new TipoNoConsumibleModelo(0, "");
        nuevoTipo.setNomTip(nomTip.trim());
        
        try {
            TipoNoConsumibleModelo tipoRegistrado = tdao.registrarTipo(nuevoTipo);
            
            //Actualizar ComboBox
            actualizarComboBoxTipos();
            
            return tipoRegistrado;
            
        } catch (SQLException e){
            throw new RuntimeException("Error al registriar nuevo tipo", e);
        }
    }
    
    private void actualizarComboBoxTipos() {
        try {
            List<TipoNoConsumibleModelo> tipos = tdao.listar();
            DefaultComboBoxModel<TipoNoConsumibleModelo> model = new DefaultComboBoxModel<>();
            
            for(TipoNoConsumibleModelo tipo : tipos) {
                model.addElement(tipo);
            }
            
            vista.getCmbNoConsumibles().setModel(model);
            vista.getCmbNoConsumibles().setSelectedItem(null);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    private TipoNoConsumibleModelo buscarTipMat(String tipMat){
        for(TipoNoConsumibleModelo tipo : listaTipoNoConsumible){
            if(tipo.getNomTip().equals(tipMat)){
                return tipo;
            }
        }
        
        return null;
    }
    
    
    public void validarIdUTNG(HerramientaNoConsumibleModelo herramienta) throws SQLException{
       
        //Validar objeto
        if(herramienta == null){
            throw new IllegalArgumentException("El ID UTNG no puede estar vacío");
        }
        
        //Validar ID vacío
        if(herramienta.getIdUTNG() == null || herramienta.getIdUTNG().trim().isEmpty()){
            throw new IllegalArgumentException("El ID UTNG no puede estar vacío");
        }
        
        //Validar duplicado
        try(Connection conn = ConexionDB.getConexion()){
            AltaNoConsumibleDAO dao = new AltaNoConsumibleDAO(conn);
            
            if(dao.existeIdUTNG(herramienta.getIdUTNG())){
                throw new IllegalArgumentException("El ID UTNG ya está registado en el sistema");    
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw new IllegalArgumentException("Error al validar el ID UTNG en la base de datos", e);
        }
    }
    
    
    public void validarIdEstatal(HerramientaNoConsumibleModelo herramienta) throws SQLException{
       
        if(herramienta == null){
            throw new IllegalArgumentException("El ID Estatal no puede estar vacío");
        }
        
        if(herramienta.getIdEst() == null || herramienta.getIdEst().trim().isEmpty()){
            throw new IllegalArgumentException("El ID Estatal no puede estar vacío");
        }
        try(Connection conn = ConexionDB.getConexion()){
            AltaNoConsumibleDAO dao = new AltaNoConsumibleDAO(conn);
            
            if(dao.existeIdEstatal(herramienta.getIdEst())){
                throw new IllegalArgumentException("El ID Estatal ya está registado en el sistema");    
            }
        }
    }
    
    public TipoNoConsumibleModelo obtenerTipoSeleccionado() throws SQLException {
        Object seleccion = vista.getCmbNoConsumibles().getSelectedItem();
        
        //Es un objeto del modelo
        if (seleccion instanceof TipoNoConsumibleModelo){
            return(TipoNoConsumibleModelo) seleccion;
        }
        
        //Es texto
        if(seleccion instanceof String){
            String texto = ((String) seleccion).trim();
            
            if(texto.isEmpty()){
                throw new IllegalArgumentException("Debe seleccionar o ingresar un tipo");
                
            }
            
            //Buscar en lista de tipos
            TipoNoConsumibleModelo tipoExistente = buscarTipoEnLista(texto);
            
            if(tipoExistente != null){
                return tipoExistente;
            }
            
            TipoNoConsumibleModelo nuevoTipo = new TipoNoConsumibleModelo (0, texto);
            nuevoTipo = tdao.registrarTipo(nuevoTipo);
            actualizarComboBoxTipos();
            return nuevoTipo;
        }
        
        throw new IllegalArgumentException("Tipo de selección no válido");
    }
}
