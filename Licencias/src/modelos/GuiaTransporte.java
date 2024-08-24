package modelos;

import java.math.BigDecimal;

public class GuiaTransporte {

	private int idGuia;
	private int idHojaRuta;
	private int nroDeRemito;
	private int idCliente;
	private String razonSocial;
	private String direccion;
	private String localidad;
	private String provincia;	
	private String cuit;	
	private String iva;	
	private String telefono;
	private BigDecimal netoNoGrabado;
	private int cantidad;
	private BigDecimal totalNeto;
	private int totalCantidad;
    private Pedido pedido;
    private FleteExpreso expreso;
    private boolean isEmtpy;
	private int nroDeRemitoBuffer;
	private BigDecimal totalNetoBuffer;
	private int totalCantidadBuffer;
    
    public GuiaTransporte() {
    	this.isEmtpy = true;
    }
    
    public GuiaTransporte(int idGuia, int idHojaRuta) {
		this.idGuia = idGuia;
		this.idHojaRuta = idHojaRuta;
    }
    

	public GuiaTransporte(int idGuia, int idHojaRuta, int nroDeRemito, int idCliente, String direccion,
			String localidad, String provincia, String cuit, String iva, String telefono, BigDecimal netoNoGrabado,
			int cantidad, BigDecimal totalNeto, int totalCantidad, Pedido pedido, FleteExpreso expreso) {
		super();
		this.idGuia = idGuia;
		this.idHojaRuta = idHojaRuta;
		this.nroDeRemito = nroDeRemito;
		this.idCliente = idCliente;
		this.direccion = direccion;
		this.localidad = localidad;
		this.provincia = provincia;
		this.cuit = cuit;
		this.iva = iva;
		this.telefono = telefono;
		this.netoNoGrabado = netoNoGrabado;
		this.cantidad = cantidad;
		this.totalNeto = totalNeto;
		this.totalCantidad = totalCantidad;
		this.pedido = pedido;
		this.expreso = expreso;
	}

	public int getIdGuia() {
		return idGuia;
	}

	public void setIdGuia(int idGuia) {
		this.idGuia = idGuia;
	}

	public int getIdHojaRuta() {
		return idHojaRuta;
	}
	
	public void setIdHojaRuta(int idHojaRuta) {
		this.idHojaRuta = idHojaRuta;
	}

	public int getNroDeRemito() {
		return nroDeRemito;
	}

	public void setNroDeRemito(int nroDeRemito) {
		this.nroDeRemito = nroDeRemito;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getIva() {
		return iva;
	}

	public void setIva(String iva) {
		this.iva = iva;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public BigDecimal getTotalNeto() {
		return totalNeto;
	}

	public void setTotalNeto(BigDecimal totalNeto) {
		this.totalNeto = totalNeto;
	}

	public int getTotalCantidad() {
		return totalCantidad;
	}

	public void setTotalCantidad(int totalCantidad) {
		this.totalCantidad = totalCantidad;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public FleteExpreso getExpreso() {
		return expreso;
	}

	public void setExpreso(FleteExpreso expreso) {
		this.expreso = expreso;
	}
	
	public BigDecimal getNetoNoGrabado() {
		return netoNoGrabado;
	}

	public void setNetoNoGrabado(BigDecimal netoNoGrabado) {
		this.netoNoGrabado = netoNoGrabado;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	
	public boolean isEmtpy() {
		return isEmtpy;
	}

	public void setEmtpy(boolean isEmtpy) {
		this.isEmtpy = isEmtpy;
	}
	

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	
	public BigDecimal getTotalNetoBuffer() {
		return totalNetoBuffer;
	}

	public void setTotalNetoBuffer(BigDecimal totalNetoBuffer) {
		this.totalNetoBuffer = totalNetoBuffer;
	}

	public int getTotalCantidadBuffer() {
		return totalCantidadBuffer;
	}

	public void setTotalCantidadBuffer(int totalCantidadBuffer) {
		this.totalCantidadBuffer = totalCantidadBuffer;
	}

	public int getNroDeRemitoBuffer() {
		return nroDeRemitoBuffer;
	}

	public void setNroDeRemitoBuffer(int nroDeRemitoBuffer) {
		this.nroDeRemitoBuffer = nroDeRemitoBuffer;
	}

	public static Object[][] ConvertArraylistToMatrix(HojaDeRuta roadmapParam){
		Object[][] data = new Object[roadmapParam.getPedidosList().size()][4];
		if(roadmapParam != null) {
			int row = 0;
			for(Pedido p : roadmapParam.getPedidosList()) {
				data[row][0] = p.getRazonSocial();
				data[row][1] = false;
				data[row][2] = false;
				row++;
			}
		}
		return data;
	}

	
	public static void setIdGuiaByIdcliente(){
		
	}
	
	
}
