package servicios;

import modelo.Producto;
import estructuras.ArbolProducto;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class InventarioService {

    // --- ESTRUCTURAS DE DATOS EN MEMORIA ---
    // Mapa para acceso instantáneo (O(1)) a productos por su código. Esta es la fuente de verdad principal en memoria.
    private HashMap<String, Producto> mapaProductos;
    // Árbol para búsqueda eficiente (O(log n)) por nombre de producto.
    private ArbolProducto arbolPorNombre;

    // Constructor: Carga los datos de la BD a las estructuras en memoria al iniciar el servicio.
    public InventarioService() {
        this.mapaProductos = new HashMap<>();
        this.arbolPorNombre = new ArbolProducto();
        cargarDatosDesdeBD();
    }

    // Carga inicial de datos desde la base de datos a las estructuras en memoria.
    private void cargarDatosDesdeBD() {
        System.out.println("Cargando productos desde la base de datos a memoria...");
        String sql = "SELECT p.codigo, p.nombre, c.nombre AS categoria, p.cantidad, p.precio " +
                     "FROM productos p JOIN categorias c ON p.categoria_id = c.id";

        try (Connection conn = ConexionBD.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Producto p = new Producto(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getInt("cantidad"),
                        rs.getDouble("precio")
                );
                // Poblar ambas estructuras en memoria
                mapaProductos.put(p.getCodigo(), p);
                arbolPorNombre.insertar(p);
            }
            System.out.println("Carga completada. " + mapaProductos.size() + " productos en memoria.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- MÉTODOS PÚBLICOS (OPERAN EN MEMORIA PRIMERO) ---

    // Obtiene todos los productos desde el mapa en memoria. Es muy rápido.
    public ArrayList<Producto> obtenerProductos() {
        return new ArrayList<>(mapaProductos.values());
    }

    // Busca un producto por código usando el HashMap. Eficiencia O(1).
    public Producto buscarPorCodigo(String codigo) {
        return mapaProductos.get(codigo);
    }

    // Busca un producto por nombre usando el Árbol Binario de Búsqueda. Eficiencia O(log n).
    public Producto buscarPorNombre(String nombre) {
        return arbolPorNombre.buscarPorNombre(nombre);
    }

    // Agrega un producto a la BD y luego a las estructuras en memoria.
    public void agregarProducto(Producto p) {
        // 1. Intentar insertar en la BD primero para asegurar persistencia.
        if (agregarProductoEnBD(p)) {
            // 2. Si la inserción en BD es exitosa, actualizar las estructuras en memoria.
            mapaProductos.put(p.getCodigo(), p);
            arbolPorNombre.insertar(p);
        }
    }

    // Actualiza un producto en la BD y luego en las estructuras en memoria.
    public void actualizarProducto(Producto actualizado) {
        // 1. Intentar actualizar en la BD.
        if (actualizarProductoEnBD(actualizado)) {
            // 2. Si es exitoso, actualizar el mapa en memoria.
            mapaProductos.put(actualizado.getCodigo(), actualizado);
            // 3. Re-sincronizar el árbol para reflejar los cambios (más simple que eliminar y reinsertar).
            sincronizarArbol();
        }
    }

    // Elimina un producto de la BD y luego de las estructuras en memoria.
    public void eliminarProducto(String codigo) {
        // 1. Intentar eliminar de la BD.
        if (eliminarProductoEnBD(codigo)) {
            // 2. Si es exitoso, eliminar del mapa en memoria.
            mapaProductos.remove(codigo);
            // 3. Re-sincronizar el árbol.
            sincronizarArbol();
        }
    }

    // Vende un producto, actualizando primero la memoria y luego la BD.
    public boolean venderProducto(String codigo, int cantidadVendida) {
        // 1. Validar la operación contra los datos en memoria (más rápido).
        Producto productoEnStock = mapaProductos.get(codigo);
        if (productoEnStock == null || productoEnStock.getCantidad() < cantidadVendida) {
            System.out.println("Venta rechazada: No hay suficiente stock en memoria.");
            return false;
        }

        // 2. Intentar actualizar la BD.
        if (venderProductoEnBD(codigo, cantidadVendida)) {
            // 3. Si la venta en BD es exitosa, actualizar el objeto en memoria.
            productoEnStock.setCantidad(productoEnStock.getCantidad() - cantidadVendida);
            return true;
        }
        return false;
    }

    // --- MÉTODOS PRIVADOS PARA INTERACCIÓN CON LA BASE DE DATOS ---

    private boolean agregarProductoEnBD(Producto p) {
        String sql = "INSERT INTO productos (codigo, nombre, categoria_id, cantidad, precio) VALUES (?, ?, (SELECT id FROM categorias WHERE nombre = ?), ?, ?)";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getCodigo());
            stmt.setString(2, p.getNombre());
            stmt.setString(3, p.getCategoria());
            stmt.setInt(4, p.getCantidad());
            stmt.setDouble(5, p.getPrecio());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean actualizarProductoEnBD(Producto p) {
        String sql = "UPDATE productos SET nombre = ?, categoria_id = (SELECT id FROM categorias WHERE nombre = ?), cantidad = ?, precio = ? WHERE codigo = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getCategoria());
            stmt.setInt(3, p.getCantidad());
            stmt.setDouble(4, p.getPrecio());
            stmt.setString(5, p.getCodigo());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean eliminarProductoEnBD(String codigo) {
        String sql = "DELETE FROM productos WHERE codigo = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean venderProductoEnBD(String codigo, int cantidadVendida) {
        String sql = "UPDATE productos SET cantidad = cantidad - ? WHERE codigo = ? AND cantidad >= ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cantidadVendida);
            stmt.setString(2, codigo);
            stmt.setInt(3, cantidadVendida); // Condición para evitar stock negativo
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Reconstruye el árbol desde el mapa para mantener la consistencia.
    private void sincronizarArbol() {
        arbolPorNombre.limpiar();
        for (Producto p : mapaProductos.values()) {
            arbolPorNombre.insertar(p);
        }
    }
}