package PracticaCRUD;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class CRUDCursos implements InterfaceCRUD{

    private Connection conn;
    private Scanner sc = new Scanner(System.in);

  //En el constructor se hace la conexión a la base de datos
    public CRUDCursos(String URL,String USER,String PASSWORD) {
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
            System.out.println("\n===== MENÚ CRUD CURSOS =====");
            System.out.println("1. Insertar curso");
            System.out.println("2. Listar cursos");
            System.out.println("3. Buscar curso por ID");
            System.out.println("4. Modificar curso");
            System.out.println("5. Eliminar curso");
            System.out.println("6. Volver al menú principal");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> insertar();
                case 2 -> listar();
                case 3 -> buscar();
                case 4 -> modificar();
                case 5 -> eliminar();
                case 6 -> System.out.println("↩️ Volviendo al menú principal...");
                default -> System.out.println("⚠️ Opción no válida.");
            }
        } while (opcion != 6);
    }
    
    @Override
    public void insertar() {
        try {
            System.out.println("📥 Insertar nuevo curso");
            System.out.print("Nombre del curso: ");
            String nombre = sc.nextLine();
            System.out.print("Tutor: ");
            String tutor = sc.nextLine();
            System.out.print("Fecha de inicio (YYYY-MM-DD): ");
            String fecha = sc.nextLine();

            String sql = "INSERT INTO cursos (nombre, tutor, fecha_inicio) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, nombre);
                ps.setString(2, tutor);
                ps.setDate(3, Date.valueOf(LocalDate.parse(fecha)));
                ps.executeUpdate();
                System.out.println("✅ Curso insertado correctamente.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error al insertar: " + e.getMessage());
        }
    }

    @Override
    public void listar() {
        String sql = "SELECT * FROM cursos ORDER BY id_curso";
        try (PreparedStatement ps = conn.prepareStatement(sql);
        	 ResultSet rs = ps.executeQuery()) {

            System.out.println("\n📚 Lista de cursos:");
            System.out.printf("%-5s %-25s %-20s %-12s%n", "ID", "Nombre", "Tutor", "Inicio");
            System.out.println("=".repeat(65));

            while (rs.next()) {
                System.out.printf("%-5d %-25s %-20s %-12s%n",
                        rs.getInt("id_curso"),
                        rs.getString("nombre"),
                        rs.getString("tutor"),
                        rs.getDate("fecha_inicio"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar: " + e.getMessage());
        }
    }

    @Override
    public void buscar() {
        System.out.print("🔎 Introduce el ID del curso: ");
        int id = sc.nextInt();
        sc.nextLine();

        String sql = "SELECT * FROM cursos WHERE id_curso = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("\n📘 Curso encontrado:");
                System.out.println("ID: " + rs.getInt("id_curso"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Tutor: " + rs.getString("tutor"));
                System.out.println("Fecha inicio: " + rs.getDate("fecha_inicio"));
            } else {
                System.out.println("⚠️ No se encontró el curso con ese ID.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error en la búsqueda: " + e.getMessage());
        }
    }
    
//Metodo para modificar, hace un select sobre un id de curso y modifica luego ese resultset para luego hacer un updaterow();
    @Override
    public void modificar() {
        System.out.print("✏️ Introduce el ID del curso a modificar: ");
        int id = sc.nextInt();
        sc.nextLine();

        String sqlSelect = "SELECT * FROM cursos WHERE id_curso = ?";
        try (PreparedStatement psSelect = conn.prepareStatement(sqlSelect,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

            psSelect.setInt(1, id);
            ResultSet rs = psSelect.executeQuery();

            if (rs.next()) {
                System.out.println("Nombre actual: " + rs.getString("nombre"));
                System.out.print("Nuevo nombre (vacío para no cambiar): ");
                String nombre = sc.nextLine();
                if (!nombre.isBlank()) rs.updateString("nombre", nombre);

                System.out.println("Tutor actual: " + rs.getString("tutor"));
                System.out.print("Nuevo tutor (vacío para no cambiar): ");
                String tutor = sc.nextLine();
                if (!tutor.isBlank()) rs.updateString("tutor", tutor);

                System.out.println("Fecha inicio actual: " + rs.getDate("fecha_inicio"));
                System.out.print("Nueva fecha (YYYY-MM-DD, vacío para no cambiar): ");
                String fecha = sc.nextLine();
                if (!fecha.isBlank()) rs.updateDate("fecha_inicio", Date.valueOf(LocalDate.parse(fecha)));

                rs.updateRow();
                System.out.println("✅ Curso actualizado correctamente.");
            } else {
                System.out.println("⚠️ No se encontró el curso con ese ID.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al modificar: " + e.getMessage());
        }
    }
    @Override
    public void eliminar() {
        System.out.print("🗑️ Introduce el ID del curso a eliminar: ");
        int id = sc.nextInt();
        sc.nextLine();

        String sql = "DELETE FROM cursos WHERE id_curso = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            if (filas > 0)
                System.out.println("✅ Curso eliminado correctamente.");
            else
                System.out.println("⚠️ No se encontró ningún curso con ese ID.");
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar: " + e.getMessage());
        }
    }

    //Método para cerrar la conexión
    @Override
    public void cerrar() {
        try {
            if (conn != null) conn.close();
            System.out.println("🔒 Conexión cerrada.");
        } catch (SQLException e) {
            System.out.println("❌ Error al cerrar: " + e.getMessage());
        }
    }
	
}
