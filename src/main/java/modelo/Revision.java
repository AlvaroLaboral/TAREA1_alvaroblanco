package modelo;


import java.time.LocalDateTime;

public class Revision extends Cita{

	public Revision(LocalDateTime fechaHora, String anotacion, Tratamiento tratamiento, Enfermero enfermero) {
		super(fechaHora, anotacion, tratamiento, enfermero);
	}
	


}
