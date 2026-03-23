/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.impl.AdministradorDAOImpl;
import modelo.Administrador;
import vista.administrador.AdministradorDialog;
import dao.interfaces.IAdministradorDAO;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import util.fondo.PanelConFondoEscalable;

/**
 *
 * @author Mr.Robot
 */
public class ControladorAdministradorDialog {
    
    private AdministradorDialog dialogo = null;
    private Administrador administrador = null;
    private IAdministradorDAO administradorDAO = null;

    public ControladorAdministradorDialog(AdministradorDialog dialogo, Administrador administrador) {
        this.dialogo = dialogo;
        this.administrador = administrador;
        administradorDAO = new AdministradorDAOImpl();
        
        cargarFondo();
        cargarEventos();
        
        if(administrador != null) {
            dialogo.getLblTitulo().setText("Editar administrador");
            cargarDatos();
        }
    }
    
    private void cargarEventos() {
         dialogo.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarAdministrador();
            }
        });

        dialogo.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogo.dispose(); // Cerrar sin guardar
            }
        });
    }
    
    private void cargarFondo() {
        dialogo.getjPFondo().setLayout(new BorderLayout());
        dialogo.getjPFondo().add(new PanelConFondoEscalable("/util/imagenes/fondoAdministradoresDialog.jpg"), BorderLayout.CENTER);
        dialogo.getjPFondo().revalidate();
        dialogo.getjPFondo().repaint();
    }
    
    private void cargarDatos() {
        dialogo.getTxtNomAdm().setText(administrador.getNomComAdm());
        dialogo.getTxtApePatAdm().setText(administrador.getApePat());
        dialogo.getTxtApeMatAdm().setText(administrador.getApeMat());
        dialogo.getTxtNumTraAdm().setText(String.valueOf(administrador.getNumTraAdm()));
        dialogo.getTxtCorreo().setText(administrador.getCorreo());
        dialogo.getTxtContraseña().setText(administrador.getContraseña());
    }
    
    private void guardarAdministrador() {
        String nomAdm = dialogo.getTxtNomAdm().getText().trim();
        String apePatAdm = dialogo.getTxtApePatAdm().getText().trim();
        String apeMatAdm = dialogo.getTxtApeMatAdm().getText().trim();
        String numTraAdmStr = dialogo.getTxtNumTraAdm().getText().trim();
        String correo = dialogo.getTxtCorreo().getText().trim();
        String contraseña = dialogo.getTxtContraseña().getText().trim();
        
        if(nomAdm.isEmpty() || apePatAdm.isEmpty() || apeMatAdm.isEmpty() || numTraAdmStr.isEmpty() || correo.isEmpty() || contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(dialogo, "Todos los campos son obligatorios", "Validacion", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        short numTraAdm;
        
        try {
            numTraAdm = Short.parseShort(numTraAdmStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dialogo, "Número de trabajador del administrador debe ser un valor númerico valido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean exito;
        
        if(administrador == null) {
            Administrador nuevo = new Administrador();
            nuevo.setNomComAdm(nomAdm);
            nuevo.setApePat(apePatAdm);
            nuevo.setApeMat(apeMatAdm);
            nuevo.setNumTraAdm(numTraAdm);
            nuevo.setCorreo(correo);
            nuevo.setContraseña(contraseña);
            
            exito = administradorDAO.guardar(nuevo);
        } else {
            administrador.setNomComAdm(nomAdm);
            administrador.setApePat(apePatAdm);
            administrador.setApeMat(apeMatAdm);
            administrador.setNumTraAdm(numTraAdm);
            administrador.setCorreo(correo);
            administrador.setContraseña(contraseña);
            
            exito = administradorDAO.editar(administrador);
        }
        
        if(exito) {
            JOptionPane.showMessageDialog(dialogo, "Administrador guardado correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
            dialogo.dispose();
        } else {
            JOptionPane.showMessageDialog(dialogo, "Error al guardar administrador", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
