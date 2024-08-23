package reportes;

public class InformeProduccion {
	private int idProducto;
	private String producto;
	private int produccion;
	private int mercLiberada;
	private int mercVendida;
	private int stock;



	public InformeProduccion(int idProducto, String producto, int produccion, int mercLiberada, int mercVendida,
			int stock) {
		super();
		this.idProducto = idProducto;
		this.producto = producto;
		this.produccion = produccion;
		this.mercLiberada = mercLiberada;
		this.mercVendida = mercVendida;
		this.stock = stock;
	}


	public int getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public int getProduccion() {
		return produccion;
	}
	public void setProduccion(int produccion) {
		this.produccion = produccion;
	}
	public int getMercLiberada() {
		return mercLiberada;
	}
	public void setMercLiberada(int mercLiberada) {
		this.mercLiberada = mercLiberada;
	}
	public int getMercVendida() {
		return mercVendida;
	}
	public void setMercVendida(int mercVendida) {
		this.mercVendida = mercVendida;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}



}
