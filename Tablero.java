/**
 * Clase Tablero: representa la cocina del restaurante.
 *
 * Valores del mapa:
 *   0 = pasillo vacío
 *   1 = pared
 *   2 = papa frita (punto normal)
 *   3 = tocino     (poder especial)
 */
public class Tablero {

    // ── Mapa base (14 filas × 19 columnas) ───────────────────────────────────
    private static final int[][] MAPA_BASE = {
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,2,2,2,2,2,1,2,2,3,2,2,1,2,2,2,2,2,1},
        {1,2,1,1,2,1,1,2,1,1,1,2,1,1,2,1,1,2,1},
        {1,3,1,0,2,2,2,2,2,2,2,2,2,2,2,0,1,3,1},
        {1,2,1,1,2,1,1,2,1,0,1,2,1,1,2,1,1,2,1},
        {1,2,2,2,2,2,2,2,1,0,1,2,2,2,2,2,2,2,1},
        {1,1,1,2,1,1,0,0,1,0,1,0,0,1,1,2,1,1,1},
        {0,0,1,2,1,0,0,0,0,0,0,0,0,0,1,2,1,0,0},
        {1,1,1,2,1,0,1,1,0,0,0,1,1,0,1,2,1,1,1},
        {1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
        {1,2,1,1,2,1,1,2,1,1,1,2,1,1,2,1,1,2,1},
        {1,3,2,2,0,2,2,2,2,3,2,2,2,2,0,2,2,3,1},
        {1,2,1,1,2,1,2,1,1,1,1,1,2,1,2,1,1,2,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };

    private int[][] mapa;
    private int totalPapas;   // cuántas papas hay al inicio

    public Tablero() {
        reiniciar();
    }

    // ── Inicializar / reiniciar ───────────────────────────────────────────────
    public final void reiniciar() {
        int filas = MAPA_BASE.length;
        int cols  = MAPA_BASE[0].length;
        mapa = new int[filas][cols];
        totalPapas = 0;
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < cols; c++) {
                mapa[f][c] = MAPA_BASE[f][c];
                if (mapa[f][c] == 2) totalPapas++;
            }
        }
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public int[][] getMapa()      { return mapa;       }
    public int getTotalPapas()    { return totalPapas; }
    public int getFilas()         { return mapa.length;    }
    public int getColumnas()      { return mapa[0].length; }

    public int getCelda(int f, int c) { return mapa[f][c]; }

    // ── Modificar celda (cuando el aguacate come algo) ────────────────────────
    public void limpiarCelda(int f, int c) { mapa[f][c] = 0; }

    // ── Dibujar tablero en consola ────────────────────────────────────────────
    public void dibujar(Aguacate aguacate, Enemigo[] enemigos) {
        for (int f = 0; f < getFilas(); f++) {
            for (int c = 0; c < getColumnas(); c++) {

                // ¿Hay aguacate aquí?
                if (aguacate.estaEn(f, c)) {
                    System.out.print(aguacate.getEmoji());
                    continue;
                }

                // ¿Hay algún enemigo aquí?
                boolean hayEnemigo = false;
                for (Enemigo e : enemigos) {
                    if (e.estaEn(f, c)) {
                        System.out.print(e.getEmoji());
                        hayEnemigo = true;
                        break;
                    }
                }
                if (hayEnemigo) continue;

                // Pintar celda según su valor
                switch (mapa[f][c]) {
                    case 1  -> System.out.print("⬛");
                    case 2  -> System.out.print("🍟");
                    case 3  -> System.out.print("🥓");
                    default -> System.out.print("  ");   // pasillo vacío (2 espacios)
                }
            }
            System.out.println();
        }
    }
}
