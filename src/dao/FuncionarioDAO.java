package dao;

import model.Funcionario;
import java.util.List;

public interface FuncionarioDAO {
    
    void crear(Funcionario funcionario) throws DataAccessException;
    
    List<Funcionario> listar() throws DataAccessException;
    
    Funcionario obtenerPorId(int id) throws DataAccessException;
    
    void actualizar(Funcionario funcionario) throws DataAccessException;
    
    void eliminar(int id) throws DataAccessException;
    
}