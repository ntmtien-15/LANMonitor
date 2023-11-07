package Control;

import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ClientScreenReciever extends Thread {

    private ObjectInputStream cObjectInputStream = null;
    private JPanel cPanel = null;
    private boolean continueLoop = true;

    public ClientScreenReciever(ObjectInputStream ois, JPanel p) {
        cObjectInputStream = ois;
        cPanel = p;
        // Thread start
        start();
    }

    public void stopReceive() {
        // Báo thread dừng khi tắt remote desktop
        continueLoop = false;
    }
//lien tuc nhan img man hinh tu client tu xa và hien thi
    @Override
    public void run() {
        try {
            while (continueLoop) {
                // Nhận ảnh trả về từ client
                byte[] bytes = (byte[]) cObjectInputStream.readObject();
                System.out.println("New image recieved");
                // Hiển thị ảnh lên panel
                showScreenShot(bytes);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void showScreenShot(byte[] bytes) throws IOException {
        try {
            final BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    try {
                        Image image = img.getScaledInstance(cPanel.getWidth(), cPanel.getHeight(), Image.SCALE_FAST);
                        if (image == null) {
                            return;
                        }
                        // Hiển thị ảnh lên panel
                        Graphics graphics = cPanel.getGraphics();
                        graphics.drawImage(image, 0, 0, cPanel.getWidth(), cPanel.getHeight(), cPanel);
                    } catch (Exception ex) {
                    }
                }
            });
        } catch (Exception ex) {
        }
    }
}
