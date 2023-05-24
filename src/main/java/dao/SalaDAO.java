package dao;

import model.Sala;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaDAO extends BaseDAO {

    public static boolean insertSALA(Sala sala) {
        final String sql = "INSERT INTO sala (nr_sala, capacidade) values (?,?)";
        try (
                Connection conexao = conexao();
                PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, sala.getNr_sala());
            preparedStatement.setInt(2, sala.getCapacidade());
            int count = preparedStatement.executeUpdate();
            return count > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Sala> listarSalas() {
        final String sql = "select * from sala";

        try (
                Connection conexao = BaseDAO.conexao();
                PreparedStatement preparedStatement = conexao.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery();

        ) {
            List<Sala> salas = new ArrayList<>();
            while (rs.next()) {
                salas.add(rsToSala(rs));
            }
            return salas;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Sala rsToSala(ResultSet rs) throws SQLException {
        Sala s = new Sala();
        s.setCod_sala(rs.getInt("cod_sala"));
        s.setNr_sala((rs.getInt("nr_sala")));
        s.setCapacidade(rs.getInt("capacidade"));
        s.setStatus(rs.getBoolean("status"));

        return s;
    }

    public static Sala selectSalabyID(int id) {
        final String sql = "select * from sala where cod_sala = ?";
        try(
                Connection connection = BaseDAO.conexao();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                )
        {
            preparedStatement.setInt(1,id);
            ResultSet rs =preparedStatement.executeQuery();
            Sala sala = null;
            while (rs.next()){
                sala = rsToSala(rs);
            }
            return sala;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }

    }

    public static boolean updateSala(int id, int numero){
        final String sql = "update sala set nr_sala = ? where cod_sala = ?";
        try(
                Connection connection = conexao();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
                )
        {
            preparedStatement.setInt(1,numero);
            preparedStatement.setInt(2,id);

            int count = preparedStatement.executeUpdate();
            System.out.println("opa");

            return count > 0;

        }
        catch (SQLException e){
            System.out.println("opaaaaaa");
            e.printStackTrace();
            return false;
        }

    }
}
