/*
 Representa un punto en un plano cartesiano bidimensional.

 Esta clase encapsula las coordenadas (x, y) de un punto y proporciona
 operaciones básicas como el cálculo de distancia euclidiana entre puntos
 y comparación de igualdad basada en coordenadas.

 Los puntos son inmutables: una vez creados, sus coordenadas no pueden modificarse.
 */

 public class Punto {
    private double x;
    private double y;

    // coordenadas de un punto
    public Punto(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    // calculo de la distancia entre dos puntos usando la formula de la distancia euclidiana
    // √[(x₂-x₁)² + (y₂-y₁)²]
    public double distancia(Punto otro) {
        return Math.sqrt(Math.pow(this.x - otro.x, 2) + Math.pow(this.y - otro.y, 2));
    }

    // representacion textual de un punto
    @Override
    public String toString() {
        return String.format("(%.1f, %.1f)", x, y);
    }

    // compara si dos puntos tienen la misma coordenada
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // compara las referencias en memoria
        if (obj == null || getClass() != obj.getClass()) return false;
        Punto punto = (Punto) obj;
        return Double.compare(punto.x, x) == 0 && Double.compare(punto.y, y) == 0;
    }
}
