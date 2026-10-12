
package controladores;
import fabricas.*;
import modelos.*;
import servicios.EnvioServicio;
import vistas.EnvioVista;

import javax.swing.table.DefaultTableModel;
import java.util.EnumMap;
import java.util.Map;

public class EnvioControlador {
    private EnvioVista vista;
    private EnvioServicio servicio;
    private Map<TipoEnvio, EnvioFactory> fabricas;

    public EnvioControlador(EnvioVista vista, EnvioServicio servicio) {
        this.vista = vista;
        this.servicio = servicio;
        this.fabricas = new EnumMap<>(TipoEnvio.class);

        // Mapeo de fábricas respetando OCP (Open/Closed Principle)
        fabricas.put(TipoEnvio.TERRESTRE, new TerrestreFactory());
        fabricas.put(TipoEnvio.AEREO, new AereoFactory());
        fabricas.put(TipoEnvio.MARITIMO, new MaritimoFactory());

        inicializarEventos();
    }

    private void inicializarEventos() {
        vista.getBtnGuardar().addActionListener(e -> guardarEnvio());
        vista.getBtnCancelar().addActionListener(e -> vista.limpiarFormulario());
        vista.getBtnAgregar().addActionListener(e -> vista.limpiarFormulario());
        vista.getBtnRetirar().addActionListener(e -> retirarEnvio());
    }

    private void guardarEnvio() {
        try {
            String codigo = vista.getCodigo();
            String cliente = vista.getCliente();
            String pesoStr = vista.getPeso();
            String distanciaStr = vista.getDistancia();

            if (codigo.isEmpty() || cliente.isEmpty() || pesoStr.isEmpty() || distanciaStr.isEmpty()) {
                vista.mostrarError("Todos los campos son obligatorios.");
                return;
            }

            double peso = Double.parseDouble(pesoStr);
            double distancia = Double.parseDouble(distanciaStr);

            if (peso <= 0 || distancia <= 0) {
                vista.mostrarError("El peso y la distancia deben ser valores mayores a cero.");
                return;
            }

            TipoEnvio tipo = vista.getTipoEnvio();
            EnvioFactory factory = fabricas.get(tipo);

            if (factory == null) {
                vista.mostrarError("No existe una fábrica configurada para este tipo de envío.");
                return;
            }

            DatosEnvio datos = new DatosEnvio(codigo, cliente, peso, distancia);
            Envio nuevoEnvio = factory.crearEnvio(datos);

            boolean exito = servicio.agregarEnvio(nuevoEnvio);
            if (exito) {
                vista.mostrarMensaje("Envío agregado exitosamente.");
                actualizarTabla();
                vista.limpiarFormulario();
            } else {
                vista.mostrarError("Ya existe un envío registrado con el código: " + codigo);
            }

        } catch (NumberFormatException ex) {
            vista.mostrarError("Por favor ingrese números válidos para Peso y Distancia.");
        }
    }

    private void retirarEnvio() {
        int filaSeleccionada = vista.getTablaEnvios().getSelectedRow();
        String codigo = null;

        if (filaSeleccionada >= 0) {
            codigo = vista.getModeloTabla().getValueAt(filaSeleccionada, 1).toString();
        } else {
            codigo = javax.swing.JOptionPane.showInputDialog(
                    vista,
                    "Ingrese el código del envío a retirar:",
                    "Retirar Envío",
                    javax.swing.JOptionPane.QUESTION_MESSAGE
            );
        }

        if (codigo != null && !codigo.trim().isEmpty()) {
            boolean eliminado = servicio.eliminarEnvio(codigo.trim());
            if (eliminado) {
                vista.mostrarMensaje("Envío con código " + codigo + " eliminado correctamente.");
                actualizarTabla();
            } else {
                vista.mostrarError("No se encontró ningún envío con el código " + codigo + ".");
            }
        }
    }

    private void actualizarTabla() {
        DefaultTableModel modelo = vista.getModeloTabla();
        modelo.setRowCount(0);

        for (Envio e : servicio.obtenerTodos()) {
            Object[] fila = new Object[]{
                    e.getTipo(),
                    e.getCodigo(),
                    e.getCliente(),
                    e.getPeso(),
                    e.getDistancia(),
                    e.calcularTarifa()
            };
            modelo.addRow(fila);
        }
    }
}