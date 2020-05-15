package dominio;

public class Instancia {
    
    private String instancia;
    public String salto = "\134" + "N";

    public void annadir(String[] insercion) {
	for (String valores : insercion) {
	    while (!valores.equals(salto)) {
                if (instancia.isEmpty()) instancia = valores;
		else {
		    String sentencia = "','" + valores;
		    instancia.concat(sentencia);
		}
	    }
	    System.out.println(getInstancia());
	}
    }
    
    public String getInstancia() {
        return instancia;
    }
    
    public String toString(String base, String tabla) {
        return "INSERT INTO " + base + "." + tabla + "('" + getInstancia() + "');";
    }
}
