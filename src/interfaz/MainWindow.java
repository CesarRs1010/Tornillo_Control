package interfaz;

import javax.swing.*;
import java.awt.*;

import interfaz.ProductoPanel;
import interfaz.OrdenPanel;
import interfaz.RutaPanel;
import interfaz.CategoriaPanel;

import servicios.InventarioService;
import servicios.OrdenService;

import estructuras.GrafoAlmacen;
import estructuras.ArbolCategorias;
import estructuras.CategoriaNodo;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("Tornillo Control - Sistema de Gestión");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Estilo general
        getContentPane().setBackground(new Color(245, 245, 245));

        // Ícono de la aplicación (si tienes logo.png en src/recursos/)
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/recursos/logo.jpeg"));
            setIconImage(icon.getImage());
        } catch (Exception e) {
            System.out.println("Logo no encontrado.");
        }

        // Servicios
        InventarioService inventario = new InventarioService();
        OrdenService ordenService = new OrdenService(inventario);

        
        
       


        // Grafo de almacén
        // Grafo de almacén con distritos de Lima
        GrafoAlmacen grafo = new GrafoAlmacen();
        String[] distritos = {
            "San Isidro", "Miraflores", "Surco", "La Molina", "San Borja",
            "Barranco", "Chorrillos", "Lince", "Jesús María", "Magdalena"
        };
        for (String d : distritos) {
            grafo.agregarNodo(d);
        }

// Conexiones con distancias estimadas
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

        // Árbol de categorías
        ArbolCategorias arbolCategorias = new ArbolCategorias("Productos");
        CategoriaNodo herramientas = new CategoriaNodo("Herramientas");
        CategoriaNodo electricos = new CategoriaNodo("Eléctricos");
        CategoriaNodo tornillos = new CategoriaNodo("Tornillos");

        herramientas.agregarSubcategoria(new CategoriaNodo("Martillos"));
        herramientas.agregarSubcategoria(new CategoriaNodo("Destornilladores"));
        electricos.agregarSubcategoria(new CategoriaNodo("Cables"));
        electricos.agregarSubcategoria(new CategoriaNodo("Interruptores"));

        arbolCategorias.getRaiz().agregarSubcategoria(herramientas);
        arbolCategorias.getRaiz().agregarSubcategoria(electricos);
        arbolCategorias.getRaiz().agregarSubcategoria(tornillos);

        // Paneles
        ProductoPanel panelProductos = new ProductoPanel(inventario);
        OrdenPanel panelOrdenes = new OrdenPanel(ordenService);
        RutaPanel panelRutas = new RutaPanel(grafo);
        CategoriaPanel panelCategorias = new CategoriaPanel(arbolCategorias);

        // Tabs
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabs.setBackground(Color.WHITE);
        tabs.setForeground(new Color(0, 51, 102)); // Azul oscuro

        tabs.addTab("Inventario", panelProductos);
        tabs.addTab("Órdenes", panelOrdenes);
        tabs.addTab("Rutas", panelRutas);
        tabs.addTab("Categorías", panelCategorias);

        add(tabs);
    }
    
}
