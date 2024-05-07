package dev.annyni.repository.imp;

import dev.annyni.model.Status;
import dev.annyni.model.Writer;
import dev.annyni.repository.WriterRepository;
import dev.annyni.util.HibernateUtil;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import java.util.Optional;

/**
 * todo Document type WriterRepositoryImpl
 */
@RequiredArgsConstructor
public class WriterRepositoryImpl implements WriterRepository {

    @Override
    public Writer save(Writer entity) {
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

            Writer writer = session.get(Writer.class, id);

            if (writer ==  null){
                throw new RuntimeException("Label not found " +  id);
            }

            writer.setStatus(Status.DELETED);
            //            session.merge(label);
            session.remove(writer);
            session.flush();

            session.getTransaction().commit();

        } catch (Exception e){
            throw new RuntimeException("Error delete entity!");
        }
    }

    @Override
    public Writer update(Writer entity) {
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
    public Optional<Writer> findById(Long id) {
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){

            session.beginTransaction();

            Writer writer = session.get(Writer.class, id);

            if (writer == null){
                throw new RuntimeException("Label not found " +  id);
            }

            session.getTransaction().commit();

            return Optional.of(writer);

        } catch (Exception e){
            throw new RuntimeException("Error find by id entity! " + id);
        }
    }

    @Override
    public List<Writer> findAll() {
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){

            session.beginTransaction();

            List<Writer> writers = session.createQuery("select w from Writer w", Writer.class)
                .getResultList();

            session.getTransaction().commit();

            return writers;

        } catch (Exception e){
            throw new RuntimeException("Error find all entities!");
        }
    }
}
