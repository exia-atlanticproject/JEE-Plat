package data.controler;

import org.apache.openjpa.persistence.jest.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

public class MessageConnector {
    private OutputStream socketOutputStream;
    private long initDate = new Date().getTime();

    public MessageConnector() throws IOException {
        InetAddress address = InetAddress.getLocalHost();
        int port = 3309;
        Socket socket = new Socket(address, port);
        socketOutputStream = socket.getOutputStream();
    }

    public void sendMessage(RequestDestination destination, RequestAction action, String data) throws IOException {
        socketOutputStream.write(data.toString().getBytes());
        socketOutputStream.flush();
        System.out.println("Message emitted");
    }
}
