package objetos;

public class Departamento {
	
	private int id;
	private String nombre;
	private String descripcion;
	private int id_padre;
	
	
	public Departamento(int id, String nombre, String descripcion, int id_padre) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.id_padre = id_padre;
	}
	
	public Departamento() {
		super();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getId_padre() {
		return id_padre;
	}
	public void setId_padre(int id_padre) {
		this.id_padre = id_padre;
	}
	
	

}
