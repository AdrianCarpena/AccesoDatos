package Pruebas;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Prueba1{
	
    public static void main(String[] args) {
    	
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UnidadPersonas");
        EntityManager em = emf.createEntityManager();
        
        Persona yo = new Persona("Pedro", 25);
        Persona alumno1 = new Persona("Paco", 19);
        Persona alumno2 = new Persona("Felix", 20);
        Persona alumno3 = new Persona("Javier", 22);
        yo.addEmail("yo@midominio.com");
        alumno1.addEmail("al1@gmail.com");
        alumno2.addEmail("al2@hotmail.com");
        alumno3.addEmail("al3@gmail.es");
        yo.addEmail("yo2@gmail.com");
        yo.addEmail("yosoy3@gmail.com");
        alumno2.addEmail("al2_2@gmail.com");
        
        try {
            em.getTransaction().begin();
            em.persist(yo);
            em.persist(alumno1);
            em.persist(alumno2);
            em.persist(alumno3);
            em.getTransaction().commit();
            System.out.println("Todo ha ido bien");
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