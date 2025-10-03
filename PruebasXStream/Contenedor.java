package PruebasXStream;

import java.util.List;


public class Contenedor {
    private List<Libro> libros;
    
    public Contenedor(List<Libro> libros) {
        this.libros = libros;
    }

    public Contenedor() {} 

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }
}