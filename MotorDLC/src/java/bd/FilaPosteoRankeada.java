package bd;

public class FilaPosteoRankeada implements Comparable{

    private String id_termino;
    private String documento;
    private int frecuencia;
    private double peso;
    private String titulo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public FilaPosteoRankeada() {
    }

    public FilaPosteoRankeada(String id_termino, String documento, int frecuencia, double peso, String titulo) {
        this.id_termino = id_termino;
        this.documento = documento;
        this.frecuencia = frecuencia;
        this.peso=peso;
        this.titulo=titulo;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getId_termino() {
        return id_termino;
    }

    public void setId_termino(String id_termino) {
        this.id_termino = id_termino;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    public double calcularPeso(int cantDocsTerm, int cantDocsTotal)
    {
        double factorStopWord=0.3;//Si no entra al if, es una stop word y se le disminuira el peso a ese termino
        
        if ((cantDocsTerm/cantDocsTotal)<=0.6) {
            factorStopWord=1;
        }
        
        double pesoCalc=factorStopWord*(this.frecuencia*(Math.log((cantDocsTotal/cantDocsTerm))));
        return pesoCalc;
    }
    
    
    @Override
    public int compareTo(Object o) {

        FilaPosteoRankeada fr = (FilaPosteoRankeada) o;

        if (this.peso > fr.getPeso()) {
            return 1;
        } else {
            if (this.peso == fr.getPeso()) {
                return 0;
            } else {
                return -1;
            }
        }
    }


}
