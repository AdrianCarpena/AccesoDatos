package PruebasXStream;

import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class LeerJSON {
	
	public static void main(String[] args) {
	        LeerJSON l=new LeerJSON();
	        l.leer_con_GSON();
	        l.leer_con_Jettison();
	}
	
	public void leer_con_Jettison() {
		try {
            
            XStream xstream = new XStream(new JettisonMappedXmlDriver());
            xstream.addPermission(AnyTypePermission.ANY);
            xstream.alias("libro", Libro.class);

            try (FileReader fr = new FileReader("libro.json")) {
                Libro libro = (Libro) xstream.fromXML(fr);
                System.out.println("Libro leído con Jettison:");
                System.out.println(libro);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void leer_con_GSON() {
		try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            try (FileReader fr = new FileReader("libro.json")) {
            	
            	Contenedor contenedor = gson.fromJson(fr, Contenedor.class);
                Libro libro = contenedor.libro;
                System.out.println("Libro leído con GSON:");
                System.out.println(libro);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	//Creo una clase Contenedor para indicarle el objeto que lee y tener las referencias.
	static class Contenedor {
        Libro libro;
    }
	
	/*La lectura del JSon con GSon me parece un poco más compleja ya que hay que indicarle en un contenedor la clase java de la 
	 * que proviene el objeto, con Jettison se es hacer lo mismo que con XML ya que la librería solo cambia el formato de xml a JSon.
	 */

}
