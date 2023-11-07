
package Views;

import java.io.ObjectInputStream;
import java.net.Socket;
import Control.ClientScreenReciever;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class TheoDoiClient extends JDialog implements Runnable {
//nhan du lieu man hinh qua socket va hien thi
    Socket socket;
    ClientScreenReciever clientScreenReciever;
    public TheoDoiClient(Socket socket) {
        this.socket = socket;
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    
    @Override
    public void run() {
        //Used to read screenshots and client screen dimension
        ObjectInputStream ois = null;
        try {
            //Read client screen dimension
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //Start recieveing screenshots
        clientScreenReciever = new ClientScreenReciever(ois, jPanelDesktopRemote);
        
    
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanelDesktopRemote = new JPanel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanelDesktopRemote.setFocusable(false);
        jPanelDesktopRemote.setPreferredSize(new Dimension(1366, 768));

        GroupLayout jPanelDesktopRemoteLayout = new GroupLayout(jPanelDesktopRemote);
        jPanelDesktopRemote.setLayout(jPanelDesktopRemoteLayout);
        jPanelDesktopRemoteLayout.setHorizontalGroup(
            jPanelDesktopRemoteLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 1087, Short.MAX_VALUE)
        );
        jPanelDesktopRemoteLayout.setVerticalGroup(
            jPanelDesktopRemoteLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 768, Short.MAX_VALUE)
        );

        getContentPane().add(jPanelDesktopRemote, BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>                        

    private void formWindowClosing(WindowEvent evt) {                                   
        clientScreenReciever.stopReceive();
    }                                  

    // Variables declaration - do not modify                     
    private JPanel jPanelDesktopRemote;
    // End of variables declaration                   
}