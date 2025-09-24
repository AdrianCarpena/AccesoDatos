package TareaRepaso;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class RandomAccesFileUso {

	public static void main(String[] args) {
		Scanner teclado= new Scanner(System.in);
		int[] numeros= new int[20];
		File fich= new File("datos.bin");
		RandomAccessFile raf=null;
		try {
			if(fich.exists()) {
				DataInputStream dis=null;
				try {
					dis= new DataInputStream(new FileInputStream(fich));
					for(int x=0;x<20;x++)
						numeros[x]=dis.readInt();
				}catch (IOException e) {
					e.printStackTrace();
				}finally {
					try {
						if(dis!=null) dis.close();
					}catch (IOException e) {
						
					}
				}
				
			}
			else {
				DataOutputStream dos=null;
				try {
					dos= new DataOutputStream(new FileOutputStream(fich));
					for(int x=0;x<20;x++) {
						dos.writeInt(0);
						numeros[x]=0;
					}
				}catch(IOException e) {
					
				}finally {
					try {
						if(dos!=null) dos.close();
					}catch (IOException e) {
						
					}
			}
		
	}
			
			raf= new RandomAccessFile(fich, "rwd");
			int pos=0;
			do {
				for(int n:numeros)
					System.out.print(n+" ");
				System.out.println("\nQue posición desea modificar?: Introduzca un numero negativo para acabar");
				pos=teclado.nextInt();
				if(pos>=0 && pos<=20) {
					System.out.println("Diga el nuevo valor: ");
					int valor=teclado.nextInt();
					numeros[pos]=valor;
					raf.seek(pos*4);
					raf.writeInt(valor);
					
				}
				
				
			}while(pos>=0);
		}catch(IOException e) {
			
		}finally {
			try {
				if(raf!=null) raf.close();
				teclado.close();
			}catch(IOException e) {
				
			}
		}
	}
		
	
}
