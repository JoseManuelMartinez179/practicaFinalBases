package dominio;

// Clase encargada de generar las instancias
public class Instancia {
    
    private String instancia="";
    public String salto = "\134";

    // Método para construir una instancia
    public void annadir(String[] insercion) {
			for (int i=0; i<insercion.length; i++) {
	    	String palabra = insercion[i];
	    	if (instancia.isEmpty()) {
 	        if (!palabra.isEmpty() && !palabra.startsWith(salto)) {
	            instancia = "'" + palabra + "'" ;
					} 
					else {
		    		instancia = instancia + "NULL"; 
					}
  	    }
	    	else {
	        if (!palabra.isEmpty() && !palabra.startsWith(salto)) {
		    		instancia = instancia + ",'" + palabra + "'";
					}
    			else { 
		    		instancia = instancia + ",NULL"; 
					}
	    	}		
	 		}
    }

    // Método para limpiar el String instancia
    public void clear() {
    	instancia = "";
    }

    // Método para obtener la instancia
    public String getInstancia() {
        return instancia;
    }
    
    // Método para construir la sentencia de inserción SQL completa
    public String toString(String base, String tabla) {
        return "INSERT INTO " + base + "." + tabla + " VALUES (" + getInstancia() + ");";
    }
}
