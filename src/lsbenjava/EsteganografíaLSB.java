/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lsbenjava;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * NOTA: LOS COMENTARIOS CARECEN DE TILDES!
 * CLASE ESTEGANOGRAFIA POR LSB
 * @author jchierro
 */
public class EsteganografíaLSB {

    /**
     * ATRIBUTO DE LA CLASE
     */
    protected BufferedImage imagen;

    /**
     * CONSTRUCTOR DE LA CLASE
     *
     * @param imagen LA IMAGEN SE LE PASA AL CONSTRUCTOR COMO PARAMETRO
     * @throws IOException EXCEPCION
     */
    public EsteganografíaLSB(BufferedImage imagen) throws IOException {
        this.imagen = imagen;
    }

    /**
     * METODO PARA CONVERTIR UN INT A BINARIO | BASE 10 - 2
     *
     * @param num NUMERO PASADO COMO PARAMETRO PARA CONVERTIR
     * @return DEVUELVE UNA CADENA EN BINARIO
     */
    private String intABinario(int num) {
        String result = Integer.toBinaryString(num);

        // RELLENA HASTA QUE TENGA 8 BITS
        while (result.length() < 8) {
            result = "0" + result;
        }

        return result;
    }

    /**
     * METODO PARA CONVERTIR UN BINARIO A INT | BASE 2 - 10
     *
     * @param cadena CADENA BINARIA COMO PARAMETRO PARA CONVERTIR
     * @return DEVUELVE UN INT
     */
    private int binarioAInt(String cadena) {
        return Integer.parseInt(cadena, 2);
    }

    /**
     * METODO PARA CONVERTIR UNA CADENA ENTERA A BINARIO
     *
     * @param cadena CADENA DE TEXTO PASADA COMO PARAMETRO PARA CONVERTIR
     * @return DEVUELVE UNA CADENA BINARIA
     */
    private String stringABinario(String cadena) {
        String result = "";

        for (int i = 0; i < cadena.length(); i++) {
            String aux = Integer.toString(cadena.charAt(i), 2);

            // RELLENA HASTA QUE TENGA 8 BITS
            while (aux.length() < 8) {
                aux = "0" + aux;
            }

            // CONCATENA
            result = result + aux;
        }

        return result;
    }

    /**
     * METODO PARA CONVERTIR UNA CADENA BINARIA ENTERA A UNA CADENA DE TEXTO
     *
     * @param cadena CADENA BINARIA PASADA COMO PARAMETRO PARA CONVERTIR
     * @return DEVUELVE UNA CADENA DE TEXTO
     */
    private String binarioAString(String cadena) {
        String aux, result = "";

        for (int i = 0; i < cadena.length(); i += 8) {
            // SUBDIVIDE DE 8 EN 8 BITS (1 BYTE)
            aux = cadena.substring(i, i + 8);
            int num = Integer.parseInt(aux, 2);
            // CONCATENA
            result = result + (char) num;
        }

        return result;
    }

    /**
     * METODO PARA LEER EL MENSAJE INTRODUCIDO EN UNA IMAGEN
     * @return DEVUELVE UNA CADENA CON EL MENSAJE
     */
    public String leerMensaje() {
        String red, green, blue, cadena = "", result;
        // CONTADOR DE PIXELS
        int contPixel = 1;

        for (int i = 0; i < this.imagen.getHeight(); i++) {
            for (int j = 0; j < this.imagen.getWidth(); j++) {
                if (contPixel <= 531) {
                    // RECOGE EL ULTIMO BIT DE LOS 3 BYTES QUE FORMAN UN PIXEL
                    // LEE DE IZQUIERDA A DERECHA
                    Color aux = new Color(this.imagen.getRGB(j, i));

                    red = intABinario(aux.getRed());
                    green = intABinario(aux.getGreen());
                    blue = intABinario(aux.getBlue());
                    
                    // FORMA EL MENSAJE
                    // EN LOS PIXELS MULTIPLO DE 3 SOLO COGE "RED" Y "GREEN"
                    // EN EL RESTO DE PIXELS COGE "RED", "GREEN" Y "BLUE"
                    if (contPixel % 3 == 0) {
                        cadena = cadena + red.charAt(7) + green.charAt(7);
                    } else {
                        cadena = cadena + red.charAt(7) + green.charAt(7) + blue.charAt(7);
                    }

                    contPixel++;
                } else {
                    break;
                }
            }
        }

        // DEVUELVE EL MENSAJE COMPLETO
        result = binarioAString(cadena);
        return result;
    }

    /**
     * METODO PARA INTRODUCIR UN MENSAJE EN UNA IMAGEN
     * @param mensaje MENSAJE PASADO COMO PARAMETRO PARA SER INTRODUCIDO
     * @throws IOException EXCEPCION
     */
    public void introducirMensaje(String mensaje) throws IOException {
        // MENSAJE PASADO A BINARIO E INTRODUCIDO EN UN ARRAY DE CHAR
        String cadena = stringABinario(mensaje), newColor;
        char[] cadenaCaracteres = cadena.toCharArray();

        // VARIABLES VARIAS
        StringBuilder red, green, blue;
        int k = 0, longitudMensaje = mensaje.length() * 3, cont = 1;

        for (int i = 0; i < this.imagen.getHeight(); i++) {
            for (int j = 0; j < this.imagen.getWidth(); j++) {
                if (cont <= longitudMensaje) {
                    // RECOGE EL VALOR DECIMAL DE LOS "RGB" DE CADA PIXEL
                    Color aux = new Color(this.imagen.getRGB(j, i));
                        
                    // PASA CADA VALOR A BINARIO | BASE 10 A 2
                    red = new StringBuilder(intABinario(aux.getRed()));
                    green = new StringBuilder(intABinario(aux.getGreen()));
                    blue = new StringBuilder(intABinario(aux.getBlue()));

                    // EN LOS PIXELS MULTIPLOS DE 3 SOLO EDITA EL ULTIMO BIT
                    // DE "RED" Y "GREEN"
                    if (cont % 3 == 0) {
                        red.setCharAt(7, cadenaCaracteres[k]);
                        green.setCharAt(7, cadenaCaracteres[k + 1]);
                        k += 2;
                        
                        // CREA EL NUEVO COLOR "MODIFICADO" Y LO INTRODUCE
                        newColor = red.toString() + green.toString() + blue.toString();
                        this.imagen.setRGB(j, i, binarioAInt(newColor));
                    } else {
                        // EN EL RESTO DE PIXELS EDITA EL ULTIMO BIT DE "RED",
                        // "GREEN" Y "BLUE"
                        red.setCharAt(7, cadenaCaracteres[k]);
                        green.setCharAt(7, cadenaCaracteres[k + 1]);
                        blue.setCharAt(7, cadenaCaracteres[k + 2]);
                        k += 3;

                        // CREA EL NUEVO COLOR "MODIFICADO" Y LO INTRODUCE
                        newColor = red.toString() + green.toString() + blue.toString();
                        this.imagen.setRGB(j, i, binarioAInt(newColor));
                    }
                    
                    cont++;
                } else {
                    // CUANDO NO SE CUMPLA LA CONDICION SALE DEL BUCLE
                    // MEJORA EL RENDIMIENTO
                    break;
                }
            }
        }

        // ESCRIBE LA NUEVA IMAGEN CON EL MENSAJE INTRODUCIDO
        ImageIO.write(this.imagen, "bmp", new File("imagenConMensaje.bmp"));
    }

}
