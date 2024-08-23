package reportes;

import java.util.ArrayList;

import modelos.ArticuloPedido;
import modelos.Pedido;

public class ReportHojaRuta {

	private ArrayList<ArticuloPedido> articulosList;
	private ArrayList<Pedido> pedidoList;

	public ReportHojaRuta(){}

	public ArrayList<ArticuloPedido> getArticulosList() {
		return articulosList;
	}
	public void setArticulosList(ArrayList<ArticuloPedido> articulosList) {
		this.articulosList = articulosList;
	}
	public ArrayList<Pedido> getPedidoList() {
		return pedidoList;
	}
	public void setPedidoList(ArrayList<Pedido> pedidoList) {
		this.pedidoList = pedidoList;
	}

	public int getCodigo() {
		return this.getPedidoList().iterator().next().getPedidoList().iterator().next().getCodigo();
	}

	public String getRazonSocial() {
		return this.getPedidoList().iterator().next().getRazonSocial();
	}

	public int getCantidad() {
		return this.getPedidoList().iterator().next().getPedidoList().iterator().next().getCantidad();
	}

}
