package entidad;

public class Termino implements Comparable{
    
    private String id_termino;
    private int frecuenciaMax;
    private int cantDocumentos;

    public Termino(String id_termino, int frecuenciaMax, int cantDocumentos) {
        this.id_termino = id_termino;
        this.frecuenciaMax = frecuenciaMax;
        this.cantDocumentos = cantDocumentos;
    }

    public Termino() {
    }

    public String getId_termino() {
        return id_termino;
    }

    public void setId_termino(String id_termino) {
        this.id_termino = id_termino;
    }

    public int getFrecuenciaMax() {
        return frecuenciaMax;
    }

    public void setFrecuenciaMax(int frecuenciaMax) {
        this.frecuenciaMax = frecuenciaMax;
    }

    public int getCantDocumentos() {
        return cantDocumentos;
    }

    public void setCantDocumentos(int cantDocumentos) {
        this.cantDocumentos = cantDocumentos;
    }

    @Override
    public int compareTo(Object o) {
       Termino aux=(Termino)o;
        
        if (this.cantDocumentos<aux.getCantDocumentos()) {
            return -1;
        }
        else
        {
            if (this.cantDocumentos>aux.getCantDocumentos()) {
                return 1;
            }
            else{
                return 0;
            }
        }
    }
        
}
