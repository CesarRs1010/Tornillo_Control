package estructuras;

import modelo.Producto;

public class NodoProducto {
    // Atributo que almacena el producto que contiene el nodo
    public Producto producto;

    // Atributos que representan los nodos hijo izquierdo y derecho en un árbol binario
    public NodoProducto izquierda;
    public NodoProducto derecha;

    // Constructor que recibe un producto y crea un nodo con dicho producto
    public NodoProducto(Producto producto) {
        this.producto = producto; // Asigna el producto al nodo
        this.izquierda = null; // Inicializa el hijo izquierdo como nulo
        this.derecha = null; // Inicializa el hijo derecho como nulo
    }
}
