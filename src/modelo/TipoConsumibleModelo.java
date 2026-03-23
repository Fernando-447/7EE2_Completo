









/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author carde
 */
public class TipoConsumibleModelo {
    private int idTipCon;
    private String nomTip;

    public TipoConsumibleModelo(int idTipCon, String nomTip) {
        this.idTipCon = idTipCon;
        this.nomTip = nomTip;
    }

    public int getIdTipCon() {
        return idTipCon;
    }

    public void setIdTipCon(int idTipCon) {
        this.idTipCon = idTipCon;
    }

    public String getNomTip() {
        return nomTip;
    }

    public void setNomTip(String nomTip) {
        this.nomTip = nomTip;
    }
    
    @Override
    public String toString(){
        return this.nomTip;
    }
}
