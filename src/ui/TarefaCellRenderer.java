package ui;

import model.Tarefa;
import dao.TarefaDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TarefaCellRenderer extends JCheckBox implements ListCellRenderer<Tarefa> {
    private final TarefaDAO dao;

    public TarefaCellRenderer(TarefaDAO dao) {
        this.dao = dao;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Tarefa> list, Tarefa value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        setText(value.getTitulo() + " (" + value.getData() + ") - Prioridade: " + value.getPrioridade());
        setSelected(value.isConcluida());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                value.setConcluida(!value.isConcluida());
                dao.atualizar(value);
                list.repaint();
            }
        });

        setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
        setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());

        return this;
    }
}