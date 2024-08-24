package modelos;

public class FleteExpreso {
	private int fleteID;
    private String fleteCondicion;
    private String fleteExpreso;
    private String fleteDireccion;
    private String fleteLocalidad;
    private String fleteCuit;
    private String fleteTelefono;
    
    
    public FleteExpreso() {
    	
    }
    
    public FleteExpreso(String fleteCondicion) {
    	this.fleteCondicion = fleteCondicion;
    }
    
	public FleteExpreso(String fleteCondicion, String fleteExpreso, String fleteDireccion) {
		super();
		this.fleteCondicion = fleteCondicion;
		this.fleteExpreso = fleteExpreso;
		this.fleteDireccion = fleteDireccion;
	}
	
	public FleteExpreso(String fleteCondicion, String fleteExpreso, String fleteDireccion, 
			int id, String cuit, String telefono) {
		super();
		this.fleteID = id;
		this.fleteCondicion = fleteCondicion;
		this.fleteExpreso = fleteExpreso;
		this.fleteDireccion = fleteDireccion;
		this.fleteCuit = cuit;
		this.fleteTelefono = telefono;
	}

	public String getFleteCondicion() {
		return fleteCondicion;
	}

	public void setFleteCondicion(String fleteCondicion) {
		this.fleteCondicion = fleteCondicion;
	}

	public String getFleteExpreso() {
		return fleteExpreso;
	}

	public void setFleteExpreso(String fleteExpreso) {
		this.fleteExpreso = fleteExpreso;
	}

	public String getFleteDireccion() {
		return fleteDireccion;
	}

	public void setFleteDireccion(String fleteDireccion) {
		this.fleteDireccion = fleteDireccion;
	}

	public int getFleteID() {
		return fleteID;
	}

	public void setFleteID(int fleteID) {
		this.fleteID = fleteID;
	}

	public String getFleteLocalidad() {
		return fleteLocalidad;
	}

	public void setFleteLocalidad(String fleteLocalidad) {
		this.fleteLocalidad = fleteLocalidad;
	}

	public String getFleteCuit() {
		return fleteCuit;
	}

	public void setFleteCuit(String fleteCuit) {
		this.fleteCuit = fleteCuit;
	}

	public String getFleteTelefono() {
		return fleteTelefono;
	}

	public void setFleteTelefono(String fleteTelefono) {
		this.fleteTelefono = fleteTelefono;
	}
	
	
}
