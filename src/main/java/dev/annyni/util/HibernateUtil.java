package dev.annyni.util;

import dev.annyni.model.Label;
import dev.annyni.model.Post;
import dev.annyni.model.Writer;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

/**
 * todo Document type HibernateUtil
 */
public class HibernateUtil {

    public static SessionFactory buildSessionFactory(){
        Configuration configuration = buildConfiguration();
        configuration.configure();

        return configuration.buildSessionFactory();
    }

    public static Configuration buildConfiguration(){
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Label.class);
        configuration.addAnnotatedClass(Post.class);
        configuration.addAnnotatedClass(Writer.class);
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        return configuration;
    }
}
