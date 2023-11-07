package Views;

import Model.Chat;
import Model.PacketTin;
import UTILS.DataUtils;
import java.net.Socket;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ChatVoiClient extends JDialog implements Runnable{

    boolean isContinued = true;
    Socket _mayClient;
    Chat _pkgChat;
    public ChatVoiClient(Socket mayClient) {
        this._mayClient = mayClient;
        initComponents();
        setTitle(DataUtils.layTenMay(mayClient) +" ("+
                DataUtils.layIPMay(mayClient)+")");
        setVisible(true);
        _pkgChat = new Chat();
    }
     @Override
    public void run() {
        // Đảm bảo sau khi chat xong 1 client
        // còn chat các lần tiếp theo nữa
        while(isContinued){
            // Nếu không dùng thread
            // chương trình sẽ chờ nhận dữ liệu client ở đây
            // kết quả chương trình sẽ bị treo do đợi
            String msg = DataUtils.nhanDuLieu(_mayClient);
            if(msg != null && !msg.isEmpty()){
                hienThiMessage(msg);
            }
        }
    }

    private void hienThiMessage(String msg){
        PacketTin pkgTin = new PacketTin();
        pkgTin.phanTichMessage(msg);
        if(pkgTin.isId(Chat.ID)){
            txtMessages.append(DataUtils.layTenMay(_mayClient)+": "
                    +pkgTin.getMessage()+"\n");
        }
    }
   
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new JScrollPane();
        txtMessages = new JTextArea();
        jScrollPane2 = new JScrollPane();
        txtInput = new JTextArea();
        btnSend = new JButton();
        jLabel1 = new JLabel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Chat với client");
        setType(Window.Type.POPUP);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        txtMessages.setEditable(false);
        txtMessages.setColumns(20);
        txtMessages.setRows(5);
        jScrollPane1.setViewportView(txtMessages);

        txtInput.setColumns(20);
        txtInput.setRows(5);
        jScrollPane2.setViewportView(txtInput);

        btnSend.setForeground(new Color(255, 0, 0));
        btnSend.setText("Send to Client");
        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new Color(51, 51, 255));
        jLabel1.setText("SERVER");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSend)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(4, 4, 4)
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSend, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    
    private void btnSendActionPerformed(ActionEvent evt) {                                        
        _pkgChat.khoiTao("",txtInput.getText());
        txtMessages.append("Server: "+txtInput.getText()+"\n");
        txtInput.setText("");
        DataUtils.goiDuLieu(_mayClient,_pkgChat.toString());
    }                                       

    private void formWindowClosing(WindowEvent evt) {                                   
       isContinued = false;
    }                                  

    // Variables declaration - do not modify                     
    private JButton btnSend;
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JTextArea txtInput;
    private JTextArea txtMessages;
    // End of variables declaration                   

    
}
