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
    
    public String getBase() { return base; }
    public String getTabla() { return tabla; }
    public String getUsuario() { return usuario; }
    public String getContrasenna() { return contrasenna; }
    public long getInicio() { return inicio; }
    public long getFin() { return fin; }
    public String getPath() { return path; }

    public void setBase(String base) { this.base = base; }
    public void setTabla(String tabla) { this.tabla = tabla; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public void setContrasenna(String contrasenna) { this.contrasenna = contrasenna; }
    public void setInicio(long inicio) { this.inicio = inicio; }
    public void setFin(long fin) { this.fin = fin; }

    // Método que obtiene el enlace físico del fichero a insertar 
    public void setPath(String fichero) {
	try {
	    String separador = System.getProperty("file.separator");
	    File f = new File(".");
	    String pathInicial = f.getCanonicalPath();
	    path = pathInicial + separador + "datos" + separador + fichero;
	}
	catch(Exception e) { System.out.println("Fallo al obtener el path del fichero"); }
    }

    // Método para copiar el archivo al directorio que se encuentra en el 'secure_file_priv'
    public void copiarArchivo(String pathOrigen, String base, String usuario, String contrasenna) {
        try {
	    Conexion c = new Conexion();
            String pathDestino = c.obtenerSFP(base, usuario, contrasenna);
            File ficheroCopiar = new File(pathOrigen);
            File ficheroDestino = new File(pathDestino);
            if(ficheroCopiar.exists()) {
                Files.copy(Paths.get(ficheroCopiar.getAbsolutePath()), Paths.get(ficheroDestino.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
            } 
	    else {
                System.out.println("El fichero no existe en " + pathOrigen);
            }
    	} catch (Exception e) { e.printStackTrace(); }
    }

    // Método para calcular el tiempo de inserción
    public String tiempo() {
    	double tiempoTotal = (getFin() - getInicio()) * Math.pow(10, -9);
	return "\nTiempo total de inserción = " + tiempoTotal + " segundos.";    
    }

    // Método para guardar los datos del fichero en un ArrayList
    public void obtenerDatos(String f) {
        try {
	    setPath(f);
	    File fichero = new File(getPath());
	    FileReader fr = new FileReader(fichero);
	    BufferedReader br = new BufferedReader(fr);

            String linea;
            while ((linea = br.readLine()) != null) {
                datos.add(linea);
            }
	}	
        catch(IOException e) { System.out.println("Fallo al obtener los datos"); }
    }

    // Método para insertar las sentencias en la base MySQL
    public void insercion(String usuario, String contrasenna, String base, String tabla, String fichero) {
        try {
	    setInicio(System.nanoTime());
	    setUsuario(usuario);
	    setContrasenna(contrasenna);
	    setBase(base);
	    setTabla(tabla);
	    obtenerDatos(fichero);
	    String instanciaSimple;
	    for(int x = 0; x < datos.size(); x++) {
                Instancia i = new Instancia();
		instanciaSimple = datos.get(x);
		String[] instanciaFinal = instanciaSimple.split("\t", 1024);
	        i.annadir(instanciaFinal);
		Conexion c = new Conexion(i.toString(getBase(), getTabla()), getBase(), getUsuario(), getContrasenna());
		i.clear();
	    }
	    setFin(System.nanoTime());
	    System.out.println(tiempo());
	    System.out.println("Datos insertados");
        }
	catch(Exception e) { System.out.println("Datos no insertados"); }
    }

    // Método para insertar las sentencias en la base MySQL optimizado
    public void insercionOptimizada(String usuario, String contrasenna, String base, String tabla, String fichero) {
        try {
	    setInicio(System.nanoTime());
	    setUsuario(usuario);
	    setContrasenna(contrasenna);
	    setBase(base);
	    setTabla(tabla);
            setPath(fichero);
	    copiarArchivo(getPath(), getBase(), getUsuario(), getContrasenna());
	    Conexion c = new Conexion(fichero, getBase(), getTabla(), getUsuario(), getContrasenna());
	    setFin(System.nanoTime());
	    System.out.println(tiempo());
	    System.out.println("Datos insertados");
	}
	catch(Exception e) { System.out.println("Datos no insertados"); }
    }
}



import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author SoftMolina
 */
public class MoverArchivoMove {

    public static void main(String[] args) {

        Path origenPath = FileSystems.getDefault().getPath("C:\\carpeta1\\ejemplo1.txt");
        Path destinoPath = FileSystems.getDefault().getPath("C:\\carpeta2\\ejemplo1.txt");

        try {
            Files.move(origenPath, destinoPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println(e);
        }

    }

}
