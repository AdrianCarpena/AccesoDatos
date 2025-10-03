package PruebasXStream;

import java.io.BufferedReader;
import java.io.FileReader;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LeerJSON {
	
	public static void main(String[] args) {
	        LeerJSON l=new LeerJSON();
	        l.leer_con_GSON();
	        l.leer_con_Jettison();
	}

	    
	    public void leer_con_Jettison() {
	    	//Leo el archivo y lo transformo en string para que funcione ya que probe a hacerlo de otras maneras 
	    	// pero me daba errores
	        try (BufferedReader br = new BufferedReader(new FileReader("libros.json"))){
	        	 StringBuilder sb = new StringBuilder();
		            String linea;
		            while ((linea = br.readLine()) != null) {
		                sb.append(linea);
		            }
	            String jsonString = sb.toString();
	            JSONObject jsonPrincipal = new JSONObject(jsonString);
	            JSONArray arrayLibros = jsonPrincipal.getJSONArray("libros");

	            System.out.println("Libros leídos con Jettison:");
	            
	            for (int i = 0; i < arrayLibros.length(); i++) {
	                
	                JSONObject jsonLibro = arrayLibros.getJSONObject(i);

	                String autor = jsonLibro.getString("autor");
	                String fecha = jsonLibro.getString("fecha");
	                String titulo = jsonLibro.getString("titulo");
	                int copias = jsonLibro.getInt("copias"); 

	                Libro libro = new Libro(autor, fecha, titulo, copias);
	                System.out.println(libro); 
	            }

	        } catch (JSONException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	
	
	
	public void leer_con_GSON() {
		 try {
		        Gson gson = new GsonBuilder().setPrettyPrinting().create();

		        try (FileReader fr = new FileReader("libros.json")) {
		            Contenedor contenedor = gson.fromJson(fr, Contenedor.class);

		            System.out.println("Libros leídos con GSON:");
		            for (Libro libro : contenedor.getLibros()) {
		                System.out.println(libro);
		            }
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
	}
	

}
