/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.impl.PrestamoDAOImpl;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Prestamo;
import util.fondo.PanelConFondo;
import vista.login.VistaLogin;
import vista.prestamo.VistaListaPrestamos;

/**
 *
 * @author Mr.Robot
 */
public class ControladorVistaListaPrestamos {
    private VistaListaPrestamos vista = null;
    PrestamoDAOImpl prestamoDAO = null;

    public ControladorVistaListaPrestamos(VistaListaPrestamos vista) {
        this.vista = vista;
        prestamoDAO = new PrestamoDAOImpl();
        cargarFondo();
        cargarPrestamos();
        cargarEventos();
    }
    
    private void cargarEventos() {
        vista.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPrestamo();
            }
        });
        
        vista.getBtnCargar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarPrestamos();
            }
        });
        
        vista.getBtnVolver().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volver();
            }
        });
        
        vista.getjMICerrarSesion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });
    }
    
    private void cargarFondo() {
        vista.getjPFondo().setLayout(new BorderLayout());
        vista.getjPFondo().add(new PanelConFondo("/util/imagenes/fondoPrestamosYMaterial.jpg"), BorderLayout.CENTER);
        vista.getjPFondo().revalidate();
        vista.getjPFondo().repaint();
    }
    
    public void cargarPrestamos() {
        vista.getModeloTabla().setRowCount(0);
        
        List<Prestamo> lista = prestamoDAO.listar();
        
        for(Prestamo p: lista) {
            Object [] fila = {
                p.getCvePreMat(),
                p.getNumTraDoc(),
                p.getNumConAlu(),
                p.getCveAdm(),
                p.getGruAlu(),
                p.getMat(),
                p.getObs(),
                p.getFecSal(),
                p.getFecEnt()
            };
            vista.getModeloTabla().addRow(fila);
        }
    }
    
    private void volver() {
        vista.dispose();
    }
    
    private void cerrarSesion() {
        vista.dispose();
        VistaLogin vista = new VistaLogin();
        new ControladorVistaLogin(vista);
        vista.setVisible(true);
    }
    
    private void buscarPrestamo() {
        if(vista.getTxtNo().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese el número");
            cargarPrestamos();
            return;
        }
        
        if(vista.getjRBNoVale().isSelected()){
            vista.getModeloTabla().setRowCount(0);
            
            String id = vista.getTxtNo().getText().trim();
            
            List<Prestamo> lista = prestamoDAO.buscarPorNoVale(id);
            
            if(lista == null) {
                JOptionPane.showMessageDialog(vista, "No se encontro el prestamo");
                return;
            }
            
            for(Prestamo p: lista) {
                Object [] fila = {
                    p.getCvePreMat(),
                    p.getNumTraDoc(),
                    p.getNumConAlu(),
                    p.getCveAdm(),
                    p.getGruAlu(),
                    p.getMat(),
                    p.getObs(),
                    p.getFecSal(),
                    p.getFecEnt()
                };
                
                vista.getModeloTabla().addRow(fila);
            }
                        
        } else if(vista.getjRBNoControl().isSelected()) {
            vista.getModeloTabla().setRowCount(0);
            
            String id = vista.getTxtNo().getText();
            
            List<Prestamo> lista = prestamoDAO.buscarPorNoCon(id);
            
            if(lista == null) {
                JOptionPane.showMessageDialog(vista, "No se encontro el prestamo");
                return;
            }
            
            for(Prestamo p: lista) {
                Object [] fila = {
                    p.getCvePreMat(),
                    p.getNumTraDoc(),
                    p.getNumConAlu(),
                    p.getCveAdm(),
                    p.getGruAlu(),
                    p.getMat(),
                    p.getObs(),
                    p.getFecSal(),
                    p.getFecEnt()
                };
                
                vista.getModeloTabla().addRow(fila);
            }
            
        } else if(vista.getjRBNoTrabajador().isSelected()) {
            vista.getModeloTabla().setRowCount(0);
            
            String id = vista.getTxtNo().getText();
            
            List<Prestamo> lista = prestamoDAO.buscarPorNoTra(id); 
            
            if(lista == null) {
                JOptionPane.showMessageDialog(vista, "No se encontro el prestamo");
                return;
            }
            
            for(Prestamo p: lista) {
                Object [] fila = {
                    p.getCvePreMat(),
                    p.getNumTraDoc(),
                    p.getNumConAlu(),
                    p.getCveAdm(),
                    p.getGruAlu(),
                    p.getMat(),
                    p.getObs(),
                    p.getFecSal(),
                    p.getFecEnt()
                };
                
                vista.getModeloTabla().addRow(fila);
            }
            
        } else {
            JOptionPane.showMessageDialog(vista, "Seleccione un tipo de número");
        }
    }
}