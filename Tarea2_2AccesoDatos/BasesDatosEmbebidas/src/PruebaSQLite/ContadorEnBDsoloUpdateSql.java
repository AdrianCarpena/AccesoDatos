package PruebaSQLite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ContadorEnBDsoloUpdateSql {

	public static void main(String[] args) {
		final String claveContador = "contador1";
		// La actualización en el propio SQL sí es atómica:
		final String sqlActualización = "UPDATE contadores SET cuenta=cuenta+1 WHERE nombre='" + claveContador + "';";
		final String sqlCrearTabla="CREATE TABLE if not exists contadores(nombre varchar(10) primary key, cuenta int);";
		final String sqlInsetarDatos="INSERT INTO contadores values('contador1',0);";
	
		 try(BufferedReader br= new BufferedReader(new FileReader("ruta.ini"))){
			 
			 Properties p= new Properties();
			 p.load(br);
			 Connection connection = DriverManager.getConnection(p.getProperty("ruta"));
			 PreparedStatement crearTablas = connection.prepareStatement(sqlCrearTabla);
			 crearTablas.execute();
			 PreparedStatement insertarDatos = connection.prepareStatement(sqlInsetarDatos);
				//Solo la 1 vez ya que si se intentan meter los mismos datos que existen da un error
				//insertarDatos.execute();
			 PreparedStatement actualización = connection.prepareStatement(sqlActualización);
			 for (int i=0; i<1000;i++) {
				 if (actualización.executeUpdate() != 1) break;
				//if (i%10==0) System.out.println(i/10 + "%");
			 }
			 
			 
		 } // try
		 catch (SQLException e) {
			 System.out.println(e.getMessage());
		 }
		 catch (Exception e) {
			 e.printStackTrace();
		 }
	} // main

} // class
