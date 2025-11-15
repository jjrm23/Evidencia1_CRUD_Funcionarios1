package ui;

import dao.DataAccessException;
import dao.FuncionarioDAO;
import dao.FuncionarioDAOImpl;
import model.Funcionario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainFrame extends JFrame {

    private FuncionarioDAO funcionarioDAO;
    private JTable funcionarioTable;
    private DefaultTableModel tableModel;

    public MainFrame() {
        // 1. Inicializar el DAO
        this.funcionarioDAO = new FuncionarioDAOImpl();
        
        // 2. Configurar la ventana principal
        setTitle("CRUD de Funcionarios - Patrón DAO y Excepciones");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // 3. Inicializar componentes
        initComponents();
        
        // 4. Cargar datos iniciales
        cargarTabla();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // --- Configuración de la Tabla ---
        String[] columnNames = {"ID", "Nombre", "Apellido", "Documento"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Las celdas no son editables directamente
            }
        };
        funcionarioTable = new JTable(tableModel);
        add(new JScrollPane(funcionarioTable), BorderLayout.CENTER);

        // --- Configuración de Botones (Panel Sur) ---
        JPanel buttonPanel = new JPanel();
        JButton btnCrear = new JButton("Crear");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");

        buttonPanel.add(btnCrear);
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnEliminar);
        add(buttonPanel, BorderLayout.SOUTH);

        // --- Manejo de Eventos ---
        btnCrear.addActionListener(e -> abrirFormulario(null));
        btnEditar.addActionListener(e -> editarFuncionario());
        btnEliminar.addActionListener(e -> eliminarFuncionario());
    }

    public void cargarTabla() {
        // Limpiar tabla
        tableModel.setRowCount(0);
        try {
            List<Funcionario> funcionarios = funcionarioDAO.listar();
            for (Funcionario f : funcionarios) {
                Object[] row = new Object[]{
                    f.getIdFuncionario(), 
                    f.getNombre(), 
                    f.getApellido(), 
                    f.getNumeroDocumento()
                };
                tableModel.addRow(row);
            }
        } catch (DataAccessException ex) {
            JOptionPane.showMessageDialog(this, "Error al listar funcionarios: " + ex.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void abrirFormulario(Funcionario f) {
        FuncionarioForm form = new FuncionarioForm(f, this);
        form.setVisible(true);
    }
    
    private void editarFuncionario() {
        int selectedRow = funcionarioTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un funcionario para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        try {
            Funcionario f = funcionarioDAO.obtenerPorId(id);
            if (f != null) {
                abrirFormulario(f);
            } else {
                JOptionPane.showMessageDialog(this, "Funcionario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (DataAccessException ex) {
            JOptionPane.showMessageDialog(this, "Error al obtener datos: " + ex.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarFuncionario() {
        int selectedRow = funcionarioTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un funcionario para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el funcionario con ID: " + id + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                funcionarioDAO.eliminar(id);
                JOptionPane.showMessageDialog(this, "Funcionario eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarTabla(); // Recargar la tabla
            } catch (DataAccessException ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}