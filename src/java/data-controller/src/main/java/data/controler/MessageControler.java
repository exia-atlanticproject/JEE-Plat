package data.controler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

public class MessageControler {
    private ServerSocket serverSocket;
    private Map<String, Thread> clients;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        Consumer<String> callback = System.out::println;
        clients = new HashMap<>();
        new ServerSocketHandler(serverSocket, clients, callback).run();

    }

    public int getClientsNumber() {
        return this.clients.size();
    }

    public Set<String> getClients() {
        return clients.keySet();
    }

    public Thread getClientThread(String id) {
        return clients.get(id);
    }

    public MessageControler() {
    }

    public void stop() throws IOException {
        serverSocket.close();
    }
}

class SockerHandler extends Thread {
    private Socket socket;
    private Consumer<String> callback;

    SockerHandler(Socket socket, Consumer<String> callback, String id) {
        super(id);
        this.socket = socket;
        this.callback = callback;
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (socket.isConnected()) {
                String text = bufferedReader.readLine();
                if ( text != null) {
                    callback.accept(text);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ServerSocketHandler extends Thread {
    private Map<String, Thread> clients;
    private ServerSocket server;
    private Consumer<String> callback;

    ServerSocketHandler(ServerSocket server, Map<String, Thread> clients, Consumer<String> callback) {
        this.server = server;
        this.clients = clients;
        this.callback = callback;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String id = UUID.randomUUID().toString();
                Thread clientHandler = new SockerHandler(server.accept(), callback, id);
                clients.put(id, clientHandler);
                clientHandler.run();
                callback.accept(clients.size()+"");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
