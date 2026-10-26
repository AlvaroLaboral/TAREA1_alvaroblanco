package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fachada.ClinicaFachada;
import modelo.Alergia;
import modelo.Categoria;
import modelo.Cirujano;
import modelo.Empleado;
import modelo.Enfermero;
import modelo.Especialidad;
import modelo.Medicamento;
import modelo.Secretario;

public class Main {

	static Scanner sc = new Scanner(System.in);
	static ClinicaFachada fachada = new ClinicaFachada();

	public static void main(String[] args) {
		menuInvitado();
	}

	static void menuInvitado() {

		int opcion;

		do {
			System.out.println("\n--- MENÚ INVITADO ---");
			System.out.println("1. Login");
			System.out.println("0. Salir");
			opcion = leerOpcion();

			switch (opcion) {
			case 1 -> {
				login();
				if (fachada.hayUsuarioLogueado()) {
					menuSegunPerfil();
				}
			}
			case 0 -> System.out.println("Hasta pronto");
			default -> System.out.println("Opción incorrecta");
			}

		} while (opcion != 0);
	}

	static void menuSegunPerfil() {
		switch (fachada.getSesion().getPerfil()) {
		case SECRETARIADO -> menuSecretariado();
		case ENFERMERIA -> menuEnfermeria();
		case ADMIN -> menuAdmin();
		default -> {
		}
		}
	}

	static void menuSecretariado() {

		int opcion;

		do {
			System.out.println("\n--- MENÚ SECRETARIADO (" + fachada.getSesion().getNombre() + ") ---");
			System.out.println("1. Nuevo empleado");
			System.out.println("0. Logout");
			opcion = leerOpcion();

			switch (opcion) {
			case 1 -> nuevoEmpleado();
			case 0 -> logout();
			default -> System.out.println("Opción incorrecta");
			}

		} while (opcion != 0);
	}

	static void menuEnfermeria() {

		int opcion;

		do {
			System.out.println("\n--- MENÚ ENFERMERÍA (" + fachada.getSesion().getNombre() + ") ---");
			System.out.println("1. Ver alergias");
			System.out.println("2. Nueva alergia");
			System.out.println("3. Editar alergia");
			System.out.println("0. Logout");
			opcion = leerOpcion();

			switch (opcion) {
			case 1 -> verAlergias();
			case 2 -> nuevaAlergia();
			case 3 -> editarAlergia();
			case 0 -> logout();
			default -> System.out.println("Opción incorrecta");
			}

		} while (opcion != 0);
	}

	static void menuAdmin() {

		int opcion;

		do {
			System.out.println("\n--- MENÚ ADMINISTRADOR ---");
			System.out.println("1. Nuevo empleado");
			System.out.println("2. Ver alergias");
			System.out.println("3. Nueva alergia");
			System.out.println("4. Editar alergia");
			System.out.println("0. Logout");
			opcion = leerOpcion();

			switch (opcion) {
			case 1 -> nuevoEmpleado();
			case 2 -> verAlergias();
			case 3 -> nuevaAlergia();
			case 4 -> editarAlergia();
			case 0 -> logout();
			default -> System.out.println("Opción incorrecta");
			}

		} while (opcion != 0);
	}

	static void login() {

		String usuario = pedir("Introduce nombre de usuario:");
		String contrasenia = pedir("Introduce la contraseña:");

		String error = fachada.login(usuario, contrasenia);

		if (error == null) {
			System.out.println("Login correcto. Bienvenido/a " + fachada.getSesion().getNombre()
					+ ", tu rol es " + fachada.getSesion().getPerfil());
		} else {
			System.out.println(error);
		}
	}

	static void logout() {
		System.out.println("Sesión cerrada. Hasta luego, " + fachada.getSesion().getNombre());
		fachada.logout();
	}

	static void nuevoEmpleado() {

		System.out.println("\n--- NUEVO EMPLEADO ---");
		System.out.println("Tipo de empleado: 1. Secretario/a  2. Enfermero/a  3. Cirujano/a");
		int tipo = leerOpcion();
		if (tipo < 1 || tipo > 3) {
			System.out.println("Tipo no válido. Registro cancelado.");
			return;
		}

		String nombre = pedir("Nombre y apellidos:");
		if (error(fachada.validarTexto(nombre, "nombre y apellidos"))) return;

		String telefono = pedir("Teléfono:");
		if (error(fachada.validarTelefono(telefono))) return;

		String email = pedir("Email:");
		if (error(fachada.validarEmail(email))) return;

		String nif = pedir("NIF/NIE:").toUpperCase();
		if (error(fachada.validarNifNie(nif))) return;

		String direccion = pedir("Dirección:");
		if (error(fachada.validarTexto(direccion, "dirección"))) return;

		String usuario = pedir("Nombre de usuario:");
		if (error(fachada.validarUsuario(usuario))) return;

		String password = pedir("Contraseña:");
		if (error(fachada.validarPassword(password))) return;

		Empleado nuevo;

		switch (tipo) {
		case 1 -> {
			int anios;
			try {
				anios = Integer.parseInt(pedir("Años de experiencia en la clínica:"));
			} catch (NumberFormatException e) {
				anios = -1;
			}
			if (anios < 0) {
				error("Los años de experiencia deben ser un número entero mayor o igual que 0.");
				return;
			}
			nuevo = new Secretario(nombre, telefono, email, nif, direccion, usuario, password, anios);
		}
		case 2 -> {
			String cat = pedir("Categoría (A, B o C):").toUpperCase();
			if (!cat.matches("[ABC]")) {
				error("La categoría debe ser A, B o C.");
				return;
			}
			nuevo = new Enfermero(nombre, telefono, email, nif, direccion, usuario, password,
					Categoria.valueOf(cat));
		}
		default -> {
			List<Especialidad> especialidades = pedirEspecialidades();
			if (error(fachada.validarEspecialidades(especialidades))) return;
			nuevo = new Cirujano(nombre, telefono, email, nif, direccion, usuario, password, especialidades);
		}
		}

		System.out.println("Se va a dar de alta a " + nombre + " con usuario " + usuario + ".");
		if (!confirmar()) {
			System.out.println("Registro cancelado.");
			return;
		}

		String resultado = fachada.altaEmpleado(nuevo);
		System.out.println(resultado == null ? "Empleado dado de alta correctamente." : resultado);
	}

	static List<Especialidad> pedirEspecialidades() {

		for (Especialidad e : Especialidad.values()) {
			System.out.println(e.getId() + ". " + e.getNombre());
		}
		System.out.println("Introduce los números de sus especialidades separados por comas (ej: 1,4):");

		List<Especialidad> lista = new ArrayList<>();
		for (String trozo : sc.nextLine().split(",")) {
			try {
				int id = Integer.parseInt(trozo.trim());
				for (Especialidad e : Especialidad.values()) {
					if (e.getId() == id && !lista.contains(e)) {
						lista.add(e);
					}
				}
			} catch (NumberFormatException e) {
			}
		}
		return lista;
	}

	static void verAlergias() {

		List<Alergia> alergias = fachada.listarAlergias();

		System.out.println("\n--- ALERGIAS REGISTRADAS ---");
		if (alergias.isEmpty()) {
			System.out.println("No hay alergias registradas.");
			return;
		}

		for (Alergia a : alergias) {
			mostrarAlergia(a);
		}
	}

	static void nuevaAlergia() {

		System.out.println("\n--- NUEVA ALERGIA ---");

		String nombre = pedir("Nombre de la alergia:");
		if (errorAlergia(fachada.validarTexto(nombre, "nombre de la alergia"))) return;

		String nombreMed = pedir("Nombre del medicamento:");
		if (errorAlergia(fachada.validarTexto(nombreMed, "nombre del medicamento"))) return;

		String principio = pedir("Principio activo:");
		if (errorAlergia(fachada.validarTexto(principio, "principio activo"))) return;

		String dosisTexto = pedir("Dosis máxima diaria (mg):");
		if (errorAlergia(fachada.validarDosis(dosisTexto))) return;

		Long id = fachada.siguienteIdAlergia();
		Medicamento m = new Medicamento(id, nombreMed, principio, Integer.parseInt(dosisTexto));
		Alergia a = new Alergia(id, nombre, m);

		System.out.println("Se va a crear la siguiente alergia:");
		mostrarAlergia(a);
		if (!confirmar()) {
			System.out.println("Operación cancelada.");
			return;
		}

		String resultado = fachada.guardarAlergia(a);
		System.out.println(resultado == null ? "Alergia creada correctamente con id " + id + "." : resultado);
	}

	static void editarAlergia() {

		List<Alergia> alergias = fachada.listarAlergias();
		if (alergias.isEmpty()) {
			System.out.println("No hay alergias registradas para editar.");
			return;
		}

		System.out.println("\n--- EDITAR ALERGIA ---");
		for (Alergia a : alergias) {
			System.out.println(a.getIdAlergia() + ". " + a.getNombre());
		}

		long id = leerOpcion();
		Alergia a = fachada.buscarAlergia(id);
		if (a == null) {
			System.out.println("No existe ninguna alergia con ese id.");
			return;
		}

		Medicamento m = a.getMedicamento();
		System.out.println("Deja el campo en blanco para mantener el valor actual.");

		String nombre = pedir("Nombre de la alergia [" + a.getNombre() + "]:");
		if (nombre.isEmpty()) nombre = a.getNombre();
		if (errorAlergia(fachada.validarTexto(nombre, "nombre de la alergia"))) return;

		String nombreMed = pedir("Nombre del medicamento [" + m.getNombre() + "]:");
		if (nombreMed.isEmpty()) nombreMed = m.getNombre();
		if (errorAlergia(fachada.validarTexto(nombreMed, "nombre del medicamento"))) return;

		String principio = pedir("Principio activo [" + m.getPrincipioActivo() + "]:");
		if (principio.isEmpty()) principio = m.getPrincipioActivo();
		if (errorAlergia(fachada.validarTexto(principio, "principio activo"))) return;

		String dosisTexto = pedir("Dosis máxima diaria en mg [" + m.getDosisMaxDiariaMg() + "]:");
		if (dosisTexto.isEmpty()) dosisTexto = String.valueOf(m.getDosisMaxDiariaMg());
		if (errorAlergia(fachada.validarDosis(dosisTexto))) return;

		a.setNombre(nombre);
		m.setNombre(nombreMed);
		m.setPrincipioActivo(principio);
		m.setDosisMaxDiariaMg(Integer.parseInt(dosisTexto));

		System.out.println("La alergia quedará así:");
		mostrarAlergia(a);
		if (!confirmar()) {
			System.out.println("Operación cancelada. No se ha modificado nada.");
			return;
		}

		String resultado = fachada.guardarAlergia(a);
		System.out.println(resultado == null ? "Alergia actualizada correctamente." : resultado);
	}

	static void mostrarAlergia(Alergia a) {
		Medicamento m = a.getMedicamento();
		System.out.println("ID " + a.getIdAlergia() + " | Alergia: " + a.getNombre()
				+ " | Medicamento: " + m.getNombre()
				+ " | Principio activo: " + m.getPrincipioActivo()
				+ " | Dosis máxima: " + m.getDosisMaxDiariaMg() + " mg/día");
	}

	static boolean confirmar() {
		System.out.println("¿Confirmas la operación? (S/N)");
		return sc.nextLine().trim().equalsIgnoreCase("S");
	}

	static boolean errorAlergia(String mensaje) {
		if (mensaje != null) {
			System.out.println("ERROR: " + mensaje + " Operación cancelada.");
			return true;
		}
		return false;
	}

	static int leerOpcion() {
		try {
			return Integer.parseInt(sc.nextLine().trim());
		} catch (NumberFormatException e) {
			System.out.println("Tienes que escribir un número");
			return -1;
		}
	}

	static String pedir(String mensaje) {
		System.out.println(mensaje);
		return sc.nextLine().trim();
	}

	static boolean error(String mensaje) {
		if (mensaje != null) {
			System.out.println("ERROR: " + mensaje + " Registro cancelado.");
			return true;
		}
		return false;
	}
}