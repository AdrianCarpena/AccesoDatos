package TareaRepaso;

import java.io.Serializable;

public class Libro implements Serializable{
	private static final long serialVersionUID = 1L;
	String autor;
	String fecha;
	String título;
	int copias;
	
	public Libro(String autor,String fecha,String título,int copias) {
		this.autor=autor;
		this.fecha=fecha;
		this.título=título;
		this.copias=copias;
	}

	
	public String getAutor() {
		return autor;
	}


	public void setAutor(String autor) {
		this.autor = autor;
	}


	public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	public String getTítulo() {
		return título;
	}


	public void setTítulo(String título) {
		this.título = título;
	}


	public int getCopias() {
		return copias;
	}


	public void setCopias(int copias) {
		this.copias = copias;
	}


	@Override
	public String toString() {
		return autor+";"+fecha+";"+título+";"+copias;
	}
	/*Se usa este formato de salida para que sea compatible con excel*/
	
}
