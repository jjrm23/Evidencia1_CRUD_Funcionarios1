package model;

import java.time.LocalDate;

public class GrupoFamiliar {
    private int id;
    private int idFuncionario;
    private String nombre;
    private String parentesco;
    private LocalDate fechaNacimiento;

    public GrupoFamiliar() {}

    public GrupoFamiliar(int id, int idFuncionario, String nombre, String parentesco, LocalDate fechaNacimiento) {
        this.id = id;
        this.idFuncionario = idFuncionario;
        this.nombre = nombre;
        this.parentesco = parentesco;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdFuncionario() { return idFuncionario; }
    public void setIdFuncionario(int idFuncionario) { this.idFuncionario = idFuncionario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getParentesco() { return parentesco; }
    public void setParentesco(String parentesco) { this.parentesco = parentesco; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
}
