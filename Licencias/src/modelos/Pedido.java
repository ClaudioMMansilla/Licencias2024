package modelos;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

public class Pedido {

	private ArrayList<ArticuloPedido> pedidoList;
	private int ventaID;
	private int nroDeRemito;
	private Date fecha;
	private String razonSocial;
	private int tiposDeDocumentosFiscalID;
	private BigDecimal netoNoGrabado;
	private int estadoVenta;
	private String idCliente;

	public Pedido() {
		this.pedidoList = new ArrayList<>();
	}

	public Pedido(int ventaID, int nroDeRemito, Date fecha, String razonSocial,
			int tiposDeDocumentosFiscalID, BigDecimal netoNoGrabado, int estadoVenta, String idCli) {
		super();

		this.pedidoList = new ArrayList<>();
		this.ventaID = ventaID;
		this.nroDeRemito = nroDeRemito;
		this.fecha = fecha;
		this.razonSocial = razonSocial;
		this.tiposDeDocumentosFiscalID = tiposDeDocumentosFiscalID;
		this.netoNoGrabado = netoNoGrabado;
		this.estadoVenta = estadoVenta;
		this.idCliente = idCli;
	}



	public ArrayList<ArticuloPedido> getPedidoList() {
		return pedidoList;
	}

	public void setPedidoList(ArrayList<ArticuloPedido> pedidoList) {
		this.pedidoList = pedidoList;
	}

	public int getVentaID() {
		return ventaID;
	}

	public void setVentaID(int ventaID) {
		this.ventaID = ventaID;
	}

	public int getNroDeRemito() {
		return nroDeRemito;
	}

	public void setNroDeRemito(int nroDeRemito) {
		this.nroDeRemito = nroDeRemito;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public int getTiposDeDocumentosFiscalID() {
		return tiposDeDocumentosFiscalID;
	}

	public void setTiposDeDocumentosFiscalID(int tiposDeDocumentosFiscalID) {
		this.tiposDeDocumentosFiscalID = tiposDeDocumentosFiscalID;
	}

	public BigDecimal getNetoNoGrabado() {
		return netoNoGrabado;
	}

	public void setNetoNoGrabado(BigDecimal netoNoGrabado) {
		this.netoNoGrabado = netoNoGrabado;
	}

	public int getEstadoVenta() {
		return estadoVenta;
	}

	public void setEstadoVenta(int estadoVenta) {
		this.estadoVenta = estadoVenta;
	}


	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}



	public String getNetoNoGrabadoToString() {

        // Crear un objeto DecimalFormat con el formato deseado
        DecimalFormat formato = new DecimalFormat("#,###.00");

        // Formatear el número y obtener la salida como una cadena
        return formato.format(this.netoNoGrabado);
	}

	public java.sql.Date getFechaSqlDate() {
		java.sql.Date sqlDate = new java.sql.Date( getFecha().getTime() );
		return sqlDate;
	}

}
