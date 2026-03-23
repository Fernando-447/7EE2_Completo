/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao.interfaces;

import java.util.List;
import modelo.Administrador;

/**
 *
 * @author Mr.Robot
 */
public interface IAdministradorDAO {
    List<Administrador> listar();
    boolean guardar(Administrador administrador);
    boolean editar(Administrador administrador);
    boolean eliminar(int id);
    Administrador buscarPorId(String num);
    Administrador buscarPorNoTra(String num);
}