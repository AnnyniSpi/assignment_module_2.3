package dev.annyni.repository.imp;

import dev.annyni.model.Label;
import dev.annyni.model.Status;
import dev.annyni.repository.LabelRepository;
import dev.annyni.util.HibernateUtil;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import java.util.Optional;

/**
 * todo Document type LabelRepositoryImpl
 */
@RequiredArgsConstructor
public class LabelRepositoryImpl implements LabelRepository {

    @Override
    public Label save(Label entity) {
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

            Label label = session.get(Label.class, id);

            if (label ==  null){
                throw new RuntimeException("Label not found " +  id);
            }

            label.setStatus(Status.DELETED);
//            session.merge(label);
            session.remove(label);
            session.flush();

            session.getTransaction().commit();

        } catch (Exception e){
            throw new RuntimeException("Error delete entity!");
        }

    }

    @Override
    public Label update(Label entity) {
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
    public Optional<Label> findById(Long id) {
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){

            session.beginTransaction();

            Label label = session.get(Label.class, id);

            if (label == null){
                throw new RuntimeException("Label not found " +  id);
            }

            session.getTransaction().commit();

            return Optional.of(label);

        } catch (Exception e){
            throw new RuntimeException("Error find by id entity! " + id);
        }
    }

    @Override
    public List<Label> findAll() {
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){

            session.beginTransaction();

            List<Label> labels = session.createQuery("select l from Label l", Label.class)
                .getResultList();

            session.getTransaction().commit();

            return labels;

        } catch (Exception e){
            throw new RuntimeException("Error find all entities!");
        }
    }
}
