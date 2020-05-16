package dominio;

public class Instancia {
    
    private String instancia="";
    public String salto = "\134";

    public void annadir(String[] insercion) {
	for (int i=0; i<insercion.length; i++) {
	    String palabra = insercion[i];
	    if (instancia.isEmpty()) {
 	        if (!palabra.isEmpty() && !palabra.startsWith(salto)) {
	            instancia = palabra;
		} 
		else {instancia = instancia + ",'"; }
  	    }
	    else {
	        if (!palabra.isEmpty() && !palabra.startsWith(salto)) {
		    instancia = instancia + "','" + palabra;
		}
    		else { instancia = instancia + ",'"; }
	    }		
	 }
    }
   
    public void clear() {
    	instancia = "";
    }

    public String getInstancia() {
        return instancia;
    }
    
    public String toString(String base, String tabla) {
        return "INSERT INTO " + base + "." + tabla + "('" + getInstancia() + "');";
    }
}
