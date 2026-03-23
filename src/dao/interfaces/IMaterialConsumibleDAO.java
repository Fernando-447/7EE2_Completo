/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao.interfaces;

import java.util.List;
import modelo.MaterialConsumible;

/**
 *
 * @author Mr.Robot
 */
public interface IMaterialConsumibleDAO {
    List<MaterialConsumible> listar();
    MaterialConsumible buscarPorIdLoc(String id);
    MaterialConsumible buscarPorCveMatCon(String id);
}
