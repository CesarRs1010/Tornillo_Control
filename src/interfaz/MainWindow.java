package interfaz;

import estructuras.ArbolCategorias;
import estructuras.CategoriaNodo;
import estructuras.GrafoAlmacen;
import java.awt.*;
import javax.swing.*;
import servicios.InventarioService;
import servicios.OrdenService;

public class MainWindow extends JFrame {

    // Constructor de la ventana principal
    public MainWindow() {
        setTitle("Tornillo Control - Sistema de Gestión");  // Título de la ventana
        setSize(1000, 700);  // Tamaño de la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE);  // Acción de cierre de la ventana
        setLocationRelativeTo(null);  // Centra la ventana en la pantalla

        // Estilo general de la ventana
        getContentPane().setBackground(new Color(245, 245, 245));  // Color de fondo

        // Ícono de la aplicación (si tienes logo.png en src/recursos/)
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/recursos/logo.jpeg"));
            setIconImage(icon.getImage());  // Establece el ícono de la ventana
        } catch (Exception e) {
            System.out.println("Logo no encontrado.");
        }

        // Creación de los servicios utilizados en la aplicación
        InventarioService inventario = new InventarioService();
        OrdenService ordenService = new OrdenService(inventario);

        // Grafo de almacén con distritos de Lima
        GrafoAlmacen grafo = new GrafoAlmacen();
        String[] distritos = {
            "San Isidro", "Miraflores", "Surco", "La Molina", "San Borja",
            "Barranco", "Chorrillos", "Lince", "Jesús María", "Magdalena"
        };
        // Agrega los distritos al grafo como nodos
        for (String d : distritos) {
            grafo.agregarNodo(d);
        }

        // Agrega conexiones entre los nodos con distancias estimadas
        grafo.agregarConexion("San Isidro", "Miraflores", 3);
        grafo.agregarConexion("San Isidro", "Lince", 2);
        grafo.agregarConexion("San Isidro", "San Borja", 5);
        grafo.agregarConexion("Miraflores", "Barranco", 2);
        grafo.agregarConexion("Barranco", "Chorrillos", 4);
        grafo.agregarConexion("San Borja", "La Molina", 6);
        grafo.agregarConexion("San Borja", "Surco", 4);
        grafo.agregarConexion("Surco", "La Molina", 5);
        grafo.agregarConexion("Lince", "Jesús María", 2);
        grafo.agregarConexion("Jesús María", "Magdalena", 3);
        grafo.agregarConexion("Magdalena", "San Isidro", 3);

        // Árbol de categorías de productos
        ArbolCategorias arbolCategorias = new ArbolCategorias("Productos");
        CategoriaNodo herramientas = new CategoriaNodo("Herramientas");
        CategoriaNodo electricos = new CategoriaNodo("Eléctricos");
        CategoriaNodo tornillos = new CategoriaNodo("Tornillos");

        // Agrega subcategorías a las categorías
        herramientas.agregarSubcategoria(new CategoriaNodo("Martillos"));
        herramientas.agregarSubcategoria(new CategoriaNodo("Destornilladores"));
        electricos.agregarSubcategoria(new CategoriaNodo("Cables"));
        electricos.agregarSubcategoria(new CategoriaNodo("Interruptores"));

        // Agrega las categorías al árbol principal
        arbolCategorias.getRaiz().agregarSubcategoria(herramientas);
        arbolCategorias.getRaiz().agregarSubcategoria(electricos);
        arbolCategorias.getRaiz().agregarSubcategoria(tornillos);

        // Paneles para diferentes funcionalidades de la aplicación
        ProductoPanel panelProductos = new ProductoPanel(inventario);
        OrdenPanel panelOrdenes = new OrdenPanel(ordenService);
        RutaPanel panelRutas = new RutaPanel(grafo);
        CategoriaPanel panelCategorias = new CategoriaPanel(arbolCategorias);

        // Crear la pestaña de navegación
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 14));  // Fuente de las pestañas
        tabs.setBackground(Color.WHITE);  // Fondo de las pestañas
        tabs.setForeground(new Color(0, 51, 102));  // Color del texto de las pestañas

        // Agregar los paneles a las pestañas
        tabs.addTab("Inventario", panelProductos);
        tabs.addTab("Órdenes", panelOrdenes);
        tabs.addTab("Rutas", panelRutas);
        tabs.addTab("Categorías", panelCategorias);

        // Agregar las pestañas a la ventana principal
        add(tabs);  
    }
}
