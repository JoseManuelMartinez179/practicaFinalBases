package dominio;

public class Instancia {
    
    private String instancia;
    
    public void add(String insercion) {
        if(instancia.isEmpty()) instancia = "'" + insercion + "'";
        else instancia = instancia + ", '" + insercion + "'";
    }
    
    public String getInstancia() {
        return instancia;
    }
    
    public String toString(String base, String tabla) {
        return "INSERT INTO" + base + "." + tabla + "(" + getInstancia() + ");";
    }
}
