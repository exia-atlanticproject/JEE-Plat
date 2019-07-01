package data;

import data.model.DevicesEntity;
import data.model.MetricsEntity;
import data.model.UsersEntity;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QueryExecutorTest {

    private QueryExecutor queryExecutor;
    private Connection connection;

    @BeforeAll
    void beforeAll() throws SQLException {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        connection = DriverManager.getConnection("jdbc:mysql://localhost:23506/atlantis", "root", "root");
        queryExecutor = QueryExecutor.getInstance();
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
    void getUserDevices() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM Devices where id_Users=1;");
        result.next();
        int count = result.getInt(1);
        List devices = queryExecutor.getUserDevices(1);
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
        ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM Metrics WHERE id_Devices = 1 AND date >= '2019-01-01';");
        result.next();
        int count = result.getInt(1);

        Calendar date = Calendar.getInstance();
        date.set(2019, 1, 1);

        List<MetricsEntity> metrics = queryExecutor.getMetrics(1, new Date(date.getTimeInMillis()));
        Assertions.assertEquals(count, metrics.size());
        statement.close();
    }

    @Test
    void getMetricsWithStartAndEndDate() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM Metrics WHERE id_Devices = 1 AND date >= '2019-01-01' AND date <='2019-01-02';");
        result.next();
        int count = result.getInt(1);

        Calendar start = Calendar.getInstance();
        start.set(2019, Calendar.JANUARY, 1);
        Calendar end = Calendar.getInstance();
        end.set(2019, Calendar.JANUARY, 2);

        List metrics = queryExecutor.getMetrics(1, new Date(start.getTime().getTime()), new Date(end.getTime().getTime()));
        Assertions.assertEquals(count, metrics.size());
        statement.close();
    }

    @Test
    void addDevice() {
        DevicesEntity device = queryExecutor.addDevice("Test Model", UUID.randomUUID().toString());
        assertTrue(queryExecutor.getDevices().contains(device));
    }

    @Test
    void linkDeviceToUser() {
        DevicesEntity device = queryExecutor.addDevice("Test Model", UUID.randomUUID().toString());
        queryExecutor.linkDeviceToUser(1, device.getId());

        assertEquals(queryExecutor.getDevice(device.getId()).getOwner().getId(), 1);
    }
}