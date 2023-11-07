package Views;

public class MainCLient {
	public static void main(String[] args) {
        new Thread(new ClientGUI()).start();
    }
}
