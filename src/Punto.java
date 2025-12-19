/**
 * Representa un punto en un plano cartesiano bidimensional.
 * Esta clase permite crear puntos con coordenadas (x, y) y realizar
 * operaciones básicas como calcular distancias y comparar puntos.
 */

public class Punto {
    private double x;
    private double y;

    public Punto(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX(){ return x; }
    public double getY(){ return y; }

    /**
     * Calcula la distancia euclidiana entre este punto y otro punto.
     * La fórmula utilizada es: √[(x₂-x₁)² + (y₂-y₁)²]
     *
     * @param otroPunto el punto con el cual se calculará la distancia
     * @return la distancia euclidiana entre este punto y otroPunto
     * @throws NullPointerException si otroPunto es null
     */

    public double distancia(Punto otroPunto){
        return Math.sqrt(Math.pow(this.x - otroPunto.x, 2) + Math.pow(this.y - otroPunto.y, 2));
    }

    @Override
    public String toString(){ return String.format("(%.1f, %.1f)", x, y); }

    /**
     * Compara este punto con el objeto especificado para determinar si son iguales.
     * Dos puntos se consideran iguales si tienen las mismas coordenadas x e y.
     *
     * @param obj el objeto a comparar con este punto
     * @return true si el objeto es un punto con las mismas coordenadas que este punto,
     *         false en caso contrario
     */
    @Override
    public boolean equals(Object obj){
        // compara referencias entre objetos
        if (this == obj){
            return true;
        }

        // si el objeto es nulo o son de diferentes clases, retorna false
        if (obj == null || getClass() != obj.getClass()){
            return false;
        }

        Punto punto = (Punto) obj; // conversion de object a Punto

        // si ambas coordenadas son iguales (.compare devuelve 0 si son iguales), retorna true
        return Double.compare(punto.x, x) == 0 && Double.compare(punto.y, y) == 0;
    }
}