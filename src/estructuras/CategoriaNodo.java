package estructuras;

import java.util.ArrayList;
import java.util.List;

public class CategoriaNodo {
    private String nombre;
    private List<CategoriaNodo> subcategorias;

    public CategoriaNodo(String nombre) {
        this.nombre = nombre;
        this.subcategorias = new ArrayList<>();
    }

    public void agregarSubcategoria(CategoriaNodo sub) {
        subcategorias.add(sub);
    }

    public String getNombre() {
        return nombre;
    }

    public List<CategoriaNodo> getSubcategorias() {
        return subcategorias;
    }
}
