package modelo;

public enum Especialidad {
	
	
	ODONTOLOGIA(1, "Odontología"),
    FISIOTERAPIA(2, "Fisioterapia"),
    GINECOLOGIA(3, "Ginecología"),
    CIRUGIA_PLASTICA(4, "Cirugía plástica"),
    DIGESTIVO(5, "Digestivo"),
    CIRUGIA_PEDIATRICA(6, "Cirugía pediátrica"),
    RADIOLOGIA(7, "Radiología"),
    GERIATRIA(8, "Geriatría");

    private int id;
    private String nombre;

    Especialidad(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

}
