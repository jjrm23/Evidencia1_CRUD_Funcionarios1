package ui;

import dao.FuncionarioDAO;
import dao.FuncionarioDAOImpl;
import dao.DataAccessException;
import model.Funcionario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainFrame extends JFrame {
    private FuncionarioDAO funcionarioDAO;
    private JTable table;
    private DefaultTableModel model;

    public MainFrame() {
        funcionarioDAO = new FuncionarioDAOImpl();

        setTitle("Gestión de Funcionarios");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(
                new Object[]{"ID", "Tipo ID", "Número ID", "Nombres", "Apellidos", "Estado Civil", "Sexo", "Dirección", "Teléfono", "Fecha Nac."}, 0
        );
        table = new JTable(model);

        JButton btnNuevo = new JButton("Nuevo");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnActualizar = new JButton("Actualizar");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnNuevo.addActionListener(e -> abrirFormulario(null));
        btnEditar.addActionListener(e -> editarFuncionario());
        btnEliminar.addActionListener(e -> eliminarFuncionario());
        btnActualizar.addActionListener(e -> cargarFuncionarios());

        cargarFuncionarios();
    }

    private void abrirFormulario(Funcionario funcionario) {
        FuncionarioForm form = new FuncionarioForm(this, funcionarioDAO, funcionario);
        form.setVisible(true);
        cargarFuncionarios();
    }

    private void editarFuncionario() {
        int fila = table.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un funcionario", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Funcionario f = new Funcionario();
        f.setId((int) model.getValueAt(fila, 0));
        f.setTipoIdentificacion((String) model.getValueAt(fila, 1));
        f.setNumeroIdentificacion((String) model.getValueAt(fila, 2));
        f.setNombres((String) model.getValueAt(fila, 3));
        f.setApellidos((String) model.getValueAt(fila, 4));
        f.setEstadoCivil((String) model.getValueAt(fila, 5));
        f.setSexo((String) model.getValueAt(fila, 6));
        f.setDireccion((String) model.getValueAt(fila, 7));
        f.setTelefono((String) model.getValueAt(fila, 8));
        abrirFormulario(f);
    }

    private void eliminarFuncionario() {
        int fila = table.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un funcionario", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) model.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Desea eliminar este funcionario?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                funcionarioDAO.eliminar(id);
                cargarFuncionarios();
                JOptionPane.showMessageDialog(this, "Funcionario eliminado correctamente");
            } catch (DataAccessException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
            }
        }
    }

    public void cargarFuncionarios() {
        model.setRowCount(0);
        try {
            List<Funcionario> lista = funcionarioDAO.listar();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (Funcionario f : lista) {
                model.addRow(new Object[]{
                        f.getId(), f.getTipoIdentificacion(), f.getNumeroIdentificacion(),
                        f.getNombres(), f.getApellidos(), f.getEstadoCivil(), f.getSexo(),
                        f.getDireccion(), f.getTelefono(),
                        f.getFechaNacimiento() != null ? f.getFechaNacimiento().format(formatter) : ""
                });
            }
        } catch (DataAccessException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar funcionarios: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
