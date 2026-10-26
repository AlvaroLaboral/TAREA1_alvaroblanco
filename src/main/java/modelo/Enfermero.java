package modelo;

public class Enfermero extends Empleado {

	private Categoria categoria;



	public Enfermero(String nombreYapellidos, String telefono, String email, String nifNie, String direccion,
			String usuario, String pasword, Categoria categoria) {
		super(nombreYapellidos, telefono, email, nifNie, direccion, usuario, pasword);
		this.categoria = categoria;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}
