package modelo;

import java.time.LocalDateTime;

public abstract class Cita {
	
	
	private static Long contador = 1L;
	protected Long idCita;
	protected LocalDateTime fechaHora;
	protected String anotacion;
	protected Tratamiento tratamiento;
	protected Enfermero enfermero;
	
	
	public Cita(LocalDateTime fechaHora, String anotacion, Tratamiento tratamiento, Enfermero enfermero) {
		this.idCita = contador++;
		this.fechaHora = fechaHora;
		this.anotacion = anotacion;
		this.tratamiento = tratamiento;
		this.enfermero = enfermero;
	}


	public static Long getContador() {
		return contador;
	}


	public static void setContador(Long contador) {
		Cita.contador = contador;
	}


	public Long getIdCita() {
		return idCita;
	}


	public void setIdCita(Long idCita) {
		this.idCita = idCita;
	}


	public LocalDateTime getFechaHora() {
		return fechaHora;
	}


	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}


	public String getAnotacion() {
		return anotacion;
	}


	public void setAnotacion(String anotacion) {
		this.anotacion = anotacion;
	}


	public Tratamiento getTratamiento() {
		return tratamiento;
	}


	public void setTratamiento(Tratamiento tratamiento) {
		this.tratamiento = tratamiento;
	}


	public Enfermero getEnfermero() {
		return enfermero;
	}


	public void setEnfermero(Enfermero enfermero) {
		this.enfermero = enfermero;
	}
	
	
	
	

	
	

}
