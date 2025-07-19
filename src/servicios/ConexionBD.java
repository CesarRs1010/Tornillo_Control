package servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    // Parámetros de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/tornillo_control";  // URL de conexión a la base de datos
    private static final String USER = "root";  // Usuario de la base de datos
    private static final String PASSWORD = "root";  // Contraseña del usuario de la base de datos

    // Método estático para establecer la conexión con la base de datos
    public static Connection conectar() throws SQLException {
        // Utiliza DriverManager para obtener una conexión con la base de datos
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
