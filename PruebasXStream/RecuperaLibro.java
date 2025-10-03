package PruebasXStream;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class RecuperaLibro {
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

            /* Hay que poner estos alias para que los reconozca a la hora de leer el xml, el primero indica
             * que hay un atributo y los demas el nuevo nombre del campo en el xml en referencia al de la clase
             * de Java.En caso de no ponerlos no lo sabria interpretar y daría errores.*/
             //Si pongo libreria.xml la lee correctamente
			List<Libro> libros = (List<Libro>) xstream.fromXML(br);

            System.out.println("Objeto restaurado desde XML:");
                System.out.println(libros);
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}