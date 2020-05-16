package dominio;

import java.sql.*;

public class Conexion {
    
    String driver = "com.mysql.cj.jdbc.Driver";
    Connection conexion;
    Statement statement;
    ResultSet resultado;
    
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
}
