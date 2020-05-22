package dominio;

import java.sql.*;
import java.io.InputStream;

// Clase responsable de la conexión con la Base de Datos MySQL
public class Conexion {

    public String tabulador = "\134" + "\164";
    public String driver = "com.mysql.cj.jdbc.Driver";
    
    Connection conexion;
    Statement statement;
    ResultSet resultado;
    
    // Constructor para la inserción simple
    public Conexion(String consulta, String database, String usuario, String contrasenna) {        
        try{
            String url = "jdbc:mysql://localhost:3306/" + database + 
							"?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
            
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, usuario, contrasenna);  
            statement = conexion.createStatement();
            statement.executeUpdate(consulta);
            
            conexion.close();
        }
        catch(ClassNotFoundException | SQLException e) { System.out.println(e); }
    }

    // Constructor para la inserción optimizada
    public Conexion(String fichero, String database, String tabla, String usuario, String contrasenna) {        
        try{
            String url = "jdbc:mysql://localhost:3306/" + database + 
							"?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
            
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, usuario, contrasenna);  
            statement = conexion.createStatement();
	    			statement.executeQuery("USE " + database + ";");
            statement.executeUpdate("LOAD DATA INFILE '" + fichero + "' INTO TABLE " + tabla + " FIELDS TERMINATED BY '" + tabulador + "';");
            
            conexion.close();
        }
        catch(ClassNotFoundException | SQLException e) { System.out.println(e); }
    }
    
    // Método para obtener el valor del 'secure_file_priv'
    public String obtenerSFP(String database, String usuario, String contrasenna) {        
        try{
            String url = "jdbc:mysql://localhost:3306/" + database + 
							"?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
            
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, usuario, contrasenna);  
            statement = conexion.createStatement();
	    			resultado = statement.executeQuery("SHOW VARIABLES LIKE 'secure_file_priv';");
            String value = null;
	    			while(resultado.next()) {
                String v = resultado.getString("Variable_name");
               	value = resultado.getString("Value");
            }
						
						conexion.close();
	    			return value;	
        }
        catch(ClassNotFoundException | SQLException e) { return e.toString(); }
		}

    public Conexion() {
    }
}
