package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Intervencion extends Cita {

	private Integer duracionMin;
	private List<Cirujano> cirujanos = new ArrayList<>();

	public Intervencion(LocalDateTime fechaHora, String anotacion, Tratamiento tratamiento, Enfermero enfermero,
			Integer duracionMin, List<Cirujano> cirujanos) {
		super(fechaHora, anotacion, tratamiento, enfermero);
		this.duracionMin = duracionMin;
		this.cirujanos = cirujanos;
	}

	public Integer getDuracionMin() {
		return duracionMin;
	}

	public void setDuracionMin(Integer duracionMin) {
		this.duracionMin = duracionMin;
	}

	public List<Cirujano> getCirujanos() {
		return cirujanos;
	}

	public void setCirujanos(List<Cirujano> cirujanos) {
		this.cirujanos = cirujanos;
	}

}