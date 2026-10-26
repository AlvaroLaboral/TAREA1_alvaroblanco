package fachada;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import modelo.Alergia;
import modelo.Cirujano;
import modelo.Empleado;
import modelo.Enfermero;
import modelo.Especialidad;
import modelo.Perfil;
import modelo.Secretario;
import modelo.Sesion;

public class ClinicaFachada {

	private static Path CREDENCIALES = Path.of("Recursos/credenciales.txt");
	private static Path PROPIEDADES = Path.of("Recursos/Test/application.properties");
	private static Path CARPETA_ALERGIAS = Path.of("Recursos/alergias");

	private Sesion sesion = new Sesion(null, "Invitado", Perfil.INVITADO);

	public Sesion getSesion() {
		return sesion;
	}

	public boolean hayUsuarioLogueado() {
		return sesion.getPerfil() != Perfil.INVITADO;
	}

	public boolean puedeGestionarEmpleados() {
		return sesion.getPerfil() == Perfil.SECRETARIADO || sesion.getPerfil() == Perfil.ADMIN;
	}

	public boolean puedeGestionarAlergias() {
		return sesion.getPerfil() == Perfil.ENFERMERIA || sesion.getPerfil() == Perfil.ADMIN;
	}

	public String login(String usuario, String contrasenia) {
		if (hayUsuarioLogueado()) {
			return "Ya hay una sesión iniciada. Haz logout primero.";
		}
		if (usuario == null || usuario.isBlank() || contrasenia == null || contrasenia.isBlank()) {
			return "El usuario y la contraseña no pueden estar vacíos.";
		}
		if (esAdmin(usuario, contrasenia)) {
			sesion = new Sesion(usuario, "Administrador", Perfil.ADMIN);
			return null;
		}
		Sesion encontrada = buscarEnCredenciales(usuario, contrasenia);
		if (encontrada == null) {
			return "Usuario o contraseña incorrectos.";
		}
		sesion = encontrada;
		return null;
	}

	public void logout() {
		sesion = new Sesion(null, "Invitado", Perfil.INVITADO);
	}

	public String validarTexto(String valor, String campo) {
		if (valor == null || valor.isBlank()) {
			return "El campo " + campo + " no puede estar vacío.";
		}
		if (valor.contains("|")) {
			return "El campo " + campo + " no puede contener el carácter |";
		}
		return null;
	}

	public String validarTelefono(String telefono) {
		if (telefono == null || !telefono.matches("[6789]\\d{8}")) {
			return "El teléfono debe tener 9 dígitos y empezar por 6, 7, 8 o 9.";
		}
		return null;
	}

	public String validarEmail(String email) {
		if (email == null || !email.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)*\\.[a-zA-Z]{2,}$")) {
			return "El email no tiene un formato válido.";
		}
		return null;
	}

	public String validarNifNie(String doc) {
		if (doc == null) {
			return "El NIF/NIE no puede estar vacío.";
		}
		String d = doc.trim().toUpperCase();
		if (!d.matches("[0-9XYZ]\\d{7}[A-Z]")) {
			return "El NIF/NIE debe tener 8 caracteres y una letra (ej: 12345678Z o X1234567L).";
		}
		String numero = d.substring(0, 8).replace('X', '0').replace('Y', '1').replace('Z', '2');
		char letraCorrecta = "TRWAGMYFPDXBNJZSQVHLCKE".charAt(Integer.parseInt(numero) % 23);
		if (d.charAt(8) != letraCorrecta) {
			return "La letra del NIF/NIE no es correcta.";
		}
		return null;
	}

	public String validarUsuario(String usuario) {
		if (usuario == null || usuario.isBlank()) {
			return "El nombre de usuario no puede estar vacío.";
		}
		if (usuario.contains(" ") || usuario.contains("|")) {
			return "El nombre de usuario no puede contener espacios ni el carácter |";
		}
		if (usuarioExiste(usuario)) {
			return "Ya existe un usuario con ese nombre.";
		}
		return null;
	}

	public String validarPassword(String password) {
		if (password == null || password.isBlank()) {
			return "La contraseña no puede estar vacía.";
		}
		if (password.contains(" ") || password.contains("|")) {
			return "La contraseña no puede contener espacios ni el carácter |";
		}
		return null;
	}

	public String validarEspecialidades(List<Especialidad> especialidades) {
		if (especialidades == null || especialidades.isEmpty()) {
			return "Un cirujano debe tener al menos una especialidad.";
		}
		return null;
	}

	public String altaEmpleado(Empleado e) {
		if (!puedeGestionarEmpleados()) {
			return "No tienes permisos para dar de alta empleados.";
		}
		if (usuarioExiste(e.getUsuario())) {
			return "Ya existe un usuario con ese nombre.";
		}

		String linea = e.getUsuario() + "|" + e.getPassword() + "|" + e.getNombreYapellidos() + "|"
				+ perfilDe(e);

		try {
			String separador = "";
			if (Files.exists(CREDENCIALES) && Files.size(CREDENCIALES) > 0) {
				String contenido = Files.readString(CREDENCIALES);
				if (!contenido.endsWith("\n")) {
					separador = System.lineSeparator();
				}
			}
			Files.writeString(CREDENCIALES, separador + linea + System.lineSeparator(),
					StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (IOException ex) {
			return "No se pudo guardar en credenciales.txt: " + ex.getMessage();
		}
		return null;
	}

	public String validarDosis(String dosis) {
		try {
			int valor = Integer.parseInt(dosis.trim());
			if (valor <= 0) {
				return "La dosis máxima debe ser mayor que 0.";
			}
			return null;
		} catch (NumberFormatException | NullPointerException e) {
			return "La dosis máxima debe ser un número entero de miligramos.";
		}
	}

	public List<Alergia> listarAlergias() {
		List<Alergia> alergias = new ArrayList<>();
		if (!Files.isDirectory(CARPETA_ALERGIAS)) {
			return alergias;
		}
		try (DirectoryStream<Path> ficheros = Files.newDirectoryStream(CARPETA_ALERGIAS, "alergia_*.dat")) {
			for (Path fichero : ficheros) {
				Alergia a = leerAlergia(fichero);
				if (a != null) {
					alergias.add(a);
				}
			}
		} catch (IOException e) {
			return alergias;
		}
		alergias.sort(Comparator.comparing(Alergia::getIdAlergia));
		return alergias;
	}

	public Alergia buscarAlergia(Long id) {
		Path fichero = rutaAlergia(id);
		if (!Files.exists(fichero)) {
			return null;
		}
		return leerAlergia(fichero);
	}

	public Long siguienteIdAlergia() {
		long maximo = 0;
		for (Alergia a : listarAlergias()) {
			if (a.getIdAlergia() > maximo) {
				maximo = a.getIdAlergia();
			}
		}
		return maximo + 1;
	}

	public String guardarAlergia(Alergia a) {
		if (!puedeGestionarAlergias()) {
			return "No tienes permisos para gestionar alergias.";
		}
		try {
			Files.createDirectories(CARPETA_ALERGIAS);
			try (ObjectOutputStream oos = new ObjectOutputStream(
					Files.newOutputStream(rutaAlergia(a.getIdAlergia())))) {
				oos.writeObject(a);
			}
		} catch (IOException e) {
			return "No se pudo guardar la alergia: " + e.getMessage();
		}
		return null;
	}

	private Alergia leerAlergia(Path fichero) {
		try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(fichero))) {
			return (Alergia) ois.readObject();
		} catch (IOException | ClassNotFoundException | ClassCastException e) {
			System.out.println("Aviso: no se pudo leer el fichero " + fichero.getFileName());
			return null;
		}
	}

	private Path rutaAlergia(Long id) {
		return CARPETA_ALERGIAS.resolve("alergia_" + id + ".dat");
	}

	private String perfilDe(Empleado e) {
		if (e instanceof Secretario) {
			return "secretariado";
		} else if (e instanceof Enfermero) {
			return "enfermeria";
		} else if (e instanceof Cirujano) {
			return "cirugia";
		}
		return "invitado";
	}

	private boolean usuarioExiste(String usuario) {
		if (usuario.equalsIgnoreCase(leerPropiedad("admin.usuario"))) {
			return true;
		}
		if (!Files.exists(CREDENCIALES)) {
			return false;
		}
		try (BufferedReader br = Files.newBufferedReader(CREDENCIALES)) {
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] p = linea.split("\\|");
				if (p.length > 0 && p[0].equalsIgnoreCase(usuario)) {
					return true;
				}
			}
		} catch (IOException e) {
			return false;
		}
		return false;
	}

	private String leerPropiedad(String clave) {
		Properties props = new Properties();
		try (BufferedReader br = Files.newBufferedReader(PROPIEDADES)) {
			props.load(br);
		} catch (IOException e) {
			return null;
		}
		return props.getProperty(clave);
	}

	private boolean esAdmin(String usuario, String contrasenia) {
		return usuario.equals(leerPropiedad("admin.usuario"))
				&& contrasenia.equals(leerPropiedad("admin.password"));
	}

	private Sesion buscarEnCredenciales(String usuario, String contrasenia) {
		try (BufferedReader br = Files.newBufferedReader(CREDENCIALES)) {
			String linea;
			while ((linea = br.readLine()) != null) {
				if (linea.isBlank()) {
					continue;
				}
				String[] p = linea.split("\\|");
				if (p.length < 4) {
					continue;
				}
				if (p[0].equals(usuario) && p[1].equals(contrasenia)) {
					try {
						return new Sesion(p[0], p[2], Perfil.valueOf(p[3].trim().toUpperCase()));
					} catch (IllegalArgumentException e) {
						return null;
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Error al leer credenciales: " + e.getMessage());
		}
		return null;
	}
}