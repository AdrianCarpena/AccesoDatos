package PruebasXStream;

import java.io.File;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.persistence.FilePersistenceStrategy;
import com.thoughtworks.xstream.persistence.XmlArrayList;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class Persistentes {
	public static void main(String[] args) {
		try {
		XStream xstream = new XStream(new DomDriver());
		xstream.addPermission(AnyTypePermission.ANY);
		xstream.alias("Libro",Libro.class);
		//Hay que poner la ruta correspondiente, en mi pc personal es esta.
		String ruta="C:\\Users\\Adrian\\Documents";
		File carp = new File(ruta);
		 if (!carp.exists()) carp.mkdirs();
		FilePersistenceStrategy strategy = new FilePersistenceStrategy(carp,xstream);
		//Lo de abajo es para que no haya un warning
		@SuppressWarnings("unchecked")
		List<Libro>libros=(List<Libro>) new XmlArrayList(strategy);
		
		Libro libro1 = new Libro("Cervantes", "1605", "Don Quijote", 5);
        libros.add(libro1);

        Libro libro2 = new Libro("Rowling", "1997", "Harry Potter", 10);
        libros.add(libro2);
        
        System.out.println("Libros restaurados desde " + ruta + ":");
        for (Libro l : libros) {
            System.out.println(l);
        }
		}catch(Exception e) {
			
		}
	}
	/*El uso de estas 2 clases me parece bastante util ya que facilita las cosas al querer meter
	 * en archivo los datos y recuperarlos ya que no hay que hacer writter ni reader, basta con agregarlos a
	 * la lista y recorrerla, el resto lo hace la propia clase. No obstante tiene un grán problema y es que
	 * esta pensado para objetos individuales como un libro, un coche... para cada uno crea un nuevo xml, por
	 * lo que no sería conveniente para listas grandes , en cuyo casp veo mucha mejor opción la otra manera.
	 */

}
