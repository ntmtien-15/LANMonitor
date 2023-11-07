/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Model.ChatCL;
import UTILS.DataUtils;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChatCLVoiServer extends JDialog {

    Socket _mayServer;
    public ChatCLVoiServer(Socket mayServer) {
        this._mayServer = mayServer;
        initComponents();
        setModal(false);
    }
    public void nhanDuLieu(String cmd, String msg){
        txtMessages.append("Server: "+msg+"\n");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new JScrollPane();
        txtMessages = new JTextArea();
        jScrollPane2 = new JScrollPane();
        txtInput = new JTextArea();
        btnSend = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ChatCL với Server");

        txtMessages.setEditable(false);
        txtMessages.setColumns(20);
        txtMessages.setRows(5);
        txtMessages.setToolTipText("");
        jScrollPane1.setViewportView(txtMessages);

        txtInput.setColumns(20);
        txtInput.setRows(5);
        jScrollPane2.setViewportView(txtInput);

        btnSend.setForeground(new Color(255, 0, 51));
        btnSend.setText("GỬI");
        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSend)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnSend, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    private void btnSendActionPerformed(ActionEvent evt) {                                        
        try {
            ChatCL pkgMsg = new ChatCL();
            pkgMsg.khoiTao("",txtInput.getText());
            txtMessages.append(InetAddress.getLocalHost().getHostName()
                    +": "+txtInput.getText()+"\n");
            txtInput.setText("");
            DataUtils.goiDuLieu(_mayServer,pkgMsg.toString());
        } catch (UnknownHostException ex) {
           
        }
    }                                       

    // Variables declaration - do not modify                     
    private JButton btnSend;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JTextArea txtInput;
    private JTextArea txtMessages;
    // End of variables declaration                   

   
}
