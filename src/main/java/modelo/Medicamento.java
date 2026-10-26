package modelo;

import java.io.Serializable;

public class Medicamento implements Serializable {

	private Long idMedicamento;
	private String nombre;
	private String principioActivo;
	private Integer dosisMaxDiariaMg;
	
	public Medicamento(Long idMedicamento, String nombre, String principioActivo, Integer dosisMaxDiariaMg) {
	    this.idMedicamento = idMedicamento;
	    this.nombre = nombre;
	    this.principioActivo = principioActivo;
	    this.dosisMaxDiariaMg = dosisMaxDiariaMg;
	}

	public Long getIdMedicamento() {
		return idMedicamento;
	}

	public void setIdMedicamento(Long idMedicamento) {
		this.idMedicamento = idMedicamento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrincipioActivo() {
		return principioActivo;
	}

	public void setPrincipioActivo(String principioActivo) {
		this.principioActivo = principioActivo;
	}

	public Integer getDosisMaxDiariaMg() {
		return dosisMaxDiariaMg;
	}

	public void setDosisMaxDiariaMg(Integer dosisMaxDiariaMg) {
		this.dosisMaxDiariaMg = dosisMaxDiariaMg;
	}

	

	
	
	
	
	
	
}
