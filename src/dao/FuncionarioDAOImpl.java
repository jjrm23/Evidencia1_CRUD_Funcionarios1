package dao;

import model.Funcionario;
import java.sql.*;
import java.util.*;

public class FuncionarioDAOImpl implements FuncionarioDAO {

    private Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }

    private Funcionario map(ResultSet rs) throws SQLException {
        Funcionario f = new Funcionario();
        f.setId(rs.getInt("id_funcionario"));
        f.setTipoIdentificacion(rs.getString("tipo_identificacion"));
        f.setNumeroIdentificacion(rs.getString("numero_identificacion"));
        f.setNombres(rs.getString("nombres"));
        f.setApellidos(rs.getString("apellidos"));
        f.setEstadoCivil(rs.getString("estado_civil"));
        f.setSexo(rs.getString("sexo"));
        f.setDireccion(rs.getString("direccion"));
        f.setTelefono(rs.getString("telefono"));
        Date d = rs.getDate("fecha_nacimiento");
        if (d != null) f.setFechaNacimiento(d.toLocalDate());
        return f;
    }

    @Override
    public List<Funcionario> listar() throws DataAccessException {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM funcionario";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(map(rs));
        } catch (SQLException e) {
            throw new DataAccessException("Error al listar funcionarios", e);
        }
        return lista;
    }

    @Override
    public Optional<Funcionario> obtenerPorId(int id) throws DataAccessException {
        String sql = "SELECT * FROM funcionario WHERE id_funcionario=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(map(rs));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al obtener funcionario", e);
        }
        return Optional.empty();
    }

    @Override
    public void crear(Funcionario f) throws DataAccessException {
        String sql = "INSERT INTO funcionario (tipo_identificacion, numero_identificacion, nombres, apellidos, estado_civil, sexo, direccion, telefono, fecha_nacimiento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, f.getTipoIdentificacion());
            ps.setString(2, f.getNumeroIdentificacion());
            ps.setString(3, f.getNombres());
            ps.setString(4, f.getApellidos());
            ps.setString(5, f.getEstadoCivil());
            ps.setString(6, f.getSexo());
            ps.setString(7, f.getDireccion());
            ps.setString(8, f.getTelefono());
            ps.setDate(9, f.getFechaNacimiento() != null ? Date.valueOf(f.getFechaNacimiento()) : null);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error al crear funcionario", e);
        }
    }

    @Override
    public void actualizar(Funcionario f) throws DataAccessException {
        String sql = "UPDATE funcionario SET tipo_identificacion=?, numero_identificacion=?, nombres=?, apellidos=?, estado_civil=?, sexo=?, direccion=?, telefono=?, fecha_nacimiento=? WHERE id_funcionario=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, f.getTipoIdentificacion());
            ps.setString(2, f.getNumeroIdentificacion());
            ps.setString(3, f.getNombres());
            ps.setString(4, f.getApellidos());
            ps.setString(5, f.getEstadoCivil());
            ps.setString(6, f.getSexo());
            ps.setString(7, f.getDireccion());
            ps.setString(8, f.getTelefono());
            ps.setDate(9, f.getFechaNacimiento() != null ? Date.valueOf(f.getFechaNacimiento()) : null);
            ps.setInt(10, f.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error al actualizar funcionario", e);
        }
    }

    @Override
    public void eliminar(int id) throws DataAccessException {
        String sql = "DELETE FROM funcionario WHERE id_funcionario=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error al eliminar funcionario", e);
        }
    }
}
