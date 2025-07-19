package interfaz;

import java.awt.*;
import javax.swing.*;

public class SplashScreen extends JWindow {

    // Constructor del SplashScreen
    public SplashScreen() {
        // Panel principal para la pantalla de inicio
        JPanel content = new JPanel();
        content.setBackground(new Color(230, 230, 230));  // Establece el color de fondo
        content.setLayout(new BorderLayout());  // Utiliza un BorderLayout para organizar los componentes

        // Logo: Se intenta cargar un logo desde los recursos de la aplicación
        JLabel logoLabel = new JLabel();
        try {
            // Si el logo se encuentra en el directorio /recursos/logo_transparent.png
            ImageIcon icon = new ImageIcon(getClass().getResource("/recursos/logo_transparent.png"));
            // Redimensiona el logo a un tamaño de 150x150 píxeles
            logoLabel.setIcon(new ImageIcon(icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);  // Centra el logo
        } catch (Exception e) {
            // Si no se encuentra el logo, muestra el texto "Tornillo Control"
            logoLabel.setText("Tornillo Control");
            logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));  // Establece la fuente y tamaño del texto
            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);  // Centra el texto
        }

        // Texto de la pantalla de inicio
        JLabel texto = new JLabel("Tornillo Control - Sistema de Gestión", SwingConstants.CENTER);
        texto.setFont(new Font("Segoe UI", Font.BOLD, 18));  // Fuente y tamaño del texto
        texto.setForeground(new Color(0, 51, 102));  // Establece el color del texto

        // Barra de carga simulada
        JProgressBar barra = new JProgressBar();
        barra.setIndeterminate(true);  // Configura la barra como indeterminada (sin un progreso específico)
        barra.setBorderPainted(false);  // Elimina el borde de la barra de carga
        barra.setBackground(new Color(230, 230, 230));  // Establece el color de fondo de la barra
        barra.setForeground(new Color(255, 102, 0));  // Establece el color del progreso (naranja)

        // Agrega los componentes al panel con el layout correspondiente
        content.add(logoLabel, BorderLayout.CENTER);  // Agrega el logo en el centro
        content.add(texto, BorderLayout.NORTH);  // Agrega el texto en la parte superior
        content.add(barra, BorderLayout.SOUTH);  // Agrega la barra de carga en la parte inferior

        // Configura la ventana
        setContentPane(content);  // Establece el panel de contenido
        setSize(400, 300);  // Tamaño de la ventana
        setLocationRelativeTo(null);  // Centra la ventana en la pantalla
    }

    // Método para mostrar el SplashScreen por 3 segundos
    public void mostrar() {
        setVisible(true);  // Muestra la pantalla
        try {
            Thread.sleep(3000);  // Pausa el hilo por 3 segundos (simula tiempo de carga)
        } catch (InterruptedException e) {
            e.printStackTrace();  // Maneja cualquier interrupción del hilo
        }
        setVisible(false);  // Oculta la pantalla de inicio después de 3 segundos
        dispose();  // Libera los recursos ocupados por el SplashScreen
    }
}
