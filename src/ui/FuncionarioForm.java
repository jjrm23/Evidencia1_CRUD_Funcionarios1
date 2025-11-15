package ui;

import dao.DataAccessException;
import dao.FuncionarioDAO;
import dao.FuncionarioDAOImpl;
import model.Funcionario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class FuncionarioForm extends JFrame {

    private FuncionarioDAO funcionarioDAO;
    private Funcionario funcionarioActual; // null si es nuevo, objeto si es para editar

    // Componentes de la UI (simplificados)
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtDocumento;
    private JButton btnGuardar;

    public FuncionarioForm(Funcionario funcionario, MainFrame parentFrame) {
        // Inicialización del DAO
        this.funcionarioDAO = new FuncionarioDAOImpl();
        this.funcionarioActual = funcionario;

        setTitle(funcionario == null ? "Crear Nuevo Funcionario" : "Editar Funcionario");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        if (funcionario != null) {
            cargarDatosFuncionario(funcionario);
        }
    }

    private void initComponents() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        txtNombre = new JTextField(20);
        txtApellido = new JTextField(20);
        txtDocumento = new JTextField(20);
        btnGuardar = new JButton("Guardar");

        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Apellido:"));
        add(txtApellido);
        add(new JLabel("Documento:"));
        add(txtDocumento);
        add(btnGuardar);

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarFuncionario();
            }
        });
    }

    private void cargarDatosFuncionario(Funcionario f) {
        txtNombre.setText(f.getNombre());
        txtApellido.setText(f.getApellido());
        txtDocumento.setText(f.getNumeroDocumento());
        // Se cargarían el resto de campos: fecha, estado civil, tipo documento, etc.
    }

    private void guardarFuncionario() {
        try {
            // 1. Recoger datos de los campos
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String documento = txtDocumento.getText();
            // ... Recoger el resto de campos (idTipoDocumento, idEstadoCivil, etc.)

            // 2. Crear o actualizar el objeto Funcionario
            if (funcionarioActual == null) {
                // Asumiendo IDs fijos para este ejemplo simple
                funcionarioActual = new Funcionario(nombre, apellido, documento, LocalDate.now(), 
                                                    "Direccion ejemplo", "123456", "test@mail.com", 
                                                    1, 1); 
                funcionarioDAO.crear(funcionarioActual);
                JOptionPane.showMessageDialog(this, "Funcionario creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                funcionarioActual.setNombre(nombre);
                funcionarioActual.setApellido(apellido);
                funcionarioActual.setNumeroDocumento(documento);
                // ... Actualizar el resto de campos
                funcionarioDAO.actualizar(funcionarioActual);
                JOptionPane.showMessageDialog(this, "Funcionario actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }

            // Opcional: Refrescar la tabla en MainFrame si se implementa
            // parentFrame.cargarTabla(); 
            this.dispose();

        } catch (DataAccessException ex) {
            // Manejo de Excepciones: Aquí se captura el error de la capa DAO
            JOptionPane.showMessageDialog(this, "Error al guardar el funcionario: " + ex.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // Imprimir el stack trace para depuración
        }
    }
}