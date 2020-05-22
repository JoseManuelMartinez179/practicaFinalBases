package dominio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

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
    public void copiarArchivo(String fichero, String base, String usuario, String contrasenna) {
        Conexion c = new Conexion();
        setPath(fichero);
        Path pathOrigen = FileSystems.getDefault().getPath(getPath());
        String fin = c.obtenerSFP(base, usuario, contrasenna) + fichero;
        Path destinoPath = FileSystems.getDefault().getPath(fin);
        
        try {
            Files.copy(pathOrigen, destinoPath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (Exception e) { System.err.println(e); }
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
            Conexion c1 = new Conexion();
            String path = c1.obtenerSFP(getBase(), getUsuario(), getContrasenna()) + fichero;
            copiarArchivo(fichero, getBase(), getUsuario(), getContrasenna());
            Conexion c2 = new Conexion(path, getBase(), getTabla(), getUsuario(), getContrasenna());
            setFin(System.nanoTime());
            System.out.println(tiempo());
            System.out.println("Datos insertados");
        }
        catch(Exception e) { System.out.println("Datos no insertados"); }
    }
}
