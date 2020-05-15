package interfaz;

import dominio.*;

public class Interfaz {

    public static void iniciar(String args[]) {
        GenerarInstacia generador = new GenerarInstacia();
        if (args[0].equals("insert")) {
        }
        else if (args[0].equals("set-user")) {
            generador.setUser(args[1], args[2]);
        }
        else if (args[0].equals("set-database-table")) {
            generador.setBBDD(args[1], args[2]);
        }
        else System.out.println("\n\n Opción incorrecta "
                + "\n introducir java -jar coches.jar help para abrir la ayuda al usuario");
    }
}
