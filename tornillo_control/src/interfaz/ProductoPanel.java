package interfaz;

import modelo.Producto;
import servicios.InventarioService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class ProductoPanel extends JPanel {

    private InventarioService inventario;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public ProductoPanel(InventarioService inventario) {
        this.inventario = inventario;
        setLayout(new BorderLayout());

        modeloTabla = new DefaultTableModel(new Object[]{"Código", "Nombre", "Categoría", "Cantidad", "Precio"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnBuscar = new JButton("Buscar por nombre");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscar);
        btnBuscar.addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog("Ingrese el nombre del producto:");
            Producto encontrado = inventario.buscarPorNombre(nombre);
            if (encontrado != null) {
                JOptionPane.showMessageDialog(this, "Producto encontrado:\n"
                        + "Código: " + encontrado.getCodigo() + "\n"
                        + "Categoría: " + encontrado.getCategoria() + "\n"
                        + "Cantidad: " + encontrado.getCantidad() + "\n"
                        + "Precio: " + encontrado.getPrecio());
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.");
            }
        });

        add(panelBotones, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> mostrarFormulario(null));
        btnEditar.addActionListener(e -> editarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());

        actualizarTabla();
    }

    private void mostrarFormulario(Producto producto) {
        JTextField txtCodigo = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtCategoria = new JTextField();
        JTextField txtCantidad = new JTextField();
        JTextField txtPrecio = new JTextField();

        if (producto != null) {
            txtCodigo.setText(producto.getCodigo());
            txtCodigo.setEditable(false);
            txtNombre.setText(producto.getNombre());
            txtCategoria.setText(producto.getCategoria());
            txtCantidad.setText(String.valueOf(producto.getCantidad()));
            txtPrecio.setText(String.valueOf(producto.getPrecio()));
        }

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Código:"));
        panel.add(txtCodigo);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Categoría:"));
        panel.add(txtCategoria);
        panel.add(new JLabel("Cantidad:"));
        panel.add(txtCantidad);
        panel.add(new JLabel("Precio:"));
        panel.add(txtPrecio);

        int result = JOptionPane.showConfirmDialog(null, panel, "Formulario de Producto", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Producto nuevo = new Producto(
                    txtCodigo.getText(),
                    txtNombre.getText(),
                    txtCategoria.getText(),
                    Integer.parseInt(txtCantidad.getText()),
                    Double.parseDouble(txtPrecio.getText())
            );

            if (producto == null) {
                inventario.agregarProducto(nuevo);
            } else {
                inventario.actualizarProducto(nuevo);
            }
            actualizarTabla();
        }
    }

    private void editarProducto() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            String codigo = (String) modeloTabla.getValueAt(fila, 0);
            Producto p = inventario.buscarPorCodigo(codigo);
            if (p != null) {
                mostrarFormulario(p);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para editar.");
        }
    }

    private void eliminarProducto() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            String codigo = (String) modeloTabla.getValueAt(fila, 0);
            inventario.eliminarProducto(codigo);
            actualizarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para eliminar.");
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (Producto p : inventario.obtenerProductos()) {
            modeloTabla.addRow(new Object[]{
                p.getCodigo(), p.getNombre(), p.getCategoria(), p.getCantidad(), p.getPrecio()
            });
        }
    }
}
