package ui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import model.Tarefa;
import dao.TarefaDAO;


public class CadastroTarefaDialog extends JDialog {
    private JTextField campoTitulo;
    private JTextArea campoDescricao;
    private JTextField campoData;
    private JComboBox<String> comboPrioridade;
    private JButton btnSalvar;
    private TarefaDAO dao;
    private TarefaApp parent;

    public CadastroTarefaDialog(TarefaApp parent, TarefaDAO dao) {
        super(parent, "📝 Nova Tarefa", true);
        this.dao = dao;
        this.parent = parent;

        setSize(420, 420);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 255));

        JPanel painelCampos = new JPanel(new GridLayout(8, 1, 8, 8));
        painelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelCampos.setBackground(new Color(245, 245, 255));

        campoTitulo = new JTextField();
        campoTitulo.setFont(new Font("Arial", Font.PLAIN, 14));

        campoDescricao = new JTextArea(3, 20);
        campoDescricao.setFont(new Font("Arial", Font.PLAIN, 14));
        campoDescricao.setLineWrap(true);
        campoDescricao.setWrapStyleWord(true);

        campoData = new JTextField("dd-MM-yyyy");
        campoData.setFont(new Font("Arial", Font.PLAIN, 14));

        comboPrioridade = new JComboBox<>(new String[]{"Alta", "Média", "Baixa"});
        comboPrioridade.setFont(new Font("Arial", Font.PLAIN, 14));

        painelCampos.add(new JLabel("Título:", JLabel.LEFT));
        painelCampos.add(campoTitulo);
        painelCampos.add(new JLabel("Descrição:", JLabel.LEFT));
        painelCampos.add(new JScrollPane(campoDescricao));
        painelCampos.add(new JLabel("Data (dd-MM-yyyy):", JLabel.LEFT));
        painelCampos.add(campoData);
        painelCampos.add(new JLabel("Prioridade:", JLabel.LEFT));
        painelCampos.add(comboPrioridade);

        btnSalvar = new JButton("Salvar Tarefa");
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalvar.setBackground(new Color(100, 149, 237));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.addActionListener(e -> salvarTarefa());

        add(painelCampos, BorderLayout.CENTER);
        add(btnSalvar, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void salvarTarefa() {
        String titulo = campoTitulo.getText().trim();
        String descricao = campoDescricao.getText().trim();
        String dataStr = campoData.getText().trim();
        String prioridadeStr = (String) comboPrioridade.getSelectedItem();

        if (titulo.isEmpty() || dataStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Título e data são obrigatórios.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate data;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            data = LocalDate.parse(dataStr, formatter);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Data inválida. Use o formato dd-MM-yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Tarefa.Prioridade prioridade = switch (prioridadeStr) {
            case "Alta" -> Tarefa.Prioridade.ALTA;
            case "Média" -> Tarefa.Prioridade.MEDIA;
            case "Baixa" -> Tarefa.Prioridade.BAIXA;
            default -> Tarefa.Prioridade.MEDIA;
        };

        Tarefa novaTarefa = new Tarefa(titulo, descricao, data, false, prioridade);
        dao.inserir(novaTarefa);
        JOptionPane.showMessageDialog(this, "Tarefa adicionada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        parent.atualizarLista(); // atualiza a tela principal
        dispose(); // fecha o diálogo
    }
}