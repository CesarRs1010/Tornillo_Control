package servicios;

import java.util.LinkedList;
import java.util.Stack;
import modelo.Orden;

public class OrdenService {
    // Cola para gestionar las órdenes, utilizando FIFO (First In, First Out)
    private LinkedList<Orden> colaOrdenes = new LinkedList<>();
    // Historial de órdenes atendidas, utilizando un stack (Last In, First Out)
    private Stack<Orden> historialOrdenes = new Stack<>();
    // Servicio de inventario utilizado para realizar la venta de productos
    private InventarioService inventario;

    // Constructor que recibe el servicio de inventario
    public OrdenService(InventarioService inventario) {
        this.inventario = inventario;
    }

    // Método para registrar una nueva orden, añadiéndola al final de la cola
    public void registrarOrden(Orden orden) {
        colaOrdenes.addLast(orden);  // FIFO: La orden se agrega al final de la cola
    }

    // Método para atender la primera orden en la cola (procesar la venta)
    public Orden atenderOrden() {
        if (!colaOrdenes.isEmpty()) {  // Si hay órdenes en la cola
            Orden atendida = colaOrdenes.removeFirst();  // Se elimina la primera orden de la cola

            // Intenta vender el producto asociado con la orden
            boolean vendido = inventario.venderProducto(atendida.getProducto(), atendida.getCantidad());
            if (!vendido) {
                // Si no se pudo vender el producto (por falta de stock o inexistencia), se imprime un mensaje
                System.out.println("⚠️ No se pudo vender el producto: " + atendida.getProducto() +
                                   " (posiblemente no existe o no hay suficiente stock)");
            } else {
                // Si la venta es exitosa, la orden se agrega al historial de órdenes atendidas
                historialOrdenes.push(atendida);
            }

            return atendida;  // Retorna la orden atendida
        }
        return null;  // Si no hay órdenes en la cola, retorna null
    }

    // Método para obtener el historial de órdenes atendidas (stack)
    public Stack<Orden> getHistorial() {
        return historialOrdenes;  // Retorna el historial de órdenes atendidas
    }

    // Método para obtener las órdenes pendientes en la cola (LinkedList)
    public LinkedList<Orden> getColaOrdenes() {
        return colaOrdenes;  // Retorna la cola de órdenes pendientes
    }
}
