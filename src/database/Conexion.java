package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {
   private final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private final String URL="jdbc:mysql://localhost:3306/";  
    private final String DB = "puntoventa";
    private final String USER = "root";
    private final String PASSWORD = "admin123";
    
    public Connection  connection ; 
    public static Conexion singleInstance;
    
    private Conexion(){
        this.connection = null; 
    }
    
    public  Connection conectar (){
        try {
            Class .forName(DB_DRIVER);
            this.connection = DriverManager.getConnection(
                    URL + DB, USER, PASSWORD);
                   
        } catch ( ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.exit(0);
        }
        return  this.connection;
    }
    
    public void desconnectar(){
        try {
            this.connection.close();
        } catch (SQLException e) {
             JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public synchronized static Conexion getInstance() {
        
        if (singleInstance == null){
            singleInstance = new Conexion();
        }
        return singleInstance;
    }
}
