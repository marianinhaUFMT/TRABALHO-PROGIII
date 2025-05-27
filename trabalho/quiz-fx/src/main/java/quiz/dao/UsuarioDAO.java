package quiz.dao;

import quiz.model.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class UsuarioDAO {
    private static final SessionFactory factory;

    static {
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Usuario.class)
                .buildSessionFactory();
    }

    public void salvar(Usuario usuario) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(usuario);
            session.getTransaction().commit();
        }
    }

    public void atualizar(Usuario usuario) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.merge(usuario);
            session.getTransaction().commit();
        }
    }

    public void excluir(Usuario usuario) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(session.contains(usuario) ? usuario : session.merge(usuario));
            session.getTransaction().commit();
        }
    }

    public List<Usuario> listarTodos() {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Usuario", Usuario.class).list();
        }
    }

    public Usuario buscarPorId(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Usuario.class, id);
        }
    }

    public Usuario findByNomeAndSenha(String nome, String senha) {
        try (Session session = factory.openSession()) {
            Query<Usuario> query = session.createQuery("FROM Usuario WHERE nome = :nome AND senha = :senha", Usuario.class);
            query.setParameter("nome", nome);
            query.setParameter("senha", senha);
            return query.uniqueResult();
        }
    }

    public boolean existsByNome(String nome) {
        try (Session session = factory.openSession()) {
            Query<Usuario> query = session.createQuery("FROM Usuario WHERE nome = :nome", Usuario.class);
            query.setParameter("nome", nome);
            return query.uniqueResult() != null;
        }
    }

    public void close() {
        factory.close();
    }
}