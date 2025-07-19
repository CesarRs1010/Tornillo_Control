package interfaz;

import java.awt.*;
import javax.swing.*;
import modelo.Orden;
import servicios.OrdenService;

public class OrdenPanel extends JPanel {
    private OrdenService ordenService;  // Servicio para gestionar las órdenes
    private JTextArea areaOrdenes;  // Área de texto para mostrar las órdenes

    // Constructor del panel que recibe el servicio de órdenes
    public OrdenPanel(OrdenService ordenService) {
        this.ordenService = ordenService;  // Asigna el servicio de órdenes
        setLayout(new BorderLayout());  // Configura el layout del panel
        setBackground(new Color(245, 245, 245));  // Establece un fondo claro

        // Configuración del área de texto donde se muestran las órdenes
        areaOrdenes = new JTextArea();
        areaOrdenes.setEditable(false);  // Deshabilita la edición del área de texto
        areaOrdenes.setFont(new Font("Segoe UI", Font.PLAIN, 13));  // Establece la fuente del texto
        areaOrdenes.setBackground(new Color(255, 255, 255));  // Establece el fondo blanco
        areaOrdenes.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));  // Borde gris

        // Agrega el área de texto al panel con un JScrollPane para permitir el desplazamiento
        add(new JScrollPane(areaOrdenes), BorderLayout.CENTER);

        // Panel de botones (Registrar, Atender, Historial)
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(245, 245, 245));  // Fondo claro

        // Crea los botones de la interfaz
        JButton btnRegistrar = crearBoton("Registrar Orden");
        JButton btnAtender = crearBoton("Atender Orden");
        JButton btnHistorial = crearBoton("Ver Historial");

        // Agrega los botones al panel
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnAtender);
        panelBotones.add(btnHistorial);

        // Agrega el panel de botones al panel principal
        add(panelBotones, BorderLayout.SOUTH);

        // Asocia los eventos de los botones
        btnRegistrar.addActionListener(e -> registrarOrden());
        btnAtender.addActionListener(e -> atenderOrden());
        btnHistorial.addActionListener(e -> mostrarHistorial());

        // Actualiza la vista de las órdenes en cola al inicio
        actualizarVista();
    }

    // Método para crear un botón con estilo
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(255, 102, 0));  // Color naranja
        boton.setForeground(Color.WHITE);  // Color del texto en blanco
        boton.setFocusPainted(false);  // Deshabilita el borde de enfoque
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));  // Fuente negrita
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));  // Borde vacío con espaciado
        return boton;
    }

    // Método para registrar una nueva orden
    private void registrarOrden() {
        // Solicita los datos de la orden (cliente, producto, cantidad)
        String cliente = JOptionPane.showInputDialog("Nombre del cliente:");
        String producto = JOptionPane.showInputDialog("Producto:");
        int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Cantidad:"));

        // Registra la nueva orden usando el servicio
        ordenService.registrarOrden(new Orden(cliente, producto, cantidad));

        // Actualiza la vista con las órdenes en cola
        actualizarVista();
    }

    // Método para atender la primera orden en cola
    private void atenderOrden() {
        // Obtiene la siguiente orden en la cola
        Orden atendida = ordenService.atenderOrden();
        if (atendida != null) {
            // Muestra los detalles de la orden atendida
            JOptionPane.showMessageDialog(this,
                "Orden atendida:\nCliente: " + atendida.getCliente() +
                "\nProducto: " + atendida.getProducto() +
                "\nCantidad: " + atendida.getCantidad());
        } else {
            // Si no hay órdenes, muestra un mensaje de error
            JOptionPane.showMessageDialog(this, "No hay órdenes pendientes o no se pudo vender el producto.");
        }
        // Actualiza la vista con las órdenes restantes en cola
        actualizarVista();
    }

    // Método para mostrar el historial de órdenes atendidas
    private void mostrarHistorial() {
        StringBuilder sb = new StringBuilder("Historial de Órdenes:\n");
        // Recorre todas las órdenes en el historial y las agrega al StringBuilder
        for (Orden o : ordenService.getHistorial()) {
            sb.append("Cliente: ").append(o.getCliente())
              .append(", Producto: ").append(o.getProducto())
              .append(", Cantidad: ").append(o.getCantidad()).append("\n");
        }
        // Muestra el historial en un cuadro de diálogo
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    // Método para actualizar la vista de las órdenes en cola
    private void actualizarVista() {
        StringBuilder sb = new StringBuilder("Órdenes en cola:\n");
        // Recorre todas las órdenes en la cola y las agrega al StringBuilder
        for (Orden o : ordenService.getColaOrdenes()) {
            sb.append("Cliente: ").append(o.getCliente())
              .append(", Producto: ").append(o.getProducto())
              .append(", Cantidad: ").append(o.getCantidad()).append("\n");
        }
        // Actualiza el área de texto con las órdenes en cola
        areaOrdenes.setText(sb.toString());
    }
}
