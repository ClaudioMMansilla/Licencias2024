package reportes;

public class PrintEnvReg {
	private String fechario;
	private String planta;
	private String id;
	private String producto;
	private String lote;
	private String cajas;
	private String vto;
	private String pallet;

	public PrintEnvReg(String fechario, String planta, String id,
			String producto, String cajas, String pallet, String lote,String vto) {
		super();
		this.fechario = fechario;
		this.planta = planta;
		this.id = id;
		this.producto = producto;
		this.cajas = cajas;
		this.pallet = pallet;
		this.lote = lote;
		this.vto = vto;

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

	public String getVto() {
		return vto;
	}

	public void setVto(String vto) {
		this.vto = vto;
	}

	public String getPallet() {
		return pallet;
	}

	public void setPallet(String pallet) {
		this.pallet = pallet;
	}

}
