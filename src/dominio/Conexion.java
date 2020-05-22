package dominio;

import java.sql.*;

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
            String url = "jdbc:mysql://localhost:3306/" + database + "?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
            
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, usuario, contrasenna);  
            statement = conexion.createStatement();
            statement.executeUpdate(consulta);
            
            conexion.close();
        }
        catch(ClassNotFoundException | SQLException e){ 
            System.out.println(e);
        }
    }

    // Constructor para la inserción optimizada
    public Conexion(String fichero, String database, String tabla, String usuario, String contrasenna) {        
        try{
            String url = "jdbc:mysql://localhost:3306/" + database + "?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
            
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, usuario, contrasenna);  
            statement = conexion.createStatement();
            statement.executeUpdate("LOAD DATA INFILE '" + fichero + "' INTO " + tabla + " FIELDS TERMINATED BY '" + tabulador + "';");
            
            conexion.close();
        }
        catch(ClassNotFoundException | SQLException e){ 
            System.out.println(e);
        }
    }
}
