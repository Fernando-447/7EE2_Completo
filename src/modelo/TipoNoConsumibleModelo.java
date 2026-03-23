/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author carde
 */
public class TipoNoConsumibleModelo {
    private int idTipNoCon;
    private String nomTip;

    public TipoNoConsumibleModelo(int idTipNoCon, String nomTip) {
        this.idTipNoCon = idTipNoCon;
        this.nomTip = nomTip;
    }

    public int getIdTipNoCon() {
        return idTipNoCon;
    }

    public void setIdTipNoCon(int idTipNoCon) {
        this.idTipNoCon = idTipNoCon;
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
