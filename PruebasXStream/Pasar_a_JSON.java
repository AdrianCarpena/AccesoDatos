package PruebasXStream;

import java.io.FileWriter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class Pasar_a_JSON {
	public static void main(String[] args) {
        try {
            
            Libro libro = new Libro("Cervantes", "1605", "Don Quijote", 5);

            
            XStream xstream = new XStream(new JettisonMappedXmlDriver());
            xstream.addPermission(AnyTypePermission.ANY);
            xstream.alias("libro", Libro.class);

            
            try (FileWriter fw = new FileWriter("libro.json")) {
                xstream.toXML(libro, fw); 
            }

            System.out.println("Archivo JSON generado correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}


