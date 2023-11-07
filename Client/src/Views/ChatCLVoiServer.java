/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Model.ChatCL;
import UTILS.DataUtils;
import java.net.*;

public class ChatCLVoiServer extends javax.swing.JDialog {

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

        jScrollPane1 = new javax.swing.JScrollPane();
        txtMessages = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtInput = new javax.swing.JTextArea();
        btnSend = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ChatCL với Server");

        txtMessages.setEditable(false);
        txtMessages.setColumns(20);
        txtMessages.setRows(5);
        txtMessages.setToolTipText("");
        jScrollPane1.setViewportView(txtMessages);

        txtInput.setColumns(20);
        txtInput.setRows(5);
        jScrollPane2.setViewportView(txtInput);

        btnSend.setForeground(new java.awt.Color(255, 0, 51));
        btnSend.setText("GỬI");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSend)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnSend, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {                                        
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
    private javax.swing.JButton btnSend;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea txtInput;
    private javax.swing.JTextArea txtMessages;
    // End of variables declaration                   

   
}
