package interfaz;

import modelo.Orden;
import servicios.OrdenService;

import javax.swing.*;
import java.awt.*;

public class OrdenPanel extends JPanel {
    private OrdenService ordenService;
    private JTextArea areaOrdenes;

    public OrdenPanel(OrdenService ordenService) {
        this.ordenService = ordenService;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245)); // Fondo claro

        areaOrdenes = new JTextArea();
        areaOrdenes.setEditable(false);
        areaOrdenes.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        areaOrdenes.setBackground(new Color(255, 255, 255));
        areaOrdenes.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        add(new JScrollPane(areaOrdenes), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(245, 245, 245));

        JButton btnRegistrar = crearBoton("Registrar Orden");
        JButton btnAtender = crearBoton("Atender Orden");
        JButton btnHistorial = crearBoton("Ver Historial");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnAtender);
        panelBotones.add(btnHistorial);
        add(panelBotones, BorderLayout.SOUTH);

        btnRegistrar.addActionListener(e -> registrarOrden());
        btnAtender.addActionListener(e -> atenderOrden());
        btnHistorial.addActionListener(e -> mostrarHistorial());

        actualizarVista();
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(255, 102, 0)); // Naranja
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return boton;
    }

    private void registrarOrden() {
        String cliente = JOptionPane.showInputDialog("Nombre del cliente:");
        String producto = JOptionPane.showInputDialog("Producto:");
        int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Cantidad:"));

        ordenService.registrarOrden(new Orden(cliente, producto, cantidad));
        actualizarVista();
    }

    private void atenderOrden() {
    Orden atendida = ordenService.atenderOrden();
    if (atendida != null) {
        JOptionPane.showMessageDialog(this,
            "Orden atendida:\nCliente: " + atendida.getCliente() +
            "\nProducto: " + atendida.getProducto() +
            "\nCantidad: " + atendida.getCantidad());
    } else {
        JOptionPane.showMessageDialog(this, "No hay órdenes pendientes o no se pudo vender el producto.");
    }
    actualizarVista();
}



    private void mostrarHistorial() {
        StringBuilder sb = new StringBuilder("Historial de Órdenes:\n");
        for (Orden o : ordenService.getHistorial()) {
            sb.append("Cliente: ").append(o.getCliente())
              .append(", Producto: ").append(o.getProducto())
              .append(", Cantidad: ").append(o.getCantidad()).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void actualizarVista() {
        StringBuilder sb = new StringBuilder("Órdenes en cola:\n");
        for (Orden o : ordenService.getColaOrdenes()) {
            sb.append("Cliente: ").append(o.getCliente())
              .append(", Producto: ").append(o.getProducto())
              .append(", Cantidad: ").append(o.getCantidad()).append("\n");
        }
        areaOrdenes.setText(sb.toString());
    }
}
