package Views;

import Model.ComputerTableModel;
import Model.DieuKhienClient;
import Model.TheodoiClient;
import Model.TruyenFile;
import UTILS.DataUtils;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import java.time.DateTimeException;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ServerGUI extends JFrame implements Runnable {
    
    private final int mainThreadPortNumber = 1111;
    private final int remoteDesktopThreadPortNumber = 998;
    private final int theoDoiClientThreadPortNumber = 997;
    private final int fileTransferThreadPortNumber = 996;
    
    Timer timerUpdateListSocket;
    private int timeUpdateTable = 5; // second
    public ServerGUI() {
    	setTitle("GIAM SAT MAY TINH");
        initComponents();
        setLocation(300, 100);
        tbComputerInfo.setModel(new Model.ComputerTableModel(new ArrayList()));
        tbComputerInfo.getColumnModel().getColumn(0).setMaxWidth(100);
        setVisible(true);
        timerUpdateListSocket = new Timer();
        timerUpdateListSocket.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getTbModel().updateAllElement();
		        runThreadRemoteDesktop(); 
            }
        }, timeUpdateTable * 1000, timeUpdateTable * 1000);
        // server láº¯ng nghe remote desktop
        runThreadRemoteDesktop(); 
        // server láº¯ng nghe gá»Ÿi/ nháº­n file
        runThreadTransferFile();
        // server láº¯ng nghe theo dÃµi client
        runThreadTheoDoiClient();
    }
    private ComputerTableModel getTbModel() {
        return (ComputerTableModel) tbComputerInfo.getModel();
    }


    @Override
    public void run() {
       // chat, gá»Ÿi thÃ´ng Ä‘iá»‡p, gá»Ÿi lá»‡nh shell
       try {
            final ServerSocket server = new ServerSocket(mainThreadPortNumber);
            // Phá»¥c vá»¥ nhiá»�u client
            while (true) {
                Socket socket;
                try {
                    socket = server.accept();
                    getTbModel().addElement(socket);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }   
    }
     
    //<editor-fold defaultstate="collapsed" desc="Thread transfer file">
    private void runThreadTransferFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final ServerSocket server = new ServerSocket(fileTransferThreadPortNumber);
                    // Phá»¥c vá»¥ nhiá»�u client
                    while (true) {
                        Socket socket;
                        try {
                            socket = server.accept();
                            new Thread(new GoiFile(socket)).start();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Thread remote desktop">

    private void runThreadRemoteDesktop() {
        new Thread(new Runnable() {
            @SuppressWarnings("resource")
			@Override
            public void run() {
                try {
                    final ServerSocket server = new ServerSocket(remoteDesktopThreadPortNumber);
                   
                    while (true) {
                        Socket socket;
                        try {
                            socket = server.accept();
                            new Thread(new Views.DieuKhienClient(socket)).start();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Thread theo dÃµi client">
    private void runThreadTheoDoiClient() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final ServerSocket server = new ServerSocket(theoDoiClientThreadPortNumber);
                    // Phá»¥c vá»¥ nhiá»�u client
                    while (true) {
                        Socket socket;
                        try {
                            socket = server.accept();
                            new Thread(new TheoDoiClient(socket)).start();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }
    //</editor-fold>
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jToolBar1 = new JToolBar();
        jSeparator1 = new JToolBar.Separator();
        jSeparator2 = new JToolBar.Separator();
        jSeparator4 = new JToolBar.Separator();
        jTabbedPane1 = new JTabbedPane();
        jPanel1 = new JPanel();
        jScrollPane1 = new JScrollPane();
        tbComputerInfo = new JTable();
        jPanel2 = new JPanel();
        lblTrangThai = new JLabel();
        lblDateTime = new JLabel();
        jLabel2 = new JLabel();
        jTabbedPane2 = new JTabbedPane();
        jPanel3 = new JPanel();
        jPanel4 = new JPanel();
        lblTrangThai1 = new JLabel();
        jLabel3 = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setBackground(new Color(204, 255, 255));
        jToolBar1.setRollover(true);
        jToolBar1.add(jSeparator1);
        jToolBar1.add(jSeparator2);
        jToolBar1.add(jSeparator4);

        jTabbedPane1.setBackground(new Color(204, 255, 204));
        jTabbedPane1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        DefaultTableModel model = new DefaultTableModel(
        	    new Object [][] {},
        	    new String [] {
        	        "STT", "Tên máy", "IP"
        	    }
        	) {
        	    boolean[] canEdit = new boolean [] {
        	        false, false, false
        	    };

        	    public boolean isCellEditable(int rowIndex, int columnIndex) {
        	        return canEdit [columnIndex];
        	    }
        	};
        	tbComputerInfo.setModel(model);

        jScrollPane1.setViewportView(tbComputerInfo);
        if (tbComputerInfo.getColumnModel().getColumnCount() > 0) {
            tbComputerInfo.getColumnModel().getColumn(0).setResizable(false);
            tbComputerInfo.getColumnModel().getColumn(0).setPreferredWidth(50);
            tbComputerInfo.getColumnModel().getColumn(1).setResizable(false);
            tbComputerInfo.getColumnModel().getColumn(1).setPreferredWidth(250);
            tbComputerInfo.getColumnModel().getColumn(2).setResizable(false);
            tbComputerInfo.getColumnModel().getColumn(2).setPreferredWidth(250);
        }

        lblTrangThai.setFont(new Font("Tahoma", 0, 12)); // NOI18N
        lblTrangThai.setForeground(new Color(0, 0, 255));
        lblTrangThai.setText("Trang thai");

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblTrangThai)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblDateTime, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
                .addGap(150, 150, 150))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTrangThai)
                    .addComponent(lblDateTime, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
                .addGap(0, 1, Short.MAX_VALUE))
        );

        jLabel2.setFont(new Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setForeground(new Color(51, 51, 255));
        jLabel2.setText("Copyright@2020. Version 0.1.0.0");

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 839, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)))
        );

        jTabbedPane1.addTab("Danh Sach may tinh dang ket noi", jPanel1);

        lblTrangThai1.setForeground(new Color(0, 0, 255));
        lblTrangThai1.setText("Trang thai");

        GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(lblTrangThai1)
                .addGap(0, 587, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(lblTrangThai1)
                .addGap(0, 1, Short.MAX_VALUE))
        );

        jLabel3.setFont(new Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setForeground(new Color(51, 51, 255));
        jLabel3.setText("Copyright@2020. Version 0.1.0.0");

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(292, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)))
        );

        jTabbedPane2.addTab("Danh sach cac may tinh hoat dong", jPanel3);

        jTabbedPane1.addTab("Seach", jTabbedPane2);
        btnChatClient = new JButton();
        
                btnChatClient.setIcon(new ImageIcon(getClass().getResource("/RES/icon chat.png"))); // NOI18N
                btnChatClient.setText("Chat Client");
                btnChatClient.setFocusable(false);
                btnChatClient.setHorizontalTextPosition(SwingConstants.CENTER);
                btnChatClient.setVerticalTextPosition(SwingConstants.BOTTOM);
                btnChatClient.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        btnChatClientActionPerformed(evt);
                    }
                });
        btnGoiThongDiep = new JButton();
        
                btnGoiThongDiep.setIcon(new ImageIcon(getClass().getResource("/RES/send-message.png"))); // NOI18N
                btnGoiThongDiep.setText("Gui Thong Diep");
                btnGoiThongDiep.setFocusable(false);
                btnGoiThongDiep.setHorizontalTextPosition(SwingConstants.CENTER);
                btnGoiThongDiep.setVerticalTextPosition(SwingConstants.BOTTOM);
                btnGoiThongDiep.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        btnGoiThongDiepActionPerformed(evt);
                    }
                });
        btnTruyenTapTin = new JButton();
        
                btnTruyenTapTin.setIcon(new ImageIcon(getClass().getResource("/RES/truyen file.png"))); // NOI18N
                btnTruyenTapTin.setText("Truyen Tap Tin");
                btnTruyenTapTin.setFocusable(false);
                btnTruyenTapTin.setHorizontalTextPosition(SwingConstants.CENTER);
                btnTruyenTapTin.setVerticalTextPosition(SwingConstants.BOTTOM);
                btnTruyenTapTin.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        btnTruyenTapTinActionPerformed(evt);
                    }
                });
        jButtonTheoDoiClient = new JButton();
        
                jButtonTheoDoiClient.setIcon(new ImageIcon(getClass().getResource("/RES/theo doi client.png"))); // NOI18N
                jButtonTheoDoiClient.setText("Theo Doi Client");
                jButtonTheoDoiClient.setFocusable(false);
                jButtonTheoDoiClient.setHorizontalTextPosition(SwingConstants.CENTER);
                jButtonTheoDoiClient.setVerticalTextPosition(SwingConstants.BOTTOM);
                jButtonTheoDoiClient.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        jButtonTheoDoiClientActionPerformed(evt);
                    }
                });
        jButtonChupHinhClient = new JButton();
        
                jButtonChupHinhClient.setIcon(new ImageIcon(getClass().getResource("/RES/photograph.png"))); // NOI18N
                jButtonChupHinhClient.setText("Chup Hinh Client");
                jButtonChupHinhClient.setFocusable(false);
                jButtonChupHinhClient.setHorizontalTextPosition(SwingConstants.CENTER);
                jButtonChupHinhClient.setVerticalTextPosition(SwingConstants.BOTTOM);
                jButtonChupHinhClient.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        jButtonChupHinhClientActionPerformed(evt);
                    }
                });
        jButtonRemoteDesktop = new JButton();
        
                jButtonRemoteDesktop.setIcon(new ImageIcon(getClass().getResource("/RES/remote-access.png"))); // NOI18N
                jButtonRemoteDesktop.setText("Dieu Khien Client");
                jButtonRemoteDesktop.setFocusable(false);
                jButtonRemoteDesktop.setHorizontalTextPosition(SwingConstants.CENTER);
                jButtonRemoteDesktop.setVerticalTextPosition(SwingConstants.BOTTOM);
                jButtonRemoteDesktop.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        jButtonRemoteDesktopActionPerformed(evt);
                    }
                });
        btnThoat = new JButton();
        
                btnThoat.setFont(new Font("Tahoma", 1, 12)); // NOI18N
                btnThoat.setForeground(new Color(255, 0, 0));
                btnThoat.setIcon(new ImageIcon(getClass().getResource("/RES/exit2.png"))); // NOI18N
                btnThoat.setText("Exit");
                btnThoat.setFocusable(false);
                btnThoat.setHorizontalTextPosition(SwingConstants.CENTER);
                btnThoat.setVerticalTextPosition(SwingConstants.BOTTOM);
                btnThoat.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        btnThoatActionPerformed(evt);
                    }
                });

        GroupLayout layout = new GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(btnTruyenTapTin, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
        				.addComponent(jButtonTheoDoiClient, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
        				.addComponent(jButtonChupHinhClient)
        				.addComponent(jButtonRemoteDesktop, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(btnGoiThongDiep, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
        				.addComponent(btnChatClient, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jTabbedPane1, GroupLayout.PREFERRED_SIZE, 766, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(btnThoat))
        		.addComponent(jToolBar1, GroupLayout.DEFAULT_SIZE, 974, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addComponent(jToolBar1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        				.addGroup(layout.createSequentialGroup()
        					.addGap(10)
        					.addComponent(btnChatClient)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(btnGoiThongDiep)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(btnTruyenTapTin)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(jButtonTheoDoiClient)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(jButtonChupHinhClient)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(jButtonRemoteDesktop))
        						.addComponent(btnThoat)))
        				.addGroup(layout.createSequentialGroup()
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(jTabbedPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap(58, Short.MAX_VALUE))
        );
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>                        

    private void btnChatClientActionPerformed(ActionEvent evt) {                                              
        if (tbComputerInfo.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Báº¡n chÆ°a chá»�n mÃ¡y Ä‘á»ƒ chat!");
            return;
        }
        Socket mayClient = getTbModel().getItem(tbComputerInfo.getSelectedRow());
        // Má»Ÿ form chat vá»›i client Ä‘Ã£ chá»�n
        new Thread(new ChatVoiClient(mayClient)).start();
    }                                             
    
    private void btnGoiThongDiepActionPerformed(ActionEvent evt) {                                                
        if (tbComputerInfo.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Báº¡n chÆ°a chá»�n mÃ¡y Ä‘á»ƒ gá»Ÿi!");
            return;
        }
        int[] rowsSelected = tbComputerInfo.getSelectedRows();
        List<Socket> dsMayClient = new ArrayList();
        for (int i : rowsSelected) {
            dsMayClient.add(getTbModel().getItem(i));
        }
        // Má»Ÿ form chat vá»›i cÃ¡c client Ä‘Ã£ chá»�n
        GuiThongDiep goiThongDiep = new GuiThongDiep(dsMayClient);
        goiThongDiep.setVisible(true);
    }                                               
   
    private void btnTruyenTapTinActionPerformed(ActionEvent evt) {                                                
        if (tbComputerInfo.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane,
                    "Báº¡n chÆ°a chá»�n mÃ¡y Ä‘á»ƒ gá»Ÿi file!");
            return;
        }
        Socket mayClient =
                getTbModel().getItem(tbComputerInfo.getSelectedRow());
        TruyenFile truyenfile = new TruyenFile();
        truyenfile.setCmd(TruyenFile.CMD_KHOIDONG);
        truyenfile.setMessage(String.valueOf(fileTransferThreadPortNumber));
        DataUtils.goiDuLieu(mayClient, truyenfile.toString()); 
    }                                               
    
    private void jButtonRemoteDesktopActionPerformed(ActionEvent evt) {                                                     
        if (tbComputerInfo.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Báº¡n chÆ°a chá»�n mÃ¡y Ä‘á»ƒ Ä‘iá»�u khiá»ƒn!");
            return;
        }
        Socket mayClient = getTbModel().getItem(tbComputerInfo.getSelectedRow());
        // Gá»Ÿi lá»‡nh yÃªu cáº§u client káº¿t ná»‘i Ä‘áº¿n socket server remote desktop
        DieuKhienClient pk = new DieuKhienClient();
        pk.setCmd(DieuKhienClient.CMD_KHOIDONG);
        pk.setMessage(String.valueOf(remoteDesktopThreadPortNumber));
        DataUtils.goiDuLieu(mayClient, pk.toString());
    }                                                    
    
    private void jButtonTheoDoiClientActionPerformed(ActionEvent evt) {                                                     
        if (tbComputerInfo.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Báº¡n chÆ°a chá»�n mÃ¡y Ä‘á»ƒ Ä‘iá»�u khiá»ƒn!");
            return;
        }
        Socket mayClient = getTbModel().getItem(tbComputerInfo.getSelectedRow());
        // Gá»Ÿi lá»‡nh yÃªu cáº§u client káº¿t ná»‘i Ä‘áº¿n socket server remote desktop
        TheodoiClient pk = new TheodoiClient();
        pk.setCmd(TheodoiClient.CMD_KHOIDONG);
        pk.setMessage(String.valueOf(theoDoiClientThreadPortNumber));
        DataUtils.goiDuLieu(mayClient, pk.toString());
        
    }                                                    

    private void jButtonChupHinhClientActionPerformed(ActionEvent evt) {                                                      
         if (tbComputerInfo.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Báº¡n chÆ°a chá»�n mÃ¡y Ä‘á»ƒ chá»¥p hÃ¬nh!");
            return;
        }
        Socket mayClient = getTbModel().getItem(tbComputerInfo.getSelectedRow());
        // Má»Ÿ form chá»¥p hÃ¬nh vá»›i client Ä‘Ã£ chá»�n
        new Thread(new ChupHinhClient(mayClient)).start();
    }                                                     

    private void btnThoatActionPerformed(ActionEvent evt) {                                         
        System.exit(0);
        //dispose();        // thoat táº¡m thá»�i
    }                                        
    private JButton btnChatClient;
    private JButton btnGoiThongDiep;
    private JButton btnThoat;
    private JButton btnTruyenTapTin;
    private JButton jButtonChupHinhClient;
    private JButton jButtonRemoteDesktop;
    private JButton jButtonTheoDoiClient;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JScrollPane jScrollPane1;
    private JToolBar.Separator jSeparator1;
    private JToolBar.Separator jSeparator2;
    private JToolBar.Separator jSeparator4;
    private JTabbedPane jTabbedPane1;
    private JTabbedPane jTabbedPane2;
    private JToolBar jToolBar1;
    private JLabel lblDateTime;
    private JLabel lblTrangThai;
    private JLabel lblTrangThai1;
    private JTable tbComputerInfo;
    // End of variables declaration                   
}
