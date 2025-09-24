package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Tarefa {
    public enum Prioridade { BAIXA, MEDIA, ALTA }

    private int id;
    private String titulo;
    private String descricao;
    private LocalDateTime data;
    private boolean concluida;
    private Prioridade prioridade;

    public Tarefa(int id, String titulo, String descricao, LocalDateTime data, boolean concluida, Prioridade prioridade) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.concluida = concluida;
        this.prioridade = prioridade;
    }

    public Tarefa(String titulo, String descricao, LocalDate data, boolean concluida, Prioridade prioridade) {
        this(-1, titulo, descricao, data, concluida, prioridade);
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDate getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }
    public boolean isConcluida() { return concluida; }
    public void setConcluida(boolean concluida) { this.concluida = concluida; }
    public Prioridade getPrioridade() { return prioridade; }
    public void setPrioridade(Prioridade prioridade) { this.prioridade = prioridade; }

    @Override
    public String toString() {
        return titulo;
    }
}
