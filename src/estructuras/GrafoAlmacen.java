package estructuras;

import java.util.*;

public class GrafoAlmacen {
    private Map<String, Map<String, Integer>> grafo = new HashMap<>();

    public void agregarNodo(String nombre) {
        grafo.putIfAbsent(nombre, new HashMap<>());
    }

    public void agregarConexion(String origen, String destino, int distancia) {
        grafo.get(origen).put(destino, distancia);
        grafo.get(destino).put(origen, distancia); // bidireccional
    }

    public List<String> rutaMasCorta(String inicio, String fin) {
        Map<String, Integer> distancias = new HashMap<>();
        Map<String, String> anteriores = new HashMap<>();
        PriorityQueue<String> cola = new PriorityQueue<>(Comparator.comparingInt(distancias::get));

        for (String nodo : grafo.keySet()) {
            distancias.put(nodo, Integer.MAX_VALUE);
        }
        distancias.put(inicio, 0);
        cola.add(inicio);

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            for (Map.Entry<String, Integer> vecino : grafo.get(actual).entrySet()) {
                int nuevaDist = distancias.get(actual) + vecino.getValue();
                if (nuevaDist < distancias.get(vecino.getKey())) {
                    distancias.put(vecino.getKey(), nuevaDist);
                    anteriores.put(vecino.getKey(), actual);
                    cola.add(vecino.getKey());
                }
            }
        }

        List<String> ruta = new ArrayList<>();
        for (String at = fin; at != null; at = anteriores.get(at)) {
            ruta.add(0, at);
        }
        return ruta;
    }

    // ✅ Método para obtener todos los nodos
    public Set<String> getNodos() {
        return grafo.keySet();
    }

    // ✅ Método para calcular la distancia total de una ruta
    public int distanciaTotal(List<String> ruta) {
        int total = 0;
        for (int i = 0; i < ruta.size() - 1; i++) {
            String origen = ruta.get(i);
            String destino = ruta.get(i + 1);
            Map<String, Integer> conexiones = grafo.get(origen);
            if (conexiones != null && conexiones.containsKey(destino)) {
                total += conexiones.get(destino);
            } else {
                return -1; // Ruta inválida
            }
        }
        return total;
    }
}

