package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Sistemas2
 */
public class TablaDetallesExcel
{
    private final StringProperty NUMEROCLIENTE;
    private final StringProperty NOMBRECLIENTE;
    private final StringProperty VENTA;
    private final StringProperty FECHAVENTA;
    private final StringProperty FACTURA;
    private final StringProperty FECHAFACTURA;
    private final StringProperty TOTAL;
    private final StringProperty VENDEDOR;
    private final StringProperty FOLIODESBLOQ;
    private final StringProperty HRAIMPRESION;
    private final StringProperty PARTIDAS;
    private final StringProperty ESTATUS;
    private final StringProperty ESTADOPED;
    private final StringProperty CIUDAD;
    private final StringProperty RUTA;
    private final StringProperty BLOQUEADO;

    public final String getNUMEROCLIENTE() {
        return NUMEROCLIENTE.get();
    }

    public final void setNUMEROCLIENTE(String value) {
        NUMEROCLIENTE.set(value);
    }

    public StringProperty NUMEROCLIENTEProperty() {
        return NUMEROCLIENTE;
    }

    public final String getNOMBRECLIENTE() {
        return NOMBRECLIENTE.get();
    }

    public final void setNOMBRECLIENTE(String value) {
        NOMBRECLIENTE.set(value);
    }

    public StringProperty NOMBRECLIENTEProperty() {
        return NOMBRECLIENTE;
    }

    public final String getVENTA() {
        return VENTA.get();
    }

    public final void setVENTA(String value) {
        VENTA.set(value);
    }

    public StringProperty VENTAProperty() {
        return VENTA;
    }

    public final String getFECHAVENTA() {
        return FECHAVENTA.get();
    }

    public final void setFECHAVENTA(String value) {
        FECHAVENTA.set(value);
    }

    public StringProperty FECHAVENTAProperty() {
        return FECHAVENTA;
    }

    public final String getFACTURA() {
        return FACTURA.get();
    }

    public final void setFACTURA(String value) {
        FACTURA.set(value);
    }

    public StringProperty FACTURAProperty() {
        return FACTURA;
    }

    public final String getFECHAFACTURA() {
        return FECHAFACTURA.get();
    }

    public final void setFECHAFACTURA(String value) {
        FECHAFACTURA.set(value);
    }

    public StringProperty FECHAFACTURAProperty() {
        return FECHAFACTURA;
    }

    public final String getTOTAL() {
        return TOTAL.get();
    }

    public final void setTOTAL(String value) {
        TOTAL.set(value);
    }

    public StringProperty TOTALProperty() {
        return TOTAL;
    }

    public final String getVENDEDOR() {
        return VENDEDOR.get();
    }

    public final void setVENDEDOR(String value) {
        VENDEDOR.set(value);
    }

    public StringProperty VENDEDORProperty() {
        return VENDEDOR;
    }

    public final String getFOLIODESBLOQ() {
        return FOLIODESBLOQ.get();
    }

    public final void setFOLIODESBLOQ(String value) {
        FOLIODESBLOQ.set(value);
    }

    public StringProperty FOLIODESBLOQProperty() {
        return FOLIODESBLOQ;
    }

    public final String getHRAIMPRESION() {
        return HRAIMPRESION.get();
    }

    public final void setHRAIMPRESION(String value) {
        HRAIMPRESION.set(value);
    }

    public StringProperty HRAIMPRESIONProperty() {
        return HRAIMPRESION;
    }

    public final String getPARTIDAS() {
        return PARTIDAS.get();
    }

    public final void setPARTIDAS(String value) {
        PARTIDAS.set(value);
    }

    public StringProperty PARTIDASProperty() {
        return PARTIDAS;
    }

    public final String getESTATUS() {
        return ESTATUS.get();
    }

    public final void setESTATUS(String value) {
        ESTATUS.set(value);
    }

    public StringProperty ESTATUSProperty() {
        return ESTATUS;
    }

    public final String getESTADOPED() {
        return ESTADOPED.get();
    }

    public final void setESTADOPED(String value) {
        ESTADOPED.set(value);
    }

    public StringProperty ESTADOPEDProperty() {
        return ESTADOPED;
    }

    public final String getCIUDAD() {
        return CIUDAD.get();
    }

    public final void setCIUDAD(String value) {
        CIUDAD.set(value);
    }

    public StringProperty CIUDADProperty() {
        return CIUDAD;
    }

    public final String getRUTA() {
        return RUTA.get();
    }

    public final void setRUTA(String value) {
        RUTA.set(value);
    }

    public StringProperty RUTAProperty() {
        return RUTA;
    }

    public final String getBLOQUEADO() {
        return BLOQUEADO.get();
    }

    public final void setBLOQUEADO(String value) {
        BLOQUEADO.set(value);
    }

    public StringProperty BLOQUEADOProperty() {
        return BLOQUEADO;
    }
    
    
    public TablaDetallesExcel(String NoCliente,String Cliente,String venta,String fechaventa,String fac, String fechafac, String total,String vendedor,String foliodesbloq,String horaimpresion,String partidas, String status, String estadoped,String ciudad,String ruta, String bloqueado)
    {
        this.NUMEROCLIENTE=new SimpleStringProperty(NoCliente);
        this.NOMBRECLIENTE=new SimpleStringProperty(Cliente);
        this.VENTA=new SimpleStringProperty(venta);
        this.FECHAVENTA=new SimpleStringProperty(fechaventa);
        this.FACTURA=new SimpleStringProperty(fac);
        this.FECHAFACTURA=new SimpleStringProperty(fechafac);
        this.TOTAL= new SimpleStringProperty(total);
        this.VENDEDOR=new SimpleStringProperty(vendedor);
        this.FOLIODESBLOQ=new SimpleStringProperty(foliodesbloq);
        this.HRAIMPRESION=new SimpleStringProperty(horaimpresion);
        this.PARTIDAS=new SimpleStringProperty(partidas);
        this.ESTATUS=new SimpleStringProperty(status);
        this.ESTADOPED=new SimpleStringProperty(estadoped);
        this.CIUDAD=new SimpleStringProperty(ciudad);
        this.RUTA=new SimpleStringProperty(ruta);
        this.BLOQUEADO=new SimpleStringProperty(bloqueado);
    }
}
