package ui;

import dao.FuncionarioDAO;
import dao.DataAccessException;
import model.Funcionario;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class FuncionarioForm extends JDialog {
    private JTextField txtTipoId, txtNumId, txtNombres, txtApellidos, txtEstadoCivil,
            txtSexo, txtDireccion, txtTelefono, txtFechaNacimiento;
    private FuncionarioDAO funcionarioDAO;
    private Funcionario funcionario;

    public FuncionarioForm(Frame owner, FuncionarioDAO dao, Funcionario funcionario) {
        super(owner, true);
        this.funcionarioDAO = dao;
        this.funcionario = funcionario;

        setTitle(funcionario == null ? "Nuevo Funcionario" : "Editar Funcionario");
        setSize(400, 400);
        setLocationRelativeTo(owner);
        setLayout(new GridLayout(10, 2, 5, 5));

        add(new JLabel("Tipo ID:")); txtTipoId = new JTextField(); add(txtTipoId);
        add(new JLabel("Número ID:")); txtNumId = new JTextField(); add(txtNumId);
        add(new JLabel("Nombres:")); txtNombres = new JTextField(); add(txtNombres);
        add(new JLabel("Apellidos:")); txtApellidos = new JTextField(); add(txtApellidos);
        add(new JLabel("Estado Civil:")); txtEstadoCivil = new JTextField(); add(txtEstadoCivil);
        add(new JLabel("Sexo (M/F):")); txtSexo = new JTextField(); add(txtSexo);
        add(new JLabel("Dirección:")); txtDireccion = new JTextField(); add(txtDireccion);
        add(new JLabel("Teléfono:")); txtTelefono = new JTextField(); add(txtTelefono);
        add(new JLabel("Fecha Nac (YYYY-MM-DD):")); txtFechaNacimiento = new JTextField(); add(txtFechaNacimiento);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        add(btnGuardar); add(btnCancelar);

        if (funcionario != null) {
            txtTipoId.setText(funcionario.getTipoIdentificacion());
            txtNumId.setText(funcionario.getNumeroIdentificacion());
            txtNombres.setText(funcionario.getNombres());
            txtApellidos.setText(funcionario.getApellidos());
            txtEstadoCivil.setText(funcionario.getEstadoCivil());
            txtSexo.setText(funcionario.getSexo());
            txtDireccion.setText(funcionario.getDireccion());
            txtTelefono.setText(funcionario.getTelefono());
            if (funcionario.getFechaNacimiento() != null)
                txtFechaNacimiento.setText(funcionario.getFechaNacimiento().toString());
        }

        btnGuardar.addActionListener(e -> guardar());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void guardar() {
        try {
            if (funcionario == null) funcionario = new Funcionario();

            funcionario.setTipoIdentificacion(txtTipoId.getText());
            funcionario.setNumeroIdentificacion(txtNumId.getText());
            funcionario.setNombres(txtNombres.getText());
            funcionario.setApellidos(txtApellidos.getText());
            funcionario.setEstadoCivil(txtEstadoCivil.getText());
            funcionario.setSexo(txtSexo.getText());
            funcionario.setDireccion(txtDireccion.getText());
            funcionario.setTelefono(txtTelefono.getText());
            if (!txtFechaNacimiento.getText().isEmpty())
                funcionario.setFechaNacimiento(LocalDate.parse(txtFechaNacimiento.getText()));

            if (funcionario.getId() == 0) {
                funcionarioDAO.crear(funcionario);
                JOptionPane.showMessageDialog(this, "Funcionario creado correctamente");
            } else {
                funcionarioDAO.actualizar(funcionario);
                JOptionPane.showMessageDialog(this, "Funcionario actualizado correctamente");
            }
            dispose();
        } catch (DataAccessException ex) {
            JOptionPane.showMessageDialog(this, "Error en base de datos: " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Datos inválidos: " + ex.getMessage());
        }
    }
}
