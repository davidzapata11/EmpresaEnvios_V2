package servicios;

import modelos.Envio;
import java.util.ArrayList;
import java.util.List;

public class EnvioServicio {
    private List<Envio> envios;

    public EnvioServicio() {
        this.envios = new ArrayList<>();
    }

    public boolean agregarEnvio(Envio envio) {
        if (buscarPorCodigo(envio.getCodigo()) != null) {
            return false;
        }
        envios.add(envio);
        return true;
    }

    public boolean eliminarEnvio(String codigo) {
        Envio envio = buscarPorCodigo(codigo);
        if (envio != null) {
            return envios.remove(envio);
        }
        return false;
    }

    public Envio buscarPorCodigo(String codigo) {
        for (Envio e : envios) {
            if (e.getCodigo().equalsIgnoreCase(codigo)) {
                return e;
            }
        }
        return null;
    }

    public List<Envio> obtenerTodos() {
        return new ArrayList<>(envios);
    }
}