package servicios;

import java.sql.*;
import java.util.ArrayList;
import modelo.Producto;

public class InventarioService {

    // Obtiene el ID de la categoría desde la base de datos, dado su nombre
    private int obtenerIdCategoriaPorNombre(String nombreCategoria) {
        String sql = "SELECT id FROM categorias WHERE nombre = ?";
        try (Connection conn = ConexionBD.conectar();  // Establece la conexión con la base de datos
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreCategoria);  // Establece el nombre de la categoría en el query
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");  // Retorna el ID de la categoría
            } else {
                System.out.println("Categoría no encontrada: " + nombreCategoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Si ocurre un error, lo imprime en la consola
        }
        return -1;  // Retorna -1 si no se encuentra la categoría
    }

    // Agrega un producto a la base de datos
    public void agregarProducto(Producto p) {
        try (Connection conn = ConexionBD.conectar()) {
            int categoriaId = obtenerIdCategoriaPorNombre(p.getCategoria());  // Obtiene el ID de la categoría
            if (categoriaId == -1) {
                System.out.println("No se pudo agregar el producto porque la categoría no existe.");
                return;  // Si la categoría no existe, no se agrega el producto
            }

            // Inserta el producto en la base de datos
            String sql = "INSERT INTO productos (codigo, nombre, categoria_id, cantidad, precio) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, p.getCodigo());
            stmt.setString(2, p.getNombre());
            stmt.setInt(3, categoriaId);  // Usa el ID de la categoría
            stmt.setInt(4, p.getCantidad());
            stmt.setDouble(5, p.getPrecio());
            stmt.executeUpdate();  // Ejecuta la inserción
        } catch (SQLException e) {
            e.printStackTrace();  // Si ocurre un error, lo imprime en la consola
        }
    }

    // Obtiene todos los productos desde la base de datos
    public ArrayList<Producto> obtenerProductos() {
        ArrayList<Producto> lista = new ArrayList<>();
        // Realiza una consulta JOIN entre productos y categorías para obtener los productos junto con sus categorías
        String sql = "SELECT p.codigo, p.nombre, c.nombre AS categoria, p.cantidad, p.precio " +
                     "FROM productos p " +
                     "JOIN categorias c ON p.categoria_id = c.id";

        try (Connection conn = ConexionBD.conectar();  // Establece la conexión con la base de datos
             Statement stmt = conn.createStatement();  // Crea el Statement
             ResultSet rs = stmt.executeQuery(sql)) {  // Ejecuta la consulta

            while (rs.next()) {
                // Por cada resultado, crea un objeto Producto y lo agrega a la lista
                Producto p = new Producto(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getInt("cantidad"),
                        rs.getDouble("precio")
                );
                lista.add(p);  // Agrega el producto a la lista
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Si ocurre un error, lo imprime en la consola
        }
        return lista;  // Retorna la lista de productos
    }

    // Elimina un producto de la base de datos por su código
    public void eliminarProducto(String codigo) {
        try (Connection conn = ConexionBD.conectar()) {
            // Elimina el producto con el código proporcionado
            String sql = "DELETE FROM productos WHERE codigo = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, codigo);  // Establece el código del producto a eliminar
            stmt.executeUpdate();  // Ejecuta la eliminación
        } catch (SQLException e) {
            e.printStackTrace();  // Si ocurre un error, lo imprime en la consola
        }
    }

    // Actualiza los detalles de un producto existente en la base de datos
    public void actualizarProducto(Producto actualizado) {
        try (Connection conn = ConexionBD.conectar()) {
            int categoriaId = obtenerIdCategoriaPorNombre(actualizado.getCategoria());  // Obtiene el ID de la categoría
            if (categoriaId == -1) {
                System.out.println("No se pudo actualizar el producto porque la categoría no existe.");
                return;  // Si la categoría no existe, no se actualiza el producto
            }

            // Actualiza el producto en la base de datos
            String sql = "UPDATE productos SET nombre = ?, categoria_id = ?, cantidad = ?, precio = ? WHERE codigo = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, actualizado.getNombre());
            stmt.setInt(2, categoriaId);  // Usa el ID de la categoría
            stmt.setInt(3, actualizado.getCantidad());
            stmt.setDouble(4, actualizado.getPrecio());
            stmt.setString(5, actualizado.getCodigo());  // Establece el código del producto
            stmt.executeUpdate();  // Ejecuta la actualización
        } catch (SQLException e) {
            e.printStackTrace();  // Si ocurre un error, lo imprime en la consola
        }
    }

    // Busca un producto por su nombre
    public Producto buscarPorNombre(String nombre) {
        String sql = "SELECT p.codigo, p.nombre, c.nombre AS categoria, p.cantidad, p.precio " +
                     "FROM productos p " +
                     "JOIN categorias c ON p.categoria_id = c.id " +
                     "WHERE p.nombre = ?";

        try (Connection conn = ConexionBD.conectar();  // Establece la conexión con la base de datos
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);  // Establece el nombre del producto en la consulta
            ResultSet rs = stmt.executeQuery();  // Ejecuta la consulta
            if (rs.next()) {
                // Si encuentra el producto, lo crea y lo retorna
                return new Producto(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getInt("cantidad"),
                        rs.getDouble("precio")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Si ocurre un error, lo imprime en la consola
        }
        return null;  // Si no encuentra el producto, retorna null
    }

    // Vende un producto, reduciendo su cantidad en el inventario
    public boolean venderProducto(String codigo, int cantidadVendida) {
        try (Connection conn = ConexionBD.conectar()) {
            // Consulta el stock actual del producto
            String consulta = "SELECT cantidad FROM productos WHERE codigo = ?";
            PreparedStatement stmtConsulta = conn.prepareStatement(consulta);
            stmtConsulta.setString(1, codigo);  // Establece el código del producto
            ResultSet rs = stmtConsulta.executeQuery();

            if (rs.next()) {
                int stockActual = rs.getInt("cantidad");
                if (cantidadVendida > stockActual) {
                    System.out.println("No hay suficiente stock.");
                    return false;  // Si no hay suficiente stock, no se realiza la venta
                }

                // Actualiza el stock del producto después de la venta
                String actualizacion = "UPDATE productos SET cantidad = cantidad - ? WHERE codigo = ?";
                PreparedStatement stmtUpdate = conn.prepareStatement(actualizacion);
                stmtUpdate.setInt(1, cantidadVendida);
                stmtUpdate.setString(2, codigo);  // Establece el código del producto
                stmtUpdate.executeUpdate();  // Ejecuta la actualización
                return true;  // Venta realizada con éxito
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Si ocurre un error, lo imprime en la consola
        }
        return false;  // Si no se pudo vender el producto, retorna false
    }

    // Busca un producto por su código
    public Producto buscarPorCodigo(String codigo) {
        String sql = "SELECT p.codigo, p.nombre, c.nombre AS categoria, p.cantidad, p.precio " +
                     "FROM productos p " +
                     "JOIN categorias c ON p.categoria_id = c.id " +
                     "WHERE p.codigo = ?";

        try (Connection conn = ConexionBD.conectar();  // Establece la conexión con la base de datos
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);  // Establece el código del producto en la consulta
            ResultSet rs = stmt.executeQuery();  // Ejecuta la consulta
            if (rs.next()) {
                // Si encuentra el producto, lo crea y lo retorna
                return new Producto(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getInt("cantidad"),
                        rs.getDouble("precio")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Si ocurre un error, lo imprime en la consola
        }
        return null;  // Si no encuentra el producto, retorna null
    }
}
