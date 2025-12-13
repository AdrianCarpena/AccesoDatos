package Pruebas;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class CRUD_PERSONA {
	private Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		CRUD_PERSONA cp = new CRUD_PERSONA();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UnidadDePersonas");
		EntityManager em = emf.createEntityManager();
		cp.menu(em);
		em.close();
		emf.close();

	}

	public void menu(EntityManager em) {
		int opcion;
		do {
			System.out.println("\n===== MENÚ CRUD ALUMNOS =====");
			System.out.println("1. Insertar persona");
			System.out.println("2. Listar personas");
			System.out.println("3. Buscar persona por id");
			System.out.println("4. Modificar persona");
			System.out.println("5. Eliminar persona");
			System.out.println("6. Volver al menu principal");
			System.out.print("Elige una opción: ");
			opcion = sc.nextInt();
			sc.nextLine();

			switch (opcion) {
			case 1:
				insertar(em);
				break;
			case 2:
				listar(em);
				break;
			case 3:
				buscar(em);
				break;
			case 4:
				modificar(em);
				break;
			case 5:
				eliminar(em);
				break;
			case 6:
				System.out.println("👋 Saliendo del programa...");
				break;
			default:
				System.out.println("⚠️ Opción no válida.");
			}
		} while (opcion != 6);
	}

	public void insertar(EntityManager em) {
		Persona p = new Persona();
		System.out.println("Que nombre tendrá la persona?: ");
		String nombre = sc.next();
		System.out.println("Que edad tendrá la persona?: ");
		int edad = sc.nextInt();
		sc.nextLine();
		
		p.setNombre(nombre);
		p.setEdad(edad);
		
		String email;
		System.out.println("Que email o emails tendrá la persona, para acabar de poner email escribe fin?: ");
		do {
			email = sc.next();
			if (!email.toLowerCase().equals("fin")) {
				p.addEmail(email);
			}
			email = email.toLowerCase();
		} while (!email.equals("fin"));
		
		sc.nextLine();
		String modulo;
		System.out.println("Que modulo o modulos tendrá la persona, para acabar de poner modulos escribe fin?: ");
		do {
			modulo = sc.nextLine();
			if (!modulo.toLowerCase().equals("fin"))
				p.addModulo(em,modulo);
			modulo = modulo.toLowerCase();
		} while (!modulo.equals("fin"));
		
		

		try {
			em.getTransaction().begin();
			em.persist(p);
			System.out.println("Persona creada exitosamente");
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			System.out.println("Todo ha ido mal");

		}

	}

	public void listar(EntityManager em) {
		try {
			em.getTransaction().begin();
			// Consulta JPQL moderna
			List<Persona> personas = em.createQuery("SELECT p FROM Persona p", Persona.class).getResultList();
			for (Persona p : personas) {
				System.out.println(p);
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}

	public void modificar(EntityManager em) {
		try {
			em.getTransaction().begin();
			System.out.println("Que id tiene la persona a modificar?: ");
			Long id = sc.nextLong();
			Persona p = em.find(Persona.class, id);
			if (p != null) {
				System.out.println("Quiere modificar el nombre? s/n: ");
				String resp = sc.next();
				if (resp.toLowerCase().equals("s")) {
					System.out.println("Introduzca el nuevo nombre: ");
					String nombre = sc.next();
					p.setNombre(nombre);
					em.merge(p);
				}
				System.out.println("Quiere modificar la edad? s/n: ");
				resp = sc.next();
				if (resp.toLowerCase().equals("s")) {
					System.out.println("Introduzca la nueva edad: ");
					int edad = sc.nextInt();
					sc.nextLine();
					p.setEdad(edad);
					em.merge(p);
				}
				System.out.println("Quiere cambiar los emails? s/n: ");
				resp = sc.next();
				if (resp.toLowerCase().equals("s")) {
					String respEmails;
					// for (Email email : p.emails) {
					Iterator iterator = p.emails.iterator();
					Email email;
					while ( iterator.hasNext() ) {
						email = (Email) iterator.next();
						System.out.println(email);
						System.out.println("Quiere cambiar este email? s/n: ");
						respEmails = sc.next();
						if (respEmails.toLowerCase().equals("s")) {
							System.out.println("Introduzca el nuevo email, para borrarlo escriba del");
							String nuevoEmail = sc.next();
							sc.nextLine();
							if (nuevoEmail.toLowerCase().equals("del")) {
								// No hay necesidad de ir a buscarlo, ya está en el conjunto recuperado de bd
								// Email e = em.find(Email.class, email.getId());
								iterator.remove();
								p.delEmail(email);
								em.remove(email);
								//em.merge(p); // no borra el email ¿?
								System.out.println("Se ha borrado el email: " + email);
							} else {
								email.setEmail(nuevoEmail);
								em.merge(p);
							}
						}

					}
					System.out.println("Quieres añadir algun email? s/n: ");
					String añadir = sc.next();
					if (añadir.toLowerCase().equals("s")) {
						String nuevoEmail;
						System.out.println(
								"Que email o emails tendrá la persona, para acabar de poner email escribe fin?: ");
						do {
							nuevoEmail = sc.next();
							if (!nuevoEmail.toLowerCase().equals("fin"))
								p.addEmail(nuevoEmail);
							nuevoEmail = nuevoEmail.toLowerCase();
						} while (!nuevoEmail.equals("fin"));
					}

				}
				
				
				System.out.println("Quiere cambiar los modulos? s/n: ");
				resp = sc.next();
				if (resp.toLowerCase().equals("s")) {
					String respModulos;
					// for (Email email : p.emails) {
					Iterator iterator = p.modulos.iterator();
					Modulo modulo;
					while ( iterator.hasNext() ) {
						modulo = (Modulo) iterator.next();
						System.out.println(modulo);
						System.out.println("Quiere cambiar este modulo? s/n: ");
						respModulos = sc.next();
						if (respModulos.toLowerCase().equals("s")) {
							System.out.println("Introduzca el nuevo modulo, para borrarlo escriba del");
							String nuevoModulo = sc.next();
							sc.nextLine();
							if (nuevoModulo.toLowerCase().equals("del")) {
								// No hay necesidad de ir a buscarlo, ya está en el conjunto recuperado de bd
								// Email e = em.find(Email.class, email.getId());
								iterator.remove();
								p.delModulo(modulo);
								em.remove(modulo);
								//em.merge(p); // no borra el email ¿?
								System.out.println("Se ha borrado el modulo: " + modulo);
							} else {
								modulo.setModulo(nuevoModulo);
								em.merge(p);
							}
						}

					}
					System.out.println("Quieres añadir algun modulo? s/n: ");
					String añadir = sc.next();
					if (añadir.toLowerCase().equals("s")) {
						String nuevoModulo;
						System.out.println("Que modulo o modulos tendrá la persona, para acabar de poner email escribe fin?: ");
						sc.nextLine();
						do {
							nuevoModulo = sc.nextLine();
							if (!nuevoModulo.toLowerCase().equals("fin"))
								p.addModulo(em,nuevoModulo);
							nuevoModulo = nuevoModulo.toLowerCase();
						} while (!nuevoModulo.equals("fin"));
					}

				}

				em.getTransaction().commit();

				System.out.println("Persona modificada exitosamente");
			} else {
				System.out.println("No existe la persona que desea modificar");
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			System.out.println("Todo ha ido mal");
		}

	}

	public void eliminar(EntityManager em) {
		try {
			em.getTransaction().begin();
			System.out.println("Que id tiene la persona a modificar?: ");
			Long id = sc.nextLong();
			Persona p = em.find(Persona.class, id);
			if (p != null) {
				em.remove(p);
				em.getTransaction().commit();
			} else
				System.out.println("No existe ninguna persona con ese id");
		} catch (Exception e) {
			e.printStackTrace();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			System.out.println("Todo ha ido mal");
		}
	}

	public void buscar(EntityManager em) {
		System.out.println("Indique el id de la persona a buscar: ");
		Long id = sc.nextLong();
		try {
			em.getTransaction().begin();
			// Consulta JPQL moderna
			Persona p = em.find(Persona.class, id);
			System.out.println(p);

			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}

}
