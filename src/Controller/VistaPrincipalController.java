package Controller;

import Model.conexion;
import cma.carmelo.jasperviewerfx.JasperFX;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import static jdk.nashorn.internal.objects.NativeString.trim;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * FXML Controller class
 *
 * @author Sistemas2
 */
public class VistaPrincipalController implements Initializable {

    @FXML
    private Button btngenerar;
    @FXML
    private TextField txtvendedor;
    @FXML
    private TextField txtruta;
    @FXML
    private CheckBox CHKconruta;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void Generar_Reporte(ActionEvent event) {
        String Ven = trim(txtvendedor.getText());
        String rut = trim(txtruta.getText());
        
        if (!Ven.isEmpty()) {
            if(rut.isEmpty())
            {
                try {
                    Connection cnx = null;
                    conexion conexbd = new conexion();
                    conexbd.crearConexion();
                    cnx = conexbd.getConexion();
                    HashMap<String, Object> ParametrosJasperReport = new HashMap<>();
                    ParametrosJasperReport.put("P_VENDEDOR", Ven);
                    ParametrosJasperReport.put("P_RUTA", rut);
                    ParametrosJasperReport.put("P_SEMANA", "semana");
                    InputStream fis2 = null;
                    fis2 = this.getClass().getResourceAsStream("/Reporte/ReporteVendedoSinRuta.jasper");
                    JasperReport reporte = (JasperReport) JRLoader.loadObject(fis2);
                    JasperPrint print = JasperFillManager.fillReport(reporte, ParametrosJasperReport, cnx);
                    JasperFX jpFX = new JasperFX(print);
                    jpFX.show();
                } catch (NumberFormatException | JRException e) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Look, an Information Dialog");
                    alert.setContentText("I have a great message for you!"+e);
                    alert.showAndWait();
                }
            }else{
                try {
                    Connection cnx = null;
                    conexion conexbd = new conexion();
                    conexbd.crearConexion();
                    cnx = conexbd.getConexion();
                    HashMap<String, Object> ParametrosJasperReport = new HashMap<>();
                    ParametrosJasperReport.put("P_VENDEDOR", Ven);
                    ParametrosJasperReport.put("P_RUTA", rut);
                    ParametrosJasperReport.put("P_SEMANA", "semana");
                    InputStream fis2 = null;
                    fis2 = this.getClass().getResourceAsStream("/Reporte/ReporteVendedor.jasper");
                    JasperReport reporte = (JasperReport) JRLoader.loadObject(fis2);
                    JasperPrint print = JasperFillManager.fillReport(reporte, ParametrosJasperReport, cnx);
                    JasperFX jpFX = new JasperFX(print);
                    jpFX.show();
                } catch (NumberFormatException | JRException e) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Look, an Information Dialog");
                    alert.setContentText("I have a great message for you!"+e);
                    alert.showAndWait();
                }
            }
        }else{
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Campo Vacío");
            alert.setHeaderText("El campo es obligatorio");
            alert.setContentText("Debe escribir la clave de vendedor");
            alert.showAndWait();
        }
    }

    @FXML
    private void ClicSobreCHK(ActionEvent event)
    {
        if(CHKconruta.isSelected())
        {
            txtruta.setDisable(false);
        }else{
            txtruta.setDisable(true);
            txtruta.setText("");
        }
    }

}
