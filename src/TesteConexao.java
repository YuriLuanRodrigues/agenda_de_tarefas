import dao.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {
    public static void main(String[] args) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            if (conn != null) {
                System.out.println("✅ Conexão com o banco estabelecida com sucesso!");
            } else {
                System.out.println("⚠️ Não foi possível conectar ao banco.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao conectar ao banco: " + e.getMessage());
        }
    }
}
