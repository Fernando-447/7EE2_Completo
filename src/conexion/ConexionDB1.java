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
public class ConexionDB1 {
    private static Connection conn = null;
    
    public static Connection getConexion() throws SQLException{
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ProyectoV8",
                "root",
                "0506"
        );
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
    
    
    

