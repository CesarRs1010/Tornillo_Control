package estructuras;

import java.util.ArrayList;
import java.util.List;

public class CategoriaNodo {
    // Nombre de la categoría en el nodo
    private String nombre;
    // Lista de subcategorías (nodos hijos) de la categoría actual
    private List<CategoriaNodo> subcategorias;

    // Constructor que recibe el nombre de la categoría y crea la lista de subcategorías
    public CategoriaNodo(String nombre) {
        this.nombre = nombre;
        this.subcategorias = new ArrayList<>(); // Inicializa la lista de subcategorías
    }

    // Método para agregar una subcategoría (nodo hijo) al nodo actual
    public void agregarSubcategoria(CategoriaNodo sub) {
        subcategorias.add(sub); // Añade la subcategoría a la lista de subcategorías
    }

    // Método para obtener el nombre de la categoría
    public String getNombre() {
        return nombre;
    }

    // Método para obtener la lista de subcategorías
    public List<CategoriaNodo> getSubcategorias() {
        return subcategorias;
    }
}
