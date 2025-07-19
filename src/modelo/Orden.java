package modelo;

public class Orden {
    // Atributos de la clase Orden
    private String cliente;  // Nombre del cliente que realiza la orden
    private String producto;  // Nombre del producto que se está ordenando
    private int cantidad;  // Cantidad del producto ordenado

    // Constructor de la clase Orden, que recibe los parámetros cliente, producto y cantidad
    public Orden(String cliente, String producto, int cantidad) {
        this.cliente = cliente;  // Asigna el nombre del cliente
        this.producto = producto;  // Asigna el nombre del producto
        this.cantidad = cantidad;  // Asigna la cantidad del producto
    }

    // Métodos getter para acceder a los atributos privados de la clase
    public String getCliente() { return cliente; }  // Retorna el nombre del cliente
    public String getProducto() { return producto; }  // Retorna el nombre del producto
    public int getCantidad() { return cantidad; }  // Retorna la cantidad del producto
}
