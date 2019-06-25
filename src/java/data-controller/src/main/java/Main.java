import data.controler.MessageConnector;
import data.controler.MessageControler;
import data.controler.RequestAction;
import data.controler.RequestDestination;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new MessageControler().start(3309);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class dos {
    public static void main(String[] args) {
        try {
            MessageConnector connector = new MessageConnector();
            connector.sendMessage(RequestDestination.BROKER, RequestAction.COMMAND, "test 1");
            MessageConnector connector2 = new MessageConnector();
            connector2.sendMessage(RequestDestination.BROKER, RequestAction.COMMAND, "test 2");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
