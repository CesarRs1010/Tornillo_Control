package interfaz;

import estructuras.ArbolCategorias;
import estructuras.CategoriaNodo;
import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

public class CategoriaPanel extends JPanel {
    // Atributos principales
    private ArbolCategorias arbol;  // Instancia del árbol de categorías
    private JTree tree;  // Árbol visual para mostrar las categorías
    private DefaultTreeModel modelo;  // Modelo de árbol utilizado por JTree
    private JTextArea areaDetalles;  // Área de texto para mostrar detalles de la categoría seleccionada

    // Constructor del panel, recibe el árbol de categorías como parámetro
    public CategoriaPanel(ArbolCategorias arbol) {
        this.arbol = arbol;
        setLayout(new BorderLayout());  // Usa BorderLayout para organizar los componentes
        setBackground(new Color(245, 245, 245));  // Establece el color de fondo

        // Crear árbol visual a partir de los datos del árbol de categorías
        DefaultMutableTreeNode raizVisual = construirNodoVisual(arbol.getRaiz());
        modelo = new DefaultTreeModel(raizVisual);  // Modelo para el árbol visual
        tree = new JTree(modelo);  // Crea el JTree con el modelo
        tree.setFont(new Font("Segoe UI", Font.PLAIN, 13));  // Establece la fuente del árbol
        JScrollPane scrollArbol = new JScrollPane(tree);  // Agrega el árbol a un JScrollPane para hacerlo desplazable

        // Configuración del área de detalles
        areaDetalles = new JTextArea(5, 20);  // Área de texto con tamaño inicial
        areaDetalles.setEditable(false);  // Deshabilita la edición
        areaDetalles.setFont(new Font("Segoe UI", Font.PLAIN, 13));  // Establece la fuente
        areaDetalles.setBorder(BorderFactory.createTitledBorder("Detalles de Categoría"));  // Título en el borde

        // Panel de botones (Agregar, Editar, Eliminar)
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(245, 245, 245));  // Fondo del panel de botones

        // Botones para agregar, editar y eliminar categorías
        JButton btnAgregar = crearBoton("Agregar");
        JButton btnEditar = crearBoton("Editar");
        JButton btnEliminar = crearBoton("Eliminar");

        // Agregar botones al panel
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        // Eventos
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (nodo != null) {
                // Muestra el nombre de la categoría seleccionada en el área de detalles
                areaDetalles.setText("Categoría seleccionada: " + nodo.getUserObject().toString());
            }
        });

        // Evento para agregar una nueva subcategoría
        btnAgregar.addActionListener(e -> {
            DefaultMutableTreeNode seleccionado = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (seleccionado == null) {
                JOptionPane.showMessageDialog(this, "Selecciona una categoría para agregar una subcategoría.");
                return;
            }

            // Solicita el nombre de la nueva subcategoría
            String nombre = JOptionPane.showInputDialog("Nombre de la nueva subcategoría:");
            if (nombre != null && !nombre.trim().isEmpty()) {
                DefaultMutableTreeNode nuevo = new DefaultMutableTreeNode(nombre.trim());
                nuevo.add(new DefaultMutableTreeNode(""));  // Agrega un hijo vacío para mostrar como carpeta
                seleccionado.add(nuevo);
                modelo.reload(seleccionado);  // Recarga el nodo seleccionado para reflejar los cambios

                // Expande el nodo padre para mostrar el nuevo nodo
                TreePath path = new TreePath(modelo.getPathToRoot(nuevo));
                tree.expandPath(path.getParentPath());
            }
        });

        // Evento para editar el nombre de una subcategoría
        btnEditar.addActionListener(e -> {
            DefaultMutableTreeNode seleccionado = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (seleccionado == null || seleccionado.isRoot()) {
                JOptionPane.showMessageDialog(this, "Selecciona una subcategoría para editar.");
                return;
            }
            String nuevoNombre = JOptionPane.showInputDialog("Nuevo nombre:", seleccionado.getUserObject().toString());
            if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                seleccionado.setUserObject(nuevoNombre.trim());
                modelo.nodeChanged(seleccionado);  // Actualiza el nodo con el nuevo nombre
            }
        });

        // Evento para eliminar una subcategoría
        btnEliminar.addActionListener(e -> {
            DefaultMutableTreeNode seleccionado = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (seleccionado == null || seleccionado.isRoot()) {
                JOptionPane.showMessageDialog(this, "Selecciona una subcategoría para eliminar.");
                return;
            }
            DefaultMutableTreeNode padre = (DefaultMutableTreeNode) seleccionado.getParent();
            if (padre != null) {
                modelo.removeNodeFromParent(seleccionado);  // Elimina el nodo seleccionado
            }
        });

        // Agregar componentes al panel
        add(scrollArbol, BorderLayout.CENTER);  // Agrega el árbol al centro del panel
        add(areaDetalles, BorderLayout.EAST);  // Agrega el área de detalles a la derecha
        add(panelBotones, BorderLayout.SOUTH);  // Agrega el panel de botones al sur
    }

    // Método recursivo para construir el árbol visual desde el árbol de categorías
    private DefaultMutableTreeNode construirNodoVisual(CategoriaNodo nodo) {
        DefaultMutableTreeNode visual = new DefaultMutableTreeNode(nodo.getNombre());  // Crea el nodo visual
        for (CategoriaNodo sub : nodo.getSubcategorias()) {
            visual.add(construirNodoVisual(sub));  // Agrega las subcategorías recursivamente
        }
        return visual;
    }

    // Método para crear botones con un diseño común
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(255, 102, 0));  // Color de fondo
        boton.setForeground(Color.WHITE);  // Color del texto
        boton.setFocusPainted(false);  // Deshabilita el efecto de foco
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));  // Establece la fuente
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));  // Espaciado alrededor del botón
        return boton;
    }
}
