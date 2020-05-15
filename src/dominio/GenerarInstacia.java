package dominio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GenerarInstacia {

    private String base;
    private String tabla;
    private String usuario;
    private String contrasenna;
    private long inicio;
    private long fin;
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

    
    public void setUser(String usuario, String contraseña) {
        setUsuario(usuario);
        setContrasenna(contraseña);
    }

    public void setBBDD(String base, String tabla) {
        setBase(base);
        setTabla(tabla);
    }
    
    public void obtenerDatos() {
        try {
            File fichero = new File("C:\\archivo.txt");
            FileReader fr = new FileReader(fichero);
            BufferedReader br = new BufferedReader(fr);

            String linea;
            while ((linea = br.readLine()) != null) {
                datos.add(linea);
            }
        } 
        catch(IOException e) {
            System.out.println(e);
        }
    }
    
    public void instancias() {
        String instanciaSimple;
        for (int x = 0; x < datos.size(); x++) {
            Instancia i = new Instancia();
            instanciaSimple = datos.get(x);
            String[] instanciaFinal = instanciaSimple.split("\t", 50);
            for (String instancia : instanciaFinal) {
                i.add(instancia);
            }
            instancias.add(i);
        }
    }

    public String tiempo() {
	    tiempoTotal = getFin() - getInicio();
	    return "\nDatos Insertados.\nTiempo total de inserción = " + tiempoTotal + ".\n";
    }

    public String insertarDatos() {
        try {
            setInicio(System.nanoTime());
            obtenerDatos();
            instancias();
            for (int i = 0; i < instancias.size(); i++) {
                String s = instancias.get(i).toString(getBase(), getTabla());
                Conexion c = new Conexion(s, getBase(), getUsuario(), getContrasenna());
            }
            setFin(System.nanoTime());
	    tiempo();
        } catch (Exception e) {
            return "\nDatos no insertados\n";
        }
    }
}
