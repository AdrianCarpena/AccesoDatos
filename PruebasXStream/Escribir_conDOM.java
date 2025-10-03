package PruebasXStream;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Escribir_conDOM {
	
	public static void main(String[] args) {
		
	
	Libro l= new Libro("Cervantes","19/3/1830", "El quijote", 122);
	
	try {
		 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		 DocumentBuilder db = dbf.newDocumentBuilder();
		 Document doc = db.newDocument();
		 // definimos el elemento raíz del documento
		 Element eRaiz = doc.createElement("libros");
		 doc.appendChild(eRaiz);
		 // definimos el nodo que contendrá los elementos
		 Element elibro = doc.createElement("Libro");
		 eRaiz.appendChild(elibro);
		 // atributo para el nodo coche
		 Attr attr = doc.createAttribute("titulo");
		 attr.setValue(l.getTítulo());
		 elibro.setAttributeNode(attr);
		 // definimos cada uno de los elementos y le asignamos un valor
		 Element eautor = doc.createElement("NombreAutor");
		 eautor.appendChild(doc.createTextNode(l.getAutor()));
		 elibro.appendChild(eautor);
		 Element efecha = doc.createElement("FechaPublicacion");
		 efecha.appendChild(doc.createTextNode(l.getFecha()));
		 elibro.appendChild(efecha);
		 Element ecopias = doc.createElement("NumeroCopias");
		 ecopias.appendChild(doc.createTextNode(Integer.toString(l.getCopias())));
		 elibro.appendChild(ecopias);
		 // clases necesarias finalizar la creación del archivo XML
		 TransformerFactory transformerFactory = TransformerFactory.newInstance();
		 Transformer transformer = transformerFactory.newTransformer();
		 DOMSource source = new DOMSource(doc);
		 StreamResult result = new StreamResult(new File("ejercicio3.xml"));
		 transformer.transform(source, result);
		} catch(Exception e) {
		 e.printStackTrace();
		}
	}
	
	/*Escribir con DOM me parece más lioso y complejo ya que hay que instanciar muchas más cosas que con SXtream, además si se quisiera
	 * meter más de un libro habría que poner todo de nuevo, salvo el eraiz que es solo 1 vez ya que almacena todos los lirbos. Personalemte
	 * prefiero usar XStream que es indicar el nombre de los campos en pocas lineas y con metodos predefinidos lo escribes.
	 * Si en la clase Recuperalibro pongo que el fichero sea ejercicio3.xml en lugar de libros.xml lo lee ya que le definí los mimsmos nombres 
	 * de campos y atributo.
	 */
	
}
