/**
 * Clase abstracta base para todos los personajes del juego.
 * Aplica encapsulamiento y herencia (POO).
 */
public abstract class Personaje {

    // Atributos encapsulados
    private String nombre;
    private String emoji;
    private int fila;
    private int columna;
    private boolean activo;

    // Constructor
    public Personaje(String nombre, String emoji, int fila, int columna) {
        this.nombre  = nombre;
        this.emoji   = emoji;
        this.fila    = fila;
        this.columna = columna;
        this.activo  = true;
    }

    // ── Getters ──────────────────────────────────────────────────────────────
    public String getNombre()  { return nombre;  }
    public String getEmoji()   { return emoji;   }
    public int    getFila()    { return fila;    }
    public int    getColumna() { return columna; }
    public boolean isActivo()  { return activo;  }

    // ── Setters ──────────────────────────────────────────────────────────────
    public void setEmoji(String emoji)     { this.emoji   = emoji;   }
    public void setFila(int fila)          { this.fila    = fila;    }
    public void setColumna(int columna)    { this.columna = columna; }
    public void setActivo(boolean activo)  { this.activo  = activo;  }

    // ── Método abstracto que cada subclase debe implementar ──────────────────
    public abstract void mover(int[][] mapa);

    // ── Método concreto compartido ────────────────────────────────────────────
    public boolean estaEn(int f, int c) {
        return activo && this.fila == f && this.columna == c;
    }

    @Override
    public String toString() {
        return nombre + " en [" + fila + "," + columna + "]";
    }
}
