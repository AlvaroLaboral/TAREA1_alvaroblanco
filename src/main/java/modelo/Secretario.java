package modelo;

public class Secretario extends Empleado {

	private Integer aniosExperiencia;


	public Secretario(String nombreYapellidos, String telefono, String email, String nifNie, String direccion,
			String usuario, String pasword, Integer aniosExperiencia) {
		super(nombreYapellidos, telefono, email, nifNie, direccion, usuario, pasword);
		this.aniosExperiencia = aniosExperiencia;
	}

	public Integer getAniosExperiencia() {
		return aniosExperiencia;
	}

	public void setAniosExperiencia(Integer aniosExperiencia) {
		this.aniosExperiencia = aniosExperiencia;
	}
	
	
	
}
