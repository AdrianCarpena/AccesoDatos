package PruebasXStream;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class Libro{
	String autor;
	String fecha;
	//Esto hace que al escribir a xml con XStream sea un atributo
	@XStreamAsAttribute
	String titulo;
	
	int copias;
	
	public Libro(String autor,String fecha,String titulo,int copias) {
		this.autor=autor;
		this.fecha=fecha;
		this.titulo=titulo;
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
		return titulo;
	}


	public void setTítulo(String titulo) {
		this.titulo = titulo;
	}


	public int getCopias() {
		return copias;
	}


	public void setCopias(int copias) {
		this.copias = copias;
	}


	@Override
	public String toString() {
		return autor+";"+fecha+";"+titulo+";"+copias;
	}
	
//Constructor vacio para que funcione la lectura de XStream
	    public Libro() {
	    	
	    }
	}
