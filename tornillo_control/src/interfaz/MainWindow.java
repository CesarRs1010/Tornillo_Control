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

    // Servicios
    InventarioService inventario = new InventarioService();
    OrdenService ordenService = new OrdenService();

    // Grafo de almacén
    GrafoAlmacen grafo = new GrafoAlmacen();
    grafo.agregarNodo("A");
    grafo.agregarNodo("B");
    grafo.agregarNodo("C");
    grafo.agregarConexion("A", "B", 5);
    grafo.agregarConexion("B", "C", 3);
    grafo.agregarConexion("A", "C", 10);

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
    tabs.addTab("Inventario", panelProductos);
    tabs.addTab("Órdenes", panelOrdenes);
    tabs.addTab("Rutas", panelRutas);
    tabs.addTab("Categorías", panelCategorias);

    add(tabs);
}


    }


