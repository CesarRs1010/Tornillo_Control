package estructuras;

public class NodoAlmacen {
    // Atributo que almacena el nombre del nodo
    public String nombre;

    // Constructor que recibe el nombre del nodo y lo asigna al atributo 'nombre'
    public NodoAlmacen(String nombre) {
        this.nombre = nombre;
    }

    // Método para representar el nodo como una cadena (utilizado para imprimir el nombre del nodo)
    @Override
    public String toString() {
        return nombre; // Retorna el nombre del nodo
    }
}
