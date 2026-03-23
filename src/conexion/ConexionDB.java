/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author nataly
 */
public class ConexionDB {
    private static Connection conn = null;

    public static Connection getConexion() {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ProyectoInventario7EE2", 
                    "root", 
                    "PZPV46s#9h#8gFk@fEf3"
                );
                System.out.println("Conexión exitosa a la BD");
            } catch (ClassNotFoundException | SQLException e) {
                System.err.println("Error de conexión: " + e.getMessage());
                return null;
            }
        return conn;
    }

    public static void cerrarConexion() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
                System.out.println("Conexión cerrada");
            } catch (SQLException e) {
                System.err.println("Error cerrando conexión: " + e.getMessage());
            }
        }
    }
}
    
    
    

