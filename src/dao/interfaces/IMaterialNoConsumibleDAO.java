/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao.interfaces;

import java.util.List;
import modelo.MaterialNoConsumible;

/**
 *
 * @author Mr.Robot
 */
public interface IMaterialNoConsumibleDAO {
    List<MaterialNoConsumible> listar();
    MaterialNoConsumible buscarPorIdUTNG(String id);
    MaterialNoConsumible buscarPorIdEst(String id);
}
