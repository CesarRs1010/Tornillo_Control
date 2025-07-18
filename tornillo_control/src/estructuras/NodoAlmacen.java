package estructuras;

public class NodoAlmacen {
    public String nombre;
    public NodoAlmacen(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
