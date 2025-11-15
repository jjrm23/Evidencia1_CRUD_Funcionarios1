package model;

public class GrupoFamiliar {
    private int idFamiliar;
    private int idFuncionario; // Clave foránea
    private String nombreFamiliar;
    private String parentesco;

    public GrupoFamiliar() {
    }

    public GrupoFamiliar(int idFuncionario, String nombreFamiliar, String parentesco) {
        this.idFuncionario = idFuncionario;
        this.nombreFamiliar = nombreFamiliar;
        this.parentesco = parentesco;
    }

    // --- Getters y Setters ---

    public int getIdFamiliar() {
        return idFamiliar;
    }

    public void setIdFamiliar(int idFamiliar) {
        this.idFamiliar = idFamiliar;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNombreFamiliar() {
        return nombreFamiliar;
    }

    public void setNombreFamiliar(String nombreFamiliar) {
        this.nombreFamiliar = nombreFamiliar;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }
}