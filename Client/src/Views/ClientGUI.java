package Views;

import Model.*;
import UTILS.DataUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import Control.GoiManHinh;
//import zCLIENT.REMOTE.GoiManHinh;
import Control.ThaoTacManHinh;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ClientGUI extends JFrame implements Runnable {

    Socket socketFromServer;

    boolean continueThread = true;
    String ipServer;
    final int mainPortServer = 1111;
    
    public ClientGUI() {
        try {
            initComponents();
            setVisible(true);
            ipServer = txtIP.getText();
            lblClientName.setText(InetAddress.getLocalHost().getHostName()
                    + " (" + InetAddress.getLocalHost().getHostAddress() + ")");
            lblIPAddress.setText(ipServer);
            lblStatus.setText("Đang chờ kết nối đến server...");
        } catch (Exception ex) {
        }
    }

    @Override
    public void run() {
        while (continueThread) {
            try {
                String msg = DataUtils.nhanDuLieu(socketFromServer);
                if (msg != null && !msg.isEmpty()) {
                    xuLyDuLieuTrungTam(msg);
                }
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }
    }
    //<editor-fold defaultstate="collapsed" desc="Xử lý dữ liệu trung tâm">

    private void xuLyDuLieuTrungTam(String msg) throws UnknownHostException, IOException {
        PacketTin pkTin = new PacketTin();
        pkTin.phanTichMessage(msg);
        // Thực hiện các câu lệnh từ Server
        
         if (pkTin.isId(TheodoiClient_cl.ID)) {
            xuLyTheoDoiClient(pkTin);
        } 
//         else if (pkTin.isId(PacketChupAnh.ID)) {
//            if (screenCapture == null) {
//                screenCapture = new ScreenCapture(socketFromServer);
//            }
//            screenCapture.goiAnh();
//        }
        System.err.println(pkTin.toString());
    }
    //</editor-fold>



    //<editor-fold defaultstate="collapsed" desc="Theo dõi client">
    private void xuLyTheoDoiClient(PacketTin pkTin) {
        int port = Integer.parseInt(pkTin.getMessage().toString());
        // Dùng để xử lý màn hình
        Robot robot;

        try {
            // Tạo socket nhận remote với port đã gởi qua
            final Socket socketRemote =
                    new Socket(ipServer, port);
            try {
                // Lấy màn hình mặc định của hệ thống
                GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
                GraphicsDevice gDev = gEnv.getDefaultScreenDevice();

                // Chuẩn bị robot thao tác màn hình
                robot = new Robot(gDev);
                // Gởi màn hình 
                new GoiManHinh(socketRemote, robot);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } catch (IOException ex) {
        }
    }
    //</editor-fold>


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        lblStatus4 = new JLabel();
        lblClientName = new JLabel();
        lblStatus1 = new JLabel();
        lblIPAddress = new JLabel();
        lblStatus2 = new JLabel();
        lblStatus = new JLabel();
        btnThoat = new JToggleButton();
        jLabel1 = new JLabel();
        txtIP = new JTextField();
        jButtonConnect = new JButton();
        lblStatus3 = new JLabel();
        lblSubnetMask = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(new Color(255, 255, 255));
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        lblStatus4.setText("Tên máy:");

        lblClientName.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        lblClientName.setForeground(new Color(255, 0, 51));
        lblClientName.setText("MyComputer");

        lblStatus1.setText("IP Address:");

        lblIPAddress.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        lblIPAddress.setForeground(new Color(255, 0, 51));
        lblIPAddress.setText("127.0.0.1");

        lblStatus2.setText("Trạng thái:");

        lblStatus.setFont(new Font("Tahoma", 2, 12)); // NOI18N
        lblStatus.setForeground(new Color(255, 0, 51));
        lblStatus.setText("Status");

        btnThoat.setBackground(new Color(204, 255, 255));
        btnThoat.setForeground(new Color(204, 0, 0));
        btnThoat.setText("Thoát");
        btnThoat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        jLabel1.setFont(new Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setForeground(new Color(255, 0, 51));
        jLabel1.setText("IP Server:");

        txtIP.setForeground(new Color(0, 0, 255));
        txtIP.setText("192.168.");
        txtIP.setMinimumSize(new Dimension(8, 22));

        jButtonConnect.setForeground(new Color(255, 0, 0));
        jButtonConnect.setText("Kết nối");
        jButtonConnect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonConnectActionPerformed(evt);
            }
        });

        lblStatus3.setText("Subnet Mask:");

        lblSubnetMask.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        lblSubnetMask.setForeground(new Color(255, 0, 51));
        lblSubnetMask.setText("...");

        GroupLayout layout = new GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(layout.createSequentialGroup()
        							.addGap(39)
        							.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        								.addComponent(lblStatus2)
        								.addComponent(lblStatus1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        								.addComponent(lblStatus4)
        								.addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        							.addGap(25)
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addComponent(lblClientName, GroupLayout.PREFERRED_SIZE, 247, GroupLayout.PREFERRED_SIZE)
        								.addComponent(lblIPAddress, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE)))
        						.addGroup(layout.createSequentialGroup()
        							.addGap(130)
        							.addComponent(txtIP, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.UNRELATED)
        							.addComponent(jButtonConnect)))
        					.addGap(0, 0, Short.MAX_VALUE))
        				.addGroup(layout.createSequentialGroup()
        					.addGap(39)
        					.addComponent(lblStatus3)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(layout.createSequentialGroup()
        							.addGap(77)
        							.addComponent(btnThoat, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
        							.addGap(0, 115, Short.MAX_VALUE))
        						.addComponent(lblStatus, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
        						.addComponent(lblSubnetMask, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE))))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(25)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        					.addComponent(jLabel1)
        					.addComponent(txtIP, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
        				.addComponent(jButtonConnect))
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblClientName)
        				.addComponent(lblStatus4, Alignment.TRAILING))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblStatus1)
        				.addComponent(lblIPAddress))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblStatus3, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblSubnetMask))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblStatus2, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblStatus))
        			.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addComponent(btnThoat)
        			.addContainerGap())
        );
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>                        

    private void jButtonConnectActionPerformed(ActionEvent evt) {                                               
        ipServer = txtIP.getText();
        try {
            // Khởi tạo kết nối từ Client đến Server
            lblStatus.setText("Đang chờ kết nối đến server...");
            socketFromServer = new Socket(ipServer, mainPortServer);
            lblStatus.setText("Đã kết nối server thành công.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối với server!");
        }
    }                                              

    private void formWindowClosed(WindowEvent evt) {                                  
        continueThread = false;
    }                                 

    private void btnThoatActionPerformed(ActionEvent evt) {                                         
        dispose();
    }                                        
    // Variables declaration - do not modify                     
    private JToggleButton btnThoat;
    private JButton jButtonConnect;
    private JLabel jLabel1;
    private JLabel lblClientName;
    private JLabel lblIPAddress;
    private JLabel lblStatus;
    private JLabel lblStatus1;
    private JLabel lblStatus2;
    private JLabel lblStatus3;
    private JLabel lblStatus4;
    private JLabel lblSubnetMask;
    private JTextField txtIP;
    // End of variables declaration                   
}
