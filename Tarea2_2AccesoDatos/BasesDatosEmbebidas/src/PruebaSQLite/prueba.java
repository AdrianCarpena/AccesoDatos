package PruebaSQLite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class prueba {
	
	public static void main(String[] args) {
		try {
		Connection connection = DriverManager.getConnection("jdbc:sqlite:/home/javi/bbdd/sqlite/contadores.db");
		}catch (SQLException e) {
			e.getStackTrace();
		}
	}

}
