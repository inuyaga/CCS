package Controller;

import Model.TablaDetallesExcel;
import Model.conexion;
import cma.carmelo.jasperviewerfx.JasperFX;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import static jdk.nashorn.internal.objects.NativeString.trim;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import crpvista.CRPVISTA;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ProgressIndicator;
import javafx.util.Duration;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;


/**
 *
 * @author soporte
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Button BTNBUSQUEDA;
    @FXML
    private Button BTNCAMBIOS;
    @FXML
    private TableView<TablaDetallesExcel> TABLAINFO;
    @FXML
    private TableColumn<?, ?> COLCLIENTE;
    @FXML
    private TableColumn<?, ?> COLNOMBRECLIENTE;
    @FXML
    private TableColumn<?, ?> COLVENTA;
    @FXML
    private TableColumn<?, ?> COLFECHAVENTA;
    @FXML
    private TableColumn<?, ?> COLFAC;
    @FXML
    private TableColumn<?, ?> COLFECHAFAC;
    @FXML
    private TableColumn<?, ?> COLTOTAL;
    @FXML
    private TableColumn<?, ?> COLVENDEDOR;
    @FXML
    private TableColumn<?, ?> COLFOLIODESBLOQ;
    @FXML
    private TableColumn<?, ?> COLHORAIMPRESION;
    @FXML
    private TableColumn<?, ?> COLPARTIDAS;
    @FXML
    private TableColumn<?, ?> COLESTATUS;
    @FXML
    private TableColumn<?, ?> COLESTADOPED;
    @FXML
    private TableColumn<?, ?> COLCIUDAD;
    @FXML
    private TableColumn<?, ?> COLRUTA;
    @FXML
    private TableColumn<?, ?> COLBLOQUEDADO;
    @FXML
    private JFXTextField TXTARCHIVO;
    @FXML
    private TextField txt_Busqueda;

    ObservableList<TablaDetallesExcel> Lectura;

    ObservableList<TablaDetallesExcel> listBooks;
    @FXML
    private Label LblSemanaActual;
    @FXML
    private ProgressIndicator ProgresoTarea;
    @FXML
    private Label labelMensaje;
    @FXML
    private Label Label_msn_comentarios;
    @FXML
    private Label Label_msn_eliminar;
    @FXML
    private Label Label_msn_agragar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        mensajesNotificaciones();
        Timeline horafech = new Timeline(new KeyFrame(Duration.seconds(20), (event) -> {
            mensajesNotificaciones();
        }));
        horafech.setCycleCount(Timeline.INDEFINITE);
        horafech.play();
        
        Date F_actual=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        calendar.setTime(F_actual);
        int numberWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        int año = calendar.get(Calendar.YEAR);
        LblSemanaActual.setText(año+getSemanaM(numberWeekOfYear));
        

    }
    
    
    private void mensajesNotificaciones(){
     Connection cnx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlSolicitaAgregar = "SELECT COUNT(*) AS total FROM Solicitudes WHERE Solicitud_Tipo=1 AND Solicitud_Status=0";
        String sqlSolicitaEliminar = "SELECT COUNT(*) AS total FROM Solicitudes WHERE Solicitud_Tipo=0 AND Solicitud_Status=0";
        String comentarios = "SELECT COUNT(*) AS total FROM Corte_Rutas INNER JOIN Ruta_Comentarios ON Ruta_Comentarios.Comentarios_Venta = Corte_Rutas.DetCort_Venta";
        Modelo.conexion conexbd = new Modelo.conexion();
        conexbd.crearConexion();
        cnx = conexbd.getConexion();
        
        try {
            
            ps = cnx.prepareStatement(sqlSolicitaAgregar);
            rs = ps.executeQuery();
            if (rs.next()) {
                Label_msn_agragar.setVisible(true);
                Label_msn_agragar.setText(rs.getString("total"));

                switch (rs.getInt("total")) {
                    case 0:
                        Label_msn_agragar.setVisible(false);
                        break;
                }
            }

            ps = cnx.prepareStatement(sqlSolicitaEliminar);
            rs = ps.executeQuery();

            if (rs.next()) {
                Label_msn_eliminar.setVisible(true);
                Label_msn_eliminar.setText(rs.getString("total"));

                switch (rs.getInt("total")) {
                    case 0:
                        Label_msn_eliminar.setVisible(false);
                        break;
                }
            }

            ps = cnx.prepareStatement(comentarios);
            rs = ps.executeQuery();

            if (rs.next()) {
                Label_msn_comentarios.setVisible(true);
                Label_msn_comentarios.setText(rs.getString("total"));

                switch (rs.getInt("total")) {
                    case 0:
                        Label_msn_comentarios.setVisible(false);
                        break;
                }

            }
                
            
        } catch (SQLException e) {
           // e.printStackTrace();
        }finally{
            try {
                if (cnx != null) {
                    cnx.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
            }
        }
    
    }

    @FXML
    private void GUARDARINFORMACION(ActionEvent event) {
        TruncarTabla();
        insertar();
        BTNBUSQUEDA.setDisable(true);
    }

    public ObservableList<TablaDetallesExcel> LeerBoobExcel(FileInputStream excelFilePath) throws IOException {

        listBooks = FXCollections.observableArrayList();
        //FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

        try {

            try (Workbook workbook = new XSSFWorkbook(excelFilePath)) {
                org.apache.poi.ss.usermodel.Sheet firstSheet = workbook.getSheetAt(0);
                Iterator<Row> iterator = firstSheet.iterator();

                while (iterator.hasNext()) {
                    Row nextRow = iterator.next();
                    Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = nextRow.cellIterator();
                    TablaDetallesExcel DETALLES = new TablaDetallesExcel("","", "", "", "", "", "", "", "", "", "", "", "", "", "", "");

                    while (cellIterator.hasNext()) {
                        org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();

                        int index = cell.getColumnIndex();

                        switch (index) {
                            case 0:
                                DETALLES.setNUMEROCLIENTE(getCellValue(cell).toString());
                                break;
                            case 1:
                                DETALLES.setNOMBRECLIENTE(getCellValue(cell).toString());
                                break;
                            case 2:
                                DETALLES.setVENTA(getCellValue(cell).toString());
                                break;
                            case 3:
                                DETALLES.setFECHAVENTA(getCellValue(cell).toString());
                                break;
                            case 4:
                                DETALLES.setFACTURA(getCellValue(cell).toString());
                                break;
                            case 5:
                                DETALLES.setFECHAFACTURA(getCellValue(cell).toString());
                                break;
                            case 6:
                                DETALLES.setTOTAL(getCellValue(cell).toString());
                                break;
                            case 7:
                                DETALLES.setVENDEDOR(getCellValue(cell).toString());
                                break;
                            case 8:
                                DETALLES.setFOLIODESBLOQ(getCellValue(cell).toString());
                                break;
                            case 9:
                                String x = trim(getCellValue(cell).toString());
                                if (x.isEmpty()) {
                                    DETALLES.setHRAIMPRESION("1970/01/01 00:00:00");
                                } else {
                                    DETALLES.setHRAIMPRESION(getCellValue(cell).toString());
                                }
                                break;
                            case 10:

                                String[] parts = DETALLES.getFECHAFACTURA().split(" ");
                                String[] parts2 = getCellValue(cell).toString().split(" ");

                                if (parts.length == 2 && parts2.length == 2) {
                                    String FechaFinal = parts[0] + " " + parts2[1];
                                    DETALLES.setFECHAFACTURA(FechaFinal);

                                } else {
                                    DETALLES.setFECHAFACTURA(trim(DETALLES.getFECHAFACTURA().replace("/", " ")));
                                    DETALLES.setFECHAFACTURA("1970-01-01 00:00:00");
                                }
                                break;
                            case 11:
                                DETALLES.setPARTIDAS(getCellValue(cell).toString());
                                break;
                            case 12:
                                DETALLES.setESTATUS(getCellValue(cell).toString());
                                break;
                            case 13:
                                DETALLES.setESTADOPED(getCellValue(cell).toString());
                                break;
                            case 14:
                                DETALLES.setCIUDAD(getCellValue(cell).toString());
                                break;
                            case 16:
                                DETALLES.setRUTA(getCellValue(cell).toString());
                                break;
                            case 17:
                                DETALLES.setBLOQUEADO(getCellValue(cell).toString());
                                break;
                        }

//                          System.out.print(getCellValue(cell));
//                        System.out.print(" - ");
                    }
                    listBooks.add(DETALLES);

                }
            }

        } catch (IOException e) {
            e.getMessage();
        }

        return listBooks;
    }

    private Object getCellValue(Cell cell) {

//        cell.setCellType(CellType.STRING);
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:

                return cell.getStringCellValue();

            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();

            case Cell.CELL_TYPE_NUMERIC:

                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date);
                } else {
                    cell.setCellType(CellType.STRING);
                    return cell.getStringCellValue();
                }
            case Cell.CELL_TYPE_BLANK:
                return cell.getStringCellValue();

        }
        return null;
    }

    @FXML
    private void AccionSeleccionaArchivo(ActionEvent event) throws FileNotFoundException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar el certificado");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel 2007", "*.xlsx"),
                new FileChooser.ExtensionFilter("Excel 2003", "*.xls")
        );

        File Excel = fileChooser.showOpenDialog(new Stage());

        if (Excel != null) {

            FileInputStream fis = new FileInputStream(Excel);
            TXTARCHIVO.setText(Excel.getName());

            try {

                Lectura = LeerBoobExcel(fis);

                COLCLIENTE.setCellValueFactory(new PropertyValueFactory<>("NUMEROCLIENTE"));
                COLNOMBRECLIENTE.setCellValueFactory(new PropertyValueFactory<>("NOMBRECLIENTE"));
                COLVENTA.setCellValueFactory(new PropertyValueFactory<>("VENTA"));
                COLFECHAVENTA.setCellValueFactory(new PropertyValueFactory<>("FECHAVENTA"));
                COLFAC.setCellValueFactory(new PropertyValueFactory<>("FACTURA"));

                COLFECHAFAC.setCellValueFactory(new PropertyValueFactory<>("FECHAFACTURA"));
                COLTOTAL.setCellValueFactory(new PropertyValueFactory<>("TOTAL"));
                COLVENDEDOR.setCellValueFactory(new PropertyValueFactory<>("VENDEDOR"));
                COLFOLIODESBLOQ.setCellValueFactory(new PropertyValueFactory<>("FOLIODESBLOQ"));
                COLHORAIMPRESION.setCellValueFactory(new PropertyValueFactory<>("HRAIMPRESION"));

                COLPARTIDAS.setCellValueFactory(new PropertyValueFactory<>("PARTIDAS"));
                COLESTATUS.setCellValueFactory(new PropertyValueFactory<>("ESTATUS"));
                COLESTADOPED.setCellValueFactory(new PropertyValueFactory<>("ESTADOPED"));
                COLCIUDAD.setCellValueFactory(new PropertyValueFactory<>("CIUDAD"));
                COLRUTA.setCellValueFactory(new PropertyValueFactory<>("RUTA"));
                COLBLOQUEDADO.setCellValueFactory(new PropertyValueFactory<>("BLOQUEADO"));
                TABLAINFO.setItems(Lectura);
                COLVENDEDOR.setSortType(TableColumn.SortType.ASCENDING);

                FilteredList<TablaDetallesExcel> filteredData = new FilteredList<>(Lectura, p -> true);

                txt_Busqueda.textProperty().addListener((observable, oldValue, newValue)
                        -> {
                    filteredData.setPredicate(Detalles
                            -> {
                        // Si el texto del filtro está vacío, muestre todo el inventario.
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        // Comparar el codigo y la descripcion de cada articulo con el texto del filtro
                        String lowerCaseFilter = newValue.toLowerCase();
                        if (Detalles.getNUMEROCLIENTE().toLowerCase().contains(lowerCaseFilter)) {
                            return true; // El filtro coincide con el primer codigo.
                        } else if (Detalles.getVENTA().toLowerCase().contains(lowerCaseFilter)) {
                            return true; // El filtro coincide con la descripcion.
                        }
                        return false; // No existe ninguna coincidencia.
                    });

                });

                // 3. Envolver la lista filtrada en una lista clasificada.
                SortedList<TablaDetallesExcel> sortedData = new SortedList<>(filteredData);
                // 4. Vincule el comparador SortedList al comparador TableView.
                sortedData.comparatorProperty().bind(TABLAINFO.comparatorProperty());
                // 5. Agregue los datos clasificados (y filtrados) a la tabla.
                TABLAINFO.setItems(sortedData);

            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void AccionEliminarFila(ActionEvent event) {
        Lectura.remove(0);
    }

    public void insertar() {
        Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {

                updateMessage("Empezando");

                int i = 0;
                BTNCAMBIOS.setDisable(true);
                ResultSet rs = null;
                PreparedStatement ps = null;
                PreparedStatement ps2 = null;
                PreparedStatement ps3 = null;

                Connection cnx = null;
                conexion conexbd = new conexion();
                conexbd.crearConexion();
                cnx = conexbd.getConexion();
                String preguntaCorte = "SELECT *,YEARWEEK(Rut_FechaCorte,1) as semanaC FROM Rutas";
                String sql = "INSERT INTO Corte_Rutas"
                            + "(DetCort_NumeroCliente,"
                            + "DetCort_NombreCliente,"
                            + "DetCort_Venta,"
                            + "DetCort_FechaVenta,"
                            + "DetCort_Factura,"
                            + "DetCort_FechaHoraFactura,"
                            + "DetCort_Total,"
                            + "DetCort_ClaveVendedor,"
                            + "DetCort_FolioDesbloqueo,"
                            + "DetCort_FechaHoraImpresion,"
                            + "DetCort_Partidas,"
                            + "DetCort_Estatus,"
                            + "DetCort_EstadoPedido,"
                            + "DetCort_Ciudad,"
                            + "DetCort_Ruta,"
                            + "DetCort_Bloqueado,"
                            + "Det_TipoCorte,"
                            + "Det_Semana) "
                            + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                String sql2 = "INSERT INTO Corte_Rutas_Final"
                            + "(DetCort_NumeroCliente,"
                            + "DetCort_NombreCliente,"
                            + "DetCort_Venta,"
                            + "DetCort_FechaVenta,"
                            + "DetCort_Factura,"
                            + "DetCort_FechaHoraFactura,"
                            + "DetCort_Total,"
                            + "DetCort_ClaveVendedor,"
                            + "DetCort_FolioDesbloqueo,"
                            + "DetCort_FechaHoraImpresion,"
                            + "DetCort_Partidas,"
                            + "DetCort_Estatus,"
                            + "DetCort_EstadoPedido,"
                            + "DetCort_Ciudad,"
                            + "DetCort_Ruta,"
                            + "DetCort_Bloqueado,"
                            + "Det_TipoCorte,"
                            + "Det_Semana) "
                            + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                try {
                    cnx.setAutoCommit(false);
                    ps = cnx.prepareStatement(sql);
                    ps2 = cnx.prepareStatement(preguntaCorte);
                    ps3 = cnx.prepareStatement(sql2);
                    rs = ps2.executeQuery();
                    String fechaActual;

                    Date F_actual = new Date();
                    Date F_BD;
                    String TipoCorte;

                    Calendar calendar = Calendar.getInstance();
                    calendar.setFirstDayOfWeek(Calendar.MONDAY);
                    calendar.setMinimalDaysInFirstWeek(4);
                    calendar.setTime(F_actual);
                    int numberWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
                    int año = calendar.get(Calendar.YEAR);
                    String Ruta = "";
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    fechaActual = df.format(F_actual);
                    int max = TABLAINFO.getItems().size();

                    List<TablaDetallesExcel> TemAeliminar = new ArrayList<>();
                    while (rs.next()) {
                        F_BD = rs.getTimestamp("Rut_FechaCorte");

                        for (TablaDetallesExcel item : TABLAINFO.getItems()) {

                            if (rs.getString("Rut_ruta").equals(item.getRUTA())) {
                                updateProgress(i, max);
                                updateMessage(i + " de " + max);
                                i++;
                                if (F_actual.before(F_BD)) {
                                    TipoCorte = "Precorte";
                                        ps.setString(1, item.getNUMEROCLIENTE());
                                        ps.setString(2, item.getNOMBRECLIENTE());
                                        ps.setString(3, item.getVENTA());
                                        ps.setString(4, item.getFECHAVENTA());
                                        ps.setString(5, item.getFACTURA());
                                        ps.setString(6, item.getFECHAFACTURA());
                                        ps.setString(7, item.getTOTAL());
                                        ps.setString(8, item.getVENDEDOR());
                                        ps.setString(9, item.getFOLIODESBLOQ());
                                        ps.setString(10, item.getHRAIMPRESION());
                                        ps.setString(11, item.getPARTIDAS());
                                        ps.setString(12, item.getESTATUS());
                                        ps.setString(13, item.getESTADOPED());
                                        ps.setString(14, item.getCIUDAD());
                                        ps.setString(15, item.getRUTA());
                                        ps.setString(16, item.getBLOQUEADO());
                                        ps.setString(17, TipoCorte);
                                        ps.setString(18, rs.getString("semanaC"));
                                        ps.addBatch();
                                } else {
                                    TipoCorte = "Corte";
                                        ps3.setString(1, item.getNUMEROCLIENTE());
                                        ps3.setString(2, item.getNOMBRECLIENTE());
                                        ps3.setString(3, item.getVENTA());
                                        ps3.setString(4, item.getFECHAVENTA());
                                        ps3.setString(5, item.getFACTURA());
                                        ps3.setString(6, item.getFECHAFACTURA());
                                        ps3.setString(7, item.getTOTAL());
                                        ps3.setString(8, item.getVENDEDOR());
                                        ps3.setString(9, item.getFOLIODESBLOQ());
                                        ps3.setString(10, item.getHRAIMPRESION());
                                        ps3.setString(11, item.getPARTIDAS());
                                        ps3.setString(12, item.getESTATUS());
                                        ps3.setString(13, item.getESTADOPED());
                                        ps3.setString(14, item.getCIUDAD());
                                        ps3.setString(15, item.getRUTA());
                                        ps3.setString(16, item.getBLOQUEADO());
                                        ps3.setString(17, TipoCorte);
                                        ps3.setString(18, rs.getString("semanaC"));
                                        ps3.addBatch();
                                }
                                TemAeliminar.add(item);
                            }

                        }

                        Ruta = rs.getString("Rut_ruta");

                        if (F_actual.before(F_BD)) {

                        } else {
                            if (rs.getInt("Rut_CantidadCortesSemana") == 1) {
                                ActulizarRutaCorte(Ruta, 7);
                            } else {
                                if (rs.getInt("Rut_CantidadCortesSemana") > rs.getInt("Rut_CantidadCortesRealizados"))
                                {
                                    if (rs.getString("Rut_ruta").equals("27") || rs.getString("Rut_ruta").equals("28") || rs.getString("Rut_ruta").equals("19")) {
                                        ActulizarRutaCorte(rs.getString("Rut_ruta"), 1);//suma los dias al corte
                                    }
                                    if (rs.getString("Rut_ruta").equals("10")) {
                                        if(rs.getInt("Rut_CantidadCortesRealizados") == 0 )//el primer corte de la semana aun no se ha hecho entonces nos referimos q es el corte del lunes
                                        {
                                            ActulizarRutaCorte(rs.getString("Rut_ruta"), 1);//avanzamos un solo dia ya que el siguiente corte es el dia siguente
                                        }
                                        if(rs.getInt("Rut_CantidadCortesRealizados") == 1 )//el segundo corte de la semana entonces nos referimos q es el corte del martes
                                        {
                                            ActulizarRutaCorte(rs.getString("Rut_ruta"), 6);//avanzamos 6 dias para llegar al siguiente corte q seria el lunes
                                        }
                                    }
                                    if (rs.getString("Rut_ruta").equals("21")) {
                                        if(rs.getInt("Rut_CantidadCortesRealizados") == 0)
                                        {
                                            ActulizarRutaCorte(rs.getString("Rut_ruta"), 1);//avanzamos un solo dia ya que el siguiente corte es el dia siguente
                                        }
                                        if(rs.getInt("Rut_CantidadCortesRealizados") == 1)
                                        {
                                            ActulizarRutaCorte(rs.getString("Rut_ruta"), 6);//avanzamos un solo dia ya que el siguiente corte es el dia siguente
                                        }
                                    }
                                    if (rs.getString("Rut_ruta").equals("25")) {
                                        if(rs.getInt("Rut_CantidadCortesRealizados") == 0 )
                                        {
                                            ActulizarRutaCorte(rs.getString("Rut_ruta"), 2);
                                        }
                                        if(rs.getInt("Rut_CantidadCortesRealizados") == 1)
                                        {
                                            ActulizarRutaCorte(rs.getString("Rut_ruta"), 2);
                                        }
                                        if(rs.getInt("Rut_CantidadCortesRealizados") == 2)
                                        {
                                            ActulizarRutaCorte(rs.getString("Rut_ruta"), 3);
                                        }
                                    }
                                    if (rs.getString("Rut_ruta").equals("26")) {
                                        if(rs.getInt("Rut_CantidadCortesRealizados") == 0)
                                        {
                                            ActulizarRutaCorte(rs.getString("Rut_ruta"), 2);
                                        }
                                        if(rs.getInt("Rut_CantidadCortesRealizados") == 1)
                                        {
                                            ActulizarRutaCorte(rs.getString("Rut_ruta"), 5);
                                        }
                                    }
                                    ActulizarCortesdelasemanahechos(rs.getString("Rut_ruta"));
                                } else {
                                    ActulizarRutaCorte(rs.getString("Rut_ruta"), 1);//suma los dias al corte
                                    ActulizarCortesdelasemanahechosreset(rs.getString("Rut_ruta"));//set a col de cantidad de cortes realizados a 0 (cero)
                                }
                            }
                        }
                    }
                    ps.executeBatch();
                    if(ps3 != null)
                    {
                        ps3.executeBatch();
                    }
                    cnx.commit();

                    BTNCAMBIOS.setDisable(false);
                    BTNBUSQUEDA.setDisable(false);
                    updateMessage("Completado: " + i + " de " + max);
                    Lectura.removeAll(TemAeliminar);

                } catch (SQLException ex) {
                    System.out.println(ex);
                    try {
                        if (cnx != null) {
                            cnx.rollback();
                        }
                        ex.printStackTrace();

                        updateMessage(ex.getMessage());
                        BTNCAMBIOS.setDisable(false);
                        BTNBUSQUEDA.setDisable(false);
                    } catch (SQLException e) {
                        BTNCAMBIOS.setDisable(false);
                        BTNBUSQUEDA.setDisable(false);
                        System.out.println("Error al cerrar conexión de rollback"+e.getMessage());
                    }

                } finally {
                    try {
                        if (ps != null) {
                            ps.close();
                        }
                        if (rs != null) {
                            rs.close();
                        }
                        if (ps2 != null) {
                            ps2.close();
                        }
                        if (cnx != null) {
                            cnx.close();
                        }
                    } catch (SQLException e) {
                    }

                }

                return null;
            }
        };

        ProgresoTarea.progressProperty().bind(task.progressProperty());

        labelMensaje.textProperty().bind(task.messageProperty());
        new Thread(task).start();

    }

    private String getSemanaM(int numero) {
        String retorno = null;
        switch (numero) {
            case 1:
                retorno = "01";
                break;
            case 2:
                retorno = "02";
                break;
            case 3:
                retorno = "03";
                break;
            case 4:
                retorno = "04";
                break;
            case 5:
                retorno = "05";
                break;
            case 6:
                retorno = "06";
                break;
            case 7:
                retorno = "07";
                break;
            case 8:
                retorno = "08";
                break;
            case 9:
                retorno = "09";
                break;
        }
        if (numero >= 10) {
            retorno = Integer.toString(numero);
        }

        return retorno;
    }

    @FXML
    private void AccionVerDatos(ActionEvent event) {
        CRPVISTA x = new CRPVISTA();
        x.start(new Stage());
    }

    @FXML
    private void AccionReporteCompleto(ActionEvent event) {
        try {
            Connection cnx = null;
            Modelo.conexion conexbd = new Modelo.conexion();
            conexbd.crearConexion();
            cnx = conexbd.getConexion();
            HashMap<String, Object> ParametrosJasperReport = new HashMap<>();
            ParametrosJasperReport.put("P_VENDEDOR", "");
            ParametrosJasperReport.put("P_RUTA", "");
            ParametrosJasperReport.put("P_SEMANA", "semana");
            InputStream fis2 = null;
            fis2 = this.getClass().getResourceAsStream("/Reporte/ReporteGeneral.jasper");
            JasperReport reporte = (JasperReport) JRLoader.loadObject(fis2);
            JasperPrint print = JasperFillManager.fillReport(reporte, ParametrosJasperReport, cnx);
            JasperFX jpFX = new JasperFX(print);
            jpFX.show();
        } catch (NumberFormatException | JRException e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Look, an Information Dialog");
            alert.setContentText("I have a great message for you!" + e);
            alert.showAndWait();
        }

    }

   

    @FXML
    private void AccionReporteComentarios(ActionEvent event) {
        Connection cnx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            
            Modelo.conexion conexbd = new Modelo.conexion();
            conexbd.crearConexion();
            cnx = conexbd.getConexion();

            String sql = "SELECT * FROM Rutas ORDER BY Rut_ruta ASC";
            ps = cnx.prepareStatement(sql);
            List<String> choices = new ArrayList<>();
            rs = ps.executeQuery();
            choices.add("Todos");
            while (rs.next()) {
                choices.add(rs.getString("Rut_ruta"));
            }

            ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
            dialog.setTitle("Rutas");
            dialog.setHeaderText("Elige una opcion");
            dialog.setContentText("Ruta a consultar");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {

                if (result.get().equals("Todos")) {

                    HashMap<String, Object> ParametrosJasperReport = new HashMap<>();
                    ParametrosJasperReport.put("DetCort_Ruta_VISTA", result.get());
                    InputStream fis2 = null;
                    fis2 = this.getClass().getResourceAsStream("/Reporte/ventasComentadasTodas.jasper");
                    JasperReport reporte = (JasperReport) JRLoader.loadObject(fis2);
                    JasperPrint print = JasperFillManager.fillReport(reporte, ParametrosJasperReport, cnx);
                    JasperFX jpFX = new JasperFX(print);
                    jpFX.show();

                } else {
                    HashMap<String, Object> ParametrosJasperReport = new HashMap<>();
                    ParametrosJasperReport.put("DetCort_Ruta_VISTA", result.get());
                    InputStream fis2 = null;
                    fis2 = this.getClass().getResourceAsStream("/Reporte/ventasComentadas.jasper");
                    JasperReport reporte = (JasperReport) JRLoader.loadObject(fis2);
                    JasperPrint print = JasperFillManager.fillReport(reporte, ParametrosJasperReport, cnx);
                    JasperFX jpFX = new JasperFX(print);
                    jpFX.show();
                }
            }

        } catch (NumberFormatException | JRException e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Look, an Information Dialog");
            alert.setContentText("I have a great message for you!" + e);
            alert.showAndWait();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
        
            try {
                if (cnx != null) {
                    cnx.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                
            }
        }
    }

    public void ActulizarRutaCorte(String ruta, int DiasAumento) {
        PreparedStatement ps = null;
        Connection cnx = null;
        conexion conexbd = new conexion();
        conexbd.crearConexion();
        cnx = conexbd.getConexion();
        String sql = "UPDATE Rutas SET Rut_FechaCorte=DATE_ADD(Rut_FechaCorte, INTERVAL ? DAY) WHERE Rut_ruta=?";

        try {
            ps = cnx.prepareStatement(sql);
            ps.setInt(1, DiasAumento);
            ps.setString(2, ruta);
            ps.executeUpdate();
        } catch (SQLException ex) {
            BTNCAMBIOS.setDisable(false);
            BTNBUSQUEDA.setDisable(false);
            System.out.println("Error al cerrar conexión de Precorte"+ex.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (cnx != null) {
                    cnx.close();
                }
            } catch (SQLException e) {
                BTNCAMBIOS.setDisable(false);
                BTNBUSQUEDA.setDisable(false);
                System.out.println("Error al cerrar conexión de Precorte"+e.getMessage());
            }

        }
    }

    public void GenerarCorteFinal(TablaDetallesExcel x, String TipoCorte, String TipoSemana) {
        PreparedStatement ps = null;
        Connection cnx = null;
        conexion conexbd = new conexion();
        conexbd.crearConexion();
        cnx = conexbd.getConexion();
        String sql = "INSERT INTO Corte_Rutas_Final"
                + "(DetCort_NumeroCliente,"
                + "DetCort_NombreCliente,"
                + "DetCort_Venta,"
                + "DetCort_FechaVenta,"
                + "DetCort_Factura,"
                + "DetCort_FechaHoraFactura,"
                + "DetCort_Total,"
                + "DetCort_ClaveVendedor,"
                + "DetCort_FolioDesbloqueo,"
                + "DetCort_FechaHoraImpresion,"
                + "DetCort_Partidas,"
                + "DetCort_Estatus,"
                + "DetCort_EstadoPedido,"
                + "DetCort_Ciudad,"
                + "DetCort_Ruta,"
                + "DetCort_Bloqueado,"
                + "Det_TipoCorte,"
                + "Det_Semana) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {

            ps = cnx.prepareStatement(sql);
            ps.setString(1, x.getNUMEROCLIENTE());
            ps.setString(2, x.getNOMBRECLIENTE());
            ps.setString(3, x.getVENTA());
            ps.setString(4, x.getFECHAVENTA());
            ps.setString(5, x.getFACTURA());
            ps.setString(6, x.getFECHAFACTURA());
            ps.setString(7, x.getTOTAL());
            ps.setString(8, x.getVENDEDOR());
            ps.setString(9, x.getFOLIODESBLOQ());
            ps.setString(10, x.getHRAIMPRESION());
            ps.setString(11, x.getPARTIDAS());
            ps.setString(12, x.getESTATUS());
            ps.setString(13, x.getESTADOPED());
            ps.setString(14, x.getCIUDAD());
            ps.setString(15, x.getRUTA());
            ps.setString(16, x.getBLOQUEADO());
            ps.setString(17, TipoCorte);
            ps.setString(18, TipoSemana);
            ps.executeUpdate();
        } catch (SQLException e) {
            BTNCAMBIOS.setDisable(false);
            BTNBUSQUEDA.setDisable(false);
            System.out.println("Error : Corte" + e.toString());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (cnx != null) {
                    cnx.close();
                }
            } catch (SQLException e) {
                BTNCAMBIOS.setDisable(false);
                BTNBUSQUEDA.setDisable(false);
                System.out.println("Error al cerrar conexión de Precorte"+e.getMessage());
            }

        }
    }

    public void GenerarPreCorteGenerico(TablaDetallesExcel x, String TipoCorte, String TipoSemana) {
        PreparedStatement ps = null;
        Connection cnx = null;
        conexion conexbd = new conexion();
        conexbd.crearConexion();
        cnx = conexbd.getConexion();
        String sql = "INSERT INTO Corte_Rutas"
                + "(DetCort_NumeroCliente,"
                + "DetCort_NombreCliente,"
                + "DetCort_Venta,"
                + "DetCort_FechaVenta,"
                + "DetCort_Factura,"
                + "DetCort_FechaHoraFactura,"
                + "DetCort_Total,"
                + "DetCort_ClaveVendedor,"
                + "DetCort_FolioDesbloqueo,"
                + "DetCort_FechaHoraImpresion,"
                + "DetCort_Partidas,"
                + "DetCort_Estatus,"
                + "DetCort_EstadoPedido,"
                + "DetCort_Ciudad,"
                + "DetCort_Ruta,"
                + "DetCort_Bloqueado,"
                + "Det_TipoCorte,"
                + "Det_Semana) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {

            ps = cnx.prepareStatement(sql);
            ps.setString(1, x.getNUMEROCLIENTE());
            ps.setString(2, x.getNOMBRECLIENTE());
            ps.setString(3, x.getVENTA());
            ps.setString(4, x.getFECHAVENTA());
            ps.setString(5, x.getFACTURA());
            ps.setString(6, x.getFECHAFACTURA());
            ps.setString(7, x.getTOTAL());
            ps.setString(8, x.getVENDEDOR());
            ps.setString(9, x.getFOLIODESBLOQ());
            ps.setString(10, x.getHRAIMPRESION());
            ps.setString(11, x.getPARTIDAS());
            ps.setString(12, x.getESTATUS());
            ps.setString(13, x.getESTADOPED());
            ps.setString(14, x.getCIUDAD());
            ps.setString(15, x.getRUTA());
            ps.setString(16, x.getBLOQUEADO());
            ps.setString(17, TipoCorte);
            ps.setString(18, TipoSemana);
            ps.executeUpdate();
        } catch (SQLException e) {
            BTNCAMBIOS.setDisable(false);
            BTNBUSQUEDA.setDisable(false);
            System.out.println("Error: Precorte:--" + e.toString());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (cnx != null) {
                    cnx.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar conexión de Precorte"+e.getMessage());
            }

        }
    }
    public void TruncarTabla() {
        PreparedStatement ps = null;
        Connection cnx = null;
        conexion conexbd = new conexion();
        conexbd.crearConexion();
        cnx = conexbd.getConexion();
        String sql = "TRUNCATE Corte_Rutas";
        try {
            ps = cnx.prepareStatement(sql);
           
            ps.executeUpdate();
        } catch (SQLException e) {
            BTNCAMBIOS.setDisable(false);
            BTNBUSQUEDA.setDisable(false);
            System.out.println("Error: " + e.toString());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (cnx != null) {
                    cnx.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void ActulizarCortesdelasemanahechos(String ruta) {
        PreparedStatement ps = null;
        Connection cnx = null;
        conexion conexbd = new conexion();
        conexbd.crearConexion();
        cnx = conexbd.getConexion();
        String sql = "UPDATE Rutas SET Rut_CantidadCortesRealizados=Rut_CantidadCortesRealizados+1 WHERE Rut_ruta=?";

        try {
            ps = cnx.prepareStatement(sql);
            ps.setString(1, ruta);
            ps.executeUpdate();
        } catch (SQLException ex) {
            BTNCAMBIOS.setDisable(false);
            BTNBUSQUEDA.setDisable(false);
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (cnx != null) {
                    cnx.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    public void ActulizarCortesdelasemanahechosreset(String ruta) {
        PreparedStatement ps = null;
        Connection cnx = null;
        conexion conexbd = new conexion();
        conexbd.crearConexion();
        cnx = conexbd.getConexion();
        String sql = "UPDATE Rutas SET Rut_CantidadCortesRealizados=0 WHERE Rut_ruta=?";

        try {
            ps = cnx.prepareStatement(sql);
            ps.setString(1, ruta);
            ps.executeUpdate();
        } catch (SQLException ex) {
            BTNCAMBIOS.setDisable(false);
            BTNBUSQUEDA.setDisable(false);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (cnx != null) {
                    cnx.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    private void AccionEliminaVenta(ActionEvent event) {
         try {
            Connection cnx = null;
            Modelo.conexion conexbd = new Modelo.conexion();
            conexbd.crearConexion();
            cnx = conexbd.getConexion();
            HashMap<String, Object> ParametrosJasperReport = new HashMap<>();
            ParametrosJasperReport.put("DetCort_Ruta_VISTA", "");
           
            InputStream fis2 = null;
            fis2 = this.getClass().getResourceAsStream("/Reporte/SolicitudEliminacionVenta.jasper");
            JasperReport reporte = (JasperReport) JRLoader.loadObject(fis2);
            JasperPrint print = JasperFillManager.fillReport(reporte, ParametrosJasperReport, cnx);
            JasperFX jpFX = new JasperFX(print);
            jpFX.show();
            
            
            
            
            
            
             Alert alert = new Alert(AlertType.CONFIRMATION);
             alert.setTitle("Confirmar");
             alert.setHeaderText("Desea marcar como leido estos mensajes");
             alert.setContentText("Recuerde guardar el documento");

             ButtonType buttonTypeOne = new ButtonType("Si");
             ButtonType buttonTypeTwo = new ButtonType("No");
             

             alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

             Optional<ButtonType> result = alert.showAndWait();
             if (result.get() == buttonTypeOne) {
                MarcarComoLeidoMensajes(0);
             } else if (result.get() == buttonTypeTwo) {
                 // ... user chose "Two"
             }else {
                 // ... user chose CANCEL or closed the dialog
             }
             
             
        } catch (NumberFormatException | JRException e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Look, an Information Dialog");
            alert.setContentText("I have a great message for you!" + e);
            alert.showAndWait();
        }
    }

    @FXML
    private void AccionAgregarVenta(ActionEvent event) {
         try {
            Connection cnx = null;
            Modelo.conexion conexbd = new Modelo.conexion();
            conexbd.crearConexion();
            cnx = conexbd.getConexion();
            HashMap<String, Object> ParametrosJasperReport = new HashMap<>();
            ParametrosJasperReport.put("DetCort_Ruta_VISTA", "");
            InputStream fis2 = null;
            fis2 = this.getClass().getResourceAsStream("/Reporte/SolicitudAgregaVenta.jasper");
            JasperReport reporte = (JasperReport) JRLoader.loadObject(fis2);
            JasperPrint print = JasperFillManager.fillReport(reporte, ParametrosJasperReport, cnx);
            JasperFX jpFX = new JasperFX(print);
            jpFX.show();
            
            
            
           
             Alert alert = new Alert(AlertType.CONFIRMATION);
             alert.setTitle("Confirmar");
             alert.setHeaderText("Desea marcar como leido estos mensajes");
             alert.setContentText("Recuerde guardar el documento");

             ButtonType buttonTypeOne = new ButtonType("Si");
             ButtonType buttonTypeTwo = new ButtonType("No");
             

             alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

             Optional<ButtonType> result = alert.showAndWait();
             if (result.get() == buttonTypeOne) {
                MarcarComoLeidoMensajes(1);
             } else if (result.get() == buttonTypeTwo) {
                 // ... user chose "Two"
             }else {
                 // ... user chose CANCEL or closed the dialog
             }
            
            
        } catch (NumberFormatException | JRException e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Look, an Information Dialog");
            alert.setContentText("I have a great message for you!" + e);
            alert.showAndWait();
        }
    }
    
    
    private void MarcarComoLeidoMensajes(int tipo){
        Connection cnx = null;
        PreparedStatement ps=null;
        Modelo.conexion conexbd = new Modelo.conexion();
        conexbd.crearConexion();
        cnx = conexbd.getConexion();
        String sql="UPDATE Solicitudes SET Solicitud_Status = 1 WHERE Solicitud_Tipo=?";
        try {
            ps=cnx.prepareStatement(sql);
            ps.setInt(1, tipo);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (cnx != null) {
                    cnx.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
            }
        }
        
    }
}
