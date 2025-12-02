package Pruebas;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class BorrarPersona {
	public static void main(String[] args) {
		
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UnidadPersonas");
        EntityManager em = emf.createEntityManager();
        
        
        try {
            em.getTransaction().begin();
            Persona p= em.find(Persona.class, "Ramon");
            if(p!=null) {
	            em.remove(p);
	            em.getTransaction().commit();
            }
            else
            	System.out.println("No existe ninguna persona con ese nombre");
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