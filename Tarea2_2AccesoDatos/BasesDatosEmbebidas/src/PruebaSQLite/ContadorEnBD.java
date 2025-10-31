package PruebaSQLite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ContadorEnBD {

	public static void main(String[] args) {
		final String sqlConsulta = "SELECT cuenta FROM contadores WHERE nombre=?;";
		final String sqlActualización = "UPDATE contadores SET cuenta=? WHERE nombre=?;";
		final String sqlCrearTabla="CREATE TABLE if not exists contadores(nombre varchar(10) primary key, cuenta int);";
		final String sqlInsetarDatos="INSERT INTO contadores values('contador1',0);";
		final String claveContador = "contador1";

		try (BufferedReader br= new BufferedReader(new FileReader("ruta.ini"))){
			Properties p= new Properties();
			p.load(br);
			Connection connection = DriverManager.getConnection(p.getProperty("ruta"));
			
			PreparedStatement crearTablas = connection.prepareStatement(sqlCrearTabla);
			crearTablas.execute();
			PreparedStatement insertarDatos = connection.prepareStatement(sqlInsetarDatos);
			//Solo la 1 vez ya que si se intentan meter los mismos datos que existen da un error
			//insertarDatos.execute();
			
			PreparedStatement consulta = connection.prepareStatement(sqlConsulta);
			PreparedStatement actualización = connection.prepareStatement(sqlActualización);
			
			
			int cuenta = 0;

			consulta.setString(1, claveContador);
			actualización.setString(2, claveContador);
			for (int i = 0; i < 1000; i++) {
				ResultSet res = consulta.executeQuery();
				if (res.next()) {
					cuenta = res.getInt(1) + 1;
					actualización.setInt(1, cuenta);
					actualización.executeUpdate();
				}
				// else break;
				else
					System.out.println("Error");
				// if (i%10==0) System.out.println(i/10 + "%");
			}
			System.out.println("Valor final: " + cuenta);

		} // try
		catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // main

} // class
