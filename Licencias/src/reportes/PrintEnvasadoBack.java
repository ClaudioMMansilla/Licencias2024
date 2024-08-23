package reportes;

public class PrintEnvasadoBack {
	private String fechario;
	private String planta;
	private String id;
	private String producto;
	private String lote;
	private String cajas;
	private String scrap;

	public PrintEnvasadoBack(String fechario, String planta, String id,
			String producto, String lote, String cajas, String scrap) {
		super();
		this.fechario = fechario;
		this.planta = planta;
		this.id = id;
		this.producto = producto;
		this.lote = lote;
		this.cajas = cajas;
		this.scrap = scrap;
	}

	public String getFechario() {
		return fechario;
	}

	public void setFechario(String fechario) {
		this.fechario = fechario;
	}

	public String getPlanta() {
		return planta;
	}

	public void setPlanta(String planta) {
		this.planta = planta;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public String getCajas() {
		return cajas;
	}

	public void setCajas(String cajas) {
		this.cajas = cajas;
	}

	public String getScrap() {
		return scrap;
	}

	public void setScrap(String scrap) {
		this.scrap = scrap;
	}

}
