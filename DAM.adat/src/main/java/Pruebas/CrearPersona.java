package Pruebas;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class CrearPersona{
	
    public static void main(String[] args) {
    	
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UnidadPersonas");
        EntityManager em = emf.createEntityManager();
        
        Persona p = new Persona();
        p.setNombre("Javier");
        p.setEdad(41);
        
        try {
            em.getTransaction().begin();
            if( em.find(Persona.class, "Javier")==null) {
            	em.persist(p);
            System.out.println("Persona creada exitosamente");
            }
            else
            	System.out.println("Ya existe esa persona");
            em.getTransaction().commit();
          
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Todo ha ido mal");
        } finally {
            em.close();
            emf.close();
        }
    }
}
