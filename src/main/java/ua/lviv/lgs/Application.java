package ua.lviv.lgs;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.util.HashSet;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");

        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
                .buildServiceRegistry();
        Session session = configuration.buildSessionFactory(serviceRegistry).openSession();

        Post post = new Post("Earth");
        Comment comment1 = new Comment("Petro");
        Comment comment2 = new Comment("Stepan");
        Comment comment3 = new Comment("Ivan");
        comment1.setPost(post);
        comment2.setPost(post);
        comment3.setPost(post);

        Set<Comment> commentSet = new HashSet<>();
        commentSet.add(comment1);
        commentSet.add(comment2);
        commentSet.add(comment3);
        post.setComments(commentSet);

        Transaction transaction = session.beginTransaction();

        session.save(post);
        transaction.commit();

        Post post1 = (Post) session.get(Post.class,1);
        System.out.println(post1 + " ---> " + post1.getComments());
        session.close();


    }
}
