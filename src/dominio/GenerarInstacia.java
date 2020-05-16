package dominio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GenerarInstacia {

    private String base, tabla, usuario, contrasenna, path;
    private long inicio, fin;
    ArrayList<String> datos = new ArrayList<>();
    ArrayList<Instancia> instancias = new ArrayList<>();
    
    public String getBase() { return base; }
    public String getTabla() { return tabla; }
    public String getUsuario() { return usuario; }
    public String getContrasenna() { return contrasenna; }
    public long getInicio() { return inicio; }
    public long getFin() { return fin; }
    
    public void setBase(String base) { this.base = base; }
    public void setTabla(String tabla) { this.tabla = tabla; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public void setContrasenna(String contrasenna) { this.contrasenna = contrasenna; }
    public void setInicio(long inicio) { this.inicio = inicio; }
    public void setFin(long fin) { this.fin = fin; }

    public String tiempo() {
    	long tiempoTotal = getFin() - getInicio();
	return "\nDatos insertados correctamente.\nTiempo total de inserción = " 
		+ tiempoTotal + " nanosegundos.";    
    }

    public void setPath(String fichero) {
	try {
	    String separador = System.getProperty("file.separator");
	    File f = new File(".");
	    String pathInicial = f.getCanonicalPath();
	    path = pathInicial + separador + "datos" + separador + fichero;
	}
	catch (Exception e) { 
            System.out.println("Fallo al obtener el path del fichero");
	}
    }

    public String getPath() {
        return path;	
    }

    public void obtenerDatos(String f) {
        try {
	    setPath(f);
	    System.out.println(f);
	    File fichero = new File(getPath());
	    FileReader fr = new FileReader(fichero);
	    BufferedReader br = new BufferedReader(fr);

            String linea;
            while ((linea = br.readLine()) != null) {
                datos.add(linea);
            }
	}	
        catch(IOException e) { 
	    System.out.println("Fallo al obtener los datos"); 
	}
    }
    
    public void instancias() {
        try {
	    String instanciaSimple;
	    Instancia i = new Instancia();
	    for (int x = 0; x < datos.size(); x++) {
                instanciaSimple = datos.get(x);
		String[] instanciaFinal = instanciaSimple.split("\t", 50);
	        i.annadir(instanciaFinal);
		instancias.add(i);
	    }
        }
	catch (Exception e) {
	    System.out.println("Fallo al generar las instancias");
	}
    }

    public void insertarDatos(String usuario, String contrasenna, String base, String tabla, String fichero) {
        try {
            setInicio(System.nanoTime());
	    setUsuario(usuario);
	    setContrasenna(contrasenna);
	    setBase(base);
	    setTabla(tabla);
            obtenerDatos(fichero);
            instancias();
            for (int i = 0; i < instancias.size(); i++) {
                String s = instancias.get(i).toString(getBase(), getTabla());
                Conexion c = new Conexion(s, getBase(), getUsuario(), getContrasenna());
            }
            setFin(System.nanoTime());
	    System.out.println(tiempo());
        } catch (Exception e) {
            System.out.println("\nDatos no insertados\n");
        }
    }
}
