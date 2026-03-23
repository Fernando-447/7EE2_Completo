/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Mr.Robot
 */
public class MaterialConsumible {
    private String cveMatCon;
    private String idLoc = "No aplica";
    private String desMatCon = "Sin descripción";
    private EstadoMaterial estMatCon = EstadoMaterial.BUENO;
    private DisposicionMaterial disposicion = DisposicionMaterial.DISPONIBLE;
    private int tipMat;

    // Constructores
    public MaterialConsumible() {
    }

    public MaterialConsumible(String cveMatCon, String idLoc, String desMatCon, EstadoMaterial estMatCon, DisposicionMaterial disposicion, int tipMat) {
        this.cveMatCon = cveMatCon;
        this.idLoc = idLoc;
        this.desMatCon = desMatCon;
        this.estMatCon = estMatCon;
        this.disposicion = disposicion;
        this.tipMat = tipMat;
    }
    
    // Setters
    public void setCveMatCon(String cveMatCon) {
        this.cveMatCon = cveMatCon;
    }

    public void setIdLoc(String idLoc) {
        this.idLoc = idLoc;
    }

    public void setDesMatCon(String desMatCos) {
        this.desMatCon = desMatCos;
    }

    public void setEstMatCon(EstadoMaterial estMatCon) {
        this.estMatCon = estMatCon;
    }

    public void setDisposicion(DisposicionMaterial disposicion) {
        this.disposicion = disposicion;
    }

    public void setTipMat(int tipMat) {
        this.tipMat = tipMat;
    }
    
    //Getters
    public String getCveMatCon() {
        return cveMatCon;
    }

    public String getIdLoc() {
        return idLoc;
    }

    public String getDesMatCon() {
        return desMatCon;
    }

    public EstadoMaterial getEstMatCon() {
        return estMatCon;
    }

    public DisposicionMaterial getDisposicion() {
        return disposicion;
    }

    public int getTipMat() {
        return tipMat;
    }
        
    // Mapear estado
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
                if (estado.getValorBD().equals(valor)) {
                    return estado;
                }
            }
            return null; // o lanzar una excepción
        }
    }
     
     // Maperar disposición
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
            for (DisposicionMaterial dispo : values()) {
                if (dispo.getValorBD().equals(valor)) {
                    return dispo;
                }
            }
            return null; // o lanzar una excepción
        }
    }
}