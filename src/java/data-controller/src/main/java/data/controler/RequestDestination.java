package data.controler;

public enum RequestDestination {
    DATABASE(1),
    BROKER(2);

    private int index;
    RequestDestination(int index) {
        this.index = index;
    }
}
