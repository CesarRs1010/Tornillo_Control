package interfaz;

import estructuras.ArbolCategorias;
import estructuras.CategoriaNodo;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;

public class CategoriaPanel extends JPanel {
    private ArbolCategorias arbol;
    private JTree tree;
    private DefaultTreeModel modelo;
    private JTextArea areaDetalles;

    public CategoriaPanel(ArbolCategorias arbol) {
        this.arbol = arbol;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        // Crear árbol visual
        DefaultMutableTreeNode raizVisual = construirNodoVisual(arbol.getRaiz());
        modelo = new DefaultTreeModel(raizVisual);
        tree = new JTree(modelo);
        tree.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JScrollPane scrollArbol = new JScrollPane(tree);

        // Área de detalles
        areaDetalles = new JTextArea(5, 20);
        areaDetalles.setEditable(false);
        areaDetalles.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        areaDetalles.setBorder(BorderFactory.createTitledBorder("Detalles de Categoría"));

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(245, 245, 245));

        JButton btnAgregar = crearBoton("Agregar");
        JButton btnEditar = crearBoton("Editar");
        JButton btnEliminar = crearBoton("Eliminar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        // Eventos
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (nodo != null) {
                areaDetalles.setText("Categoría seleccionada: " + nodo.getUserObject().toString());
            }
        });

        btnAgregar.addActionListener(e -> {
    DefaultMutableTreeNode seleccionado = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
    if (seleccionado == null) {
        JOptionPane.showMessageDialog(this, "Selecciona una categoría para agregar una subcategoría.");
        return;
    }

    String nombre = JOptionPane.showInputDialog("Nombre de la nueva subcategoría:");
    if (nombre != null && !nombre.trim().isEmpty()) {
        DefaultMutableTreeNode nuevo = new DefaultMutableTreeNode(nombre.trim());

        // Agregar un hijo "falso" para que se muestre como carpeta
        nuevo.add(new DefaultMutableTreeNode(""));

        seleccionado.add(nuevo);
        modelo.reload(seleccionado);

        // Expandir el nodo padre para mostrar el nuevo
        TreePath path = new TreePath(modelo.getPathToRoot(nuevo));
        tree.expandPath(path.getParentPath());
    }

        });

        btnEditar.addActionListener(e -> {
            DefaultMutableTreeNode seleccionado = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (seleccionado == null || seleccionado.isRoot()) {
                JOptionPane.showMessageDialog(this, "Selecciona una subcategoría para editar.");
                return;
            }
            String nuevoNombre = JOptionPane.showInputDialog("Nuevo nombre:", seleccionado.getUserObject().toString());
            if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                seleccionado.setUserObject(nuevoNombre.trim());
                modelo.nodeChanged(seleccionado);
            }
        });

        btnEliminar.addActionListener(e -> {
            DefaultMutableTreeNode seleccionado = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (seleccionado == null || seleccionado.isRoot()) {
                JOptionPane.showMessageDialog(this, "Selecciona una subcategoría para eliminar.");
                return;
            }
            DefaultMutableTreeNode padre = (DefaultMutableTreeNode) seleccionado.getParent();
            if (padre != null) {
                modelo.removeNodeFromParent(seleccionado);
            }
        });

        // Agregar componentes al panel
        add(scrollArbol, BorderLayout.CENTER);
        add(areaDetalles, BorderLayout.EAST);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private DefaultMutableTreeNode construirNodoVisual(CategoriaNodo nodo) {
        DefaultMutableTreeNode visual = new DefaultMutableTreeNode(nodo.getNombre());
        for (CategoriaNodo sub : nodo.getSubcategorias()) {
            visual.add(construirNodoVisual(sub));
        }
        return visual;
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(255, 102, 0));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return boton;
    }
}
