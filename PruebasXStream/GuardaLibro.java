package PruebasXStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
public class GuardaLibro {
	public static void main(String[] args) {
		
		try ( FileWriter writer = new FileWriter("libros.xml")){ 
			XStream xstream = new XStream(new DomDriver());
			//La linea de abajo sirve para que vea en la clase libro anotaciones como que titulo es atributo
			xstream.processAnnotations(Libro.class);  
			xstream.addPermission(AnyTypePermission.ANY);
			Libro l= new Libro("Cervantes","19/3/1830", "El quijote", 122);
			Libro l2 = new Libro("Rowling", "1997", "Harry Potter", 10);
			List<Libro>libros=new ArrayList<Libro>();
			libros.add(l);
			libros.add(l2);
			//Indico los alias usados
			xstream.alias("libros", List.class);
			xstream.alias("Libro",Libro.class);
			xstream.aliasField("NombreAutor", Libro.class, "autor");
            xstream.aliasField("FechaPublicacion", Libro.class, "fecha");
            xstream.aliasField("NumeroCopias", Libro.class, "copias");
            
            /*Le pongo alias y atributo para mayor claridad y compactación.
             * En ejercicios1.pdf veo que se puede hacer que un campo no salga en el xml con omitfield,
             * pero aquí no me sirve ya que quiero todos visibles.*/
     
			String xml= xstream.toXML(libros);
			System.out.println("XML generado:\n" + xml);
            writer.write(xml);

            System.out.println("\n Libro guardado en 'libro.xml'");
        } catch (IOException e) {
            e.printStackTrace();
			} 
		} 
	}
		