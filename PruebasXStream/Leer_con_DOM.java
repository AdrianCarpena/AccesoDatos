package PruebasXStream;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Leer_con_DOM {
	
	public static void main(String[] args) {
		
		File file = new File("libros.xml");
		try {
			 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			 DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			 Document doc = dBuilder.parse(file);
			 doc.getDocumentElement();
			 doc.normalize();
			 NodeList nList = doc.getElementsByTagName("Libro");
			 System.out.println("Número de libros: " + nList.getLength());
			 
			 for(int temp = 0; temp < nList.getLength(); temp++) {
				 Node nNode = nList.item(temp);
				 if(nNode.getNodeType() == Node.ELEMENT_NODE) {
				 Element eElement = (Element) nNode;
				 System.out.println("\nTitulo libro: " + eElement.getAttribute("titulo"));
				 System.out.println("Autor: "
				 +
				eElement.getElementsByTagName("NombreAutor").item(0).getTextContent());
				 System.out.println("Fecha: "
				 +
				eElement.getElementsByTagName("FechaPublicacion").item(0).getTextContent());
				 System.out.println("Copias: "
				 +
				eElement.getElementsByTagName("NumeroCopias").item(0).getTextContent());
				 }
				}
			}
			catch(Exception e) {
			 e.printStackTrace();
			}
	}

}
