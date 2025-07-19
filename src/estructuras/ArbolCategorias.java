package estructuras;

public class ArbolCategorias {
    // Nodo raíz del árbol de categorías
    private CategoriaNodo raiz;

    // Constructor que recibe el nombre de la raíz y crea el nodo raíz
    public ArbolCategorias(String nombreRaiz) {
        this.raiz = new CategoriaNodo(nombreRaiz);
    }

    // Método que retorna el nodo raíz del árbol
    public CategoriaNodo getRaiz() {
        return raiz;
    }

    // Método público para mostrar todas las categorías a partir de la raíz
    public void mostrarCategorias() {
        // Llama al método recursivo para mostrar las categorías
        mostrarRecursivo(raiz, 0);
    }

    // Método recursivo para mostrar las categorías y subcategorías
    private void mostrarRecursivo(CategoriaNodo nodo, int nivel) {
        // Imprime la indentación basada en el nivel en el árbol
        for (int i = 0; i < nivel; i++) System.out.print("  ");
        // Muestra el nombre de la categoría en el nodo actual
        System.out.println("- " + nodo.getNombre());

        // Llama recursivamente para mostrar las subcategorías
        for (CategoriaNodo sub : nodo.getSubcategorias()) {
            mostrarRecursivo(sub, nivel + 1);
        }
    }
}
