/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Model.*;
import UTILS.DataUtils;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import Control.GoiManHinh;
import Control.ThaoTacManHinh;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ClientGUI extends JFrame implements Runnable {

    Socket socketFromServer;
    ChatCLVoiServer dialogChatServer;
    NhanThongDiep dialogNhanTDiep;
    boolean continueThread = true;
    String ipServer;
    final int mainPortServer = 1111;
    Socket socketNhanFile;
    ScreenCapture screenCapture;
    
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
        if (pkTin.isId(Chat.ID)) {
            if (dialogChatServer == null) {
                // Khởi tạo
                dialogChatServer = new ChatCLVoiServer(socketFromServer);
            }
            // Gởi dữ liệu đã phân tích
            dialogChatServer.nhanDuLieu(pkTin.getCmd(), pkTin.getMessage().toString());
            if (!dialogChatServer.isVisible()) {
                dialogChatServer.setVisible(true);
            }
        } else if (pkTin.isId(ThongDiepCL.ID)) {
            if (dialogNhanTDiep == null) {
                // Khởi tạo
                dialogNhanTDiep = new NhanThongDiep(socketFromServer);
            }
            // Gởi dữ liệu đã phân tích
            dialogNhanTDiep.nhanDuLieu(pkTin.getCmd(), pkTin.getMessage().toString());
            if (!dialogNhanTDiep.isVisible()) {
                dialogNhanTDiep.setVisible(true);
            }
        } else if (pkTin.isId(TruyenFileCl.ID)) {
            int port = Integer.parseInt(pkTin.getMessage().toString());
            // Tạo socket nhận file với port đã gởi qua
            socketNhanFile = new Socket(ipServer, port);
            xuLyNhanFile();
        } else if (pkTin.isId(DieuKhienCL.ID)) {
            xuLyRemoteDesktop(pkTin);
        } else if (pkTin.isId(TheodoiClient.ID)) {
            xuLyTheoDoiClient(pkTin);
        } else if (pkTin.isId(ChupImg.ID)) {
            if (screenCapture == null) {
                screenCapture = new ScreenCapture(socketFromServer);
            }
            screenCapture.goiAnh();
        }
        System.err.println(pkTin.toString());
    }
    //</editor-fold>


    //<editor-fold defaultstate="collapsed" desc="Remote desktop">
    private void xuLyRemoteDesktop(PacketTin pkTin) {
        int port = Integer.parseInt(pkTin.getMessage().toString());
        // Dùng để xử lý màn hình
        Robot robot;
        // Dùng dể tính độ phân giải và kích thước màn hình cho client
        Rectangle rectangle;
        try {
            // Tạo socket nhận remote với port đã gởi qua
            final Socket socketRemote =
                    new Socket(ipServer, port);
            try {
                // Lấy màn hình mặc định của hệ thống
                GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
                GraphicsDevice gDev = gEnv.getDefaultScreenDevice();

                // Lấy dimension màn hình
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                rectangle = new Rectangle(dim);

                // Chuẩn bị robot thao tác màn hình
                robot = new Robot(gDev);

                // Gởi màn hình
                new GoiManHinh(socketRemote, robot);
                // Gởi event chuột/phím thao tác màn hình
                new ThaoTacManHinh(socketRemote, robot);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
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


    //<editor-fold defaultstate="collapsed" desc="Xử lý nhận file">
    private void xuLyNhanFile() throws IOException {
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showSaveDialog(null) != JOptionPane.OK_OPTION) {
            return;
        }
        int bytesRead;
        InputStream in = socketNhanFile.getInputStream();
        DataInputStream clientData = new DataInputStream(in);
        System.err.println("C[Nhận File]: bắt đầu chờ nhận file....");
        String fileName = clientData.readUTF();
        System.err.println("C[Nhận File]: đã thấy tên file: " + fileName);
        String fullPath = chooser.getSelectedFile().getPath() + "\\" + fileName;
         try {
            OutputStream output = new FileOutputStream(fullPath);
     
            System.err.println("C[Nhận File]: bắt đầu nhận file: " + fileName);
            long size = clientData.readLong();
            byte[] buffer = new byte[3024];
            while (size > 0 && (bytesRead =
                    clientData.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                output.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }
            output.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        System.err.println("C[Nhận File]: đã nhận xong: " + fileName);
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
        jButton1 = new JButton();

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
        txtIP.setText("192.168.0.105");
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

        jButton1.setText("Quét IP");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblStatus2)
                                    .addComponent(lblStatus1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblStatus4)
                                    .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(lblClientName, GroupLayout.PREFERRED_SIZE, 247, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblIPAddress, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(130, 130, 130)
                                .addComponent(txtIP, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonConnect)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(lblStatus3)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnThoat, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(lblStatus, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                            .addComponent(lblSubnetMask, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtIP, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonConnect))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblClientName)
                    .addComponent(lblStatus4, GroupLayout.Alignment.TRAILING))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatus1)
                    .addComponent(lblIPAddress))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatus3, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSubnetMask))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatus2, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStatus))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(btnThoat))
                .addContainerGap())
        );

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
    private JButton jButton1;
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
