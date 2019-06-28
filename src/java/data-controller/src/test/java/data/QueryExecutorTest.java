package data;

import data.model.DevicesEntity;
import data.model.MetricsEntity;
import data.model.UsersEntity;
import org.junit.jupiter.api.*;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QueryExecutorTest {

    private QueryExecutor queryExecutor;
    private Connection connection;

    @BeforeAll
    void beforeAll() throws SQLException {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        connection = DriverManager.getConnection("jdbc:mysql://localhost:23506/atlantis", "root", "root");
        queryExecutor = new QueryExecutor();
    }

    @BeforeEach
    void setUp() throws SQLException {
    }

    @AfterEach
    void tearDown() throws SQLException {
    }

    @AfterAll
    void afterAll() throws SQLException {
        connection.close();
        queryExecutor.close();
    }


    @Test
    void getDevices() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM Devices;");
        result.next();
        int count = result.getInt(1);
        List devices = queryExecutor.getDevices();
        Assertions.assertEquals(count, devices.size());
        statement.close();
    }

    @Test
    void getDevice() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM Devices WHERE id = 1;");
        result.next();
        int id = result.getInt(1);
        String model = result.getString(2);
        String mac_address = result.getString(3);

        DevicesEntity device = queryExecutor.getDevice(1);
        Assertions.assertEquals(id, device.getId());
        Assertions.assertEquals(model, device.getModel());
        Assertions.assertEquals(mac_address, device.getMacAddress());
        statement.close();
    }

    @Test
    void getUsers() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM Users;");
        result.next();
        int count = result.getInt(1);

        List<UsersEntity> users = queryExecutor.getUsers();
        Assertions.assertEquals(count, users.size());
        statement.close();
    }

    @Test
    void getUserWithId() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM Users WHERE id = 1;");
        result.next();
        int id = result.getInt(1);
        String name = result.getString(2);
        String surname = result.getString(3);
        String email = result.getString(4);

        UsersEntity users = queryExecutor.getUser(1);
        Assertions.assertEquals(id, users.getId());
        Assertions.assertEquals(name, users.getName());
        Assertions.assertEquals(surname, users.getSurname());
        Assertions.assertEquals(email, users.getEmail());
        statement.close();
    }

    @Test
    void getUserWithEmail() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM Users WHERE email = 'user.test@mail.com';");
        result.next();
        int id = result.getInt(1);
        String name = result.getString(2);
        String surname = result.getString(3);
        String email = result.getString(4);

        UsersEntity users = queryExecutor.getUser(1);
        Assertions.assertEquals(id, users.getId());
        Assertions.assertEquals(name, users.getName());
        Assertions.assertEquals(surname, users.getSurname());
        Assertions.assertEquals(email, users.getEmail());
        statement.close();
    }

    @Test
    void addMetric() throws SQLException, ParseException {
    }

    @Test
    void getMetrics() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM Metrics WHERE id_Devices = 1;");
        result.next();
        int count = result.getInt(1);

        List<MetricsEntity> metrics = queryExecutor.getMetrics(1);
        Assertions.assertEquals(count, metrics.size());
        statement.close();
    }

    @Test
    void getMetricsWithStartDate() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM Metrics WHERE id_Devices = 1;");
        result.next();
        int count = result.getInt(1);

        List<MetricsEntity> metrics = queryExecutor.getMetrics(1);
        Assertions.assertEquals(count, metrics.size());
        statement.close();
    }

    @Test
    void getMetricsWithStartAndEndDate() {
    }

    @Test
    void addDevice() {
        DevicesEntity device = queryExecutor.addDevice("Test Model", UUID.randomUUID().toString());
        Assertions.assertTrue(queryExecutor.getDevices().contains(device));
    }

    @Test
    void linkDeviceToUser() {
        DevicesEntity device = queryExecutor.addDevice("Test Model", UUID.randomUUID().toString());
        queryExecutor.linkDeviceToUser(1, device.getId());

        Assertions.assertEquals(queryExecutor.getDevice(device.getId()).getOwner().getId(), 1);
    }
}