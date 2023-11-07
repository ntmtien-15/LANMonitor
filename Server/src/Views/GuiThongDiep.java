package Views;

import Model.ThongDiep;
import UTILS.DataUtils;
import UTILS.ObjectUtils;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class GuiThongDiep extends JDialog{

    List<Socket>  _dsMayClient;
    ThongDiep _PkgThongDiep;
    public GuiThongDiep(List<Socket> _dsMayClient) {
        this._dsMayClient = _dsMayClient;
        initComponents();
        setTitle("Gởi thông điệp đến client");
        _PkgThongDiep = new ThongDiep();
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new JScrollPane();
        txtMessages = new JTextArea();
        btnSend = new JButton();
        jLabel1 = new JLabel();
        jPanel1 = new JPanel();
        jLabel2 = new JLabel();
        jRadioButton1 = new JRadioButton();
        jRadioButton2 = new JRadioButton();
        jScrollPane2 = new JScrollPane();
        txtInput = new JTextArea();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Chat với client");
        setType(Window.Type.POPUP);

        txtMessages.setEditable(false);
        txtMessages.setColumns(20);
        txtMessages.setRows(5);
        jScrollPane1.setViewportView(txtMessages);

        btnSend.setForeground(new Color(255, 51, 0));
        btnSend.setText("GỬI");
        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new Color(51, 51, 255));
        jLabel1.setText("SERVER>>>");

        jLabel2.setFont(new Font("Tahoma", 2, 13)); // NOI18N
        jLabel2.setText("Tùy chọn");

        jRadioButton1.setFont(new Font("Tahoma", 2, 11)); // NOI18N
        jRadioButton1.setText("Client hiện tại");

        jRadioButton2.setFont(new Font("Tahoma", 2, 11)); // NOI18N
        jRadioButton2.setText("Tất cả Client");

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jRadioButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton2))
                    .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE))
                .addGap(0, 92, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtInput.setColumns(20);
        txtInput.setRows(5);
        jScrollPane2.setViewportView(txtInput);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 535, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    
    private void btnSendActionPerformed(ActionEvent evt) {                                        
        _PkgThongDiep.khoiTao("",txtInput.getText());
        txtMessages.append("Server ("+
             ObjectUtils.formatDate(new Date(), "dd-mm hh:mm:ss")
                +"): "+txtInput.getText()+"\n");
        txtInput.setText("");
        for(Socket s : _dsMayClient){
            DataUtils.goiDuLieu(s,_PkgThongDiep.toString());
        }
       
    }                                       

    // Variables declaration - do not modify                     
    private JButton btnSend;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JPanel jPanel1;
    private JRadioButton jRadioButton1;
    private JRadioButton jRadioButton2;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JTextArea txtInput;
    private JTextArea txtMessages;
    // End of variables declaration                   

   

    
}
