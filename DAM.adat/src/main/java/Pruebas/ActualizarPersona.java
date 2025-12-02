package Pruebas;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ActualizarPersona {
	public static void main(String[] args) {
		
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UnidadPersonas");
        EntityManager em = emf.createEntityManager();
        
        
        try {
            em.getTransaction().begin();
            Persona p= em.find(Persona.class, "pad");
            if(p!=null) {
            	p.setEdad(19);
	            em.merge(p);
	            em.getTransaction().commit();
            }
            else {
            	System.out.println("No existe la persona que desea modificar");
            }
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
