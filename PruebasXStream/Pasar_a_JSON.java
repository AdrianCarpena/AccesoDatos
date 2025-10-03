package PruebasXStream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class Pasar_a_JSON {
	public static void main(String[] args) {
		 try (BufferedReader br = new BufferedReader(new FileReader("libros.xml"))){
	            
	        	
	           XStream xstream = new XStream(new DomDriver());
	           xstream.processAnnotations(Libro.class);  
	           xstream.addPermission(AnyTypePermission.ANY);
	           xstream.alias("libros", List.class);
	           xstream.alias("Libro",Libro.class);
	           xstream.aliasField("NombreAutor", Libro.class, "autor");
	           xstream.aliasField("FechaPublicacion", Libro.class, "fecha");
	           xstream.aliasField("NumeroCopias", Libro.class, "copias");

	            
			List<Libro> libros = (List<Libro>) xstream.fromXML(br);
			for(Libro libro:libros)
				System.out.println(libro);
			
			XStream xstream2 = new XStream(new JsonHierarchicalStreamDriver());
			xstream2.addPermission(AnyTypePermission.ANY);

			// Alias de la clase Libro
			xstream2.alias("libro", Libro.class);

			// Alias para la lista
			xstream2.alias("libros", List.class);
			xstream2.aliasField("titulo", Libro.class, "titulo");
			xstream2.aliasField("autor", Libro.class, "autor");
			xstream2.aliasField("fecha", Libro.class, "fecha");
			xstream2.aliasField("copias", Libro.class, "copias");

            
            try (FileWriter fw = new FileWriter("libros.json")) {
            	xstream2.toXML(libros, fw);
            }

            System.out.println("Archivo JSON generado correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}


