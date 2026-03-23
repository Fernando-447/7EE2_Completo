/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modulo;

import controlador.ControladorVistaLogin;
import vista.login.VistaLogin;

/**
 *
 * @author Mr.Robot
 */
public class ModuloVistaLogin {
    public static void iniciar() {
        VistaLogin vista = new VistaLogin();
        new ControladorVistaLogin(vista);
        vista.setVisible(true);
    }
}
