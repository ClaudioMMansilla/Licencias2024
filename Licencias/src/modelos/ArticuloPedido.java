package modelos;


/***
 * Objeto modelado de acuerdo a atributos de la base de datos MSSQL Server
 * @author pc
 */
public class ArticuloPedido {

	private int ventaID;
	private int productoID;
	private int codigo;
	private int cantidad;
	private String textoEnFactura;

	public ArticuloPedido(int ventaID, int productoID, int codigo, int cantidad, String textoEnFactura) {
		super();
		this.ventaID = ventaID;
		this.productoID = productoID;
		this.codigo = codigo;
		this.cantidad = cantidad;
		this.textoEnFactura = textoEnFactura;
	}


	public ArticuloPedido(int codigo, int cantidad, String textoEnFactura) {
		this.codigo = codigo;
		this.cantidad = cantidad;
		this.textoEnFactura = textoEnFactura;
	}

	public int getVentaID() {
		return ventaID;
	}

	public void setVentaID(int ventaID) {
		this.ventaID = ventaID;
	}

	public int getProductoID() {
		return productoID;
	}

	public void setProductoID(int productoID) {
		this.productoID = productoID;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getTextoEnFactura() {
		return textoEnFactura;
	}

	public void setTextoEnFactura(String textoEnFactura) {
		this.textoEnFactura = textoEnFactura;
	}









}
