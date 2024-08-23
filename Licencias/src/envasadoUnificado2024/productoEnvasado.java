package envasadoUnificado2024;

public class productoEnvasado{

	// nombre de los atributos tomado idem de la base de datos

	private String fechaIngreso;
	private String usuarioIngreso;
	private String fechaEgreso;
	private String usuarioEgreso;
	private int idProducto;
	private String planta;
	private String producto;
	private int lote;
	private String vencimiento;
	private int cajasInformadas;
	private int cajasAjustadas;
	private int cajasValidadas;
	private int flagDisponible;
	private int flagAjuste;



	public productoEnvasado(
			String fechaIngreso,String usuarioIngreso,String fechaEgreso,String usuarioEgreso,
			int idProducto,String planta,String producto,int lote,String vencimiento,
			int cajasInformadas,int cajasAjustadas, int cajasValidadas,
			int flagDisponible, int flagAjuste) {
	}


	public String getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getUsuarioIngreso() {
		return usuarioIngreso;
	}

	public void setUsuarioIngreso(String usuarioIngreso) {
		this.usuarioIngreso = usuarioIngreso;
	}

	public String getFechaEgreso() {
		return fechaEgreso;
	}

	public void setFechaEgreso(String fechaEgreso) {
		this.fechaEgreso = fechaEgreso;
	}

	public String getUsuarioEgreso() {
		return usuarioEgreso;
	}

	public void setUsuarioEgreso(String usuarioEgreso) {
		this.usuarioEgreso = usuarioEgreso;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public String getPlanta() {
		return planta;
	}

	public void setPlanta(String planta) {
		this.planta = planta;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public int getLote() {
		return lote;
	}

	public void setLote(int lote) {
		this.lote = lote;
	}

	public String getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(String vencimiento) {
		this.vencimiento = vencimiento;
	}

	public int getCajasInformadas() {
		return cajasInformadas;
	}

	public void setCajasInformadas(int cajasInformadas) {
		this.cajasInformadas = cajasInformadas;
	}

	public int getCajasAjustadas() {
		return cajasAjustadas;
	}

	public void setCajasAjustadas(int cajasAjustadas) {
		this.cajasAjustadas = cajasAjustadas;
	}

	public int getCajasValidadas() {
		return cajasValidadas;
	}

	public void setCajasValidadas(int cajasValidadas) {
		this.cajasValidadas = cajasValidadas;
	}

	public int getFlagDisponible() {
		return flagDisponible;
	}

	public void setFlagDisponible(int flagDisponible) {
		this.flagDisponible = flagDisponible;
	}

	public int getFlagAjuste() {
		return flagAjuste;
	}

	public void setFlagAjuste(int flagAjuste) {
		this.flagAjuste = flagAjuste;
	}


}
