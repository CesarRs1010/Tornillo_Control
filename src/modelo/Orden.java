package modelo;

public class Orden {
    private String cliente;
    private String producto;
    private int cantidad;

    public Orden(String cliente, String producto, int cantidad) {
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public String getCliente() { return cliente; }
    public String getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
}
