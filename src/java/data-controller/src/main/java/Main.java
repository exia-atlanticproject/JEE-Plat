import data.QueryExecutor;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import java.util.logging.Level;

public class Main {


    public static void main(final String[] args) throws Exception {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);

    }
}