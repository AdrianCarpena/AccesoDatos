package PracticaCRUD;

import java.sql.Date;

public class Curso {

	private int idCurso;
    private String nombre;
    private String tutor;
    private Date fechaInicio;

    public Curso(int idCurso, String nombre, String tutor, Date fechaInicio) {
        this.idCurso = idCurso;
        this.nombre = nombre;
        this.tutor = tutor;
        this.fechaInicio = fechaInicio;
    }

	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTutor() {
		return tutor;
	}

	public void setTutor(String tutor) {
		this.tutor = tutor;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	@Override
	public String toString() {
		return "Curso [idCurso=" + idCurso + ", nombre=" + nombre + ", tutor=" + tutor + ", fechaInicio=" + fechaInicio
				+ "]";
	}
    
    
}
