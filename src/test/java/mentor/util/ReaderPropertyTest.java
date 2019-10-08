package mentor.util;

import org.junit.Ignore;
import org.junit.Test;
import ru.java.mentor.util.ExceptionFromReadMethod;
import ru.java.mentor.util.ReaderProperty;

import static org.junit.Assert.assertEquals;

public class ReaderPropertyTest {

    @Test
    public void readProperyJDBC() throws ExceptionFromReadMethod {
        assertEquals("testuser", ReaderProperty.readProperty("jdbc.connection.password"));
        assertEquals("testuser", ReaderProperty.readProperty("jdbc.connection.user"));
        assertEquals("jdbc:mysql://localhost:3306/test?serverTimezone=UTC", ReaderProperty.readProperty("jdbc.connection.url"));

    }

    @Test
    public void readMethodJDBC() throws ExceptionFromReadMethod {
        assertEquals("JDBC", ReaderProperty.readProperty("MethodToConnectDB"));
    }

    @Ignore
    @Test
    public void readMethodHibernate() throws ExceptionFromReadMethod {
        assertEquals("Hibernate", ReaderProperty.readProperty("MethodToConnectDB"));
    }

}