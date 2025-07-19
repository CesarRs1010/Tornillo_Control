package estructuras;

public class ArbolCategorias {
    private CategoriaNodo raiz;

    public ArbolCategorias(String nombreRaiz) {
        this.raiz = new CategoriaNodo(nombreRaiz);
    }

    public CategoriaNodo getRaiz() {
        return raiz;
    }

    public void mostrarCategorias() {
        mostrarRecursivo(raiz, 0);
    }

    private void mostrarRecursivo(CategoriaNodo nodo, int nivel) {
        for (int i = 0; i < nivel; i++) System.out.print("  ");
        System.out.println("- " + nodo.getNombre());

        for (CategoriaNodo sub : nodo.getSubcategorias()) {
            mostrarRecursivo(sub, nivel + 1);
        }
    }
}
