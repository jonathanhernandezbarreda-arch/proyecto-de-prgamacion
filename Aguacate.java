import java.util.Scanner;

/**
 * Protagonista del juego: el Aguacate.
 * Hereda de Personaje y agrega lógica de invulnerabilidad y puntaje.
 */
public class Aguacate extends Personaje {

    private int  puntos;
    private int  turnosInvulnerable;   // contador de turnos con poder activo
    private static final int TURNOS_PODER = 5;

    private static final String EMOJI_NORMAL  = "🥑";
    private static final String EMOJI_PODER   = "🔥";

    public Aguacate(int fila, int columna) {
        super("Aguacate", EMOJI_NORMAL, fila, columna);
        this.puntos            = 0;
        this.turnosInvulnerable = 0;
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public int  getPuntos()          { return puntos;          }
    public int  getTurnosInvulnerable() { return turnosInvulnerable; }
    public boolean isInvulnerable()  { return turnosInvulnerable > 0; }

    // ── Acciones ──────────────────────────────────────────────────────────────
    public void sumarPunto()  { puntos++;  }

    public void activarPoder() {
        turnosInvulnerable = TURNOS_PODER;
        setEmoji(EMOJI_PODER);
        System.out.println("\n🥓 ¡TOCINO! ¡EL AGUACATE ES IMPARABLE POR " + TURNOS_PODER + " TURNOS!");
    }

    public void decrementarPoder() {
        if (turnosInvulnerable > 0) {
            turnosInvulnerable--;
            if (turnosInvulnerable == 0) {
                setEmoji(EMOJI_NORMAL);
                System.out.println("  (El poder del tocino terminó...)");
            }
        }
    }

    /**
     * Mueve al aguacate según la tecla ingresada por el jugador.
     * w=arriba  s=abajo  a=izquierda  d=derecha
     */
    @Override
    public void mover(int[][] mapa) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Mover (w/a/s/d): ");
        String input = sc.nextLine().trim().toLowerCase();

        int nuevaFila    = getFila();
        int nuevaColumna = getColumna();

        switch (input) {
            case "w" -> nuevaFila--;
            case "s" -> nuevaFila++;
            case "a" -> nuevaColumna--;
            case "d" -> nuevaColumna++;
            default  -> { System.out.println("Tecla inválida, turno perdido."); return; }
        }

        // Verificar límites y paredes (1 = pared)
        if (nuevaFila >= 0 && nuevaFila < mapa.length
                && nuevaColumna >= 0 && nuevaColumna < mapa[0].length
                && mapa[nuevaFila][nuevaColumna] != 1) {
            setFila(nuevaFila);
            setColumna(nuevaColumna);
        } else {
            System.out.println("  ¡Hay una pared ahí!");
        }
    }
}
