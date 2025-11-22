import java.util.ArrayList;
import java.util.List;

/*
Esta clase modela figuras geométricas básicas (rectángulos, cuadrados, triángulos (rectangulos, acutangulos))
mediante una lista de vértices (puntos), calculando y almacenando su área.

 Características principales:
  - Inmutable: Una vez creada, no se pueden modificar sus propiedades
  - Tipos soportados: Definidos mediante el enum TipoFigura
  - Identificación única: Cada figura tiene un identificador (ej: "R1", "T2")
  - Copias defensivas: Los puntos se copian para proteger el estado interno
 */

public class Figura {
    /*
    Enumeración que define los tipos de figuras geométricas soportadas, cada tipo tiene un nombre
    interno (para uso en código) y un nombre legible (para mostrar al usuario)
    */

    public enum TipoFigura {
        RECTANGULO("Rectángulo"), // lados opuestos iguales
        CUADRADO("Cuadrado"), // todos los lados iguales
        TRIANGULO_ACUTANGULO("Triángulo Acutángulo"), // todos sus angulos agudos menores a 90º
        TRIANGULO_RECTANGULO("Triángulo Rectángulo"); // angulo recto de 90º

        private String nombre;

        // constructor del enum
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

    // constructor de una nueva figura geometrica
    public Figura(TipoFigura tipo, List<Punto> puntos, double area, String identificador) {
        this.tipo = tipo;
        this.puntos = new ArrayList<>(puntos); // copia defensiva para la lista de puntos
        this.area = area;
        this.identificador = identificador;
    }

    public TipoFigura getTipo() {
        return tipo;
    }

    // obtiene una copia de los puntos de la figura, si se modifica la lista
    // no afecta a la figura original
    public List<Punto> getPuntos() {
        return new ArrayList<>(puntos);
    }

    public double getArea() {
        return area;
    }

    public String getIdentificador() {
        return identificador;
    }

    // representacion textual de la figura
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