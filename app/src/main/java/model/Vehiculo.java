 package model;

 public class Vehiculo {

    private String Kms;
    private String Matricula;
    private String IdConcesionario;
    /**
     * No args constructor for use in serialization
     *
     *
     */
    public Vehiculo() {
    }
    /**
     *
     * @param Kms
     * @param idConcesionario
     * @param matricula
     */

    public Vehiculo(String Kms, String matricula, String idConcesionario) {
        super();
        this.Kms = Kms;
        this.Matricula = matricula;
        this.IdConcesionario = idConcesionario;
    }

    public String getKms() {
        return Kms;
    }

    public void setKms(String kms) {
        this.Kms = kms;
    }

    public String getMatricula() {
        return Matricula;
    }

    public void setMatricula(String matricula) {
        this.Matricula = matricula;
    }

    public String getIdConcesionario() {
        return IdConcesionario;
    }

    public void setIdConcesionario(String idConcesionario) {
        this.IdConcesionario = idConcesionario;
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "Kms='" + Kms + '\'' +
                ", Matricula='" + Matricula + '\'' +
                ", IdConcesionario='" + IdConcesionario + '\'' +
                '}';
    }
}
