import dao.TarefaDAO;
import ui.TarefaApp;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:tarefas.db");
            TarefaDAO dao = new TarefaDAO(conn);
            new TarefaApp(dao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}