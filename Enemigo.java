import java.util.Random;

/**
 * Clase Enemigo: un vegetal que patrulla la cocina.
 * Hereda de Personaje; su movimiento es aleatorio.
 */
public class Enemigo extends Personaje {

    private static final Random rng = new Random();

    // Posición de "respawn" (la nevera) para cuando es comido
    private final int filaNevera;
    private final int columnaNevera;

    public Enemigo(String nombre, String emoji, int fila, int columna) {
        super(nombre, emoji, fila, columna);
        this.filaNevera    = fila;
        this.columnaNevera = columna;
    }

    // ── Acción de regreso a la nevera ─────────────────────────────────────────
    public void volverANevera() {
        setFila(filaNevera);
        setColumna(columnaNevera);
        setActivo(true);
        System.out.println("  🧊 " + getNombre() + " fue a la nevera y volvió al inicio.");
    }

    /**
     * El enemigo se mueve de forma aleatoria por casillas libres.
     */
    @Override
    public void mover(int[][] mapa) {
        if (!isActivo()) return;

        // Intentar hasta 10 veces encontrar una dirección válida
        int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
        for (int intento = 0; intento < 10; intento++) {
            int[] dir = dirs[rng.nextInt(4)];
            int nf = getFila()    + dir[0];
            int nc = getColumna() + dir[1];
            if (nf >= 0 && nf < mapa.length
                    && nc >= 0 && nc < mapa[0].length
                    && mapa[nf][nc] != 1) {
                setFila(nf);
                setColumna(nc);
                return;
            }
        }
        // Si no encontró dirección válida, se queda quieto
    }
}
