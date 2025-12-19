/**
 * Clase que proporciona métodos de ordenamiento para arreglos.
 */
public class Ordenamiento {

    /**
     * Ordena un arreglo de números decimales en orden ascendente

     * double[] arreglo = {5.2, 2.1, 4.7, 6.3, 1.9, 3.5};
     * Ordenamiento.ordenar(arreglo);
     * Resultado: {1.9, 2.1, 3.5, 4.7, 5.2, 6.3}
     */
    public static void ordenar(double[] arreglo){
        // Recorre desde el segundo elemento hasta el final del arreglo
        for (int i = 1; i < arreglo.length; i++){
            double valorActual = arreglo[i]; // Guarda el valor del elemento actual que se va a insertar
            int j = i - 1;                   // Índice para comparar con los elementos anteriores

            // Mientras haya elementos anteriores y sean mayores que el valor actual,
            // desplaza esos elementos una posición hacia la derecha mientras el valor actual es guardado en memoria
            while (j >= 0 && arreglo[j] > valorActual){
                arreglo[j + 1] = arreglo[j];
                j--;
            }

            // Inserta el valor actual en su posición correcta
            arreglo[j + 1] = valorActual;
        }
    }

    /**
     * Ordena un arreglo de puntos por su ángulo polar respecto a un punto central,
     * utilizando el algoritmo de ordenamiento por inserción.
     * Punto[] puntos = {
     *     new Punto(3.0, 1.0),  // 45° desde el centro
     *     new Punto(1.0, 3.0),  // 135° desde el centro
     *     new Punto(-1.0, 1.0), // -135° desde el centro
     *     new Punto(1.0, -1.0)  // -45° desde el centro
     * };
     *
     * // Ordenar respecto al centro (0, 0)
     * Ordenamiento.ordenarPorAngulo(puntos, 0.0, 0.0);
     */
    public static void ordenarPorAngulo(Punto[] puntos, double centroX, double centroY) {
        for (int i = 1; i < puntos.length; i++){
            Punto puntoActual = puntos[i];
            // Calcula el ángulo polar del punto actual respecto al centro
            // atan2 devuelve un valor entre -π y π radianes
            double anguloActual = Math.atan2(puntoActual.getY() - centroY, puntoActual.getX() - centroX);
            int j = i - 1;

            // Compara con los puntos anteriores para encontrar la posición correcta
            while (j >= 0) {
                // Calcula el ángulo del punto anterior para comparar
                double anguloComparar = Math.atan2(puntos[j].getY() - centroY, puntos[j].getX() - centroX);

                // Si el ángulo del punto anterior es menor o igual, ya encontramos la posición
                if (anguloComparar <= anguloActual) {
                    break;
                }

                // Desplaza el punto anterior hacia la derecha
                puntos[j + 1] = puntos[j];
                j--;
            }

            // Inserta el punto actual en su posición correcta
            puntos[j + 1] = puntoActual;
        }
    }
}
