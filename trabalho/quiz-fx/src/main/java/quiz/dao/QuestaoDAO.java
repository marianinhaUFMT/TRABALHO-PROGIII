package quiz.dao;

import quiz.model.Questao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class QuestaoDAO {
    private static final SessionFactory factory;

    static {
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Questao.class)
                .buildSessionFactory();
    }

    public void salvar(Questao questao) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(questao);
            session.getTransaction().commit();
        }
    }

    public void atualizar(Questao questao) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(questao);
            session.getTransaction().commit();
        }
    }

    public void excluir(Questao questao) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(session.contains(questao) ? questao : session.merge(questao));
            session.getTransaction().commit();
        }
    }

    public List<Questao> listarTodos() {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Questao", Questao.class).list();
        }
    }

    public Questao buscarPorId(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Questao.class, id);
        }
    }

    public List<Questao> findByDificuldade(String dificuldade) {
        try (Session session = factory.openSession()) {
            Query<Questao> query = session.createQuery("FROM Questao WHERE dificuldade = :dificuldade", Questao.class);
            query.setParameter("dificuldade", dificuldade);
            return query.list();
        }
    }

    public void close() {
        factory.close();
    }
}