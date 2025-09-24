package TareaRepaso;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;


public class PruebaVel {

	public static void main(String[] args) {
		File f= new File("Prueb");
		Libro l1=new Libro("Jose, el del prado","12/03/1999","Las barcas",2321);
		StringBuffer sb;
		try {
			RandomAccessFile raf=new RandomAccessFile(f, "rwd");
			long start=System.currentTimeMillis();
			for(int z=0;z<100000;z++) {
			sb = new StringBuffer(l1.getAutor());
	        sb.setLength(20);
	        raf.writeChars(sb.toString());
	        sb = new StringBuffer(l1.getFecha());
	        sb.setLength(20);
	        raf.writeChars(sb.toString());
	        sb = new StringBuffer(l1.getTítulo());
	        sb.setLength(20);
	        raf.writeChars(sb.toString());
	        raf.writeInt(l1.getCopias());
			}
			long end = System.currentTimeMillis();
			System.out.println("Tiempo transcurrido: " +(end-start)+ " ms");
			long start2=System.currentTimeMillis();
			raf.seek(0);
			sb = new StringBuffer("Ramon");
		    sb.setLength(20);
		    raf.writeChars(sb.toString());
		    sb = new StringBuffer("22/04/2009");
		    sb.setLength(20);
		    raf.writeChars(sb.toString());
		    sb = new StringBuffer("La avispa");
		    sb.setLength(20);
		    raf.writeChars(sb.toString());
		    raf.writeInt(23);
		
			long end2 = System.currentTimeMillis();
			System.out.println("Tiempo transcurrido: " +(end2-start2)+ " ms");
			
	        
	}catch(IOException e) {
		
	}
	
		
 }
}
