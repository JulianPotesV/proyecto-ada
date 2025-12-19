import java.util.ArrayList;
import java.util.List;

/*
clase encargada de la deteccion geometrica de figuras, analiza una lista de puntos
y encuentra todas las figuras posibles (cuadrados, rectángulos, triángulos).
*/
public class DetectorFiguras {
    // su objetivo es usarlo para comparaciones (la diferencia entre doubles)
    private static final double EPSILON = 1e-9; // epsilon es igual a 0.000000001

    public List<Figura> detectarFiguras(List<Punto> puntos) {
        List<Figura> figuras = new ArrayList<>();
        int contador = 0;

        // Detectar cuadrados (4 puntos)
        List<List<Punto>> cuadrados = detectarCuadrados(puntos);
        for (List<Punto> cuad : cuadrados) {
            double area = calcularArea(cuad);
            figuras.add(new Figura(Figura.TipoFigura.CUADRADO, cuad, area, "Cuadrado_" + (++contador)));
        }

        // Detectar rectángulos (4 puntos) que no sean cuadrados
        List<List<Punto>> rectangulos = detectarRectangulos(puntos);
        for (List<Punto> rect : rectangulos) {
            if (!esUnoCuadrado(rect)) { // evita duplicados
                double area = calcularArea(rect);
                figuras.add(new Figura(Figura.TipoFigura.RECTANGULO, rect, area, "Rectangulo_" + (++contador)));
            }
        }

        // Detectar triángulos rectángulos
        List<List<Punto>> triangulosRect = detectarTriangulosRectangulos(puntos);
        for (List<Punto> tri : triangulosRect) {
            double area = calcularArea(tri);
            figuras.add(new Figura(Figura.TipoFigura.TRIANGULO_RECTANGULO, tri, area, "TrianguloRectangulo_" + (++contador)));
        }

        // Detectar triángulos acutángulos
        List<List<Punto>> triangulosAcuta = detectarTriangulosAcutangulos(puntos);
        for (List<Punto> tri : triangulosAcuta) {
            double area = calcularArea(tri);
            figuras.add(new Figura(Figura.TipoFigura.TRIANGULO_ACUTANGULO, tri, area, "TrianguloAcutangulo_" + (++contador)));
        }

        return figuras;
    }

    // prueba todas las combinaciones posibles de 4 puntos y revisar si forman un cuadrado
    // algoritmo 1
    private List<List<Punto>> detectarCuadrados(List<Punto> puntos) {
        List<List<Punto>> cuadrados = new ArrayList<>();
        int n = puntos.size();

        // realiza combinatoria de c(n,4)
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {
                        List<Punto> cuatro = new ArrayList<>();
                        cuatro.add(puntos.get(i));
                        cuatro.add(puntos.get(j));
                        cuatro.add(puntos.get(k));
                        cuatro.add(puntos.get(l));

                        if (esCuadrado(cuatro)) {
                            cuadrados.add(ordenarPuntos(cuatro));
                        }
                    }
                }
            }
        }
        return cuadrados;
    }

    // algoritmo 2
    private List<List<Punto>> detectarRectangulos(List<Punto> puntos) {
        List<List<Punto>> rectangulos = new ArrayList<>();
        int n = puntos.size();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {
                        List<Punto> cuatro = new ArrayList<>();
                        cuatro.add(puntos.get(i));
                        cuatro.add(puntos.get(j));
                        cuatro.add(puntos.get(k));
                        cuatro.add(puntos.get(l));

                        if (esRectangulo(cuatro)) {
                            rectangulos.add(ordenarPuntos(cuatro));
                        }
                    }
                }
            }
        }
        return rectangulos;
    }

    // algoritmo 3
    private List<List<Punto>> detectarTriangulosRectangulos(List<Punto> puntos) {
        List<List<Punto>> triangulos = new ArrayList<>();
        int n = puntos.size();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    List<Punto> tres = new ArrayList<>();
                    tres.add(puntos.get(i));
                    tres.add(puntos.get(j));
                    tres.add(puntos.get(k));

                    if (esTrianguloRectangulo(tres)) {
                        triangulos.add(tres);
                    }
                }
            }
        }
        return triangulos;
    }

    // algoritmo 4
    private List<List<Punto>> detectarTriangulosAcutangulos(List<Punto> puntos) {
        List<List<Punto>> triangulos = new ArrayList<>();
        int n = puntos.size();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    List<Punto> tres = new ArrayList<>();
                    tres.add(puntos.get(i));
                    tres.add(puntos.get(j));
                    tres.add(puntos.get(k));

                    if (esTrianguloAcutangulo(tres)) {
                        triangulos.add(tres);
                    }
                }
            }
        }
        return triangulos;
    }

    // algoritmo 5
    private boolean esCuadrado(List<Punto> puntos) {
        if (puntos.size() != 4) return false;

        double[] lados = new double[6]; // 6 distancias entre 4 puntos, 4 lados y 2 diagonales
        int idx = 0;

        // calcular todas las distancias entre los 4 puntos
        for (int i = 0; i < puntos.size(); i++) {
            for (int j = i + 1; j < puntos.size(); j++) {
                lados[idx++] = puntos.get(i).distancia(puntos.get(j));
            }
        }

        Ordenamiento.ordenar(lados); // Ordenar de menor a mayor

        // En un cuadrado: 4 lados iguales y 2 diagonales iguales
        boolean cuatroLadosIguales = Math.abs(lados[0] - lados[3]) < EPSILON;
        boolean dosDiagonalesIguales = Math.abs(lados[4] - lados[5]) < EPSILON;
        boolean diagonalMayorQueLado = lados[4] > lados[0];

        return cuatroLadosIguales && dosDiagonalesIguales && diagonalMayorQueLado;
    }

    // algoritmo 6
    private boolean esRectangulo(List<Punto> puntos) {
        if (puntos.size() != 4) return false;

        double[] lados = new double[6];
        int idx = 0;

        for (int i = 0; i < puntos.size(); i++) {
            for (int j = i + 1; j < puntos.size(); j++) {
                lados[idx++] = puntos.get(i).distancia(puntos.get(j));
            }
        }

        Ordenamiento.ordenar(lados); // Ordenar de menor a mayor

        // En un rectángulo: 2 pares de lados iguales y 2 diagonales iguales
        boolean dosParesLados = Math.abs(lados[0] - lados[1]) < EPSILON && Math.abs(lados[2] - lados[3]) < EPSILON;
        boolean dosDiagonalesIguales = Math.abs(lados[4] - lados[5]) < EPSILON;
        boolean teoremaPitagoras = Math.abs(lados[0] * lados[0] + lados[2] * lados[2] - lados[4] * lados[4]) < EPSILON;

        return dosParesLados && dosDiagonalesIguales && teoremaPitagoras;
    }

    private boolean esUnoCuadrado(List<Punto> puntos) {
        return esCuadrado(puntos);
    }

    // algoritmo 7
    private boolean esTrianguloRectangulo(List<Punto> puntos) {
        if (puntos.size() != 3) return false;

        double a = puntos.get(0).distancia(puntos.get(1));
        double b = puntos.get(1).distancia(puntos.get(2));
        double c = puntos.get(0).distancia(puntos.get(2));

        double[] lados = {a, b, c};
        Ordenamiento.ordenar(lados); // Ordenar de menor a mayor

        // Teorema de Pitágoras: a² + b² = c²
        return Math.abs(lados[0] * lados[0] + lados[1] * lados[1] - lados[2] * lados[2]) < EPSILON;
    }

    // algoritmo 8
    private boolean esTrianguloAcutangulo(List<Punto> puntos) {
        if (puntos.size() != 3) return false;

        // Primero verificar que es un triángulo válido
        double a = puntos.get(0).distancia(puntos.get(1));
        double b = puntos.get(1).distancia(puntos.get(2));
        double c = puntos.get(0).distancia(puntos.get(2));

        // Desigualdad triangular
        if (a + b <= c || b + c <= a || a + c <= b) return false;

        // No puede ser rectángulo
        double[] lados = {a, b, c};
        Ordenamiento.ordenar(lados); // Ordenar de menor a mayor
        if (Math.abs(lados[0] * lados[0] + lados[1] * lados[1] - lados[2] * lados[2]) < EPSILON) {
            return false;
        }

        // Todos los ángulos deben ser menores a 90° (acutángulo)
        // Usar ley de cosenos: cos(ángulo) = (b² + c² - a²) / (2*b*c)
        // Si todos los cosenos son positivos, todos los ángulos son agudos
        double cosA = (b * b + c * c - a * a) / (2 * b * c);
        double cosB = (a * a + c * c - b * b) / (2 * a * c);
        double cosC = (a * a + b * b - c * c) / (2 * a * b);

        return cosA > EPSILON && cosB > EPSILON && cosC > EPSILON;
    }

    public double calcularArea(List<Punto> puntos) {
        if (puntos.size() == 3) {
            return calcularAreaTriangulo(puntos);
        } else if (puntos.size() == 4) {
            return calcularAreaCuadrilatero(puntos);
        }
        return 0;
    }

    // algoritmo 9
    private double calcularAreaTriangulo(List<Punto> puntos) {
        // Fórmula de Shoelace
        Punto p1 = puntos.get(0);
        Punto p2 = puntos.get(1);
        Punto p3 = puntos.get(2);

        double area = Math.abs((p1.getX() * (p2.getY() - p3.getY()) + p2.getX() * (p3.getY() - p1.getY()) + p3.getX() * (p1.getY() - p2.getY())) / 2.0);
        return area;
    }

    // algoritmo 10
    private double calcularAreaCuadrilatero(List<Punto> puntos) {
        // Fórmula de Shoelace para cuadrilátero
        List<Punto> ordenados = ordenarPuntos(puntos);
        double area = 0;
        int n = ordenados.size();

        for (int i = 0; i < n; i++) {
            int j = (i + 1) % n;
            area += ordenados.get(i).getX() * ordenados.get(j).getY();
            area -= ordenados.get(j).getX() * ordenados.get(i).getY();
        }

        return Math.abs(area / 2.0);
    }

    // algoritmo 11
    private List<Punto> ordenarPuntos(List<Punto> puntos) {
        // Encontrar el centroide
        double centroX = 0, centroY = 0;
        for (Punto p : puntos) {
            centroX += p.getX();
            centroY += p.getY();
        }
        centroX /= puntos.size();
        centroY /= puntos.size();

        // Convertir List a Array (porque nuestro algoritmo trabaja con arrays)
        Punto[] arregloPuntos = puntos.toArray(new Punto[0]);

        // Ordenar usando nuestro algoritmo propio
        Ordenamiento.ordenarPorAngulo(arregloPuntos, centroX, centroY);

        // Convertir de vuelta a List
        List<Punto> ordenados = new ArrayList<>();
        for (Punto p : arregloPuntos) {
            ordenados.add(p);
        }

        return ordenados;
    }
}
