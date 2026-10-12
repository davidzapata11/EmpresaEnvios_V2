package modelos;

public enum TipoEnvio {
    TERRESTRE("Terrestre"),
    AEREO("Aéreo"),
    MARITIMO("Maritimo");

    private final String nombre;

    TipoEnvio(String nombre) {
        this.nombre = nombre;
    }

    // el nombre que se muestra en la lista desplegable y en la tabla
    @Override
    public String toString() {
        return nombre;
    }
}
