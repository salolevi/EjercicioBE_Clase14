package daos;

import entities.Medicamento;

import java.sql.SQLException;
import java.util.List;

public interface MedicamentoDAO {

    public Medicamento getById(Long id) throws SQLException;

    public List<Medicamento> getAll() throws SQLException;

    public void addMedicamento(Medicamento m) throws SQLException;

    public void removeMedicamento(Long id) throws SQLException;

    public void updateMedicamento (Medicamento m) throws SQLException;
}
