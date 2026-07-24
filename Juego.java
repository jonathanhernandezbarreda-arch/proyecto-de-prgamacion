/**
 * Clase Juego: orquesta el bucle principal y la lógica del juego.
 * Coordina al Aguacate, los Enemigos y el Tablero.
 */
public class Juego {

    private Tablero  tablero;
    private Aguacate aguacate;
    private Enemigo[] enemigos;

    private int  papasComidas;
    private boolean enJuego;

    // ── Constructor ───────────────────────────────────────────────────────────
    public Juego() {
        tablero = new Tablero();

        // Posición inicial del aguacate (centro superior)
        aguacate = new Aguacate(1, 9);

        // Los 3 vegetales enemigos con sus emojis y posiciones iniciales
        enemigos = new Enemigo[] {
            new Enemigo("Brócoli",   "🥦", 7, 3),
            new Enemigo("Zanahoria", "🥕", 7, 9),
            new Enemigo("Tomate",    "🍅", 7, 15)
        };

        papasComidas = 0;
        enJuego      = true;
    }

    // ── Bucle principal ───────────────────────────────────────────────────────
    public void iniciar() {
        mostrarBienvenida();

        while (enJuego) {
            limpiarConsola();
            mostrarEstado();
            tablero.dibujar(aguacate, enemigos);
            System.out.println();

            // 1. El jugador mueve al aguacate
            aguacate.mover(tablero.getMapa());

            // 2. Verificar lo que hay en la nueva celda
            procesarCeldaAguacate();

            // 3. Los enemigos se mueven
            for (Enemigo e : enemigos) e.mover(tablero.getMapa());

            // 4. Verificar colisiones con enemigos
            verificarColisiones();

            // 5. Decrementar poder si está activo
            aguacate.decrementarPoder();

            // 6. ¿Ganó?
            if (papasComidas >= tablero.getTotalPapas()) {
                victoria();
            }
        }
    }

    // ── Lógica de la celda donde llegó el aguacate ────────────────────────────
    private void procesarCeldaAguacate() {
        int f = aguacate.getFila();
        int c = aguacate.getColumna();
        int celda = tablero.getCelda(f, c);

        if (celda == 2) {           // Papa frita
            aguacate.sumarPunto();
            papasComidas++;
            tablero.limpiarCelda(f, c);
            System.out.println("  🍟 ¡Papas fritas! +" + 10 + " pts");
        } else if (celda == 3) {    // Tocino → poder especial
            aguacate.activarPoder();
            tablero.limpiarCelda(f, c);
        }
    }

    // ── Verificar colisiones aguacate ↔ enemigos ──────────────────────────────
    private void verificarColisiones() {
        for (Enemigo e : enemigos) {
            if (!e.isActivo()) continue;
            if (e.getFila() == aguacate.getFila() && e.getColumna() == aguacate.getColumna()) {
                if (aguacate.isInvulnerable()) {
                    // El aguacate se come al vegetal
                    System.out.println("\n😈 ¡El aguacate se comió al " + e.getNombre() + "! Lo mandó a la nevera.");
                    aguacate.sumarPunto();
                    e.volverANevera();
                } else {
                    // El vegetal atrapa al aguacate
                    gameOver();
                    return;
                }
            }
        }
    }

    // ── Pantallas de resultado ─────────────────────────────────────────────────
    private void victoria() {
        limpiarConsola();
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║   🎉 ¡CHEAT DAY COMPLETADO!  🎉      ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.printf ("║   Papas comidas : %3d / %-3d           ║%n",
                           papasComidas, tablero.getTotalPapas());
        System.out.printf ("║   Puntaje final : %-6d               ║%n",
                           aguacate.getPuntos() * 10);
        System.out.println("╚══════════════════════════════════════╝");
        enJuego = false;
    }

    private void gameOver() {
        limpiarConsola();
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║   💀 ¡TE ATRAPARON! GAME OVER 💀     ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.printf ("║   Papas comidas : %3d / %-3d           ║%n",
                           papasComidas, tablero.getTotalPapas());
        System.out.printf ("║   Puntaje final : %-6d               ║%n",
                           aguacate.getPuntos() * 10);
        System.out.println("║   Los vegetales ganaron esta vez...  ║");
        System.out.println("╚══════════════════════════════════════╝");
        enJuego = false;
    }

    // ── Utilidades de UI ──────────────────────────────────────────────────────
    private void mostrarBienvenida() {
        System.out.println("════════════════════════════════════════");
        System.out.println("   🥑  ESCAPE DE LA HAMBURGUESERÍA  🥑  ");
        System.out.println("         ★  C H E A T  D A Y  ★        ");
        System.out.println("════════════════════════════════════════");
        System.out.println(" Controles: w=arriba  s=abajo");
        System.out.println("            a=izquierda  d=derecha");
        System.out.println(" 🍟 Papa frita = 10 pts");
        System.out.println(" 🥓 Tocino     = ¡INVULNERABLE 5 turnos!");
        System.out.println(" 🥦🥕🍅 Son tus enemigos — ¡evítalos!");
        System.out.println("════════════════════════════════════════");
        System.out.println("  Presiona ENTER para comenzar...");
        try { System.in.read(); } catch (Exception ignored) {}
    }

    private void mostrarEstado() {
        String poder = aguacate.isInvulnerable()
                ? " | 🔥 PODER: " + aguacate.getTurnosInvulnerable() + " turnos"
                : "";
        System.out.println("  🥑 Puntos: " + (aguacate.getPuntos() * 10)
                + " | 🍟 " + papasComidas + "/" + tablero.getTotalPapas() + poder);
        System.out.println();
    }

    private void limpiarConsola() {
        // Funciona en terminales ANSI; en Windows puede verse diferente
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
