/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao.interfaces;

import java.util.List;
import modelo.Prestamo;

/**
 *
 * @author Mr.Robot
 */
public interface IPrestamoDAO {
    List<Prestamo> listar();
    List<Prestamo> buscarPorNoVale(String id);
    List<Prestamo> buscarPorNoCon(String id);
    List<Prestamo> buscarPorNoTra(String id);
}
