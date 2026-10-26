package modelo;

public abstract class Empleado {

	private static long contador = 1L;
	protected Long idEmpleado;
	protected String nombreYapellidos;
	protected String telefono;
	protected String email;
	protected String nifNie;
	protected String direccion;
	protected String usuario;
	protected String password;

	public Empleado(String nombreYapellidos, String telefono, String email, String nifNie, String direccion,
			String usuario, String pasword) {
		this.idEmpleado = contador++;
		this.nombreYapellidos = nombreYapellidos;
		this.telefono = telefono;
		this.email = email;
		this.nifNie = nifNie;
		this.direccion = direccion;
		this.usuario = usuario;
		this.password = pasword;

	}

	public static long getContador() {
		return contador;
	}

	public static void setContador(long contador) {
		Empleado.contador = contador;
	}

	public Long getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public String getNombreYapellidos() {
		return nombreYapellidos;
	}

	public void setNombreYapellidos(String nombreYapellidos) {
		this.nombreYapellidos = nombreYapellidos;
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

	public String getNifNie() {
		return nifNie;
	}

	public void setNifNie(String nifNie) {
		this.nifNie = nifNie;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
