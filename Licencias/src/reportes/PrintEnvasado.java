package reportes;

public class PrintEnvasado {
	private String numPallet;
	private String fechaIngreso;
	private String idProducto;
	private String producto;
	private String lote;
	private String vencimiento;
	private String cajasInformadas;

	public PrintEnvasado(
			String numPallet,
			String fechaIngreso,
			String idProducto,
			String producto,
			String lote,
			String vencimiento,
			String cajasInformadas)
	{
		super();
		this.numPallet = numPallet;
		this.fechaIngreso = fechaIngreso;
		this.idProducto = idProducto;
		this.producto = producto;
		this.lote = lote;
		this.vencimiento = vencimiento;
		this.cajasInformadas = cajasInformadas;
	}

	public String getNumPallet() {
		return numPallet;
	}

	public void setNumPallet(String numPallet) {
		this.numPallet = numPallet;
	}

	public String getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getId() {
		return idProducto;
	}

	public void setId(String idProducto) {
		this.idProducto = idProducto;
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

	public String getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(String vencimiento) {
		this.vencimiento = vencimiento;
	}

	public String getCajasInformadas() {
		return cajasInformadas;
	}

	public void setCajasInformadas(String cajasInformadas) {
		this.cajasInformadas = cajasInformadas;
	}

}
