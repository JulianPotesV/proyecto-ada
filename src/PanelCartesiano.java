import javax.swing.*;
import java.awt.*;
import java.util.List;

/*
Panel visual que representa un plano cartesiano bidimensional.
    Esta clase extiende JPanel y se encarga de renderizar gráficamente:
      - Ejes cartesianos (X e Y) con sus respectivas marcas y etiquetas
      - Cuadrícula de referencia en color gris claro
      - Puntos individuales representados como círculos rojos con sus coordenadas
      - Figuras geométricas (polígonos) con relleno semitransparente, bordes sólidos y vértices marcados

    Características principales:
      - Sistema de coordenadas: Origen centrado en el panel, eje Y crece hacia arriba
      - Escala configurable: Por defecto 30 píxeles por unidad
      - Visualización controlada: Permite mostrar/ocultar puntos y figuras independientemente
      - Colores automáticos: Asigna colores distintos a cada figura de forma cíclica
      - Antialiasing: Renderizado suavizado para mejor calidad visual
 */

public class PanelCartesiano extends JPanel {
    private List<Punto> puntos;
    private List<Figura> figurasActuales;
    private int escala = 30; // píxeles por unidad
    private boolean mostrarPuntos = true;
    private boolean mostrarFiguras = true;

    public PanelCartesiano() {
        this.setBackground(Color.WHITE); //fondo blanco
    }

    // lista de puntos a dibujar
    public void setPuntos(List<Punto> puntos) {
        this.puntos = puntos;
        repaint();
    }
    // lista de figuras a dibujar
    public void setFiguras(List<Figura> figuras) {
        this.figurasActuales = figuras;
        repaint();
    }

    // mostrar u ocultar puntos
    public void setMostrarPuntos(boolean mostrar) {
        this.mostrarPuntos = mostrar;
        repaint();
    }

    // mostrar u ocultar figuras
    public void setMostrarFiguras(boolean mostrar) {
        this.mostrarFiguras = mostrar;
        repaint();
    }

    // metodo encargado del dibujo
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // calculo del centro del panel (para dibujar los ejes del plano)
        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;

        // Dibujar el plano cartesiano (ejes en cruz)
        dibujarEjes(g2d, centerX, centerY, width, height);
        dibujarCuadricula(g2d, centerX, centerY, width, height);

        // Dibujar figuras si existen
        if (mostrarFiguras && figurasActuales != null && !figurasActuales.isEmpty()) {
            dibujarFiguras(g2d, centerX, centerY);
        }

        // Dibujar puntos si existen
        if (mostrarPuntos && puntos != null && !puntos.isEmpty()) {
            dibujarPuntos(g2d, centerX, centerY);
        }
    }

    // dibuja los ejes x,y
    private void dibujarEjes(Graphics2D g2d, int centerX, int centerY, int width, int height) {
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));

        // Eje X (horizontal)
        g2d.drawLine(0, centerY, width, centerY);

        // Eje Y (vertical)
        g2d.drawLine(centerX, 0, centerX, height);

        // Flechas de los ejes
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("X", width - 20, centerY - 10);
        g2d.drawString("Y", centerX + 10, 20);

        // Marcas en los ejes
        g2d.setColor(Color.DARK_GRAY);
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));
        for (int i = -10; i <= 10; i++) {
            if (i == 0) continue;

            // convierte coordenadas a pixeles
            int x = centerX + i * escala;
            int y = centerY + i * escala;

            // Marca en eje X
            g2d.drawLine(x, centerY - 3, x, centerY + 3);
            if (i % 2 == 0) {
                g2d.drawString(String.valueOf(i), x - 5, centerY + 15);
            }

            // Marca en eje Y
            g2d.drawLine(centerX - 3, y, centerX + 3, y);
            if (i % 2 == 0) {
                g2d.drawString(String.valueOf(i), centerX - 20, y + 5);
            }
        }

        // Origen
        g2d.drawString("0", centerX - 10, centerY + 15);
    }

    // dibuja las lineas grises de fondo que forman la cuadricula del plano cartesiano
    private void dibujarCuadricula(Graphics2D g2d, int centerX, int centerY, int width, int height) {
        g2d.setColor(new Color(220, 220, 220)); // gris claro
        g2d.setStroke(new BasicStroke(1)); // grosor de linea de 1 pixel

        // Dibujo de las líneas verticales
        for (int i = -10; i <= 10; i++) {
            int x = centerX + i * escala;
            g2d.drawLine(x, 0, x, height);
        }

        // Dibujo de las líneas horizontales
        for (int i = -10; i <= 10; i++) {
            int y = centerY + i * escala;
            g2d.drawLine(0, y, width, y);
        }
    }

    // dibujar los puntos en el plano cartesiano, recorriendo la lista de puntos
    // y dibujando cada uno con color rojo y etiqueta azul
    private void dibujarPuntos(Graphics2D g2d, int centerX, int centerY) {
        g2d.setColor(Color.RED);
        for (Punto p : puntos) {
            // conversion de coordenandas matematicas a pixeles
            int x = centerX + (int)(p.getX() * escala);
            int y = centerY - (int)(p.getY() * escala);

            // Dibuja un círculo pequeño
            g2d.fillOval(x - 4, y - 4, 8, 8);

            // Etiqueta del punto
            g2d.setColor(Color.BLUE);
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            g2d.drawString(p.toString(), x + 8, y - 8);
            g2d.setColor(Color.RED);
        }
    }

    // dibuja figuras (triangulos rectangulos - acutangulos, cuadrado, rectangulo en el plano cartesiano
    private void dibujarFiguras(Graphics2D g2d, int centerX, int centerY) {
        // paleta de colores
        Color[] colores = {
                new Color(0, 100, 200), // azul
                new Color(200, 0, 100), // rosa / magenta
                new Color(0, 150, 100), // verde azulado
                new Color(200, 100, 0)  // naranja
        };


        for (int i = 0; i < figurasActuales.size(); i++) {
            // asignacion de colores para cada figura
            Figura fig = figurasActuales.get(i);
            Color color = colores[i % colores.length];

            // prepara el array de coordenadas para dibujar
            List<Punto> puntosFig = fig.getPuntos();
            int[] xPoints = new int[puntosFig.size()]; // ej. [x1, x2, x3, ...]
            int[] yPoints = new int[puntosFig.size()]; // ej. [y1, y2, y3, ...]

            // convierte los puntos (expresados matematicamente) en pixeles
            for (int j = 0; j < puntosFig.size(); j++) {
                Punto p = puntosFig.get(j);
                xPoints[j] = centerX + (int)(p.getX() * escala);
                yPoints[j] = centerY - (int)(p.getY() * escala);
            }

            // Dibujar la figura rellena semitransparente
            g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 50));
            g2d.fillPolygon(xPoints, yPoints, puntosFig.size());

            // Dibujar el borde
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawPolygon(xPoints, yPoints, puntosFig.size()); // dibujar el contorno

            // Dibujar los vértices
            g2d.setColor(Color.BLACK);
            for (int j = 0; j < puntosFig.size(); j++) {
                int x = xPoints[j];
                int y = yPoints[j];
                g2d.fillOval(x - 3, y - 3, 6, 6); // dibuja un circulo negro en cada vertice
            }
        }
    }
}