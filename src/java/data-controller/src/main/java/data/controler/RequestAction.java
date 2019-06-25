package data.controler;

public enum RequestAction {
    COMMAND(1),
    CALCULATION(2);

    private int index;
    RequestAction(int index) {
        this.index = index;
    }
}
