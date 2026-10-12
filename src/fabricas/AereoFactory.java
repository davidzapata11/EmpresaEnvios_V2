package fabricas;

import modelos.DatosEnvio;
import modelos.Envio;
import modelos.Aereo;

public class AereoFactory extends EnvioFactory {
    @Override
    public Envio crearEnvio(DatosEnvio datos) {
        return new Aereo(datos.getCodigo(), datos.getCliente(), datos.getPeso(), datos.getDistancia());
    }
}