package Pruebas;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Queries {
	
	private static EntityManagerFactory emf;
	private static EntityManager em;

	public static void main(String[] args) {
		System.setProperty("java.util.logging.config.file", "src/main/resources/logging.properties");
		
		emf = Persistence.createEntityManagerFactory("UnidadDePersonas");
		em= emf.createEntityManager();
		
		Queries q= new Queries();
		
		System.out.println("Query 1: Listado de todas las entidades de una tabla seleccionando solo algunos campos, "
				+ "por ejemplo, nombre y edad.");
		query1();
		
		q.filtroEdad();
		
		System.out.println("Query 3: Listados ordenados por algún criterio, por ejemplo por edad.");
		query3();
		
		System.out.println("Query 4: Búsqueda de elementos por criterios exactos y aproximados.");
		query4();
		
		q.filtroEdadRango();
		q.usoJoin();
		q.Operaciones();
		
		
	}
	
	public static void query1() {
		try {
			em.getTransaction().begin();
			List<Object[]> personas = em.createQuery("SELECT p.nombre, p.edad FROM Persona p", Object[].class).getResultList();
			for (Object[] p : personas) {
				System.out.println("Nombre: " + p[0] + ", Edad: " + p[1]);
			}
			em.getTransaction().commit();
		} 
		catch (Exception e) {
			e.printStackTrace();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}
	
	
	public void filtroEdad() {
		
		List<Persona> personas = em.createQuery("SELECT p FROM Persona p where edad> :edad", Persona.class).setParameter("edad", 25).getResultList();
		System.out.println("\nResultado de filtrar por edad:");
		for (Persona p : personas) {
			System.out.println(p);
		}
		
	}
	
	public static void query3() {
		try {
			em.getTransaction().begin();
			
			List<Persona> personas = em.createQuery("SELECT p FROM Persona p ORDER BY edad DESC", Persona.class).getResultList();
			for (Persona p : personas) {
				System.out.println(p);
			}
			em.getTransaction().commit();
		} 
		catch (Exception e) {
			e.printStackTrace();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}
	
	//En este metodo filtro según un criterio aproximado y otro exacto, en este caso segun la edad mayor a 20 el exacto y el aproximado es que acabe el correo por @gmail.com.
	public static void query4() {
		try {
			em.getTransaction().begin();
			List<Object[]> personas = em.createQuery("SELECT p.id, p.nombre, e.email FROM Persona p JOIN p.emails e "
					+ "ON p.id= e.id WHERE p.edad>20 AND e.email LIKE '%@gmail.com'", Object[].class).getResultList();
			for (Object[] p : personas) {
				System.out.println("Id: " + p[0] + ", Nombre: " + p[1] + "Email: " + p[2]);
			}
			em.getTransaction().commit();
		} 
		catch (Exception e) {
			e.printStackTrace();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}
	

	public void filtroEdadRango() {
		
		
		System.out.println("\nResultado de filtrar por un rango de edades");
		List<Persona> personas = em.createQuery("SELECT p FROM Persona p where edad between :edad1 and :edad2", Persona.class).setParameter("edad1", 20)
				.setParameter("edad2", 25).getResultList();
		for (Persona p : personas) {
			System.out.println(p);
		}
	}
		
	public void usoJoin() {
		List<Persona> personas = em.createQuery(
			    "SELECT p FROM Persona p JOIN p.emails e WHERE e.id = :id",
			    Persona.class
			).setParameter("id", 2)
			.getResultList();
		for (Persona p : personas) {
			System.out.println("persona con join:"+p);
		}
	}
	
	public void Operaciones() {
		//Devuelve un array , cada posicion es uno de los valores que pedí.
		Object[] resultado = em.createQuery(
			    "SELECT SUM(p.edad), AVG(p.edad),count(p) FROM Persona p WHERE p.edad > :edad",
			    Object[].class
			)
			.setParameter("edad", 18)
			.getSingleResult();

			Long suma = (Long) resultado[0];
			Double media = (Double) resultado[1];
			Long num_registros=(Long) resultado[2];

			System.out.println("Suma de edades: " + suma);
			System.out.println("Media de edades: " + media);
			System.out.println("Total de registros: " + num_registros);
	}
	
	
}
