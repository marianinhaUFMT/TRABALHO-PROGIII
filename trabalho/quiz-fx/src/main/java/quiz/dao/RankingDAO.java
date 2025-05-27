package quiz.dao;

import quiz.model.Ranking;
import quiz.model.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class RankingDAO {
    private static final SessionFactory factory;

    static {
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Ranking.class)
                .buildSessionFactory();
    }

    public void salvar(Ranking ranking) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(ranking);
            session.getTransaction().commit();
        }
    }

    public void atualizar(Ranking ranking) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(ranking);
            session.getTransaction().commit();
        }
    }

    public Ranking findByUsuario(Usuario usuario) {
        try (Session session = factory.openSession()) {
            Query<Ranking> query = session.createQuery("FROM Ranking WHERE usuario = :usuario", Ranking.class);
            query.setParameter("usuario", usuario);
            return query.uniqueResult();
        }
    }

    public List<Ranking> listarTodos() {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Ranking", Ranking.class).list();
        }
    }

    public void close() {
        factory.close();
    }
}