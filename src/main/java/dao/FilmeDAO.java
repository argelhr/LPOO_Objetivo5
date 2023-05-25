package dao;

import model.Filme;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmeDAO extends BaseDAO {


    public static boolean insertFilme(Filme filme) {
        final String sql = "INSERT INTO filme (titulo,duracao) values (?,?)";
        try (
                Connection connection = conexao();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, filme.getTitulo());
            preparedStatement.setInt(2, filme.getDuracao());

            int count = preparedStatement.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Filme rsToFilme(ResultSet rs) throws SQLException {
        Filme f = new Filme();
        f.setCodfilme(rs.getLong("cod_filme"));
        f.setTitulo(rs.getString("titulo"));
        f.setDuracao((rs.getInt("duracao")));

//        System.out.println(f);

        return f;
    }

    public static List<Filme> buscarFilmes() {
        final String sql = "select * from filme where situacao is true";
        try (
                Connection connection = BaseDAO.conexao();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery()
        ) {
            List<Filme> filmes = new ArrayList<>();
            while (rs.next()) {
                filmes.add(rsToFilme(rs));
            }
            return filmes;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Filme> buscarFilmesDesativados(){
        final String sql = "select * from filme where situacao = 0";
        try (
                Connection connection = BaseDAO.conexao();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery()
        ) {
            List<Filme> filmes = new ArrayList<>();
            while (rs.next()) {
                filmes.add(rsToFilme(rs));
            }
            return filmes;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Filme selectFilmeByID(long id) {
        final String sql = "SELECT * FROM filme WHERE cod_filme = ? and situacao is true";
        try (Connection connection = conexao();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            Filme filme = null;
            if(rs.next())
                filme = rsToFilme(rs);
            return filme;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean alteraFilme(Filme filme) {
        final String sql = "UPDATE filme set titulo = ?, duracao = ? where cod_filme = ? and situacao is true";
        try (
                Connection connection = conexao();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, filme.getTitulo());
            preparedStatement.setInt(2, filme.getDuracao());
            preparedStatement.setLong(3, filme.getCodfilme());

            int count = preparedStatement.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    public static boolean softdelete(long id){
        final String sql = "UPDATE filme set situacao = false where cod_filme = ? and situacao is true";
        try(
                Connection connection = BaseDAO.conexao();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                )
        {
            preparedStatement.setLong(1,id);
            int count = preparedStatement.executeUpdate();
            return count > 0;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateFilmeSituacao(long id){
        final String sql = "UPDATE filme set situacao = true where cod_filme = ?";
        try(
                Connection connection = BaseDAO.conexao();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        )
        {
            preparedStatement.setLong(1,id);
            int count = preparedStatement.executeUpdate();
            return count > 0;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(FilmeDAO.conexao());
    }

}
