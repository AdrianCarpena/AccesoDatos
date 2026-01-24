package Reto2.prueba;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/peliculas")
public class ControladorPelicula {
	
	ServicePelicula servicePelicula;
	
	public ControladorPelicula(ServicePelicula servicePelicula) {
		this.servicePelicula=servicePelicula;
		//repositorioPelicula.save(new Pelicula("drama", "Los lunes del sol", "Amenabar"));
		//repositorioPelicula.save(new Pelicula("comedia", "El gran lebowsky", "Hermanos Coher"));
	}
	
	@GetMapping
    public ResponseEntity<List<Pelicula>> getAll() {
		//Indico que devuelva ok como código y llame al método findAll de servicePelicula.
        return ResponseEntity.ok(servicePelicula.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pelicula> getOne(@PathVariable("id") long id) {
    	
    	//Obtengo la película en caso de haber una con ese id, de lo contrario será null
    	Optional<Pelicula> peliculaOpt = servicePelicula.findById(id);
    	
    	//Dependiendo de si existe devuelvo una respuesta u otra
    	if (peliculaOpt.isPresent()) {
    	    return ResponseEntity.ok(peliculaOpt.get());
    	} else {
    	    return ResponseEntity.notFound().build();
    	}
    }
    

    @PostMapping
    public ResponseEntity<Pelicula> postPelicula(@RequestBody Pelicula pelicula) {

    	//Buscamos la pelicula con optional por si no existe
        Optional<Pelicula> duplicada= servicePelicula.findDuplicada(pelicula);
        
        //Si la pelúcla ya existe el codigo de respuesta sera 400 BadRequest y no hara nada
        if(duplicada.isPresent()) {
        	return ResponseEntity.badRequest().build();
        }
        
        //Si no existe la creo con .save y retorno un codigo respuesta created con la peli creada
        Pelicula creada=servicePelicula.save(pelicula);

        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<Pelicula> putOne(@PathVariable("id") long id, @RequestBody Pelicula peliculaNueva) {

        Optional<Pelicula> existenteOptional=servicePelicula.findById(id);

        if (existenteOptional.isEmpty()) {
        	return ResponseEntity.notFound().build();
        }
        //Si la pelicula existe, recuperamos la peli con get y la asignamos los valores que se pasaron. Después la actualizamos y devolvemos codigo ok
        Pelicula existente = existenteOptional.get();
        existente.setCategoria(peliculaNueva.getCategoria());
        existente.setNombre(peliculaNueva.getNombre());
        existente.setDirector(peliculaNueva.getDirector());
        
        Pelicula actualizada=servicePelicula.save(existente);
        return ResponseEntity.ok(actualizada);
        
    }
    
    

    @DeleteMapping("/{id}")
    public ResponseEntity<Pelicula> deleteOne(@PathVariable("id") long id) {
    	
    	Optional<Pelicula> peliculaOpt=servicePelicula.findById(id);
    	
    	if(peliculaOpt.isEmpty()) {
    		return ResponseEntity.notFound().build();
    	}
    	
    	//En caso de que exista una peli con ese id, recuperamos la pelic on get y la borramos. El codigo de respuesta será ok y se mostrará la peli creada
    	Pelicula pelicula=peliculaOpt.get();
    	servicePelicula.delete(pelicula);
    	
    	 return ResponseEntity.ok(pelicula);
        
    }
    
    
    
    @PatchMapping("/{id}")
    public ResponseEntity<Pelicula> patchOne(@PathVariable("id") long id, @RequestBody Pelicula peliculaParcial) {

        Optional<Pelicula> existenteOpt= servicePelicula.findById(id);

        if(existenteOpt.isEmpty()) {
        	return ResponseEntity.notFound().build();
        }
        
        //Si existe la peli la obtenemos y cambiamos los atrubutos que se hayan puesto
        Pelicula pelicula=existenteOpt.get();

        if (peliculaParcial.getCategoria() != null) {
            pelicula.setCategoria(peliculaParcial.getCategoria());
        }

        if (peliculaParcial.getNombre() != null) {
            pelicula.setNombre(peliculaParcial.getNombre());
        }

        if (peliculaParcial.getDirector() != null) {
            pelicula.setDirector(peliculaParcial.getDirector());
        }
        
        //La guardamos y mostramos el código de respuesta ok, mostrando la peli actualizada
        servicePelicula.save(pelicula);

        return ResponseEntity.ok(pelicula);
    }
	
}
