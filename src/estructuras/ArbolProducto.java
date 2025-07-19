package estructuras;

import modelo.Producto;

public class ArbolProducto {
    // Nodo raíz del árbol de productos
    private NodoProducto raiz;

    // Método público para limpiar o vaciar el árbol.
    public void limpiar() {
        this.raiz = null;
    }

    // Método público para insertar un producto en el árbol
    public void insertar(Producto producto) {
        raiz = insertarRec(raiz, producto); // Llama al método recursivo para insertar el producto
    }

    // Método recursivo para insertar un producto en el árbol binario de búsqueda
    private NodoProducto insertarRec(NodoProducto nodo, Producto producto) {
        // Si el nodo es nulo, crea un nuevo nodo con el producto
        if (nodo == null) {
            return new NodoProducto(producto);
        }

        // Compara el nombre del producto con el nombre del nodo actual
        // Si el nombre del producto es menor, se inserta a la izquierda
        if (producto.getNombre().compareToIgnoreCase(nodo.producto.getNombre()) < 0) {
            nodo.izquierda = insertarRec(nodo.izquierda, producto);
        } else if (producto.getNombre().compareToIgnoreCase(nodo.producto.getNombre()) > 0) {
            // Si el nombre es mayor, se inserta a la derecha
            nodo.derecha = insertarRec(nodo.derecha, producto);
        }
        // Si son iguales, no se inserta para evitar duplicados en el árbol por nombre.
        
        return nodo;
    }

    // Método público para buscar un producto por su nombre
    public Producto buscarPorNombre(String nombre) {
        return buscarRec(raiz, nombre); // Llama al método recursivo para buscar el producto
    }

    // Método recursivo para buscar un producto por su nombre en el árbol
    private Producto buscarRec(NodoProducto nodo, String nombre) {
        // Si el nodo es nulo, significa que no se encontró el producto
        if (nodo == null) return null;

        // Compara el nombre del producto con el nodo actual
        int cmp = nombre.compareToIgnoreCase(nodo.producto.getNombre());
        
        // Si los nombres coinciden, retorna el producto
        if (cmp == 0) return nodo.producto;
        // Si el nombre a buscar es menor, busca en la subárbol izquierdo
        else if (cmp < 0) return buscarRec(nodo.izquierda, nombre);
        // Si el nombre a buscar es mayor, busca en el subárbol derecho
        else return buscarRec(nodo.derecha, nombre);
    }
}
