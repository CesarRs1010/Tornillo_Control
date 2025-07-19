package interfaz;

import estructuras.GrafoAlmacen;
import java.awt.*;
import java.util.List;
import java.util.Set;
import javax.swing.*;

public class RutaPanel extends JPanel {
    private GrafoAlmacen grafo;  // Grafo que representa las rutas entre nodos
    private JTextArea area;  // Área para mostrar los resultados de las rutas

    // Constructor que recibe el grafo de rutas
    public RutaPanel(GrafoAlmacen grafo) {
        this.grafo = grafo;  // Asigna el grafo
        setLayout(new BorderLayout());  // Configura el layout del panel
        setBackground(new Color(245, 245, 245));  // Establece el fondo claro

        // Título del panel
        JLabel titulo = new JLabel("Cálculo de Ruta Óptima", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));  // Establece la fuente y el tamaño del título
        titulo.setForeground(new Color(0, 51, 102));  // Establece el color del texto
        add(titulo, BorderLayout.NORTH);  // Agrega el título en la parte superior

        // Configuración del área de resultados (donde se mostrará la ruta y la distancia)
        area = new JTextArea();
        area.setEditable(false);  // Deshabilita la edición
        area.setFont(new Font("Segoe UI", Font.PLAIN, 13));  // Establece la fuente
        area.setBackground(Color.WHITE);  // Establece el fondo blanco
        area.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));  // Agrega un borde gris alrededor
        add(new JScrollPane(area), BorderLayout.CENTER);  // Agrega el área dentro de un JScrollPane para que sea desplazable

        // Panel de botones para realizar acciones
        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(new Color(245, 245, 245));  // Fondo claro del panel de botones

        // Botones para calcular ruta óptima y ver nodos
        JButton btnRuta = crearBoton("Calcular Ruta Óptima");
        JButton btnVerNodos = crearBoton("Ver Nodos");

        // Agrega los botones al panel de botones
        panelBoton.add(btnRuta);
        panelBoton.add(btnVerNodos);
        add(panelBoton, BorderLayout.SOUTH);  // Agrega el panel de botones en la parte inferior

        // Acción para calcular la ruta más corta
        btnRuta.addActionListener(e -> {
            // Solicita al usuario el nodo de inicio y destino
            String inicio = JOptionPane.showInputDialog("Nodo de inicio:");
            String fin = JOptionPane.showInputDialog("Nodo de destino:");

            // Verifica que los nodos ingresados no sean vacíos
            if (inicio == null || fin == null || inicio.isEmpty() || fin.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debes ingresar ambos nodos.");
                return;
            }

            // Calcula la ruta más corta entre los nodos de inicio y fin
            List<String> ruta = grafo.rutaMasCorta(inicio, fin);
            if (ruta.size() <= 1) {
                area.setText("No se encontró una ruta válida entre " + inicio + " y " + fin + ".");
            } else {
                int distanciaTotal = grafo.distanciaTotal(ruta);  // Calcula la distancia total de la ruta
                // Muestra la ruta más corta y la distancia total
                area.setText("Ruta más corta:\n" + String.join(" → ", ruta) +
                        "\n\nDistancia total: " + distanciaTotal + " km");
            }
        });

        // Acción para ver todos los nodos disponibles en el grafo
        btnVerNodos.addActionListener(e -> {
            Set<String> nodos = grafo.getNodos();  // Obtiene los nodos del grafo
            if (nodos.isEmpty()) {
                area.setText("No hay nodos registrados.");
            } else {
                area.setText("Nodos disponibles:\n" + String.join(", ", nodos));  // Muestra los nodos en el área de texto
            }
        });
    }

    // Método para crear un botón con estilo
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(255, 102, 0));  // Color de fondo naranja
        boton.setForeground(Color.WHITE);  // Color del texto blanco
        boton.setFocusPainted(false);  // Deshabilita el borde de enfoque
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));  // Establece la fuente en negrita
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));  // Borde vacío con espaciado
        return boton;
    }
}
