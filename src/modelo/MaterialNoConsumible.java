/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Mr.Robot
 */
public class MaterialNoConsumible {
    private String idUTNG;
    private String idEst = "No aplica";
    private String desMatNoCon = "Sin descripción";
    private String numAct;
    private String numSer = "S/N";
    private String marca;
    private String modelo;
    private short costo; 
    private EstadoMaterial estMatNoCon = EstadoMaterial.BUENO;
    private DisposicionMaterial disposicion = DisposicionMaterial.DISPONIBLE;
    private int tipMat;

    // Constructores
    public MaterialNoConsumible() {
    }

    public MaterialNoConsumible(String idUTNG, String numAct, String marca, String modelo, short costo, int tipMat) {
        this.idUTNG = idUTNG;
        this.numAct = numAct;
        this.marca = marca;
        this.modelo = modelo;
        this.costo = costo;
        this.tipMat = tipMat;
    }
    
    // Setters
    public void setIdUTNG(String idUTNG) {
        this.idUTNG = idUTNG;
    }

    public void setIdEst(String idEst) {
        this.idEst = idEst;
    }

    public void setDesMatNoCon(String desMatNoCon) {
        this.desMatNoCon = desMatNoCon;
    }

    public void setNumAct(String numAct) {
        this.numAct = numAct;
    }

    public void setNumSer(String numSer) {
        this.numSer = numSer;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setCosto(short costo) {
        this.costo = costo;
    }

    public void setEstMatNoCon(EstadoMaterial estMatNoCon) {
        this.estMatNoCon = estMatNoCon;
    }

    public void setDisposicion(DisposicionMaterial disposicion) {
        this.disposicion = disposicion;
    }

    public void setTipMat(int tipMat) {
        this.tipMat = tipMat;
    }
    
    // Getters
    public String getIdUTNG() {
        return idUTNG;
    }

    public String getIdEst() {
        return idEst;
    }

    public String getDesMatNoCon() {
        return desMatNoCon;
    }

    public String getNumAct() {
        return numAct;
    }

    public String getNumSer() {
        return numSer;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public short getCosto() {
        return costo;
    }

    public EstadoMaterial getEstMatNoCon() {
        return estMatNoCon;
    }

    public DisposicionMaterial getDisposicion() {
        return disposicion;
    }

    public int getTipMat() {
        return tipMat;
    }
    

   // Maperar estado
    public enum EstadoMaterial {
        BUENO("Bueno"),
        REGULAR("Regular"),
        DESGASTADO("Desgastado"),
        DANADO("Dañado");

        private final String valorBD;

        EstadoMaterial(String valorBD) {
            this.valorBD = valorBD;
        }

        public String getValorBD() {
            return valorBD;
        }

        public static EstadoMaterial fromBD(String valor) {
            for (EstadoMaterial estado : values()) {
                if (estado.valorBD.equals(valor)) {
                    return estado;
                }
            }
            return null;
        }
    }

    // Mapear disposición
    public enum DisposicionMaterial {
        DISPONIBLE("Disponible"),
        NO_DISPONIBLE("No disponible");

        private final String valorBD;

        DisposicionMaterial(String valorBD) {
            this.valorBD = valorBD;
        }

        public String getValorBD() {
            return valorBD;
        }

        public static DisposicionMaterial fromBD(String valor) {
            for (DisposicionMaterial d : values()) {
                if (d.valorBD.equals(valor)) {
                    return d;
                }
            }
            return null;
        }
    }
}