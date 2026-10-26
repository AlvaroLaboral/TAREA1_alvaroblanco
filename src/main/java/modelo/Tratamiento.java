package modelo;

import java.time.LocalDate;

public class Tratamiento {
	
	
	private static Long contador = 1L;
	private Long idTratamiento;
	private String nombre;
	private LocalDate fechaInicio;
	private boolean consentimiento;
	private Paciente paciente;
	
	public Tratamiento(Paciente paciente, String nombre, LocalDate fechaInicio, boolean consentimiento) {
		this.idTratamiento = contador++;
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.consentimiento = consentimiento;
		this.setPaciente(paciente);
	}
	public Long getIdTratamiento() {
		return idTratamiento;
	}
	public void setIdTratamiento(Long idTratamiento) {
		this.idTratamiento = idTratamiento;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public boolean isConsentimiento() {
		return consentimiento;
	}
	public void setConsentimiento(boolean consentimiento) {
		this.consentimiento = consentimiento;
	}
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	
	
	

}
