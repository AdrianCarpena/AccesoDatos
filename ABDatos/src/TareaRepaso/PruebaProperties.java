package TareaRepaso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PruebaProperties {
	
	public static void main(String[] args) {
		Properties properties= new Properties();
		properties.setProperty("perro", "Jose");
		properties.setProperty("gato", "Paul");
		properties.setProperty("oso", "Ramon");
		properties.setProperty("lemur", "Alex");
		
		File fich=new File("DatosProp");
		File fichxml=new File("DatosProp.xml");
		FileOutputStream fos = null;
		FileOutputStream fosxml= null;
		try {
		fos = new FileOutputStream(fich);
		fosxml = new FileOutputStream(fichxml);
		properties.store(fos, "Guardados");
		properties.storeToXML(fosxml,"Guardado en xml");
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(fos!=null)	fos.close();
				if(fosxml!=null)   fosxml.close();
			}catch (IOException e) {
			}
		}
		
		Properties cargar=new Properties();
		FileInputStream fis= null;
		FileInputStream fisxml= null;
		
		try {
			fis= new FileInputStream(fich);
			fisxml= new FileInputStream(fichxml);
			cargar.load(fis);
			System.out.println("Properties: "+ cargar.getProperty("oso"));
			cargar.loadFromXML(fisxml);
			System.out.println("Properties: "+ cargar.getProperty("lemur"));
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(fis!=null)	fos.close();
				if(fisxml!=null)   fosxml.close();
			}catch (IOException e) {
			}
		}
		/*La clase properties no me parece muy adecuada para guardar objetos a mínimo que sean algo complejos
		ya que solo puede almacenar un valor por clave, por esto es util para objetos de un atributo, sin embargo
		si tuviera que almacenar más de un valor sería más complicado.Para guardar y recuperar un colección de 
		objetos como libros usaría el ObjectOutputStream y ObjectInputStream ya que estan diseñados para esto,
		haciendolo mucho mas sencillo que properties.
		También se podria pasar a csv
		 */
		
	}

}
