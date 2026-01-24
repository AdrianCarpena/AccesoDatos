package Reto2.prueba;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pelicula {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	Long id;
	
	String categoria;
	String nombre;
	String director;
	
	
	
	
	public Pelicula(String categoria, String nombre, String director) {
		super();
		this.categoria = categoria;
		this.nombre = nombre;
		this.director = director;
	}
	
	public Pelicula() {}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}

	@Override
	public int hashCode() {
		return Objects.hash(director, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pelicula other = (Pelicula) obj;
		return Objects.equals(director, other.director) && Objects.equals(nombre, other.nombre);
	}

	
	
	
}
