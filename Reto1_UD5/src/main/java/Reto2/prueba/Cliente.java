package Reto2.prueba;

import org.springframework.data.annotation.Id;

public class Cliente {

	@Id
	public String id;

	public String nombre;
	public String apellido;

	public Cliente() {}

	public Cliente(String nombre, String apellido) {
		    this.nombre = nombre;
		    this.apellido = apellido;
		  }

	@Override
	public String toString() {
		return String.format("Cliente[id=%s, nombre='%s', apellido='%s']", id, nombre, apellido);
	}
}
