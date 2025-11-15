package model;

import java.time.LocalDate;

public class Academico {
    private int idFormacion;
    private int idFuncionario; // Clave foránea
    private String nivelAcademico;
    private String institucion;
    private LocalDate fechaGraduacion;

    public Academico() {
    }

    public Academico(int idFuncionario, String nivelAcademico, String institucion, LocalDate fechaGraduacion) {
        this.idFuncionario = idFuncionario;
        this.nivelAcademico = nivelAcademico;
        this.institucion = institucion;
        this.fechaGraduacion = fechaGraduacion;
    }

    // --- Getters y Setters ---

    public int getIdFormacion() {
        return idFormacion;
    }

    public void setIdFormacion(int idFormacion) {
        this.idFormacion = idFormacion;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNivelAcademico() {
        return nivelAcademico;
    }

    public void setNivelAcademico(String nivelAcademico) {
        this.nivelAcademico = nivelAcademico;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public LocalDate getFechaGraduacion() {
        return fechaGraduacion;
    }

    public void setFechaGraduacion(LocalDate fechaGraduacion) {
        this.fechaGraduacion = fechaGraduacion;
    }
}