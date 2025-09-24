package ui;

import dao.TarefaDAO;
import model.Tarefa;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TarefaApp extends JFrame {
    private DefaultListModel<Tarefa> modeloLista;
    private JList<Tarefa> lista;
    private TarefaDAO dao;

    public TarefaApp(TarefaDAO dao) {
        this.dao = dao;
        setTitle("Minhas Tarefas");
        setSize(400, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        modeloLista = new DefaultListModel<>();
        lista = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(lista);

        JButton btnAdicionar = new JButton("Adicionar");
        btnAdicionar.addActionListener(e -> new CadastroTarefaDialog(this, dao));

        JButton btnConcluir = new JButton("Concluir");
        btnConcluir.addActionListener(e -> marcarComoConcluida());

        JButton btnConcluidas = new JButton("Ver Concluídas");
        btnConcluidas.addActionListener(e -> new TarefasConcluidasApp(dao));

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnConcluir);
        painelBotoes.add(btnConcluidas);

        add(scrollPane, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        atualizarLista();
        setVisible(true);
    }

    private void marcarComoConcluida() {
        Tarefa selecionada = lista.getSelectedValue();
        if (selecionada != null && !selecionada.isConcluida()) {
            selecionada.setConcluida(true);
            dao.atualizar(selecionada);
            atualizarLista();
        }
    }

    public void atualizarLista() {
        modeloLista.clear();
        List<Tarefa> tarefas = dao.listar();

        if (tarefas.isEmpty() || tarefas.stream().noneMatch(t -> !t.isConcluida())) {
            modeloLista.addElement(new Tarefa("Sem tarefas pendentes", "", LocalDate.now(), false, Tarefa.Prioridade.MEDIA));
            lista.setCellRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                              boolean isSelected, boolean cellHasFocus) {
                    JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    label.setFont(new Font("Arial", Font.ITALIC, 16));
                    label.setForeground(Color.GRAY);
                    return label;
                }
            });
            return;
        }

        for (Tarefa t : tarefas) {
            if (!t.isConcluida()) {
                modeloLista.addElement(t);
            }
        }

        lista.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                Tarefa tarefa = (Tarefa) value;
                label.setText("📌 " + tarefa.getTitulo() + " - " + tarefa.getData().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                label.setFont(new Font("Arial", Font.PLAIN, 14));
                label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                return label;
            }
        });
    }
}