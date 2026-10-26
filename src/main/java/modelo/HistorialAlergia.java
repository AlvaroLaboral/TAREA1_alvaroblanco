package modelo;

import java.io.Serializable;

public class HistorialAlergia implements Serializable {
	
	
	
private static Long contador=1L;
private Long idAlegria;
private String nombre;

public HistorialAlergia(String nombre) {
	this.idAlegria = contador++;
	this.nombre = nombre;
}

public static Long getContador() {
	return contador;
}

public static void setContador(Long contador) {
	HistorialAlergia.contador = contador;
}

public Long getIdAlegria() {
	return idAlegria;
}

public void setIdAlegria(Long idAlegria) {
	this.idAlegria = idAlegria;
}

public String getNombre() {
	return nombre;
}

public void setNombre(String nombre) {
	this.nombre = nombre;
}




}
