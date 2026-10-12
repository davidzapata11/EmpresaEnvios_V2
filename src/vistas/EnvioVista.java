package vistas;

import modelos.TipoEnvio;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EnvioVista extends JFrame {
    private JTextField txtCodigo;
    private JTextField txtCliente;
    private JTextField txtPeso;
    private JTextField txtDistancia;
    private JComboBox<TipoEnvio> cbTipoEnvio;
    private JButton btnAgregar;
    private JButton btnRetirar;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JTable tablaEnvios;
    private DefaultTableModel modeloTabla;

    public EnvioVista() {
        setTitle("Operador Logístico");
        setSize(720, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Barra de herramientas superior con los iconos + y -
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        btnAgregar = new JButton();
        btnRetirar = new JButton();

        try {
            btnAgregar.setIcon(new ImageIcon(getClass().getResource("/iconos/+.png")));
            btnRetirar.setIcon(new ImageIcon(getClass().getResource("/iconos/-.png")));
        } catch (Exception e) {
            btnAgregar.setText(" + ");
            btnRetirar.setText(" - ");
        }

        btnAgregar.setToolTipText("Agregar Envío");
        btnRetirar.setToolTipText("Retirar Envío");

        toolBar.add(btnAgregar);
        toolBar.add(btnRetirar);
        add(toolBar, BorderLayout.NORTH);

        // Panel de Formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fila 0: Número | Tipo
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Número"), gbc);
        gbc.gridx = 1;
        txtCodigo = new JTextField(12);
        panelFormulario.add(txtCodigo, gbc);

        gbc.gridx = 2;
        panelFormulario.add(new JLabel("Tipo"), gbc);
        gbc.gridx = 3;
        cbTipoEnvio = new JComboBox<>(TipoEnvio.values());
        panelFormulario.add(cbTipoEnvio, gbc);

        // Fila 1: Cliente | Distancia en Km
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Cliente"), gbc);
        gbc.gridx = 1;
        txtCliente = new JTextField(12);
        panelFormulario.add(txtCliente, gbc);

        gbc.gridx = 2;
        panelFormulario.add(new JLabel("Distancia en Km"), gbc);
        gbc.gridx = 3;
        txtDistancia = new JTextField(12);
        panelFormulario.add(txtDistancia, gbc);

        // Fila 2: Peso | Botones Guardar y Cancelar
        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulario.add(new JLabel("Peso"), gbc);
        gbc.gridx = 1;
        txtPeso = new JTextField(12);
        panelFormulario.add(txtPeso, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        gbc.gridx = 2; gbc.gridy = 2; gbc.gridwidth = 2;
        panelFormulario.add(panelBotones, gbc);

        // Tabla de Envios
        String[] columnas = {"Tipo", "Código", "Cliente", "Peso", "Distancia", "Costo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaEnvios = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaEnvios);

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.add(panelFormulario, BorderLayout.NORTH);
        panelCentro.add(scrollPane, BorderLayout.CENTER);

        add(panelCentro, BorderLayout.CENTER);
    }

    public String getCodigo() { return txtCodigo.getText().trim(); }
    public String getCliente() { return txtCliente.getText().trim(); }
    public String getPeso() { return txtPeso.getText().trim(); }
    public String getDistancia() { return txtDistancia.getText().trim(); }
    public TipoEnvio getTipoEnvio() { return (TipoEnvio) cbTipoEnvio.getSelectedItem(); }
    public JTable getTablaEnvios() { return tablaEnvios; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }

    public JButton getBtnAgregar() { return btnAgregar; }
    public JButton getBtnRetirar() { return btnRetirar; }
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnCancelar() { return btnCancelar; }

    public void limpiarFormulario() {
        txtCodigo.setText("");
        txtCliente.setText("");
        txtPeso.setText("");
        txtDistancia.setText("");
        cbTipoEnvio.setSelectedIndex(0);
        txtCodigo.requestFocus();
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}