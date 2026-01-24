package Reto2.prueba;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioPelicula extends CrudRepository<Pelicula, Long>{
	
	
	  Optional<Pelicula> findByNombreAndDirector(String nombre, String director);

}
