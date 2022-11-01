package daos;

import entities.Medicamento;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoDAOH2 implements MedicamentoDAO{
    private static final Logger logger = LogManager.getLogger(MedicamentoDAOH2.class);
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdcb:h2:~/Medicamentos; INIT=RUNSCRIPT FROM 'create.sql'");
    }

    @Override
    public Medicamento getById(Long id) throws SQLException {
        Connection c  = getConnection();
        String SELECT = "SELECT * FROM Medicamentos WHERE id = ?";
        PreparedStatement selectSt = c.prepareStatement(SELECT);
        selectSt.setLong(1, id);
        ResultSet res = selectSt.executeQuery();
        if (res.next()) return new Medicamento(res.getLong(1), res.getString(2), res.getDouble(3), res.getString(4));
        return null;
    }

    @Override
    public List<Medicamento> getAll() throws SQLException {
        Connection c  = getConnection();
        String SELECT = "SELECT * FROM Medicamentos";
        Statement st  = c.createStatement();
        var res = st.executeQuery(SELECT);
        List<Medicamento> medicamentos = new ArrayList<>();
        logger.info("Buscando todos los medicamentos de la base de datos");
        while (res.next()) {
            medicamentos.add(new Medicamento(res.getLong(1), res.getString(2), res.getDouble(3), res.getString(4)));
        }
        if (medicamentos.size() == 0) return null;
        return medicamentos;
    }

    @Override
    public void addMedicamento(Medicamento m) throws SQLException {
        Connection c  = getConnection();
        String INSERT = "INSERT INTO MEDICAMENTOS VALUES (?, ?, ?, ?)";
        PreparedStatement st  = c.prepareStatement(INSERT);
        st.setString(2, m.nombre());
        st.setDouble(3, m.precio());
        st.setString(4, m.labo());
        logger.info("Agregando nuevo medicamento");
        st.execute();
    }

    @Override
    public void removeMedicamento(Long id) throws SQLException {
        Connection c  = getConnection();
        String DELETE = "DELETE FROM Medicamentos WHERE ID = ?";
        PreparedStatement st  = c.prepareStatement(DELETE);
        st.setLong(1, id);

        st.execute();
        logger.info("Eliminando medicamento " + id);
    }

    @Override
    public void updateMedicamento(Medicamento m) throws SQLException {
        Connection c  = getConnection();
        String UPDATE = "UPDATE SET nombre = ?, precio = ?, labo = ? WHERE ID = ?";
        PreparedStatement st  = c.prepareStatement(UPDATE);
        st.setString(1, m.nombre());
        st.setDouble(2, m.precio());
        st.setString(3, m.labo());
        st.setLong(4, m.id());
        logger.info("Actualizando medicamento " + m.id());
    }
}
