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

public class ContadorEnBDUpdatable {

	public static void main(String[] args) {
		final String claveContador = "contador1";
		final String sqlConsulta = "SELECT nombre,cuenta FROM contadores WHERE nombre=?";
		final String sqlActualización = "UPDATE contadores SET cuenta=? WHERE nombre=?;";
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
			 
			 //Statement consulta = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE); 
			 PreparedStatement consulta = connection.prepareStatement(sqlConsulta,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			 consulta.setString(1, claveContador);
			 int cuenta = 0;
			 
			 for (int i=0; i<1000;i++) {
				 //ResultSet res = consulta.executeQuery("SELECT nombre,cuenta FROM contador WHERE nombre='" + claveContador + "';");
				 //ResultSet res = consulta.executeQuery();
				 if (consulta.execute()) {  // por ver para qué sirve esto del boolean devuelvo por el execute ¿?
					 ResultSet res = consulta.getResultSet();
					 if (res.next()) {
						 cuenta = res.getInt(2)+1;
						 res.updateInt(2, cuenta);
						 res.updateRow();
					 }
					 //else break;
					 else System.out.println("Error");
				 }
				 //if (i%10==0) System.out.println(i/10 + "%");
			 } //
			 System.out.println("Valor final: " + cuenta);
			 
		 } // try
		 catch (SQLException e) {
			 System.out.println(e.getMessage());
		 }
		 catch (Exception e) {
			 e.printStackTrace();
		 }
	} // main

} // class
