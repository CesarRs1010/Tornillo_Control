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

        areaOrdenes = new JTextArea();
        areaOrdenes.setEditable(false);
        add(new JScrollPane(areaOrdenes), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        JButton btnRegistrar = new JButton("Registrar Orden");
        JButton btnAtender = new JButton("Atender Orden");
        JButton btnHistorial = new JButton("Ver Historial");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnAtender);
        panelBotones.add(btnHistorial);
        add(panelBotones, BorderLayout.SOUTH);

        btnRegistrar.addActionListener(e -> registrarOrden());
        btnAtender.addActionListener(e -> atenderOrden());
        btnHistorial.addActionListener(e -> mostrarHistorial());

        actualizarVista();
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
            JOptionPane.showMessageDialog(this, "Orden atendida:\nCliente: " + atendida.getCliente());
        } else {
            JOptionPane.showMessageDialog(this, "No hay órdenes pendientes.");
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
