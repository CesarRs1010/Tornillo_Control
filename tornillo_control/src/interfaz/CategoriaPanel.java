package interfaz;

import estructuras.ArbolCategorias;
import estructuras.CategoriaNodo;

import javax.swing.*;
import java.awt.*;

public class CategoriaPanel extends JPanel {
    private ArbolCategorias arbol;

    public CategoriaPanel(ArbolCategorias arbol) {
        this.arbol = arbol;
        setLayout(new BorderLayout());

        JButton btnMostrar = new JButton("Mostrar Categorías");
        JTextArea area = new JTextArea();
        area.setEditable(false);

        add(new JScrollPane(area), BorderLayout.CENTER);
        add(btnMostrar, BorderLayout.SOUTH);

        btnMostrar.addActionListener(e -> {
            area.setText("");
            mostrarRecursivo(arbol.getRaiz(), 0, area);
        });
    }

    private void mostrarRecursivo(CategoriaNodo nodo, int nivel, JTextArea area) {
        for (int i = 0; i < nivel; i++) area.append("  ");
        area.append("- " + nodo.getNombre() + "\n");

        for (CategoriaNodo sub : nodo.getSubcategorias()) {
            mostrarRecursivo(sub, nivel + 1, area);
        }
    }
}
