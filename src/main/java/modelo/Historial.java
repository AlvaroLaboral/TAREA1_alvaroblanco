package modelo;

public class Historial {
	
	private static Long contador=1L;
	private Long numHistoria;
	private String descripcion;
	
	


	public Historial(String descripcion) {
		this.numHistoria = contador++;
		this.descripcion = descripcion;
	}


	public Long getNumHistoria() {
		return numHistoria;
	}


	public void setNumHistoria(Long numHistoria) {
		this.numHistoria = numHistoria;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
	
	
	
}
