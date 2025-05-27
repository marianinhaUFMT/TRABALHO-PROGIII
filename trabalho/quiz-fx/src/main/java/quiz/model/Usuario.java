package quiz.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String senha;
    private boolean isAdmin;

    public Usuario() {}

    public Usuario(String nome, String senha, boolean isAdmin) {
        this.nome = nome;
        this.senha = senha;
        this.isAdmin = isAdmin;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public boolean isAdmin() { return isAdmin; }
    public void setAdmin(boolean isAdmin) { this.isAdmin = isAdmin; } 
    
    @Override
    public String toString() {
        return id + " - " + nome + " (" + senha + ", Admin: " + isAdmin + ")";
    }
}