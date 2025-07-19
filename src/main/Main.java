package main;

import interfaz.MainWindow;
import interfaz.SplashScreen;

import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Font;

public class Main {

    public static void main(String[] args) {
        try {
            // Aplicar tema Nimbus
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

            // Personalizar colores y fuentes globales
            UIManager.put("control", new Color(242, 242, 242)); // Fondo general
            UIManager.put("info", new Color(255, 255, 255));
            UIManager.put("nimbusBase", new Color(0, 51, 102)); // Azul oscuro
            UIManager.put("nimbusBlueGrey", new Color(169, 176, 190));
            UIManager.put("nimbusLightBackground", new Color(255, 255, 255));
            UIManager.put("text", new Color(0, 0, 0));
            UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 14));
            UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 13));
            UIManager.put("Table.font", new Font("Segoe UI", Font.PLAIN, 13));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Mostrar pantalla de bienvenida
        SplashScreen splash = new SplashScreen();
        splash.mostrar();

        // Lanzar la ventana principal
        javax.swing.SwingUtilities.invokeLater(() -> {
            new MainWindow().setVisible(true);
        });
    }
}
