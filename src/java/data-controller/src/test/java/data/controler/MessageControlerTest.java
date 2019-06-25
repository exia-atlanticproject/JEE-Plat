package data.controler;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MessageControlerTest {

    private MessageControler controler = new MessageControler();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void start() {
        try {
            controler.start(3300);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Start socket failed");
        }
        System.out.println("sdfkgjhdjfg");
        try {
            MessageConnector client = new MessageConnector();
        } catch (IOException e) {
            e.printStackTrace();
            fail("Start socket client failed");
        }
        if (controler.getClientsNumber() != 1) fail("The client is not connected");
        try {
            controler.stop();
        } catch (IOException e) {
            e.printStackTrace();
            fail("Stop socket failed");
        }

    }

    @Test
    void stop() {

    }
}