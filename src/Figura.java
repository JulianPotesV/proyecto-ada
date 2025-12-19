import java.util.List;
import java.util.ArrayList;

/**
 * Esta clase se encarga de modelar figuras geométricas básicas
 * junto con sus características.
 * Permite representar diferentes tipos de figuras (rectángulos, cuadrados,
 * triángulos) con sus puntos, área e identificador único.
 */

public class Figura {
    /**
     * Enum que representa los diferentes tipos de figuras geométricas disponibles.
     */
    public enum TipoFigura {
        RECTANGULO("Rectangulo"),
        CUADRADO("Cuadrado"),
        TRIANGULO_ACUTANGULO("Triangulo Acutangulo"),
        TRIANGULO_RECTANGULO("Triangulo Rectangulo");

        private String nombre;

        TipoFigura(String nombre) {
            this.nombre = nombre;
        }

        public String getNombre() {
            return nombre;
        }
    }

    private TipoFigura tipo;
    private List<Punto> puntos;
    private double area;
    private String identificador;

    public Figura(TipoFigura tipo, List<Punto> puntos, double area, String identificador) {
        this.tipo = tipo;
        this.puntos = new ArrayList<>(puntos);
        this.area = area;
        this.identificador = identificador;
    }

    public TipoFigura getTipo() {
        return tipo;
    }

    public List<Punto> getPuntos() {
        return new ArrayList<>(puntos);
    }

    public double getArea() {
        return area;
    }

    public String getIdentificador() {
        return identificador;
    }

    /**
     * Retorna una representación en cadena de esta figura.
     * El formato incluye el identificador, tipo, área (con 2 decimales)
     * y todos los puntos de la figura, cada uno en una línea separada.
     *
     * @return una cadena que representa esta figura con toda su información
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Figura: ").append(identificador).append("\n");
        sb.append("Tipo: ").append(tipo.getNombre()).append("\n");
        sb.append("Area: ").append(String.format("%.2f", area)).append("\n");
        sb.append("Puntos: ");
        for (Punto p : puntos) {
            sb.append(p).append(" ");
        }

        return sb.toString();
    }
}