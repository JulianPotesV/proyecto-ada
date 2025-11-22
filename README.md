# Analizador de Figuras Geométricas

Aplicación de escritorio en Java que detecta y visualiza figuras geométricas (cuadrados, rectángulos y triángulos) a partir de conjuntos de puntos en un plano cartesiano.

## 🎯 Características

- **Detección automática** de figuras geométricas:
  - Cuadrados
  - Rectángulos
  - Triángulos rectángulos
  - Triángulos acutángulos

- **Visualización interactiva** en plano cartesiano con:
  - Ejes coordenados y cuadrícula
  - Puntos marcados con sus coordenadas
  - Figuras renderizadas con colores distintos

- **Análisis de datos**:
  - Tabla con detalles de cada figura detectada
  - Estadísticas en tiempo real
  - Ordenamiento por área

## 🖼️ Capturas de Pantalla

<img width="1917" height="1078" alt="image" src="https://github.com/user-attachments/assets/c420d6d9-e4ec-4372-8677-b5b03632314e" />

## 🛠️ Tecnologías

- **Java 8+**
- **Swing** (interfaz gráfica)
- **Java AWT** (renderizado)

## 📋 Requisitos

- JDK 8 o superior
- IDE compatible con Java (IntelliJ IDEA, Eclipse, NetBeans)

## 🚀 Instalación y Uso

### Clonar el repositorio
```bash
git clone https://github.com/JulianPotesV/proyecto-ada.git
cd AnalizadorFiguras
```

### Compilar y ejecutar
```bash
# Compilar
javac *.java

# Ejecutar
java AnalizadorFiguras
```

### Desde un IDE
1. Importar el proyecto
2. Ejecutar la clase `AnalizadorFiguras.java`

## 📁 Estructura del Proyecto
```
analizador-figuras/
│
├── Punto.java                  # Representa un punto (x, y)
├── Figura.java                 # Representa una figura geométrica
├── DetectorFiguras.java        # Motor de detección geométrica
├── PanelCartesiano.java        # Panel de visualización
└── AnalizadorFiguras.java      # Clase principal (GUI)
```

## 🧮 Algoritmos Utilizados

- **Teorema de Pitágoras** para detectar triángulos rectángulos
- **Ley del Coseno** para identificar triángulos acutángulos
- **Fórmula de Shoelace** para calcular áreas
- **Comparación de distancias** para verificar cuadrados y rectángulos

## 🎮 Cómo Usar

1. **Seleccionar lista**: Elige una lista de puntos del menú desplegable
2. **Visualizar**: Observa las figuras detectadas en el plano cartesiano
3. **Analizar**: Revisa la tabla con detalles de cada figura
4. **Alternar vista**: Usa los checkboxes para mostrar/ocultar puntos y figuras

## 📊 Ejemplo de Datos

La aplicación incluye 5 listas de prueba con diferentes configuraciones de puntos que forman diversas figuras geométricas.

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Por favor:

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -m 'Añadir nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

## 📝 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## ✨ Autor

**Andres Mauricio Peña Lopez**  
**Santiago**  
**Julian Potes**  

Estudiantes de Desarrollo de Software

---

⭐ Si te gustó este proyecto, ¡dale una estrella en GitHub!
