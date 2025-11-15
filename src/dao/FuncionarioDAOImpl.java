package dao;

import model.Funcionario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAOImpl implements FuncionarioDAO {

    private static final String SQL_INSERT = "INSERT INTO funcionario (nombre, apellido, numero_documento, fecha_nacimiento, direccion, telefono, email, id_tipo_documento, id_estado_civil) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_ALL = "SELECT * FROM funcionario";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM funcionario WHERE id_funcionario = ?";
    private static final String SQL_UPDATE = "UPDATE funcionario SET nombre=?, apellido=?, numero_documento=?, fecha_nacimiento=?, direccion=?, telefono=?, email=?, id_tipo_documento=?, id_estado_civil=? WHERE id_funcionario=?";
    private static final String SQL_DELETE = "DELETE FROM funcionario WHERE id_funcionario = ?";

    @Override
    public void crear(Funcionario f) throws DataAccessException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            
            stmt.setString(1, f.getNombre());
            stmt.setString(2, f.getApellido());
            stmt.setString(3, f.getNumeroDocumento());
            
            // Manejo de la Fecha de Nacimiento (LocalDate a java.sql.Date o NULL)
            if (f.getFechaNacimiento() != null) {
                stmt.setDate(4, Date.valueOf(f.getFechaNacimiento())); 
            } else {
                stmt.setNull(4, java.sql.Types.DATE); 
            }
            
            stmt.setString(5, f.getDireccion());
            stmt.setString(6, f.getTelefono());
            stmt.setString(7, f.getEmail());
            stmt.setInt(8, f.getIdTipoDocumento());
            stmt.setInt(9, f.getIdEstadoCivil());
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new DataAccessException("Error al insertar funcionario: " + f.getNombre(), e);
        } finally {
            DBConnection.close(stmt);
            DBConnection.close(conn);
        }
    }

    @Override
    public List<Funcionario> listar() throws DataAccessException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Funcionario> funcionarios = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_ALL);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setIdFuncionario(rs.getInt("id_funcionario"));
                f.setNombre(rs.getString("nombre"));
                f.setApellido(rs.getString("apellido"));
                f.setNumeroDocumento(rs.getString("numero_documento"));
                
                Date sqlDate = rs.getDate("fecha_nacimiento");
                if (sqlDate != null) {
                    f.setFechaNacimiento(sqlDate.toLocalDate()); 
                }
                
                f.setDireccion(rs.getString("direccion"));
                f.setTelefono(rs.getString("telefono"));
                f.setEmail(rs.getString("email"));
                f.setIdTipoDocumento(rs.getInt("id_tipo_documento"));
                f.setIdEstadoCivil(rs.getInt("id_estado_civil"));
                funcionarios.add(f);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al listar funcionarios.", e);
        } finally {
            DBConnection.close(rs);
            DBConnection.close(stmt);
            DBConnection.close(conn);
        }
        return funcionarios;
    }
    
    @Override
    public Funcionario obtenerPorId(int id) throws DataAccessException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Funcionario f = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                f = new Funcionario();
                f.setIdFuncionario(rs.getInt("id_funcionario"));
                f.setNombre(rs.getString("nombre"));
                f.setApellido(rs.getString("apellido"));
                f.setNumeroDocumento(rs.getString("numero_documento"));
                
                Date sqlDate = rs.getDate("fecha_nacimiento");
                if (sqlDate != null) {
                    f.setFechaNacimiento(sqlDate.toLocalDate()); 
                }
                
                f.setDireccion(rs.getString("direccion"));
                f.setTelefono(rs.getString("telefono"));
                f.setEmail(rs.getString("email"));
                f.setIdTipoDocumento(rs.getInt("id_tipo_documento"));
                f.setIdEstadoCivil(rs.getInt("id_estado_civil"));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al buscar funcionario con ID " + id, e);
        } finally {
            DBConnection.close(rs);
            DBConnection.close(stmt);
            DBConnection.close(conn);
        }
        return f;
    }

    @Override
    public void actualizar(Funcionario f) throws DataAccessException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            
            stmt.setString(1, f.getNombre());
            stmt.setString(2, f.getApellido());
            stmt.setString(3, f.getNumeroDocumento());
            
            // Manejo de la Fecha de Nacimiento (LocalDate a java.sql.Date o NULL)
            if (f.getFechaNacimiento() != null) {
                stmt.setDate(4, Date.valueOf(f.getFechaNacimiento()));
            } else {
                stmt.setNull(4, java.sql.Types.DATE); 
            }
            
            stmt.setString(5, f.getDireccion());
            stmt.setString(6, f.getTelefono());
            stmt.setString(7, f.getEmail());
            stmt.setInt(8, f.getIdTipoDocumento());
            stmt.setInt(9, f.getIdEstadoCivil());
            stmt.setInt(10, f.getIdFuncionario()); 

            if (stmt.executeUpdate() == 0) {
                throw new DataAccessException("No se encontró el funcionario con ID: " + f.getIdFuncionario() + " para actualizar.");
            }
            
        } catch (SQLException e) {
            throw new DataAccessException("Error al actualizar funcionario con ID: " + f.getIdFuncionario(), e);
        } finally {
            DBConnection.close(stmt);
            DBConnection.close(conn);
        }
    }
    
    @Override
    public void eliminar(int id) throws DataAccessException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, id);
            
            if (stmt.executeUpdate() == 0) {
                throw new DataAccessException("No se encontró el funcionario con ID: " + id + " para eliminar.");
            }
            
        } catch (SQLException e) {
            throw new DataAccessException("Error al eliminar funcionario con ID: " + id, e);
        } finally {
            DBConnection.close(stmt);
            DBConnection.close(conn);
        }
    }
}