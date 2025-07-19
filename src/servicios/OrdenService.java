package servicios;

import modelo.Orden;
import servicios.InventarioService;

import java.util.LinkedList;
import java.util.Stack;

public class OrdenService {
    private LinkedList<Orden> colaOrdenes = new LinkedList<>();
    private Stack<Orden> historialOrdenes = new Stack<>();
    private InventarioService inventario;

    public OrdenService(InventarioService inventario) {
        this.inventario = inventario;
    }

    public void registrarOrden(Orden orden) {
        colaOrdenes.addLast(orden); // FIFO
    }

    public Orden atenderOrden() {
    if (!colaOrdenes.isEmpty()) {
        Orden atendida = colaOrdenes.removeFirst();

        boolean vendido = inventario.venderProducto(atendida.getProducto(), atendida.getCantidad());
        if (!vendido) {
            System.out.println("⚠️ No se pudo vender el producto: " + atendida.getProducto() +
                               " (posiblemente no existe o no hay suficiente stock)");
        } else {
            historialOrdenes.push(atendida);
        }

        return atendida;
    }
    return null;
}


    public Stack<Orden> getHistorial() {
        return historialOrdenes;
    }

    public LinkedList<Orden> getColaOrdenes() {
        return colaOrdenes;
    }
}
