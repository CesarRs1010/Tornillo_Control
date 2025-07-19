package servicios;

import modelo.Producto;
import java.sql.*;
import java.util.ArrayList;

public class InventarioService {
private int obtenerIdCategoriaPorNombre(String nombreCategoria) {
    String sql = "SELECT id FROM categorias WHERE nombre = ?";
    try (Connection conn = ConexionBD.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, nombreCategoria);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("id");
        } else {
            System.out.println("Categoría no encontrada: " + nombreCategoria);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1; // o puedes lanzar una excepción si prefieres
}

    // Agrega un producto a la base de datos
    public void agregarProducto(Producto p) {
    try (Connection conn = ConexionBD.conectar()) {
        int categoriaId = obtenerIdCategoriaPorNombre(p.getCategoria());
        if (categoriaId == -1) {
            System.out.println("No se pudo agregar el producto porque la categoría no existe.");
            return;
        }

        String sql = "INSERT INTO productos (codigo, nombre, categoria_id, cantidad, precio) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, p.getCodigo());
        stmt.setString(2, p.getNombre());
        stmt.setInt(3, categoriaId); // ahora sí es un entero
        stmt.setInt(4, p.getCantidad());
        stmt.setDouble(5, p.getPrecio());
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    // Obtiene todos los productos desde la base de datos
    public ArrayList<Producto> obtenerProductos() {
    ArrayList<Producto> lista = new ArrayList<>();
    String sql = "SELECT p.codigo, p.nombre, c.nombre AS categoria, p.cantidad, p.precio " +
                 "FROM productos p " +
                 "JOIN categorias c ON p.categoria_id = c.id";

    try (Connection conn = ConexionBD.conectar();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            Producto p = new Producto(
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getString("categoria"), // ahora sí existe
                    rs.getInt("cantidad"),
                    rs.getDouble("precio")
            );
            lista.add(p);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
}


    // Elimina un producto por código
    public void eliminarProducto(String codigo) {
        try (Connection conn = ConexionBD.conectar()) {
            String sql = "DELETE FROM productos WHERE codigo = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, codigo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Actualiza un producto existente
    public void actualizarProducto(Producto actualizado) {
    try (Connection conn = ConexionBD.conectar()) {
        int categoriaId = obtenerIdCategoriaPorNombre(actualizado.getCategoria());
        if (categoriaId == -1) {
            System.out.println("No se pudo actualizar el producto porque la categoría no existe.");
            return;
        }

        String sql = "UPDATE productos SET nombre = ?, categoria_id = ?, cantidad = ?, precio = ? WHERE codigo = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, actualizado.getNombre());
        stmt.setInt(2, categoriaId); // ahora usamos el ID
        stmt.setInt(3, actualizado.getCantidad());
        stmt.setDouble(4, actualizado.getPrecio());
        stmt.setString(5, actualizado.getCodigo());
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public Producto buscarPorNombre(String nombre) {
    String sql = "SELECT p.codigo, p.nombre, c.nombre AS categoria, p.cantidad, p.precio " +
                 "FROM productos p " +
                 "JOIN categorias c ON p.categoria_id = c.id " +
                 "WHERE p.nombre = ?";

    try (Connection conn = ConexionBD.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, nombre);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Producto(
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getInt("cantidad"),
                    rs.getDouble("precio")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

public boolean venderProducto(String codigo, int cantidadVendida) {
    try (Connection conn = ConexionBD.conectar()) {
        // Verificar stock actual
        String consulta = "SELECT cantidad FROM productos WHERE codigo = ?";
        PreparedStatement stmtConsulta = conn.prepareStatement(consulta);
        stmtConsulta.setString(1, codigo);
        ResultSet rs = stmtConsulta.executeQuery();

        if (rs.next()) {
            int stockActual = rs.getInt("cantidad");
            if (cantidadVendida > stockActual) {
                System.out.println("No hay suficiente stock.");
                return false;
            }

            // Actualizar stock
            String actualizacion = "UPDATE productos SET cantidad = cantidad - ? WHERE codigo = ?";
            PreparedStatement stmtUpdate = conn.prepareStatement(actualizacion);
            stmtUpdate.setInt(1, cantidadVendida);
            stmtUpdate.setString(2, codigo);
            stmtUpdate.executeUpdate();
            return true;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

    // Busca un producto por código
    public Producto buscarPorCodigo(String codigo) {
    String sql = "SELECT p.codigo, p.nombre, c.nombre AS categoria, p.cantidad, p.precio " +
                 "FROM productos p " +
                 "JOIN categorias c ON p.categoria_id = c.id " +
                 "WHERE p.codigo = ?";

    try (Connection conn = ConexionBD.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, codigo);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Producto(
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getInt("cantidad"),
                    rs.getDouble("precio")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

    
}


