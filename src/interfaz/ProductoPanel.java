package interfaz;

import modelo.Producto;
import servicios.InventarioService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class ProductoPanel extends JPanel {

    private InventarioService inventario;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public ProductoPanel(InventarioService inventario) {
        this.inventario = inventario;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        // Tabla
        modeloTabla = new DefaultTableModel(new Object[]{"Código", "Nombre", "Categoría", "Cantidad", "Precio"}, 0);
        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(28);
        tabla.setGridColor(new Color(220, 220, 220));

        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(0, 51, 102));
        header.setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(245, 245, 245));

        JButton btnAgregar = crearBoton("Agregar");
        JButton btnEditar = crearBoton("Editar");
        JButton btnEliminar = crearBoton("Eliminar");
        JButton btnBuscar = crearBoton("Buscar por nombre");
        JButton btnVender = crearBoton("Vender");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnVender);

        add(panelBotones, BorderLayout.SOUTH);

        // Acciones
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

        btnAgregar.addActionListener(e -> mostrarFormulario(null));
        btnEditar.addActionListener(e -> editarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnVender.addActionListener(e -> venderProducto());

        actualizarTabla();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            ImageIcon icon = new ImageIcon("C:/ruta/completa/a/logo_transparent.png");
            Image img = icon.getImage();
            int width = getWidth();
            int height = getHeight();
            int imgWidth = img.getWidth(null);
            int imgHeight = img.getHeight(null);

            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.08f));
            g2d.drawImage(img, (width - imgWidth) / 2, (height - imgHeight) / 2, this);
            g2d.dispose();
        } catch (Exception e) {
            // Si no se encuentra la imagen, no hacer nada
        }
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(255, 102, 0));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return boton;
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

    private void venderProducto() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            String codigo = (String) modeloTabla.getValueAt(fila, 0);
            Producto producto = inventario.buscarPorCodigo(codigo);

            if (producto != null) {
                String input = JOptionPane.showInputDialog(this, "Ingrese la cantidad a vender:");
                if (input != null && !input.isEmpty()) {
                    try {
                        int cantidad = Integer.parseInt(input);
                        if (cantidad <= 0) {
                            JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que cero.");
                            return;
                        }

                        boolean vendido = inventario.venderProducto(codigo, cantidad);
                        if (vendido) {
                            JOptionPane.showMessageDialog(this, "Venta realizada con éxito.");
                            actualizarTabla();
                        } else {
                            JOptionPane.showMessageDialog(this, "No hay suficiente stock para esta venta.");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Ingrese un número válido.");
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para vender.");
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
