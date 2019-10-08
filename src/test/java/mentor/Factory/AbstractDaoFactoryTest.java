/*
Test connection and work database, CRUD and other method
*/
package mentor.Factory;

import org.junit.Test;
import ru.java.mentor.model.User;
import ru.java.mentor.service.UserService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AbstractDaoFactoryTest {
    private User user0 = new User("Ivan Ivanovich", "testuser", "testPassword", "test", LocalDate.of(1967, 06, 22));
    private User user1 = new User("Vasilii Pupkin", "testuser1", "test1", "test1",  LocalDate.of(2000, 04, 01));


    @Test
    public void userCR() {
        List<User> list;
        UserService service = UserService.getInstance();
        service.dropTable();
        service.createTable();
        service.addUser(user0);
        service.addUser(user1);
        list = service.getAllUsers();
        List<User> referenceUserList = new ArrayList<>();
        user0.setId(list.get(0).getId());
        user1.setId(list.get(1).getId());
        referenceUserList.add(user0);
        referenceUserList.add(user1);
        assertEquals(true, list.equals(referenceUserList));
    }

    @Test
    public void userCRU() {
        UserService service = UserService.getInstance();
        service.dropTable();
        service.createTable();
        User userDB;
        service.addUser(user0);
        List<User> users = service.getAllUsers();
        userDB = users.get(0);
        user0.setId(userDB.getId());
        assertEquals(userDB, user0);
    }

    @Test
    public void userCRD() {
        UserService service = UserService.getInstance();
        service.dropTable();
        service.createTable();
        User userDB0;
        User userDB1;
        service.addUser(user0);
        List<User> users = service.getAllUsers();
        userDB0 = users.get(0);
        service.deleteUser(userDB0);
        userDB1 = service.getUserById(1L);
        user0.setId(userDB0.getId());
        assertTrue(userDB0.equals(user0) && userDB1 == null);
    }

    @Test
    public void userValidate() {
        UserService service = UserService.getInstance();
        service.dropTable();
        service.createTable();
        service.addUser(user0);
        List<User> users = service.getAllUsers();
        User userDB = users.get(0);
        assertTrue(user0.getLogin().equals(userDB.getLogin()) &&
                user0.getPassword().equals(userDB.getPassword()));
    }

    @Test
    public void loginExist() throws SQLException {
        UserService service = UserService.getInstance();
        service.dropTable();
        service.createTable();
        service.addUser(user0);
        assertTrue(service.isExistLogin(user0.getLogin()));
    }
}