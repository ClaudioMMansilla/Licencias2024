package modelos;

import java.sql.Date;
import java.util.ArrayList;

public class HojaDeRuta {

	private int hojaRutaID;
	private String choferID;
	private String vehiculoID;
	private int nroDeRemito;
	private int estadoHojaID;
	private java.util.Date fechaEmision;
	private java.util.Date fechaProgramada;
	private ArrayList<Pedido> pedidosList; // para utilizar en tiempo de ejecucion

	public HojaDeRuta() {
		this.pedidosList = new ArrayList<>();
	}

	public HojaDeRuta(int hojaRutaID, String choferID, String vehiculoID, int nroDeRemito, int estadoHojaID,
			Date fechaEmision, Date fechaProgramada) {
		super();
		this.hojaRutaID = hojaRutaID;
		this.choferID = choferID;
		this.vehiculoID = vehiculoID;
		this.nroDeRemito = nroDeRemito;
		this.estadoHojaID = estadoHojaID;
		this.fechaEmision = fechaEmision;
		this.fechaProgramada = fechaProgramada;
	}

	public int getHojaRutaID() {
		return hojaRutaID;
	}

	public void setHojaRutaID(int hojaRutaID) {
		this.hojaRutaID = hojaRutaID;
	}

	public String getChoferID() {
		return choferID;
	}

	public void setChoferID(String choferID) {
		this.choferID = choferID;
	}

	public String getVehiculoID() {
		return vehiculoID;
	}

	public void setVehiculoID(String vehiculoID) {
		this.vehiculoID = vehiculoID;
	}

	public int getNroDeRemito() {
		return nroDeRemito;
	}

	public void setNroDeRemito(int nroDeRemito) {
		this.nroDeRemito = nroDeRemito;
	}

	public int getEstadoHojaID() {
		return estadoHojaID;
	}

	public void setEstadoHojaID(int estadoHojaID) {
		this.estadoHojaID = estadoHojaID;
	}

	public java.util.Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public java.util.Date getFechaProgramada() {
		return fechaProgramada;
	}

	public void setFechaProgramada(java.util.Date date) {
		this.fechaProgramada = date;
	}

	public ArrayList<Pedido> getPedidosList() {
		return pedidosList;
	}

	public void setPedidosList(ArrayList<Pedido> pedidosList) {
		this.pedidosList = pedidosList;
	}

	public void addPedidoIntoPedidosList(Pedido pedido) {
		this.pedidosList.add(pedido);
	}


	public java.sql.Date getFechaProgramadaSqlDate() {
		java.sql.Date sqlDate = new java.sql.Date( getFechaProgramada().getTime() );
		return sqlDate;
	}


}