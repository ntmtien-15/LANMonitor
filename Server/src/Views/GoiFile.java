
package Views;

import java.io.*;
import java.net.Socket;
import javax.swing.*;

public class GoiFile extends JDialog implements Runnable {

    Socket socketToClient;

    public GoiFile(Socket mayKhach) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        socketToClient = mayKhach;
    }

    @Override
    public void run() {
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        btnGoi = new JButton();
        txtFile = new JTextField();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jButton2 = new JButton();
        btnThoat = new JToggleButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        btnGoi.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnGoi.setForeground(new java.awt.Color(255, 0, 0));
        btnGoi.setText("GỬI");
        btnGoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoiActionPerformed(evt);
            }
        });

        txtFile.setEditable(false);
        txtFile.setForeground(new java.awt.Color(51, 51, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Tập tin:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Chọn tập tin để gửi");

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 0, 0));
        jButton2.setText("Tìm");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnThoat.setBackground(new java.awt.Color(204, 255, 255));
        btnThoat.setForeground(new java.awt.Color(204, 0, 0));
        btnThoat.setText("HỦY");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(btnThoat, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(btnGoi, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtFile, GroupLayout.PREFERRED_SIZE, 305, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2, GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(txtFile, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThoat, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGoi, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void btnGoiActionPerformed(java.awt.event.ActionEvent evt) {                                       

        try {
            if (txtFile.getText().isEmpty()) {
                JOptionPane.showConfirmDialog(null, "Bạn chưa chọn tập tin để gởi!");
                return;
            }
            File myFile = new File(txtFile.getText());
            byte[] mybytearray = new byte[(int) myFile.length()];
            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);

            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(mybytearray, 0, mybytearray.length);

            OutputStream os = socketToClient.getOutputStream();

            DataOutputStream dos = new DataOutputStream(os);
            String filename = chooser.getSelectedFile().getName();
            System.err.println("S[Gởi file]: Chuẩn bị gởi file: " + filename);
            dos.writeUTF(filename);
            dos.writeLong(mybytearray.length);
            System.err.println("S[Gởi file]: Bắt đầu gởi file!");
            dos.write(mybytearray, 0, mybytearray.length);
            dos.flush();
            System.err.println("S[Gởi file]: Hoàn tất gởi file!");
            socketToClient.close();
            dispose();
        } catch (Exception ex) {
        }

    }                                      
    JFileChooser chooser;
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        chooser = new JFileChooser(".");
        int status = chooser.showOpenDialog(null);
        if (status == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            txtFile.setText(f.getPath());
        }
    }                                        

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {                                         
          dispose();
    }                                        

    // Variables declaration - do not modify                     
    private JButton btnGoi;
    private JToggleButton btnThoat;
    private JButton jButton2;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JTextField txtFile;
    // End of variables declaration                   
}
