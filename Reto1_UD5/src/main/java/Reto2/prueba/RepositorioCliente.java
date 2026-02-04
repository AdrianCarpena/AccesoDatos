package Reto2.prueba;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositorioCliente extends MongoRepository<Cliente, String> {

	  public Cliente findByFirstName(String nombre);
	  public List<Cliente> findByLastName(String apellido);

	}

