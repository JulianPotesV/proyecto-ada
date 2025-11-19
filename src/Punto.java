public class Punto {
    private double x;
    private double y;

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

    public double distancia(Punto otro) {
        return Math.sqrt(Math.pow(this.x - otro.x, 2) + Math.pow(this.y - otro.y, 2));
    }

    @Override
    public String toString() {
        return String.format("(%.1f, %.1f)", x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Punto punto = (Punto) obj;
        return Double.compare(punto.x, x) == 0 && Double.compare(punto.y, y) == 0;
    }
}
