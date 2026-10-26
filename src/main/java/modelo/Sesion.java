package modelo;

public class Sesion {
	  private String usuario;
	    private String nombre;
	    private Perfil perfil;
	    
	    
		public Sesion(String usuario, String nombre, Perfil perfil) {
			this.usuario = usuario;
			this.nombre = nombre;
			this.perfil = perfil;
			
		}
		public String getUsuario() {
			return usuario;
		}
		public void setUsuario(String usuario) {
			this.usuario = usuario;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public Perfil getPerfil() {
			return perfil;
		}
		public void setPerfil(Perfil perfil) {
			this.perfil = perfil;
		}


	    
}
