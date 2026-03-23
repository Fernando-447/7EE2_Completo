/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.impl.AdministradorDAOImpl;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Administrador;
import util.fondo.PanelConFondo;
import util.fondo.PanelConFondoEscalable;
import vista.login.VistaLogin;
import vista.DashBoard;

/**
 *
 * @author Mr.Robot
 */
public class ControladorVistaLogin {
    private VistaLogin vista = null;
    private AdministradorDAOImpl administradorDAO = null;

    public ControladorVistaLogin(VistaLogin vista) {
        this.vista = vista;
        administradorDAO = new AdministradorDAOImpl();
        cargarFondoYIcon();
        cargarEventos();
    }
    private void cargarFondoYIcon() {
        
        // Fondo
        vista.getjPFondo().setLayout(new BorderLayout());
        vista.getjPFondo().add(new PanelConFondo("/util/imagenes/fondoLogin.jpg"), BorderLayout.CENTER);
        vista.getjPFondo().revalidate();
        vista.getjPFondo().repaint();
        
        // Icon
        vista.getjPImagenPerfil().setLayout(new BorderLayout());
        vista.getjPImagenPerfil().add(new PanelConFondoEscalable("/util/imagenes/iconPerfil.jpg"), BorderLayout.CENTER);
        vista.getjPImagenPerfil().revalidate();
        vista.getjPImagenPerfil().repaint();
    }
    
    private void cargarEventos() {
        vista.getBtnIniciarSesion().addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                iniciarSesion();
            } catch (SQLException ex) {
                Logger.getLogger(ControladorVistaLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    });
    }
    
    private void iniciarSesion() throws SQLException {
        String correo = vista.getTxtCorreo().getText();
        String contraseña = new String(vista.getjPassContraseña().getPassword());

        if(correo.isEmpty() || contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese todos los datos solicitados", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
        }
        List<Administrador> lista = administradorDAO.listar();

        for(Administrador a: lista) {
            if(correo.equals(a.getCorreo()) && contraseña.equals(a.getContraseña())) {
                JOptionPane.showMessageDialog(vista, "Acceso permitido");
                
                DashBoard pri = new DashBoard();
                pri.setVisible(true);
                vista.dispose();
                return;
            }
        }
        
        // Si se recorrió toda la lista sin encontrar coincidencia
        JOptionPane.showMessageDialog(vista, "Acceso denegado\nCorreo o contraseña incorrecta");
        vista.getjPassContraseña().setText("");
    }
}