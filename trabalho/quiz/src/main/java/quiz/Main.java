package quiz;

import quiz.model.Questao;
import quiz.model.Ranking;
import quiz.model.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDateTime;

public class Main {
    private static SessionFactory factory;

    public static void main(String[] args) {
        // Configura o SessionFactory
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Usuario.class)
                .addAnnotatedClass(Questao.class)
                .addAnnotatedClass(Ranking.class)
                .buildSessionFactory();

        // Exemplo de operações CRUD
        criarUsuario();
        consultarUsuario(1L);
        atualizarUsuario(1L);
        criarQuestao();
        consultarQuestao(1L);
        criarRanking(1L, 1L);
        consultarRanking(1L);
        deletarUsuario(1L);

        // Fecha o SessionFactory
        factory.close();
    }

    // CREATE - Usuário
    private static void criarUsuario() {
        Session session = factory.openSession();
        session.beginTransaction();

        Usuario usuario = new Usuario("João Silva", "1234", false);
        session.persist(usuario);

        session.getTransaction().commit();
        session.close();
        System.out.println("Usuário criado: " + usuario);
    }

    // READ - Usuário
    private static void consultarUsuario(Long id) {
        Session session = factory.openSession();
        session.beginTransaction();

        Usuario usuario = session.get(Usuario.class, id);
        System.out.println("Usuário consultado: " + usuario);

        session.getTransaction().commit();
        session.close();
    }

    // UPDATE - Usuário
    private static void atualizarUsuario(Long id) {
        Session session = factory.openSession();
        session.beginTransaction();

        Usuario usuario = session.get(Usuario.class, id);
        if (usuario != null) {
            usuario.setNome("João Silva Atualizado");
            session.merge(usuario);
            System.out.println("Usuário atualizado: " + usuario);
        } else {
            System.out.println("Usuário com ID " + id + " não encontrado.");
        }

        session.getTransaction().commit();
        session.close();
    }

    // DELETE - Usuário
    private static void deletarUsuario(Long id) {
        Session session = factory.openSession();
        session.beginTransaction();

        Usuario usuario = session.get(Usuario.class, id);
        if (usuario != null) {
            session.remove(usuario);
            System.out.println("Usuário deletado: " + usuario);
        } else {
            System.out.println("Usuário com ID " + id + " não encontrado.");
        }

        session.getTransaction().commit();
        session.close();
    }

    // CREATE - Questão
    private static void criarQuestao() {
        Session session = factory.openSession();
        session.beginTransaction();

        Questao questao = new Questao(
                "Qual é a capital do Brasil?",
                "São Paulo",
                "Brasília",
                "Rio de Janeiro",
                "Salvador",
                "B",
                "Fácil"
        );
        session.persist(questao);

        session.getTransaction().commit();
        session.close();
        System.out.println("Questão criada: " + questao);
    }

    // READ - Questão
    private static void consultarQuestao(Long id) {
        Session session = factory.openSession();
        session.beginTransaction();

        Questao questao = session.get(Questao.class, id);
        System.out.println("Questão consultada: " + questao);

        session.getTransaction().commit();
        session.close();
    }

    // CREATE - Ranking
    private static void criarRanking(Long usuarioId, Long rankingId) {
        Session session = factory.openSession();
        session.beginTransaction();

        Usuario usuario = session.get(Usuario.class, usuarioId);
        if (usuario != null) {
            Ranking ranking = new Ranking(usuario, 50, LocalDateTime.now());
            ranking.setId(rankingId);
            session.persist(ranking);
            System.out.println("Ranking criado: " + ranking);
        } else {
            System.out.println("Usuário com ID " + usuarioId + " não encontrado para criar ranking.");
        }

        session.getTransaction().commit();
        session.close();
    }

    // READ - Ranking
    private static void consultarRanking(Long id) {
        Session session = factory.openSession();
        session.beginTransaction();

        Ranking ranking = session.get(Ranking.class, id);
        System.out.println("Ranking consultado: " + ranking);

        session.getTransaction().commit();
        session.close();
    }
}