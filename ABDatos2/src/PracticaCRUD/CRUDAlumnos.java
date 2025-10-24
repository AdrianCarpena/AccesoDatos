package PracticaCRUD;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class CRUDAlumnos implements InterfaceCRUD {


	    private Connection conn;
	    private Scanner sc = new Scanner(System.in);

	    //En el constructor se hace la conexión a la base de datos
	    public CRUDAlumnos(String URL,String USER,String PASSWORD) {
	        try {
	            conn = DriverManager.getConnection(URL, USER, PASSWORD);
	            System.out.println("✅ Conectado a la base de datos.");
	        } catch (SQLException e) {
	            System.out.println("❌ Error de conexión: " + e.getMessage());
	        }
	    }

	    @Override
	    public void menu() {
	        int opcion;
	        do {
	            System.out.println("\n===== MENÚ CRUD ALUMNOS =====");
	            System.out.println("1. Insertar alumno");
	            System.out.println("2. Listar alumnos");
	            System.out.println("3. Buscar alumno por DNI");
	            System.out.println("4. Modificar alumno");
	            System.out.println("5. Eliminar alumno");
	            System.out.println("6. Volver al menu principal");
	            System.out.print("Elige una opción: ");
	            opcion = sc.nextInt();
	            sc.nextLine(); 

	            switch (opcion) {
	                case 1 -> insertar();
	                case 2 -> listar();
	                case 3 -> buscar();
	                case 4 -> modificar();
	                case 5 -> eliminar();
	                case 6 -> System.out.println("👋 Saliendo del programa...");
	                default -> System.out.println("⚠️ Opción no válida.");
	            }
	        } while (opcion != 6);
	    }

	    @Override
	    public void insertar() {
	        try {
	            System.out.println("📥 Insertar nuevo alumno");
	            System.out.print("DNI: ");
	            String dni = sc.nextLine();
	            System.out.print("Nombre: ");
	            String nombre = sc.nextLine();
	            System.out.print("Correo: ");
	            String correo = sc.nextLine();
	            System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
	            String fecha = sc.nextLine();
	            System.out.print("ID del curso: ");
	            int idCurso = sc.nextInt();
	            sc.nextLine();

	            String sql = "INSERT INTO alumnos (dni, nombre, correo, fecha_nacimiento, id_curso) VALUES (?, ?, ?, ?, ?)";
	            try (PreparedStatement ps = conn.prepareStatement(sql)) {
	                ps.setString(1, dni);
	                ps.setString(2, nombre);
	                ps.setString(3, correo);
	                ps.setDate(4, Date.valueOf(LocalDate.parse(fecha)));
	                ps.setInt(5, idCurso);
	                ps.executeUpdate();
	                System.out.println("✅ Alumno insertado correctamente.");
	            }
	        } catch (Exception e) {
	            System.out.println("❌ Error al insertar: " + e.getMessage());
	        }
	    }

	@Override
	    public void listar() {
	        String sql = """
	            SELECT a.dni, a.nombre, a.correo, a.fecha_nacimiento, c.nombre AS curso
	            FROM alumnos a
	            JOIN cursos c ON a.id_curso = c.id_curso
	            ORDER BY a.nombre;
	        """;
	        try (PreparedStatement ps = conn.prepareStatement(sql);
	        	     ResultSet rs = ps.executeQuery()) {
	        	
	            System.out.println("\n📋 Lista de alumnos:");
	            System.out.printf("%-10s %-20s %-25s %-12s %-20s%n",
	                    "DNI", "Nombre", "Correo", "Nacimiento", "Curso");
	            System.out.println("=".repeat(90));

	            while (rs.next()) {
	                System.out.printf("%-10s %-20s %-25s %-12s %-20s%n",
	                        rs.getString("dni"),
	                        rs.getString("nombre"),
	                        rs.getString("correo"),
	                        rs.getDate("fecha_nacimiento"),
	                        rs.getString("curso"));
	            }

	        } catch (SQLException e) {
	            System.out.println("❌ Error al listar: " + e.getMessage());
	        }
	    }

		@Override
	    public void buscar() {
	        System.out.print("🔎 Introduce el DNI del alumno: ");
	        String dni = sc.nextLine();

	        String sql = """
	            SELECT a.dni, a.nombre, a.correo, a.fecha_nacimiento, c.nombre AS curso
	            FROM alumnos a
	            JOIN cursos c ON a.id_curso = c.id_curso
	            WHERE a.dni = ?;
	        """;

	        try (PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setString(1, dni);
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                System.out.println("\n👤 Alumno encontrado:");
	                System.out.println("DNI: " + rs.getString("dni"));
	                System.out.println("Nombre: " + rs.getString("nombre"));
	                System.out.println("Correo: " + rs.getString("correo"));
	                System.out.println("Nacimiento: " + rs.getDate("fecha_nacimiento"));
	                System.out.println("Curso: " + rs.getString("curso"));
	            } else {
	                System.out.println("⚠️ No se encontró ningún alumno con ese DNI.");
	            }
	        } catch (SQLException e) {
	            System.out.println("❌ Error en la búsqueda: " + e.getMessage());
	        }
	    }

		
		//Metodo para modificar, hace un select sobre un id de alumno y modifica luego ese resultset para luego hacer un updaterow();
	   @Override
	    public void modificar() {
	        System.out.print("✏️ Introduce el DNI del alumno a modificar: ");
	        String dni = sc.nextLine();

	        String sqlSelect = "SELECT * FROM alumnos WHERE dni = ?";
	        try (PreparedStatement psSelect = conn.prepareStatement(sqlSelect,
	                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
	            psSelect.setString(1, dni);
	            ResultSet rs = psSelect.executeQuery();

	            if (rs.next()) {
	                System.out.println("Nombre actual: " + rs.getString("nombre"));
	                System.out.print("Nuevo nombre (dejar vacío para no cambiar): ");
	                String nombre = sc.nextLine();
	                if (!nombre.isBlank()) rs.updateString("nombre", nombre);

	                System.out.println("Correo actual: " + rs.getString("correo"));
	                System.out.print("Nuevo correo (dejar vacío para no cambiar): ");
	                String correo = sc.nextLine();
	                if (!correo.isBlank()) rs.updateString("correo", correo);

	                System.out.println("Fecha nacimiento actual: " + rs.getDate("fecha_nacimiento"));
	                System.out.print("Nueva fecha (YYYY-MM-DD, vacío para no cambiar): ");
	                String fecha = sc.nextLine();
	                if (!fecha.isBlank()) rs.updateDate("fecha_nacimiento", Date.valueOf(LocalDate.parse(fecha)));

	                System.out.println("Curso actual ID: " + rs.getInt("id_curso"));
	                System.out.print("Nuevo ID curso (0 para no cambiar): ");
	                int idCurso = sc.nextInt();
	                sc.nextLine();
	                if (idCurso != 0) rs.updateInt("id_curso", idCurso);

	                rs.updateRow();
	                System.out.println("✅ Alumno actualizado correctamente.");
	            } else {
	                System.out.println("⚠️ No se encontró el alumno con ese DNI.");
	            }
	        } catch (SQLException e) {
	            System.out.println("❌ Error al modificar: " + e.getMessage());
	        }
	    }
	   
	   @Override
	    public void eliminar() {
	        System.out.print("🗑️ Introduce el DNI del alumno a eliminar: ");
	        String dni = sc.nextLine();

	        String sql = "DELETE FROM alumnos WHERE dni = ?";
	        try (PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setString(1, dni);
	            int filas = ps.executeUpdate();
	            if (filas > 0)
	                System.out.println("✅ Alumno eliminado correctamente.");
	            else
	                System.out.println("⚠️ No se encontró ningún alumno con ese DNI.");
	        } catch (SQLException e) {
	            System.out.println("❌ Error al eliminar: " + e.getMessage());
	        }
	    }

	    @Override
	    public void cerrar() {
	        try {
	            if (conn != null) conn.close();
	            System.out.println("🔒 Conexión cerrada.");
	        } catch (SQLException e) {
	            System.out.println("❌ Error al cerrar la conexión: " + e.getMessage());
	        }
	    }
	
}
