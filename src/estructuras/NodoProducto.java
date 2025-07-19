package estructuras;

import modelo.Producto;

public class NodoProducto {
    public Producto producto;
    public NodoProducto izquierda;
    public NodoProducto derecha;

    public NodoProducto(Producto producto) {
        this.producto = producto;
        this.izquierda = null;
        this.derecha = null;
    }
}
