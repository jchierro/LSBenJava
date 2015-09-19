/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lsbenjava;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * 
 * @author jchierro
 */
public class Main {

    /**
     * MAIN DEL PROYECTO
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        Main x = new Main(); int opc;
        
        do {
            opc = x.menu();
            
            switch (opc) {
                case 1:
                    x.introducirMensaje();
                    break;
                case 2:
                    x.leerMensaje();
                    break;
                case 3:
                    break;
            }
        } while (opc != 3);
          
    }
    
    public int menu() {
        System.out.println("\n ## Esteganografía por LSB - JAVA ##");
        System.out.println("    1º Introducir mensaje a una imagen.");
        System.out.println("    2º Leer mensaje de una imagen.");
        System.out.println("    3º Salir. \n");
        System.out.println(" Introduzca una opción:");
        
        return Entrada.entero();
    }

    public void introducirMensaje() throws IOException {
        System.out.println("\n ## Introducir mensaje ##");
        System.out.println("Introduzca la ruta y nombre de la imagen a usar (Ejemplo: test.jpg).");
        String nombre = Entrada.cadena();
        System.out.println("Introduzca el mensaje para introducir en la imagen (Ejemplo: hola mundo!).");
        String mensaje = Entrada.cadena();
        
        EsteganografíaLSB x = new EsteganografíaLSB(ImageIO.read(new File(nombre)));
        x.introducirMensaje(mensaje);
        System.out.println("Mensaje intoducido correctamente! \n");
    }
    
    public void leerMensaje() throws IOException {
        System.out.println("\n ## Leer mensaje ##");
        System.out.println("Introduzca la ruta y nombre de la imagen a usar (Ejemplo: test.jpg).");
        String nombre = Entrada.cadena();
        //System.out.println("Introduzca el mensaje para introducir en la imagen (Ejemplo: hola mundo!).");
        //String mensaje = Entrada.cadena();
        
        EsteganografíaLSB x = new EsteganografíaLSB(ImageIO.read(new File(nombre)));
        System.out.println("Mensaje: " + x.leerMensaje() + "\n");
    }
}
