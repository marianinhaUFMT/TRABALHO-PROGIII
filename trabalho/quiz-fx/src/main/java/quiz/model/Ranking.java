package quiz.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Ranking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    private int pontuacao;
    private LocalDateTime data;

    public Ranking() {}

    public Ranking(Usuario usuario, int pontuacao, LocalDateTime data) {
        this.usuario = usuario;
        this.pontuacao = pontuacao;
        this.data = data;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public int getPontuacao() { return pontuacao; }
    public void setPontuacao(int pontuacao) { this.pontuacao = pontuacao; }
    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }

    @Override
    public String toString() {
        return id + " - " + usuario.getNome() + " (" + pontuacao + " pontos, " + data.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ")";
    }
}