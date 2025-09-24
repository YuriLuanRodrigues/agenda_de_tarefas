package dao;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInit {
    public static void init(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            String sql = """
                CREATE TABLE IF NOT EXISTS tarefas (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    titulo TEXT NOT NULL,
                    descricao TEXT,
                    data TEXT,
                    concluida INTEGER,
                    prioridade INTEGER DEFAULT 2
                );
            """;
            stmt.execute(sql);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inicializar o banco de dados", e);
        }
    }
}