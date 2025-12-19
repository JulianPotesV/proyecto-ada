import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
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

    // Variables para el desplazamiento (Panning)
    private int offsetX = 0;
    private int offsetY = 0;
    private Point mousePt; // Para guardar la posición del mouse al hacer clic

    public PanelCartesiano() {
        this.setBackground(Color.WHITE); //fondo blanco

        // Configurar interacción con el mouse
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePt = e.getPoint(); // Guardar dónde se hizo clic inicial
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - mousePt.x;
                int dy = e.getY() - mousePt.y;

                offsetX += dx; // Actualizar desplazamiento horizontal
                offsetY += dy; // Actualizar desplazamiento vertical

                mousePt = e.getPoint(); // Actualizar punto de referencia
                repaint(); // Redibujar el panel con los nuevos offsets
            }

            @Override
            public void mouseWheelMoved(    MouseWheelEvent e) {
                // Zoom simple
                if (e.getWheelRotation() < 0) {
                    escala += 2; // Zoom In (Acercar)
                } else {
                    escala = Math.max(5, escala - 2); // Zoom Out (Alejar) con límite mínimo
                }
                repaint();
            }
        };

        // Añadir los listeners al panel
        this.addMouseListener(ma);
        this.addMouseMotionListener(ma);
        this.addMouseWheelListener(ma);
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

        // Activar suavizado para gráficos de alta calidad
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Cálculo del centro dinámico (incluyendo el desplazamiento offsetX/Y)
        int width = getWidth();
        int height = getHeight();

        // Aquí es donde aplicamos el "panning": el centro se mueve según lo que hayas arrastrado
        int centerX = (width / 2) + offsetX;
        int centerY = (height / 2) + offsetY;

        // Dibujar elementos en orden (capas de abajo hacia arriba)
        dibujarCuadricula(g2d, centerX, centerY, width, height);
        dibujarEjes(g2d, centerX, centerY, width, height);

        // Dibujar figuras si existen y está activa la opción
        if (mostrarFiguras && figurasActuales != null && !figurasActuales.isEmpty()) {
            dibujarFiguras(g2d, centerX, centerY);
        }

        // 4. Dibujar puntos si existen y está activa la opción
        if (mostrarPuntos && puntos != null && !puntos.isEmpty()) {
            dibujarPuntos(g2d, centerX, centerY);
        }
    }

    // dibuja los ejes x,y
    private void dibujarEjes(Graphics2D g2d, int centerX, int centerY, int width, int height) {
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1));

        // Dibujar líneas de los ejes X e Y
        g2d.drawLine(0, centerY, width, centerY);  // Eje X
        g2d.drawLine(centerX, 0, centerX, height); // Eje Y

        // Dibujar marcas y números en los ejes
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));

        // Marcas en Eje X
        for (int i = centerX + escala; i < width; i += escala) dibujarMarcaX(g2d, i, centerY, (i - centerX) / escala);
        for (int i = centerX - escala; i > 0; i -= escala) dibujarMarcaX(g2d, i, centerY, (i - centerX) / escala);

        // Marcas en Eje Y
        for (int i = centerY - escala; i > 0; i -= escala) dibujarMarcaY(g2d, centerX, i, (centerY - i) / escala);
        for (int i = centerY + escala; i < height; i += escala) dibujarMarcaY(g2d, centerX, i, (centerY - i) / escala);
    }

    private void dibujarMarcaX(Graphics2D g2d, int x, int y, int valor) {
        g2d.drawLine(x, y - 3, x, y + 3);
        g2d.drawString(String.valueOf(valor), x - 5, y + 15);
    }

    private void dibujarMarcaY(Graphics2D g2d, int x, int y, int valor) {
        g2d.drawLine(x - 3, y, x + 3, y);
        g2d.drawString(String.valueOf(valor), x + 5, y + 5);
    }

    // dibuja las lineas grises de fondo que forman la cuadricula del plano cartesiano
    private void dibujarCuadricula(Graphics2D g2d, int centerX, int centerY, int width, int height) {
        g2d.setColor(new Color(230, 230, 230)); // Gris muy claro

        // Líneas verticales (partiendo del centro hacia la derecha e izquierda)
        for (int i = centerX; i < width; i += escala) g2d.drawLine(i, 0, i, height);
        for (int i = centerX; i > 0; i -= escala) g2d.drawLine(i, 0, i, height);

        // Líneas horizontales (partiendo del centro hacia abajo y arriba)
        for (int i = centerY; i < height; i += escala) g2d.drawLine(0, i, width, i);
        for (int i = centerY; i > 0; i -= escala) g2d.drawLine(0, i, width, i);
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