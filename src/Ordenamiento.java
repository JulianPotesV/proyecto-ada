public class Ordenamiento {
    // Ordena una arreglo de minimo 3 elementos y maximo 6 de menor a mayor
    public static void ordenar(double[] arreglo) {
        // Recorre desde el segundo elemento hasta el final
        for (int i = 1; i < arreglo.length; i++) {
            // Guarda el valor actual que vamos a insertar
            double valorActual = arreglo[i];
            int j = i - 1;

            // Mueve todos los elementos mayores una posición a la derecha
            while (j >= 0 && arreglo[j] > valorActual) {
                arreglo[j + 1] = arreglo[j];
                j--;
            }

            // Inserta el valor en su posición correcta
            arreglo[j + 1] = valorActual;
        }
    }

    /*
      Ordena un arreglo de Puntos por ángulo respecto a un centroide

      Se usa para ordenar los vértices de un polígono en sentido antihorario
      partiendo desde el centroide (centro geométrico).

      @param puntos Arreglo de Puntos a ordenar
      @param centroX Coordenada X del centroide
      @param centroY Coordenada Y del centroide
    */
    public static void ordenarPorAngulo(Punto[] puntos, double centroX, double centroY) {
        // Usando Insertion Sort adaptado para comparar ángulos
        for (int i = 1; i < puntos.length; i++) {
            Punto puntoActual = puntos[i];
            // Calcular ángulo del punto actual respecto al centroide
            double anguloActual = Math.atan2(puntoActual.getY() - centroY,
                    puntoActual.getX() - centroX);

            int j = i - 1;

            // Mover puntos con ángulo mayor hacia la derecha
            while (j >= 0) {
                double anguloComparar = Math.atan2(puntos[j].getY() - centroY,
                        puntos[j].getX() - centroX);

                if (anguloComparar <= anguloActual) {
                    break; // Ya encontramos la posición correcta
                }

                puntos[j + 1] = puntos[j];
                j--;
            }

            puntos[j + 1] = puntoActual;
        }
    }
}
