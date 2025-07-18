package interfaz;

import estructuras.GrafoAlmacen;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RutaPanel extends JPanel {
    private GrafoAlmacen grafo;

    public RutaPanel(GrafoAlmacen grafo) {
        this.grafo = grafo;
        setLayout(new BorderLayout());

        JButton btnRuta = new JButton("Calcular Ruta Óptima");
        add(btnRuta, BorderLayout.SOUTH);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        add(new JScrollPane(area), BorderLayout.CENTER);

        btnRuta.addActionListener(e -> {
            String inicio = JOptionPane.showInputDialog("Nodo de inicio:");
            String fin = JOptionPane.showInputDialog("Nodo de destino:");
            List<String> ruta = grafo.rutaMasCorta(inicio, fin);
            area.setText("Ruta más corta:\n" + String.join(" → ", ruta));
        });
    }
}
