/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.impl.AdministradorDAOImpl;
import dao.interfaces.IAdministradorDAO;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Administrador;
import util.fondo.PanelConFondo;
import vista.administrador.AdministradorDialog;
import vista.administrador.VistaListaAdministradores;
import vista.login.VistaLogin;

/**
 *
 * @author Mr.Robot
 */
public class ControladorVistaListaAdministradores {
    VistaListaAdministradores vista = null;
    IAdministradorDAO administradorDAO = null;

    public ControladorVistaListaAdministradores(VistaListaAdministradores vista) {
        this.vista = vista;
        administradorDAO = new AdministradorDAOImpl();
        cargarFondo();
        cargarAdministradores();
        cargarEventos();
    }
    
    private void cargarEventos() {
        vista.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarAdministrador();
            }
        });
        
        vista.getBtnAgregar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarAdministrador();
            }
        });

        vista.getBtnEditar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarAdministrador();
            }
        });

        vista.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarAdministrador();
            }
        });
        
        vista.getBtnCargar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarAdministradores();
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
        vista.getjPFondo().add(new PanelConFondo("/util/imagenes/fondoAdministradores.jpg"), BorderLayout.CENTER);
        vista.getjPFondo().revalidate();
        vista.getjPFondo().repaint();
    }
    
    private void cargarAdministradores() {
        vista.getModeloTabla().setRowCount(0);
        
        List<Administrador> lista = administradorDAO.listar();
        
        for(Administrador a: lista) {
            Object [] fila = {
                a.getCveAdm(),
                a.getNomComAdm(),
                a.getApePat(),
                a.getApeMat(),                
                a.getNumTraAdm(),
                a.getCorreo(),
                a.getContraseña()
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
    
    private void buscarAdministrador() {
        if(vista.getTxtId().getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(vista, "Ingrese el ID");
            cargarAdministradores();
            return;
        }
        
        if(vista.getjRBId().isSelected()) {
            vista.getModeloTabla().setRowCount(0);
            
            String id = vista.getTxtId().getText().trim();
            
            Administrador a = administradorDAO.buscarPorId(id);
            
            if(a == null) {
                JOptionPane.showMessageDialog(vista, "No se encontro el Administrador");
            }
            
            Object [] fila = {
                a.getCveAdm(),
                a.getNomComAdm(),
                a.getApePat(),
                a.getApeMat(),                
                a.getNumTraAdm(),
                a.getCorreo(),
                a.getContraseña()
            };
            
            vista.getModeloTabla().addRow(fila);
        } else if(vista.getjRBNoTrabajadorAdministrador().isSelected()) {
            vista.getModeloTabla().setRowCount(0);
            
            String id = vista.getTxtId().getText().trim();
            
            Administrador a = administradorDAO.buscarPorNoTra(id);
            
            if(a == null) {
                JOptionPane.showMessageDialog(vista, "No se encontro el Administrador");
            }
            
            Object [] fila = {
                a.getCveAdm(),
                a.getNomComAdm(),
                a.getApePat(),
                a.getApeMat(),                
                a.getNumTraAdm(),
                a.getCorreo(),
                a.getContraseña()
            };
            
            vista.getModeloTabla().addRow(fila);
        } else {
            JOptionPane.showMessageDialog(vista, "Seleccione un tipo de id");
        }
    }
    
    private void agregarAdministrador() {
        AdministradorDialog dialogo = new AdministradorDialog(vista, true);
        new ControladorAdministradorDialog(dialogo, null);
        dialogo.setVisible(true);
        cargarAdministradores();
    }
    
    private void editarAdministrador() {
    int fila = vista.getTblAdministradores().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Selecciona un administrador");
            return;
        }
        
        String idAdministrador = vista.getTblAdministradores().getValueAt(fila, 0).toString();
        
        Administrador administrador = administradorDAO.buscarPorId(idAdministrador);
        
        if(administrador == null) {
            JOptionPane.showMessageDialog(vista, "No se pudo encontrar el administrador en la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        AdministradorDialog dialogo = new AdministradorDialog(vista, true);
        new ControladorAdministradorDialog(dialogo, administrador);
        dialogo.setVisible(true);
        cargarAdministradores();
    }
    
    private void eliminarAdministrador() {
        int fila = vista.getTblAdministradores().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Selecciona un administrador");
            return;
        }

        int id = ((Number) vista.getTblAdministradores().getValueAt(fila, 0)).intValue();
        int confirm = JOptionPane.showConfirmDialog(vista, "¿Eliminar administrador?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean eliminado = administradorDAO.eliminar(id);
            if (eliminado) {
                JOptionPane.showMessageDialog(vista, "Administrador eliminado");
                cargarAdministradores();
            }
            else {
                JOptionPane.showMessageDialog(vista, "No se pudo eliminar el administrador");
            }
        }
    }
}
