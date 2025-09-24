package TareaRepaso;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RAF_fijos {

	public static void main(String[] args) {
		File fich= new File("DatosFijos");
		Libro l1=new Libro("Jose, el del prado","12/03/1999","Las barcas",2321);
		try {
		RandomAccessFile raf=new RandomAccessFile(fich, "rwd");
		StringBuffer sb = new StringBuffer(l1.getAutor());
        sb.setLength(20);
        raf.writeChars(sb.toString());
        sb = new StringBuffer(l1.getFecha());
        sb.setLength(20);
        raf.writeChars(sb.toString());
        sb = new StringBuffer(l1.getTítulo());
        sb.setLength(20);
        raf.writeChars(sb.toString());
        raf.writeInt(l1.getCopias());
        
        raf.seek(0);
        for(int x=0;x<60;x++)
        System.out.print(raf.readChar());
        System.out.println(raf.readInt());
        
        /*Modificar un campo como la fecha, voy a la pos 40 ya que el campo mide 20 pero un char son 2 bytes*/
        raf.seek(40);
        sb = new StringBuffer("30/11/1997");
        sb.setLength(20);
        raf.writeChars(sb.toString());
        
        raf.seek(0);
        System.out.println("Datos modificados:");
        for(int x=0;x<60;x++)
        System.out.print(raf.readChar());
        System.out.println(raf.readInt());
        raf.close();
		}catch(IOException e) {
			
		}
	}
}
