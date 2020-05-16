package interfaz;

import dominio.*;

public class Interfaz {

    public static void iniciar(String args[]) {
        GenerarInstacia generador = new GenerarInstacia();
        if (args[0].equals("insert")) {
            generador.instancias(args[1], args[2], args[3], args[4], args[5]);
	}
	else if (args[0].equals("help")) {
	    System.out.println("Ayuda");
	}
	else System.out.println("\n\n Opción incorrecta "
            + "\n introducir java -jar instancias.jar help para abrir la ayuda al usuario");
	
    }
}
