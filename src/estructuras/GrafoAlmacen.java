package estructuras;

import java.util.*;

public class GrafoAlmacen {
    // Mapa que representa el grafo. Cada nodo tiene un mapa de conexiones con otros nodos y las distancias respectivas
    private Map<String, Map<String, Integer>> grafo = new HashMap<>();

    // Método para agregar un nodo al grafo (representado por su nombre)
    public void agregarNodo(String nombre) {
        grafo.putIfAbsent(nombre, new HashMap<>()); // Si el nodo no existe, lo agrega con una lista vacía de conexiones
    }

    // Método para agregar una conexión entre dos nodos con una distancia asociada
    public void agregarConexion(String origen, String destino, int distancia) {
        // Agrega la conexión bidireccional entre origen y destino con la distancia indicada
        grafo.get(origen).put(destino, distancia);
        grafo.get(destino).put(origen, distancia); // bidireccional, ya que las conexiones se deben poder recorrer en ambos sentidos
    }

    // Método para encontrar la ruta más corta entre dos nodos utilizando el algoritmo de Dijkstra
    public List<String> rutaMasCorta(String inicio, String fin) {
        // Mapas para almacenar las distancias mínimas y los nodos anteriores en el camino más corto
        Map<String, Integer> distancias = new HashMap<>();
        Map<String, String> anteriores = new HashMap<>();
        // Cola de prioridad para procesar los nodos de menor distancia primero
        PriorityQueue<String> cola = new PriorityQueue<>(Comparator.comparingInt(distancias::get));

        // Inicializa todas las distancias a infinito, excepto la del nodo de inicio
        for (String nodo : grafo.keySet()) {
            distancias.put(nodo, Integer.MAX_VALUE);
        }
        distancias.put(inicio, 0); // La distancia desde el nodo de inicio a sí mismo es 0
        cola.add(inicio);

        // Algoritmo de Dijkstra
        while (!cola.isEmpty()) {
            String actual = cola.poll(); // Obtiene el nodo con la menor distancia
            // Recorre los vecinos del nodo actual
            for (Map.Entry<String, Integer> vecino : grafo.get(actual).entrySet()) {
                int nuevaDist = distancias.get(actual) + vecino.getValue(); // Calcula la nueva distancia hasta el vecino
                // Si la nueva distancia es menor que la distancia previamente registrada
                if (nuevaDist < distancias.get(vecino.getKey())) {
                    distancias.put(vecino.getKey(), nuevaDist); // Actualiza la distancia
                    anteriores.put(vecino.getKey(), actual); // Registra el nodo actual como el nodo anterior del vecino
                    cola.add(vecino.getKey()); // Añade el vecino a la cola para procesarlo
                }
            }
        }

        // Reconstruye el camino más corto desde el nodo final hasta el nodo inicial
        List<String> ruta = new ArrayList<>();
        for (String at = fin; at != null; at = anteriores.get(at)) {
            ruta.add(0, at); // Inserta al inicio de la lista el nodo actual
        }
        return ruta;
    }

    // Método para obtener todos los nodos del grafo
    public Set<String> getNodos() {
        return grafo.keySet(); // Devuelve los nodos del grafo
    }

    // Método para calcular la distancia total de una ruta dada como lista de nodos
    public int distanciaTotal(List<String> ruta) {
        int total = 0;
        // Recorre todos los nodos de la ruta
        for (int i = 0; i < ruta.size() - 1; i++) {
            String origen = ruta.get(i);
            String destino = ruta.get(i + 1);
            Map<String, Integer> conexiones = grafo.get(origen); // Obtiene las conexiones del nodo origen
            if (conexiones != null && conexiones.containsKey(destino)) {
                total += conexiones.get(destino); // Suma la distancia de la conexión
            } else {
                return -1; // Si no existe una conexión válida, retorna -1 (ruta inválida)
            }
        }
        return total; // Retorna la distancia total de la ruta
    }
}
