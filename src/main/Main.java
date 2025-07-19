package main;

import interfaz.MainWindow;
import interfaz.SplashScreen;
import java.awt.Color;
import java.awt.Font;
import javax.swing.UIManager;

public class Main {

    // Método principal que se ejecuta al iniciar la aplicación
    public static void main(String[] args) {
        try {
            // Aplicar tema Nimbus
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                // Busca el tema Nimbus entre los temas instalados
                if ("Nimbus".equals(info.getName())) {
                    // Establece el tema Nimbus como el tema de la interfaz de usuario
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

            // Personalizar colores y fuentes globales para la aplicación
            UIManager.put("control", new Color(242, 242, 242));  // Fondo general de los controles
            UIManager.put("info", new Color(255, 255, 255));  // Color de la información
            UIManager.put("nimbusBase", new Color(0, 51, 102));  // Azul oscuro como base
            UIManager.put("nimbusBlueGrey", new Color(169, 176, 190));  // Color azul grisáceo
            UIManager.put("nimbusLightBackground", new Color(255, 255, 255));  // Fondo claro
            UIManager.put("text", new Color(0, 0, 0));  // Color del texto
            UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 14));  // Fuente de los botones
            UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 13));  // Fuente de las etiquetas
            UIManager.put("Table.font", new Font("Segoe UI", Font.PLAIN, 13));  // Fuente de las tablas
        } catch (Exception e) {
            e.printStackTrace();  // Si ocurre un error, lo imprime en la consola
        }

        // Mostrar la pantalla de bienvenida (SplashScreen)
        SplashScreen splash = new SplashScreen();
        splash.mostrar();  // Muestra la pantalla de inicio durante 3 segundos

        // Lanzar la ventana principal (MainWindow) después de la pantalla de inicio
        javax.swing.SwingUtilities.invokeLater(() -> {
            new MainWindow().setVisible(true);  // Muestra la ventana principal de la aplicación
        });
    }
}
