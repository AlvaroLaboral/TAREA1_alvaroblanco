package modelo;

import java.util.ArrayList;
import java.util.List;

public class Cirujano extends Empleado {

	private List<Especialidad> especialidades = new ArrayList<>();

	public Cirujano(String nombreYapellidos, String telefono, String email, String nifNie, String direccion,
			String usuario, String pasword, List<Especialidad> especialidades) {
		super(nombreYapellidos, telefono, email, nifNie, direccion, usuario, pasword);
		this.especialidades = especialidades;
	}

	public List<Especialidad> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<Especialidad> especialidades) {
		this.especialidades = especialidades;
	}

}
