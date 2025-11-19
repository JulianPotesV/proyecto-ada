import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

public class AnalizadorFiguras extends JFrame {
    private PanelCartesiano panelCartesiano;
    private JComboBox<String> comboPuntosLista;
    private JTable tablaFiguras;
    private DefaultTableModel modeloTabla;
    private JLabel labelContadores;
    private DetectorFiguras detector;
    private Map<String, List<Punto>> listas;
    private Map<String, List<Figura>> figurasDetectadas;
    private String listaActual;

    public AnalizadorFiguras() {
        setTitle("Analizador de Figuras Geométricas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        detector = new DetectorFiguras();
        listas = new LinkedHashMap<>();
        figurasDetectadas = new HashMap<>();

        // Inicializar datos de prueba
        inicializarDatos();

        // Crear interfaz
        crearInterfaz();

        setVisible(true);
    }

    private void inicializarDatos() {
        // Lista 1: Cuadrado y Rectángulo
        List<Punto> lista1 = new ArrayList<>();
        lista1.add(new Punto(1, 1));
        lista1.add(new Punto(5, 1));
        lista1.add(new Punto(5, 5));
        lista1.add(new Punto(1, 5));
        lista1.add(new Punto(2, 2));
        lista1.add(new Punto(8, 2));
        lista1.add(new Punto(8, 5));
        lista1.add(new Punto(2, 5));
        listas.put("Lista 1", lista1);

        // Lista 2: Triángulos
        List<Punto> lista2 = new ArrayList<>();
        lista2.add(new Punto(0, 0));
        lista2.add(new Punto(4, 0));
        lista2.add(new Punto(2, 3));
        lista2.add(new Punto(-2, 0));
        lista2.add(new Punto(1, 2));
        lista2.add(new Punto(-1, 2));
        listas.put("Lista 2", lista2);

        // Lista 3: Combinado
        List<Punto> lista3 = new ArrayList<>();
        lista3.add(new Punto(1, 1));
        lista3.add(new Punto(1, 5));
        lista3.add(new Punto(5, 1));
        lista3.add(new Punto(5, 5));
        lista3.add(new Punto(7, 2));
        lista3.add(new Punto(9, 2));
        lista3.add(new Punto(8, 4));
        listas.put("Lista 3", lista3);

        // Lista 4: Múltiples figuras
        List<Punto> lista4 = new ArrayList<>();
        lista4.add(new Punto(0, 0));
        lista4.add(new Punto(3, 0));
        lista4.add(new Punto(3, 3));
        lista4.add(new Punto(0, 3));
        lista4.add(new Punto(5, 5));
        lista4.add(new Punto(7, 5));
        lista4.add(new Punto(6, 7));
        lista4.add(new Punto(-2, 2));
        lista4.add(new Punto(2, 2));
        lista4.add(new Punto(0, 4));
        listas.put("Lista 4", lista4);

        // Detectar figuras para cada lista
        for (Map.Entry<String, List<Punto>> entry : listas.entrySet()) {
            List<Figura> figuras = detector.detectarFiguras(entry.getValue());
            figuras.sort(Comparator.comparingDouble(Figura::getArea));
            figurasDetectadas.put(entry.getKey(), figuras);
        }

        listaActual = "Lista 1";
    }

    private void crearInterfaz() {
        // Panel superior con controles
        JPanel panelControl = crearPanelControl();

        // Panel central con el gráfico
        panelCartesiano = new PanelCartesiano();

        // Panel inferior con tabla de figuras
        JPanel panelTabla = crearPanelTabla();

        // Organizar en BorderLayout
        add(panelControl, BorderLayout.NORTH);
        add(panelCartesiano, BorderLayout.CENTER);
        add(panelTabla, BorderLayout.SOUTH);

        actualizarVista();
    }

    private JPanel crearPanelControl() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(new Color(240, 240, 240));

        // ComboBox para seleccionar lista
        panel.add(new JLabel("Seleccionar Lista:"));
        comboPuntosLista = new JComboBox<>(listas.keySet().toArray(new String[0]));
        comboPuntosLista.setSelectedIndex(0);
        comboPuntosLista.addActionListener(e -> {
            listaActual = (String) comboPuntosLista.getSelectedItem();
            actualizarVista();
        });
        panel.add(comboPuntosLista);

        // Botón para mostrar/ocultar puntos
        JCheckBox checkPuntos = new JCheckBox("Mostrar Puntos", true);
        checkPuntos.addActionListener(e -> panelCartesiano.setMostrarPuntos(checkPuntos.isSelected()));
        panel.add(checkPuntos);

        // Botón para mostrar/ocultar figuras
        JCheckBox checkFiguras = new JCheckBox("Mostrar Figuras", true);
        checkFiguras.addActionListener(e -> panelCartesiano.setMostrarFiguras(checkFiguras.isSelected()));
        panel.add(checkFiguras);

        // Label con contadores
        labelContadores = new JLabel();
        panel.add(new JSeparator(JSeparator.VERTICAL));
        panel.add(labelContadores);

        return panel;
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Figuras Detectadas (Ordenadas por Área)"));

        // Crear tabla
        String[] columnas = {"Identificador", "Tipo", "Área", "Puntos"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaFiguras = new JTable(modeloTabla);
        tablaFiguras.setRowHeight(30);
        tablaFiguras.getColumnModel().getColumn(3).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(tablaFiguras);
        panel.add(scrollPane, BorderLayout.CENTER);

        panel.setPreferredSize(new Dimension(0, 200));

        return panel;
    }

    private void actualizarVista() {
        List<Punto> puntosActuales = listas.get(listaActual);
        List<Figura> figurasActuales = figurasDetectadas.get(listaActual);

        // Actualizar panel cartesiano
        panelCartesiano.setPuntos(puntosActuales);
        panelCartesiano.setFiguras(figurasActuales);

        // Actualizar tabla
        modeloTabla.setRowCount(0);
        for (Figura fig : figurasActuales) {
            StringBuilder puntos = new StringBuilder();
            for (Punto p : fig.getPuntos()) {
                if (puntos.length() > 0) puntos.append(", ");
                puntos.append(p.toString());
            }

            modeloTabla.addRow(new Object[]{
                    fig.getIdentificador(),
                    fig.getTipo().getNombre(),
                    String.format("%.2f", fig.getArea()),
                    puntos.toString()
            });
        }

        // Actualizar contadores
        int cuadrados = (int) figurasActuales.stream()
                .filter(f -> f.getTipo() == Figura.TipoFigura.CUADRADO).count();
        int rectangulos = (int) figurasActuales.stream()
                .filter(f -> f.getTipo() == Figura.TipoFigura.RECTANGULO).count();
        int triRectangulos = (int) figurasActuales.stream()
                .filter(f -> f.getTipo() == Figura.TipoFigura.TRIANGULO_RECTANGULO).count();
        int triAcutangulos = (int) figurasActuales.stream()
                .filter(f -> f.getTipo() == Figura.TipoFigura.TRIANGULO_ACUTANGULO).count();

        labelContadores.setText(String.format(
                "Cuadrados: %d | Rectángulos: %d | Tri. Rectángulos: %d | Tri. Acutángulos: %d | Total: %d",
                cuadrados, rectangulos, triRectangulos, triAcutangulos, figurasActuales.size()
        ));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AnalizadorFiguras());
    }
}
