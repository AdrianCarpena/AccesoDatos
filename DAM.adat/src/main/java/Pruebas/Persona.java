package Pruebas;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
//@Table(name = "Personas")
public class Persona {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	//@Column(name = "ID_Persona")
	long id;
	//@Column(name = "Nombre")
	private String nombre;
	//@Column(name = "Edad")
	private int edad;
	@OneToMany(cascade= CascadeType.ALL, mappedBy = "persona", fetch=FetchType.EAGER)
	Set<Email> emails= new HashSet<Email>();
	
	@ManyToMany(cascade= CascadeType.ALL, fetch=FetchType.EAGER)
	Set<Modulo> modulos= new HashSet<Modulo>();
	
	public Modulo addModulo(EntityManager em,String moduloStr) {
		  List<Modulo> lista = em
			        .createQuery("SELECT m FROM Modulo m WHERE m.modulo = :mod", Modulo.class)
			        .setParameter("mod", moduloStr)
			        .getResultList();

		  Modulo modulo;

		  if (!lista.isEmpty()) {
			    modulo = lista.get(0);      
		  } else {
			  	modulo = new Modulo(moduloStr);   
		  }
			    
		return this.addModulo(modulo);
	}
	
	public Modulo addModulo(Modulo modulo) {
		modulos.add(modulo);
		return modulo;
	}
	
	public boolean delModulo(String modulo) {
		Modulo m= new Modulo(modulo);
		return modulos.remove(m);
	}
	
	public boolean delModulo(Modulo modulo) {
		return modulos.remove(modulo);
	}
	
	public Persona(String nombre, int edad) {
		super();
		setEdad(edad);
		setNombre(nombre);
	}
	
	public Persona() {
		super();

	}
	
	
	public Email addEmail(Email email) {
		email.setPersona(this);
		emails.add(email);
		return email;
	}	
	
	public Email addEmail(String emailStr) {
		Email email= new Email(emailStr);
		return this.addEmail(email);
	}
	
	public boolean delEmail(String email) {
		Email e= new Email(email);
		return emails.remove(e);
	}
	
	public boolean delEmail(Email email) {
		return emails.remove(email);
	}
	
	public long getId() {

		return id;
	}

	public String getNombre() {

		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;

	}

	public int getEdad() {

		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;

	}
	

	@Override
	public String toString() {
		/*String strEmails="";
		for(Email email:emails) {
			strEmails+=email+",";
		}*/
		return "Persona [id=" + id + ", nombre=" + nombre + ", edad=" + edad + ", emails=" + emails +", modulos= "+modulos+"]";
	}

	@Override
	public int hashCode() {
//simplificacion
		return nombre.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
//simplificacion
		Persona nueva = (Persona) obj;
		return nueva.getNombre().equals(this.getNombre());

	}
}