package interfaz;

import estructuras.GrafoAlmacen;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;

public class RutaPanel extends JPanel {
    private GrafoAlmacen grafo;
    private JTextArea area;

    public RutaPanel(GrafoAlmacen grafo) {
        this.grafo = grafo;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245)); // Fondo claro

        // Título
        JLabel titulo = new JLabel("Cálculo de Ruta Óptima", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(new Color(0, 51, 102));
        add(titulo, BorderLayout.NORTH);

        // Área de resultados
        area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        area.setBackground(Color.WHITE);
        area.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(new JScrollPane(area), BorderLayout.CENTER);

        // Botones
        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(new Color(245, 245, 245));

        JButton btnRuta = crearBoton("Calcular Ruta Óptima");
        JButton btnVerNodos = crearBoton("Ver Nodos");

        panelBoton.add(btnRuta);
        panelBoton.add(btnVerNodos);
        add(panelBoton, BorderLayout.SOUTH);

        // Acción: Calcular ruta
        btnRuta.addActionListener(e -> {
            String inicio = JOptionPane.showInputDialog("Nodo de inicio:");
            String fin = JOptionPane.showInputDialog("Nodo de destino:");

            if (inicio == null || fin == null || inicio.isEmpty() || fin.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debes ingresar ambos nodos.");
                return;
            }

            List<String> ruta = grafo.rutaMasCorta(inicio, fin);
            if (ruta.size() <= 1) {
                area.setText("No se encontró una ruta válida entre " + inicio + " y " + fin + ".");
            } else {
                int distanciaTotal = grafo.distanciaTotal(ruta);
                area.setText("Ruta más corta:\n" + String.join(" → ", ruta) +
                        "\n\nDistancia total: " + distanciaTotal + " km");
            }
        });

        // Acción: Ver nodos
        btnVerNodos.addActionListener(e -> {
            Set<String> nodos = grafo.getNodos();
            if (nodos.isEmpty()) {
                area.setText("No hay nodos registrados.");
            } else {
                area.setText("Nodos disponibles:\n" + String.join(", ", nodos));
            }
        });
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(255, 102, 0)); // Naranja
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return boton;
    }
}
