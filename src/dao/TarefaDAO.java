package dao;

import model.Tarefa;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {
    private Connection conn;

    public TarefaDAO(Connection conn) {
        this.conn = conn;
    }

    public void inserir(Tarefa tarefa) {
        String sql = "INSERT INTO tarefas (titulo, descricao, data, concluida, prioridade) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tarefa.getTitulo());
            stmt.setString(2, tarefa.getDescricao());
            stmt.setString(3, tarefa.getData().toString());
            stmt.setBoolean(4, tarefa.isConcluida());
            stmt.setInt(5, tarefa.getPrioridade().ordinal());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Tarefa> listar() {
        List<Tarefa> tarefas = new ArrayList<>();
        String sql = "SELECT * FROM tarefas ORDER BY data ASC";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Tarefa t = new Tarefa(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("descricao"),
                        LocalDate.parse(rs.getString("data")),
                        rs.getBoolean("concluida"),
                        Tarefa.Prioridade.values()[rs.getInt("prioridade")]
                );
                tarefas.add(t);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tarefas;
    }

    public List<Tarefa> listarConcluidas() {
        List<Tarefa> tarefas = new ArrayList<>();
        String sql = "SELECT * FROM tarefas WHERE concluida = 1 ORDER BY data ASC";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Tarefa t = new Tarefa(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("descricao"),
                        LocalDate.parse(rs.getString("data")),
                        rs.getBoolean("concluida"),
                        Tarefa.Prioridade.values()[rs.getInt("prioridade")]
                );
                tarefas.add(t);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tarefas;
    }

    public void atualizar(Tarefa tarefa) {
        String sql = "UPDATE tarefas SET titulo = ?, descricao = ?, data = ?, concluida = ?, prioridade = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tarefa.getTitulo());
            stmt.setString(2, tarefa.getDescricao());
            stmt.setString(3, tarefa.getData().toString());
            stmt.setBoolean(4, tarefa.isConcluida());
            stmt.setInt(5, tarefa.getPrioridade().ordinal());
            stmt.setInt(6, tarefa.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remover(int id) {
        String sql = "DELETE FROM tarefas WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}