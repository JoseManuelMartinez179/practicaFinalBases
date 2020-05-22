package interfaz;

import dominio.*;

// Implementa una interfaz de tipo texto para la inserción de ficheros
public class Interfaz {

    public static void iniciar(String args[]) {
        GenerarInstacia generador = new GenerarInstacia();
        if (args[0].equals("insert")) {
          generador.insercion(args[1], args[2], args[3], args[4], args[5]);
				}
				else if (args[0].equals("optimized-insert")) {
	    		generador.insercionOptimizada(args[1], args[2], args[3], args[4], args[5]);
				}
				else if (args[0].equals("help")) {
	    		System.out.println("\nPara importar un fichero --> java -jar instancias.jar insert <Usuario MySQL> <Contraseña> <Base de Datos> <Tabla> <Nombre del fichero> \n\n"+
			    "Para importar un fichero (función optimizada) --> java -jar instancias.jar optimized-insert <Usuario MySQL> <Contraseña> <Base de Datos> <Tabla> <Nombre del fichero>\n\n" +
			    "Para poder importar un fichero este primero debe estar copiado en la carpeta 'datos' del proyecto.\n");
				}
				else System.out.println("\nOpción incorrecta "
            + "\n introducir 'java -jar instancias.jar help' para abrir la ayuda al usuario\n");
    }
}
