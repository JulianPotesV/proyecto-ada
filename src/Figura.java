import java.util.ArrayList;
import java.util.List;

public class Figura {
    public enum TipoFigura {
        RECTANGULO("Rectángulo"),
        CUADRADO("Cuadrado"),
        TRIANGULO_ACUTANGULO("Triángulo Acutángulo"),
        TRIANGULO_RECTANGULO("Triángulo Rectángulo");

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Figura: ").append(identificador).append("\n");
        sb.append("Tipo: ").append(tipo.getNombre()).append("\n");
        sb.append("Área: ").append(String.format("%.2f", area)).append("\n");
        sb.append("Puntos: ");
        for (Punto p : puntos) {
            sb.append(p).append(" ");
        }
        return sb.toString();
    }
}