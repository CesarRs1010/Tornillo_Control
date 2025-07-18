package servicios;

import modelo.Producto;
import java.util.ArrayList;
import estructuras.ArbolProducto;

public class InventarioService {

    private ArrayList<Producto> productos;
    private ArbolProducto arbol = new ArbolProducto();

    public InventarioService() {
        productos = new ArrayList<>();
    }

    public void agregarProducto(Producto p) {
        productos.add(p);
        arbol.insertar(p);
    }

    public ArrayList<Producto> obtenerProductos() {
        return productos;
    }

    public Producto buscarPorNombre(String nombre) {
        return arbol.buscarPorNombre(nombre);
    }

    public void eliminarProducto(String codigo) {
        productos.removeIf(p -> p.getCodigo().equals(codigo));
    }

    public void actualizarProducto(Producto actualizado) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo().equals(actualizado.getCodigo())) {
                productos.set(i, actualizado);
                break;
            }
        }
    }

    public Producto buscarPorCodigo(String codigo) {
        for (Producto p : productos) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

}
