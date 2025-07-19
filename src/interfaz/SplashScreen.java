package interfaz;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {

    public SplashScreen() {
        JPanel content = new JPanel();
        content.setBackground(new Color(230, 230, 230));
        content.setLayout(new BorderLayout());

        // Logo
        JLabel logoLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/recursos/logo_transparent.png")); // o logo.png
            logoLabel.setIcon(new ImageIcon(icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        } catch (Exception e) {
            logoLabel.setText("Tornillo Control");
            logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }

        // Texto
        JLabel texto = new JLabel("Tornillo Control - Sistema de Gestión", SwingConstants.CENTER);
        texto.setFont(new Font("Segoe UI", Font.BOLD, 18));
        texto.setForeground(new Color(0, 51, 102));

        // Barra de carga simulada
        JProgressBar barra = new JProgressBar();
        barra.setIndeterminate(true);
        barra.setBorderPainted(false);
        barra.setBackground(new Color(230, 230, 230));
        barra.setForeground(new Color(255, 102, 0));

        content.add(logoLabel, BorderLayout.CENTER);
        content.add(texto, BorderLayout.NORTH);
        content.add(barra, BorderLayout.SOUTH);

        setContentPane(content);
        setSize(400, 300);
        setLocationRelativeTo(null);
    }

    public void mostrar() {
        setVisible(true);
        try {
            Thread.sleep(3000); // Mostrar por 3 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setVisible(false);
        dispose();
    }
}
