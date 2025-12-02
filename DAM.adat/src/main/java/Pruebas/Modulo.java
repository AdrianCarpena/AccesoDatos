package Pruebas;
	
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import jakarta.persistence.ManyToMany;


	@Entity
	public class Modulo {
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		Long id;

		String modulo;
		
		 @ManyToMany(mappedBy = "modulos")   
		 Set<Persona> personas = new HashSet<>();
		
		public Modulo() {}
		
		public Modulo(String modulo) {
			super();
			this.modulo=modulo;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getModulo() {
			return modulo;
		}

		public void setModulo(String modulo) {
			this.modulo = modulo;
		}
		
	
		public Set<Persona> getPersonas() {
			return personas;
		}

		public void setPersona(Persona persona) {
			this.personas.add(persona);
		}

		@Override
		public String toString() {
			return "Modulo [id=" + id + ", modulo=" + modulo + "]";
		}

		@Override
		public int hashCode() {
			return Objects.hash(id, modulo);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Modulo other = (Modulo) obj;
			return Objects.equals(id, other.id) && Objects.equals(modulo, other.modulo);
		}
		
	}

