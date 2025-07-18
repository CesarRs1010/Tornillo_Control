package servicios;

import modelo.Orden;
import java.util.LinkedList;
import java.util.Stack;

public class OrdenService {
    private LinkedList<Orden> colaOrdenes = new LinkedList<>();
    private Stack<Orden> historialOrdenes = new Stack<>();

    public void registrarOrden(Orden orden) {
        colaOrdenes.addLast(orden); // FIFO
    }

    public Orden atenderOrden() {
        if (!colaOrdenes.isEmpty()) {
            Orden atendida = colaOrdenes.removeFirst();
            historialOrdenes.push(atendida); // LIFO
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

