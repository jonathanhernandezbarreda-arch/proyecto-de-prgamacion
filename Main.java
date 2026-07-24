/**
 * Punto de entrada del juego "Escape de la Hamburguesería - Cheat Day".
 *
 * Para compilar y ejecutar:
 *   javac src/*.java -d out/
 *   java  -cp out Main
 */
public class Main {
    public static void main(String[] args) {
        Juego juego = new Juego();
        juego.iniciar();
    }
}
