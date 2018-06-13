package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
public class conexion {
    Connection conexion = null;
    
    public Connection getConexion()
{
        
   return conexion;
}
 

public boolean crearConexion()
{
   try {
      Class.forName("com.mysql.jdbc.Driver");
//conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/kiwi","root","Cachorro11#");
conexion = DriverManager.getConnection("jdbc:mysql://187.157.119.164:3306/Rutas_Pedidos","externo","0102261218");
//conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/kiwi","root","");
//conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/kiwi","root","M&y0102261218");
//conexion = DriverManager.getConnection("jdbc:mysql://187.157.119.164:3306/kiwi","externo","0102261218");
   } catch (SQLException ex) {
      Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Error!!");
        alert.setContentText("No existe conexión a la base de datos local!"+ex);
        alert.showAndWait();
      return false;
   } catch (ClassNotFoundException ex) {
      
      
      Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Error!!");
        alert.setContentText("Error en clases!"+ex);
        alert.showAndWait();
      return false;
   }
 
   return true;
}
// conxion por internet
public boolean crearConexionRemota()
{
    boolean estado=false;
    
   try {
      Class.forName("com.mysql.jdbc.Driver");
      conexion = DriverManager.getConnection("jdbc:mysql://187.157.119.164:3306/kiwi","externo","0102261218");
      return true;
   } catch (SQLException ex) {
      
      Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Error!!");
        alert.setContentText("No existe conexión a la base de datos remota!"+ex);
        alert.showAndWait();
   } catch (ClassNotFoundException ex) {
      
      
      Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Error!!");
        alert.setContentText("Error en clases!"+ex);
        alert.showAndWait();
   }
   return estado;
}


public boolean ejecutarSQL(String sql)
{
   try {
      Statement statement = conexion.createStatement();

      statement.executeUpdate(sql);
   } catch (SQLException ex) {
      Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Problemas en la consulta");
        alert.setHeaderText("Error!!");
        alert.setContentText("Detalles al insertar a la base de datos"+ex);
        alert.showAndWait();
   return false;
   }
 
   return true;
}
 

public ResultSet ejecutarSQLSelect(String sql)
{
   ResultSet resultado;
   try {
      Statement sentencia = conexion.createStatement();
      resultado = sentencia.executeQuery(sql);
   } catch (SQLException ex) {
      Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Problemas en la consulta");
        alert.setHeaderText("Error!!");
        alert.setContentText("Detalles en la consulta a la base de datos"+ex);
        alert.showAndWait();
      return null;
   }
 
   return resultado;
}
}
