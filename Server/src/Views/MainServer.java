package Views;

public class MainServer {
	public static void main(String[] args) {
        new Thread( new ServerGUI()).start();
    }
}
