package ui;

import dao.TarefaDAO;
import model.Tarefa;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TarefasConcluidasApp extends JFrame {
    private JPanel painelLista;
    private JButton btnVoltar;
    private TarefaDAO dao;

    public TarefasConcluidasApp(TarefaDAO dao) {
        this.dao = dao;
        setTitle("✅ Tarefas Concluídas");
        setSize(400, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        painelLista = new JPanel();
        painelLista.setLayout(new BoxLayout(painelLista, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(painelLista);

        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> dispose());

        add(scrollPane, BorderLayout.CENTER);
        add(btnVoltar, BorderLayout.SOUTH);

        carregarTarefasConcluidas();
        setVisible(true);
    }

    private void carregarTarefasConcluidas() {
        painelLista.removeAll();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        List<Tarefa> concluidas = dao.listarConcluidas();
        for (Tarefa tarefa : concluidas) {
            JLabel label = new JLabel("✔ " + tarefa.getTitulo() + " - " + tarefa.getData().format(formato));
            label.setFont(new Font("Arial", Font.BOLD, 16));
            label.setForeground(new Color(0, 128, 0));
            label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            painelLista.add(label);
        }

        painelLista.revalidate();
        painelLista.repaint();
    }
}