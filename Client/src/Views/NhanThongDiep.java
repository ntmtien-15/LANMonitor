/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import UTILS.ObjectUtils;
import java.net.Socket;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NhanThongDiep extends JDialog {

    Socket _mayServer;
    public NhanThongDiep(Socket mayServer) {
        _mayServer = mayServer;
        initComponents();
        setLocationRelativeTo(null);
        
    }
    public void nhanDuLieu(String cmd, String msg){
        txtMessage.append("Server ("+
             ObjectUtils.formatDate(new Date(), "dd-mm hh:mm:ss")+"): "+msg+"\n");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new JScrollPane();
        txtMessage = new JTextArea();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Thông điệp từ máy chủ");

        txtMessage.setBackground(new Color(255, 255, 204));
        txtMessage.setColumns(20);
        txtMessage.setFont(new Font("Monospaced", 0, 18)); // NOI18N
        txtMessage.setForeground(new Color(51, 0, 51));
        txtMessage.setRows(5);
        jScrollPane1.setViewportView(txtMessage);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        


    // Variables declaration - do not modify                     
    private JScrollPane jScrollPane1;
    private JTextArea txtMessage;
    // End of variables declaration                   
}
