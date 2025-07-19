package interfaz;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import modelo.Producto;
import servicios.InventarioService;

public class ProductoPanel extends JPanel {

    private InventarioService inventario;  // Servicio para gestionar los productos
    private JTable tabla;  // Tabla para mostrar los productos
    private DefaultTableModel modeloTabla;  // Modelo de tabla para gestionar las filas

    // Constructor del panel, que recibe el servicio de inventario
    public ProductoPanel(InventarioService inventario) {
        this.inventario = inventario;  // Asigna el servicio de inventario
        setLayout(new BorderLayout());  // Configura el layout del panel
        setBackground(new Color(245, 245, 245));  // Establece el color de fondo

        // Crea el modelo de la tabla con las columnas "Código", "Nombre", "Categoría", "Cantidad" y "Precio"
        modeloTabla = new DefaultTableModel(new Object[]{"Código", "Nombre", "Categoría", "Cantidad", "Precio"}, 0);
        tabla = new JTable(modeloTabla);  // Crea la tabla con el modelo
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));  // Establece la fuente de la tabla
        tabla.setRowHeight(28);  // Ajusta la altura de las filas
        tabla.setGridColor(new Color(220, 220, 220));  // Establece el color de la cuadrícula

        // Configuración del encabezado de la tabla
        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));  // Establece la fuente del encabezado
        header.setBackground(new Color(0, 51, 102));  // Establece el fondo del encabezado
        header.setForeground(Color.WHITE);  // Establece el color del texto en el encabezado

        JScrollPane scroll = new JScrollPane(tabla);  // Agrega la tabla a un JScrollPane para hacerla desplazable
        add(scroll, BorderLayout.CENTER);  // Agrega el JScrollPane al panel

        // Panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(245, 245, 245));  // Fondo del panel de botones

        // Botones para agregar, editar, eliminar, buscar y vender productos
        JButton btnAgregar = crearBoton("Agregar");
        JButton btnEditar = crearBoton("Editar");
        JButton btnEliminar = crearBoton("Eliminar");
        JButton btnBuscar = crearBoton("Buscar por nombre");
        JButton btnVender = crearBoton("Vender");

        // Agrega los botones al panel
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnVender);

        // Agrega el panel de botones al panel principal
        add(panelBotones, BorderLayout.SOUTH);

        // Acciones asociadas a los botones
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

        btnAgregar.addActionListener(e -> mostrarFormulario(null));  // Muestra el formulario para agregar un producto
        btnEditar.addActionListener(e -> editarProducto());  // Muestra el formulario para editar un producto
        btnEliminar.addActionListener(e -> eliminarProducto());  // Elimina un producto seleccionado
        btnVender.addActionListener(e -> venderProducto());  // Vende un producto seleccionado

        // Actualiza la vista con los productos actuales
        actualizarTabla();
    }

    // Método para dibujar el logo en el fondo del panel con un efecto de transparencia
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
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.08f));  // Configura la transparencia
            g2d.drawImage(img, (width - imgWidth) / 2, (height - imgHeight) / 2, this);  // Dibuja la imagen centrada
            g2d.dispose();
        } catch (Exception e) {
            // Si no se encuentra la imagen, no se hace nada
        }
    }

    // Método para crear un botón con estilo
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(255, 102, 0));  // Color de fondo
        boton.setForeground(Color.WHITE);  // Color del texto
        boton.setFocusPainted(false);  // Deshabilita el borde de enfoque
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));  // Fuente en negrita
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));  // Borde vacío con espaciado
        return boton;
    }

    // Método para mostrar el formulario de producto (agregar o editar)
    private void mostrarFormulario(Producto producto) {
        JTextField txtCodigo = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtCategoria = new JTextField();
        JTextField txtCantidad = new JTextField();
        JTextField txtPrecio = new JTextField();

        // Si se pasa un producto, rellena el formulario con sus datos
        if (producto != null) {
            txtCodigo.setText(producto.getCodigo());
            txtCodigo.setEditable(false);
            txtNombre.setText(producto.getNombre());
            txtCategoria.setText(producto.getCategoria());
            txtCantidad.setText(String.valueOf(producto.getCantidad()));
            txtPrecio.setText(String.valueOf(producto.getPrecio()));
        }

        // Panel con campos para ingresar los datos del producto
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
                inventario.agregarProducto(nuevo);  // Agrega el nuevo producto al inventario
            } else {
                inventario.actualizarProducto(nuevo);  // Actualiza el producto existente
            }
            actualizarTabla();  // Actualiza la tabla con los productos actuales
        }
    }

    // Método para editar un producto seleccionado en la tabla
    private void editarProducto() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            String codigo = (String) modeloTabla.getValueAt(fila, 0);
            Producto p = inventario.buscarPorCodigo(codigo);
            if (p != null) {
                mostrarFormulario(p);  // Muestra el formulario con los datos del producto para editar
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para editar.");
        }
    }

    // Método para eliminar un producto seleccionado en la tabla
    private void eliminarProducto() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            String codigo = (String) modeloTabla.getValueAt(fila, 0);
            inventario.eliminarProducto(codigo);  // Elimina el producto del inventario
            actualizarTabla();  // Actualiza la tabla
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para eliminar.");
        }
    }

    // Método para vender un producto seleccionado en la tabla
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

                        boolean vendido = inventario.venderProducto(codigo, cantidad);  // Vende el producto
                        if (vendido) {
                            JOptionPane.showMessageDialog(this, "Venta realizada con éxito.");
                            actualizarTabla();  // Actualiza la tabla
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

    // Método para actualizar la tabla con los productos del inventario
    private void actualizarTabla() {
        modeloTabla.setRowCount(0);  // Elimina todas las filas actuales
        for (Producto p : inventario.obtenerProductos()) {
            modeloTabla.addRow(new Object[]{  // Agrega una fila para cada producto
                    p.getCodigo(), p.getNombre(), p.getCategoria(), p.getCantidad(), p.getPrecio()
            });
        }
    }
}
