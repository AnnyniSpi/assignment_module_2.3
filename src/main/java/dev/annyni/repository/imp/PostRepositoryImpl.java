package dev.annyni.repository.imp;

import dev.annyni.model.Post;
import dev.annyni.model.Status;
import dev.annyni.repository.PostRepository;
import dev.annyni.util.HibernateUtil;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import java.util.Optional;

/**
 * todo Document type PostRepositoryImpl
 */
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {
    @Override
    public Post save(Post entity) {
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){

            session.beginTransaction();

            session.persist(entity);

            session.getTransaction().commit();

            return entity;
        } catch (Exception e){
            throw new RuntimeException("Error save entity!");
        }
    }

    @Override
    public void delete(Long id) {
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){

            session.beginTransaction();

            Post post = session.get(Post.class, id);

            if (post ==  null){
                throw new RuntimeException("Label not found " +  id);
            }

            post.setStatus(Status.DELETED);
            //            session.merge(label);
            session.remove(post);
            session.flush();

            session.getTransaction().commit();

        } catch (Exception e){
            throw new RuntimeException("Error delete entity!");
        }
    }

    @Override
    public Post update(Post entity) {
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){

            session.beginTransaction();

            session.merge(entity);

            session.getTransaction().commit();

            return entity;
        } catch (Exception e){
            throw new RuntimeException("Error update entity!");
        }
    }

    @Override
    public Optional<Post> findById(Long id) {
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){

            Post post = session.createQuery("select p from Post p " +
                                            "left join fetch p.labels " +
                                            "where p.id = :id", Post.class)
                .setParameter("id", id)
                .uniqueResult();

            if (post == null){
                throw new RuntimeException("Label not found " +  id);
            }

            return Optional.of(post);

        } catch (Exception e){
            throw new RuntimeException("Error find by id entity! " + id);
        }
    }

    @Override
    public List<Post> findAll() {
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){

            return session.createQuery("select p from Post p", Post.class)
                .getResultList();

        } catch (Exception e){
            throw new RuntimeException("Error find all entities!");
        }
    }
}
