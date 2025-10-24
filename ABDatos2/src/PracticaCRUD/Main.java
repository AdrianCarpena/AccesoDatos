package PracticaCRUD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

		private static final String URL = "jdbc:mysql://localhost:3306/adat"; // URL de quien lo use
	    private static final String USER = "dam2";// Usuario de mysql del quien lo use
	    private static final String PASSWORD = "asdf.1234";//Aqui la contraseña de mysql     
	    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    

	    public static void main(String[] args) {
	    	//Usar solo la primera vez, para crear tablas e introducir datos de los csv
	        //crearEImportarDatos();
	        Scanner sc = new Scanner(System.in);
	        
	        int opcion;

	        do {
	            System.out.println("\n===== MENÚ PRINCIPAL =====");
	            System.out.println("1. Gestionar ALUMNOS");
	            System.out.println("2. Gestionar CURSOS");
	            System.out.println("3. Salir");
	            System.out.print("Elige una opción: ");
	            opcion = sc.nextInt();
	            sc.nextLine();
	            InterfaceCRUD icrud;  

	            switch (opcion) {
	                case 1 -> {
	                	icrud = new CRUDAlumnos(URL, USER, PASSWORD); 
                        icrud.menu(); 
                        icrud.cerrar();
	                }
	                case 2 -> {
	                	 icrud = new CRUDCursos(URL, USER, PASSWORD);
	                     icrud.menu(); 
	                     icrud.cerrar();
	                }
	                case 3 -> System.out.println("👋 Saliendo del programa...");
	                default -> System.out.println("⚠️ Opción no válida.");
	            }

	        } while (opcion != 3);

	        sc.close();
	    }
	    
	    //Este metodo es para que en la primera ejecución se creen las tablas y se inserten los datos een la base de datos
	    public static void crearEImportarDatos() {
	        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
	            System.out.println("✅ Conectado correctamente a la base de datos.");

	            crearTablas(conn);
	            importarCursos(conn, "cursos.csv");
	            importarAlumnos(conn, "alumnos.csv");

	            System.out.println("🎉 Tablas creadas e importadas correctamente.");

	        } catch (SQLException e) {
	            System.out.println("❌ Error de conexión: " + e.getMessage());
	        }
	    }

	 
	    //Metodo para crear todas las tablas de la base de datos, con un campo fecha y clave foranea
	    private static void crearTablas(Connection conn) throws SQLException {
	        String sqlCursos = """
	            CREATE TABLE IF NOT EXISTS cursos (
	                id_curso INT AUTO_INCREMENT PRIMARY KEY,
	                nombre VARCHAR(100),
	                tutor VARCHAR(100),
	                fecha_inicio DATE
	            );
	            """;

	        String sqlAlumnos = """
	            CREATE TABLE IF NOT EXISTS alumnos (
	                dni VARCHAR(9) PRIMARY KEY,
	                nombre VARCHAR(100),
	                correo VARCHAR(100),
	                fecha_nacimiento DATE,
	                id_curso INT,
	                FOREIGN KEY (id_curso) REFERENCES cursos(id_curso)
	                ON UPDATE CASCADE
	        		ON DELETE CASCADE
	            );
	            """;

	        try (PreparedStatement psCursos = conn.prepareStatement(sqlCursos);
	        	 PreparedStatement psAlumnos = conn.prepareStatement(sqlAlumnos)) {

	        	    psCursos.execute();
	        	    psAlumnos.execute();
	        	    System.out.println("🧱 Tablas creadas (si no existían).");
	        	}
	    }
	    
	    //Metodos para meter los datos de los 2 csv a la base de datos
	    
	    private static void importarCursos(Connection conn, String ruta) {
	        String sql = "INSERT INTO cursos (id_curso, nombre, tutor, fecha_inicio) VALUES (?, ?, ?, ?)";
	        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
	            String linea = br.readLine();
	            while ((linea = br.readLine()) != null) {
	                String[] datos = linea.split(",");
	                try (PreparedStatement ps = conn.prepareStatement(sql)) {
	                    ps.setInt(1, Integer.parseInt(datos[0]));
	                    ps.setString(2, datos[1]);
	                    ps.setString(3, datos[2]);
	                    ps.setDate(4, Date.valueOf(LocalDate.parse(datos[3], FORMAT)));
	                    ps.executeUpdate();
	                }
	            }
	            System.out.println("📚 Cursos importados correctamente.");
	        } catch (Exception e) {
	            System.out.println("⚠️ Error importando cursos: " + e.getMessage());
	        }
	    }

	    private static void importarAlumnos(Connection conn, String ruta) {
	        String sql = "INSERT INTO alumnos (dni, nombre, correo, fecha_nacimiento, id_curso) VALUES (?, ?, ?, ?, ?)";
	        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
	            String linea = br.readLine(); 
	            while ((linea = br.readLine()) != null) {
	                String[] datos = linea.split(",");
	                try (PreparedStatement ps = conn.prepareStatement(sql)) {
	                    ps.setString(1, datos[0]);
	                    ps.setString(2, datos[1]);
	                    ps.setString(3, datos[2]);
	                    ps.setDate(4, Date.valueOf(LocalDate.parse(datos[3], FORMAT)));
	                    ps.setInt(5, Integer.parseInt(datos[4]));
	                    ps.executeUpdate();
	                }
	            }
	            System.out.println("👩‍🎓 Alumnos importados correctamente.");
	        } catch (Exception e) {
	            System.out.println("⚠️ Error importando alumnos: " + e.getMessage());
	        }
	    }
	
}
