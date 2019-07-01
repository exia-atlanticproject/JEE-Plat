package data;

import data.model.Entity.DevicesEntity;
import data.model.Entity.MetricsEntity;
import data.model.Entity.UsersEntity;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    void getUserWithUid() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM Users WHERE uid = 'User';");
        result.next();
        int id = result.getInt(1);
        String name = result.getString(2);
        String surname = result.getString(3);
        String email = result.getString(4);

        UsersEntity users = queryExecutor.getUser("User");
        Assertions.assertEquals(id, users.getId());
        Assertions.assertEquals(name, users.getName());
        Assertions.assertEquals(surname, users.getSurname());
        Assertions.assertEquals(email, users.getEmail());
        statement.close();
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

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<MetricsEntity> metrics = queryExecutor.getMetrics(1, formatter.format(date.getTime()));
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

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List metrics = queryExecutor.getMetrics(1, formatter.format(start.getTime()), formatter.format(end.getTime()));
        Assertions.assertEquals(count, metrics.size());
        statement.close();
    }

    @Test
    void addDevice() {
        DevicesEntity device = queryExecutor.addDevice(UUID.randomUUID().toString(), "Test Model", "Name", UUID.randomUUID().toString());
        assertTrue(queryExecutor.getDevices().contains(device));
    }

    @Test
    void addMetric() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        MetricsEntity metric = queryExecutor.addMetric(Double.valueOf("42"), formatter.format(Calendar.getInstance().getTime()), UUID.randomUUID().toString()+5, "Model", "Name", UUID.randomUUID().toString());
//        assertTrue(queryExecutor.getMetrics().contains(metric));
    }

    @Test
    void linkDeviceToUser() {
        DevicesEntity device = queryExecutor.addDevice(UUID.randomUUID().toString(), "Test Model", "Name", UUID.randomUUID().toString());
        queryExecutor.linkDeviceToUser(1, device.getId());

        assertEquals(queryExecutor.getDevice(device.getId()).getOwner().getId(), 1);
    }
}