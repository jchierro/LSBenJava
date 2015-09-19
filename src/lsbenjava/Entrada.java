package lsbenjava;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
/**
 *
 * @author alfonso
 */
public class Entrada {
    private static String inicializar(){
        String buzon="";
        try{
        InputStreamReader flujo=new InputStreamReader(System.in,"ISO-8859-1");
        BufferedReader teclado=new BufferedReader(flujo);
        buzon=teclado.readLine();
        }
        catch(Exception e){
            System.out.append("Entrada incorrecta)");
        }

        return buzon;
    }

    public static int entero(){
        int valor=Integer.parseInt(inicializar());
        return valor;
    }

    public static double real(){
        double valor=Double.parseDouble(inicializar());
        return valor;
    }

    public static String cadena(){
        String valor=inicializar();
        return valor;
    }

    public static char caracter(){
        String valor=inicializar();
        return valor.charAt(0);
    }
}
