package TareaRepaso;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.imageio.ImageIO;

public class UsarBMP {

	public static void main(String[] args) {
		/*leerbmp();
		 Comentado para que se ejecute el otro metodo, para probar solo comentar el otro y este no*/
		leerbmp_ImageIO();
	}
	
	public static void leerbmp() {
		File bmp=new File("Dibujo.bmp");
		RandomAccessFile raf=null;
		try {
			raf=new RandomAccessFile(bmp, "rwd");
			raf.seek(18);
			int ancho1= raf.readInt();
			int anchoEndian=Integer.reverseBytes(ancho1);
			 int alto1 = raf.readInt();
	         int altoEndian = Integer.reverseBytes(alto1);
	         System.out.println("La anchura de la imagen es "+anchoEndian+" y la altura es de "+altoEndian);
	         
	         int nuevoancho=anchoEndian;
	         int nuevolargo=altoEndian;
	         raf.seek(18);
	         raf.write(nuevoancho);
	         raf.write(nuevolargo);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void leerbmp_ImageIO() {
		File bmp=new File("Dibujo.bmp");
		try {
			 BufferedImage img = ImageIO.read(bmp);
			 System.out.println("La anchura de la imagen es "+img.getWidth()+" y la altura es de "+img.getHeight());
		}catch(IOException e) {
			
		}
	}
	
	/*
	 En los apuntes de Fernando Berzal, a la hora de guardar los datos tienen tamaños fijos, lo que hace
	 posible la busqueda de uno a traves del seek del Random Acces File ya que sabe en que posición estará.
	 Por otra parte, ocupa más memoria puesto que al tener un tamaño asignado, en caso de no tener esa dimensión,
	 se le añadiran huecos en blanco que ocupan espacio.En mi caso al guardar los objetos con Serializción o en CSV
	 tienen un tamaño variable, por lo que para buscar un campo u objeto hay que recorrerlos hasta dar con el que
	 buscamos.En cuanto al tamaño o la memoria, no tenemos el problema que dije anteriormente, por ello será menor.
	 
	 
	 */
}
