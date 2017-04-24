package dal;

import dal.entities.Sale;
import junit.framework.TestCase;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Date;
import java.util.List;

public class SaleOrmTestCase extends TestCase {
    private SessionFactory sessionFactory;

    @Override
    protected void setUp() throws Exception {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
            fail(e.getMessage());
        }
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    //@SuppressWarnings("unchecked")
    public void testBasicUsage() {
        // create a couple of events...
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Sale s1 = new Sale();
        s1.setDateSold(new Date());
        s1.setQuantitySold(5);
        session.save(s1);
        Sale s2 = new Sale();
        s2.setDateSold(new Date());
        s2.setQuantitySold(10);
        session.save(s2);
        session.getTransaction().commit();
        session.close();

        // now lets pull events from the database and list them
        session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery("from Sale").list();
        for (Sale sale : (List<Sale>) result) {
            System.out.println(sale.getDateSold() + " : " + sale.getQuantitySold());
        }
        session.getTransaction().commit();
        session.close();
    }
}

