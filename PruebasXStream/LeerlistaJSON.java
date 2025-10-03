package PruebasXStream;

import java.io.FileReader;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class LeerlistaJSON {
	
	public static void main(String[] args)  {
		try {
			//Indico como se llamarán los campos/indico los alias
            Gson gson = new GsonBuilder()
                    .setFieldNamingStrategy(f -> {
                        switch(f.getName()) {
                            case "autor": return "NombreAutor";
                            case "fecha": return "FechaPublicacion";
                            case "copias": return "NumeroCopias";
                            default: return f.getName();
                        }
                    })
                    .create();

            // Lee todo el JSON y lo trata como objeto genérico
            JsonObject root = gson.fromJson(new FileReader("MOCK_DATA.json"), JsonObject.class);

            //Almacena lo anterior en una lista buscando la clave libros
            JsonArray array = root.getAsJsonArray("libros");

            // Convierte el JsonArray a List<Libro>
            List<Libro> libros = gson.fromJson(array, new TypeToken<List<Libro>>(){}.getType());

            // Imprime los libros
            for (Libro lib : libros) {
                System.out.println(lib);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
	    }
	
	/*Leido el archivo JSON de muchos libros con la libreria gson, con Jettison no lo conseguí no se porque me fallaba.*/
	
}