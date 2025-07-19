package estructuras;

import modelo.Producto;

public class ArbolProducto {
    private NodoProducto raiz;

    public void insertar(Producto producto) {
        raiz = insertarRec(raiz, producto);
    }

    private NodoProducto insertarRec(NodoProducto nodo, Producto producto) {
        if (nodo == null) {
            return new NodoProducto(producto);
        }

        if (producto.getNombre().compareToIgnoreCase(nodo.producto.getNombre()) < 0) {
            nodo.izquierda = insertarRec(nodo.izquierda, producto);
        } else {
            nodo.derecha = insertarRec(nodo.derecha, producto);
        }

        return nodo;
    }

    public Producto buscarPorNombre(String nombre) {
        return buscarRec(raiz, nombre);
    }

    private Producto buscarRec(NodoProducto nodo, String nombre) {
        if (nodo == null) return null;

        int cmp = nombre.compareToIgnoreCase(nodo.producto.getNombre());
        if (cmp == 0) return nodo.producto;
        else if (cmp < 0) return buscarRec(nodo.izquierda, nombre);
        else return buscarRec(nodo.derecha, nombre);
    }
}

