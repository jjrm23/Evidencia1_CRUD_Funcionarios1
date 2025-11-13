package model;

public class Academico {
    private int id;
    private int idFuncionario;
    private String universidad;
    private String nivelEstudio;
    private String titulo;

    public Academico() {}

    public Academico(int id, int idFuncionario, String universidad, String nivelEstudio, String titulo) {
        this.id = id;
        this.idFuncionario = idFuncionario;
        this.universidad = universidad;
        this.nivelEstudio = nivelEstudio;
        this.titulo = titulo;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdFuncionario() { return idFuncionario; }
    public void setIdFuncionario(int idFuncionario) { this.idFuncionario = idFuncionario; }

    public String getUniversidad() { return universidad; }
    public void setUniversidad(String universidad) { this.universidad = universidad; }

    public String getNivelEstudio() { return nivelEstudio; }
    public void setNivelEstudio(String nivelEstudio) { this.nivelEstudio = nivelEstudio; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
}
