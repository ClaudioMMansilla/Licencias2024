package modelos;

public class Producto {

	// nombre de los atributos tomado idem de la base de datos
	private int idProducto = -1;
	private String producto ="null";
	private String marca ="null";
	private int stock = -1;
	private int familiaProducto = -1;
	private int tableDB = -1; //tabla a la cual se debe realizar el insert


	public Producto(int idProducto, String producto) {

		this.idProducto = idProducto;
		this.producto = producto;
	}

	public void producto(int idProducto, String producto, String marca,
			int stock, int familiaProducto, int tableDB) {

		this.idProducto = idProducto;
		this.producto = producto;
		this.marca = marca;
		this.stock = stock;
		this.familiaProducto = familiaProducto;
		this.tableDB = tableDB;

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
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getFamiliaProducto() {
		return familiaProducto;
	}
	public void setFamiliaProducto(int familiaProducto) {
		this.familiaProducto = familiaProducto;
	}
	public int getTableDB() {
		return tableDB;
	}
	public void setTableDB(int tableBD) {
		this.tableDB = tableBD;
	}

}
