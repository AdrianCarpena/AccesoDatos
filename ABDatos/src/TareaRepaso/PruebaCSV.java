package TareaRepaso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PruebaCSV {

	public static void main(String[] args) {
		List<Libro> libros= new ArrayList<Libro>();
		Libro l1=new Libro("Jose, el del prado","12/03/1999","Las barcas",2321);
		Libro l2=new Libro("Carlos Herrera","16/03/2019","El planeta",1445);
		Libro l3=new Libro("Juan Servó","12/08/1981","Las arañas",56285);
		libros.add(l1);
		libros.add(l2);
		libros.add(l3);
		
		File csv= new File("archivoCSV.csv");
		PrintWriter pw=null;
		try {
			pw=new PrintWriter(new OutputStreamWriter(new FileOutputStream(csv),StandardCharsets.ISO_8859_1));
			for (Libro l : libros) {
                pw.println(l.toString());
            }
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
				if (pw!=null) pw.close();
		}
		
		BufferedReader br=null;
		try {
			br= new BufferedReader(new FileReader(csv,StandardCharsets.ISO_8859_1));
			String linea;
			while((linea=br.readLine()) !=null) {
				System.out.println(linea);
			}
			
		}catch(IOException e) {
			e.printStackTrace();			
		}finally {
			try {
				if(br!=null) br.close();
			}catch (IOException e) {
			}
		}
	}
	/* Al hacer el System.out.print aqui no hay problema con las ñ o los acentos, no obstante, en el csv
	 pone símbolos distintos debido a la codificación.Con la codificación puesta ahora mismo si funciona, aunque depende
	 cual sea se ve de una forma u otra. Si hago el csv con un procesador de textos como Word puede aplicarle
	 estilos invisibles que afecten a su manejo, por ello no es recomendable. Para el problema de poner
	 comas, se puede poner un ; como delimitador para que no suceda, en mi ejemplo hay una coma y funciona correctamente.
	 
	 
	 El uso de DataOutputStream o ObjectOutputStream en lugar de usar estos y tenerlo en csv tendría ciertas
	 ventajas como la reducción del espacio necesario para almacenar los datos o el hecho de no tener problemas 
	 con las comas, formato u otros símbolos; aunque tambíen tiene sus desventajas como que no se pueden leer
	 de manera "normal" abriendo el archivo con un editor de texto, se deben abrir especialmente con un lenguaje
	 específico.Además al serializar objetos solo se pueden usar con java.
	 En mi opinión prefiero usar DataOutputStream o ObjectOutputStream a pesar de los inconvenientes ya que
	 su uso y aplicación me parecen más sencillas.
	 
	 */
	
}
