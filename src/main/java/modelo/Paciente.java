package modelo;

import java.time.LocalDate;

public class Paciente {
	
	private static Long contador = 1L;

	
	private Long idPaciente;
	private String nombre;
	private LocalDate fechaNacimiento;
	private String telefono;
	private String email;
	private String direccion;
	private String nifNie;
	private Historial historial;
	
	public Paciente(String nombre, LocalDate fechaNacimiento, String telefono, String email,
			String direccion, String nifNie, Historial historial) {
		this.idPaciente = contador++;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.telefono = telefono;
		this.email = email;
		this.direccion = direccion;
		this.nifNie = nifNie;
		this.historial = historial;
	}

	public static Long getContador() {
		return contador;
	}

	public static void setContador(Long contador) {
		Paciente.contador = contador;
	}

	public Long getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(Long idPaciente) {
		this.idPaciente = idPaciente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNifNie() {
		return nifNie;
	}

	public void setNifNie(String nifNie) {
		this.nifNie = nifNie;
	}

	public Historial getHistorial() {
		return historial;
	}

	public void setHistorial(Historial historial) {
		this.historial = historial;
	}
	
	
	
	
	

}
