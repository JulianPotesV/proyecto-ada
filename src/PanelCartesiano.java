import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelCartesiano extends JPanel {
    private List<Punto> puntos;
    private List<Figura> figurasActuales;
    private int escala = 30; // píxeles por unidad
    private boolean mostrarPuntos = true;
    private boolean mostrarFiguras = true;

    public PanelCartesiano() {
        this.setBackground(Color.WHITE);
    }

    public void setPuntos(List<Punto> puntos) {
        this.puntos = puntos;
        repaint();
    }

    public void setFiguras(List<Figura> figuras) {
        this.figurasActuales = figuras;
        repaint();
    }

    public void setMostrarPuntos(boolean mostrar) {
        this.mostrarPuntos = mostrar;
        repaint();
    }

    public void setMostrarFiguras(boolean mostrar) {
        this.mostrarFiguras = mostrar;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

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

        // Dibujar puntos
        if (mostrarPuntos && puntos != null && !puntos.isEmpty()) {
            dibujarPuntos(g2d, centerX, centerY);
        }
    }

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

    private void dibujarCuadricula(Graphics2D g2d, int centerX, int centerY, int width, int height) {
        g2d.setColor(new Color(220, 220, 220));
        g2d.setStroke(new BasicStroke(1));

        // Líneas verticales
        for (int i = -10; i <= 10; i++) {
            int x = centerX + i * escala;
            g2d.drawLine(x, 0, x, height);
        }

        // Líneas horizontales
        for (int i = -10; i <= 10; i++) {
            int y = centerY + i * escala;
            g2d.drawLine(0, y, width, y);
        }
    }

    private void dibujarPuntos(Graphics2D g2d, int centerX, int centerY) {
        g2d.setColor(Color.RED);
        for (Punto p : puntos) {
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

    private void dibujarFiguras(Graphics2D g2d, int centerX, int centerY) {
        Color[] colores = {
                new Color(0, 100, 200),
                new Color(200, 0, 100),
                new Color(0, 150, 100),
                new Color(200, 100, 0)
        };

        for (int i = 0; i < figurasActuales.size(); i++) {
            Figura fig = figurasActuales.get(i);
            Color color = colores[i % colores.length];

            List<Punto> puntosFig = fig.getPuntos();
            int[] xPoints = new int[puntosFig.size()];
            int[] yPoints = new int[puntosFig.size()];

            for (int j = 0; j < puntosFig.size(); j++) {
                Punto p = puntosFig.get(j);
                xPoints[j] = centerX + (int)(p.getX() * escala);
                yPoints[j] = centerY - (int)(p.getY() * escala);
            }

            // Dibujar la figura rellena
            g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 50));
            g2d.fillPolygon(xPoints, yPoints, puntosFig.size());

            // Dibujar el borde
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawPolygon(xPoints, yPoints, puntosFig.size());

            // Dibujar los vértices
            g2d.setColor(Color.BLACK);
            for (int j = 0; j < puntosFig.size(); j++) {
                int x = xPoints[j];
                int y = yPoints[j];
                g2d.fillOval(x - 3, y - 3, 6, 6);
            }
        }
    }
}