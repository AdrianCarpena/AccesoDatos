package TareaRepaso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PruebaSerializar {

	public static void main(String[] args) {
		List<Libro> libros= new ArrayList<Libro>();
		Libro l1=new Libro("Jose, el del prado","12/03/1999","Las barcas",2321);
		Libro l2=new Libro("Carlos Herrera","16/03/2019","El planeta",1445);
		Libro l3=new Libro("Juan Servó","12/08/1981","Las arañas",56285);
		libros.add(l1);
		libros.add(l2);
		libros.add(l3);
		
		File fich= new File("librosSerializados");
		ObjectOutputStream oos=null;
		try {
			oos= new ObjectOutputStream(new FileOutputStream(fich));
			oos.writeObject(libros);
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(oos!=null) oos.close();
			}catch (IOException e) {
			
			}
		}
		
		ObjectInputStream ois=null;
		try {
			ois= new ObjectInputStream(new FileInputStream(fich));
			List<Libro> leido= (List<Libro>)ois.readObject();
			for(Libro l:leido) {
				System.out.println(l.toString());
			}
		}catch(IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			try {
				if(ois!=null) oos.close();
			}catch (IOException e) {
			
			}
		}
	}
}
