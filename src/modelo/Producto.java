package modelo;

public class Producto {
    // Atributos de la clase Producto
    private String codigo;  // Código único del producto
    private String nombre;  // Nombre del producto
    private String categoria;  // Categoría a la que pertenece el producto (por ejemplo, "Electrónicos", "Herramientas")
    private int cantidad;  // Cantidad disponible en inventario
    private double precio;  // Precio del producto

    // Constructor de la clase Producto, que recibe todos los atributos como parámetros
    public Producto(String codigo, String nombre, String categoria, int cantidad, double precio) {
        this.codigo = codigo;  // Asigna el código del producto
        this.nombre = nombre;  // Asigna el nombre del producto
        this.categoria = categoria;  // Asigna la categoría del producto
        this.cantidad = cantidad;  // Asigna la cantidad disponible
        this.precio = precio;  // Asigna el precio del producto
    }
    
    // Métodos getter y setter para cada atributo

    public String getCodigo() {
        return codigo;  // Retorna el código del producto
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;  // Establece el código del producto
    }

    public String getNombre() {
        return nombre;  // Retorna el nombre del producto
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;  // Establece el nombre del producto
    }

    public String getCategoria() {
        return categoria;  // Retorna la categoría del producto
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;  // Establece la categoría del producto
    }

    public int getCantidad() {
        return cantidad;  // Retorna la cantidad disponible en inventario
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;  // Establece la cantidad disponible en inventario
    }

    public double getPrecio() {
        return precio;  // Retorna el precio del producto
    }

    public void setPrecio(double precio) {
        this.precio = precio;  // Establece el precio del producto
    }
}
