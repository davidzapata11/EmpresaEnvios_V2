import controladores.EnvioControlador;
import servicios.EnvioServicio;
import vistas.EnvioVista;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EnvioVista vista = new EnvioVista();
            EnvioServicio servicio = new EnvioServicio();
            new EnvioControlador(vista, servicio);
            vista.setVisible(true);
        });
    }
}