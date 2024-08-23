package reportes;

public class LeerStock {
	private String idProducto;
	private String producto;
	private String marca;
	private String stock;

	public LeerStock(
			String idProducto,
			String producto,
			String marca,
			String stock
			 )
	{
		super();
		this.idProducto = idProducto;
		this.producto = producto;
		this.marca = marca;
		this.stock = stock;
	}


	public String getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}


}
