package modelo;

import java.io.Serializable;

public class Alergia implements Serializable {

	private Long idAlergia;
	private String nombre;
	private Medicamento medicamento;

	public Alergia(Long idAlergia, String nombre, Medicamento medicamento) {
		this.idAlergia = idAlergia;
		this.nombre = nombre;
		this.medicamento = medicamento;
	}

	public Long getIdAlergia() {
		return idAlergia;
	}

	public void setIdAlergia(Long idAlergia) {
		this.idAlergia = idAlergia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Medicamento getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}

}
