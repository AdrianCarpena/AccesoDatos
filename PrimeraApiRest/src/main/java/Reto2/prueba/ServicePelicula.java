package Reto2.prueba;

import java.util.List;
import java.util.Optional;


@org.springframework.stereotype.Service
public class ServicePelicula {

    private final RepositorioPelicula repositorio;
    

    public ServicePelicula(RepositorioPelicula repositorio) {
        this.repositorio = repositorio;
    }
    

    public List<Pelicula> findAll() {
        return (List<Pelicula>) repositorio.findAll();
    }

    public Optional<Pelicula> findById(Long id) {
        return repositorio.findById(id);
    }

    public Optional<Pelicula> findDuplicada(Pelicula pelicula) {
        return repositorio.findByNombreAndDirector(
                pelicula.getNombre(), pelicula.getDirector());
    }

    public Pelicula save(Pelicula pelicula) {
        return repositorio.save(pelicula);
    }

    public void delete(Pelicula pelicula) {
        repositorio.delete(pelicula);
    }
}