package PracticaCRUD;

import java.sql.Date;

public class Alumno {

	private String dni;
    private String nombre;
    private String correo;
    private Date fechaNacimiento;
    private int idCurso;


    public Alumno(String dni, String nombre, String correo, Date fechaNacimiento, int idCurso) {
        this.dni = dni;
        this.nombre = nombre;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.idCurso = idCurso;
    }


	public String getDni() {
		return dni;
	}


	public void setDni(String dni) {
		this.dni = dni;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}


	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}


	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}


	public int getIdCurso() {
		return idCurso;
	}


	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}


	@Override
	public String toString() {
		return "Alumno [dni=" + dni + ", nombre=" + nombre + ", correo=" + correo + ", fechaNacimiento="
				+ fechaNacimiento + ", idCurso=" + idCurso + "]";
	}
    
}
