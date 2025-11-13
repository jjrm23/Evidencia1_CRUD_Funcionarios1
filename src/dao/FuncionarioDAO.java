package dao;

import model.Funcionario;
import java.util.List;
import java.util.Optional;

public interface FuncionarioDAO {
    List<Funcionario> listar() throws DataAccessException;
    Optional<Funcionario> obtenerPorId(int id) throws DataAccessException;
    void crear(Funcionario f) throws DataAccessException;
    void actualizar(Funcionario f) throws DataAccessException;
    void eliminar(int id) throws DataAccessException;
}
