package Views;

import Model.ChupAnh;
import UTILS.DataUtils;
import UTILS.ObjectUtils;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.*;
import java.net.Socket;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;

public class ChupHinhClient extends JDialog implements Runnable {

    Socket socket;
    BufferedImage img;
    private boolean isContinued = true;
    public ChupHinhClient(Socket socket) {
        this.socket = socket;
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void run() {
        byte[] bytes;
        ObjectInputStream ois;
        while (isContinued) {
            try {
                ois = new ObjectInputStream(socket.getInputStream());
                bytes = (byte[]) ois.readObject();
                if (bytes != null) {
                    showScreenShot(bytes);
                }
            } catch (Exception ex) {
                isContinued = false;
                ex.printStackTrace();
            }
        }
    }

    public void showScreenShot(byte[] bytes) {
        try {
            img = ImageIO.read(new ByteArrayInputStream(bytes));
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    try {
                        Image image = img.getScaledInstance(jPanelDesktopRemote.getWidth(), jPanelDesktopRemote.getHeight(), Image.SCALE_SMOOTH);
                        if (image == null) {
                            return;
                        }
                        // Hiển thị ảnh lên panel
                        Graphics graphics = jPanelDesktopRemote.getGraphics();
                        graphics.drawImage(image, 0, 0, jPanelDesktopRemote.getWidth(), jPanelDesktopRemote.getHeight(), jPanelDesktopRemote);
                    } catch (Exception ex) {
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new JPanel();
        btnChupHinh = new JButton();
        btnLuu = new JButton();
        jPanelDesktopRemote = new JPanel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        btnChupHinh.setText("Chụp hình");
        btnChupHinh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnChupHinhActionPerformed(evt);
            }
        });

        btnLuu.setText("Lưu");
        btnLuu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(315, 315, 315)
                .addComponent(btnChupHinh)
                .addGap(33, 33, 33)
                .addComponent(btnLuu, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(403, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnChupHinh)
                    .addComponent(btnLuu))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, BorderLayout.PAGE_START);

        jPanelDesktopRemote.setFocusable(false);

        GroupLayout jPanelDesktopRemoteLayout = new GroupLayout(jPanelDesktopRemote);
        jPanelDesktopRemote.setLayout(jPanelDesktopRemoteLayout);
        jPanelDesktopRemoteLayout.setHorizontalGroup(
            jPanelDesktopRemoteLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 917, Short.MAX_VALUE)
        );
        jPanelDesktopRemoteLayout.setVerticalGroup(
            jPanelDesktopRemoteLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 487, Short.MAX_VALUE)
        );

        getContentPane().add(jPanelDesktopRemote, BorderLayout.CENTER);

        pack();
    }// </editor-fold>                        

    private void btnChupHinhActionPerformed(ActionEvent evt) {                                            
        ChupAnh p = new ChupAnh();
        DataUtils.goiDuLieu(socket, p.toString());
    }                                           

    private void btnLuuActionPerformed(ActionEvent evt) {                                       
        try {
            String filename = "ScreeenShot_" + socket.getInetAddress().getHostName()
                    + "_" + ObjectUtils.formatDate(new Date(), "ddMMyyyyhhmmss")
                    + ".png";

            JFileChooser chooser = new JFileChooser(filename);
            chooser.setFileFilter(new FileNameExtensionFilter("Images file", "png"));
            chooser.setSelectedFile(new File(filename));
            chooser.setMultiSelectionEnabled(false);

            if (chooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
                return;
            }
            // retrieve image
            File outputfile = chooser.getSelectedFile();
            ImageIO.write(img, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }                                      

    private void formWindowClosing(WindowEvent evt) {                                   
        isContinued = false;
    }                                  

    // Variables declaration - do not modify                     
    private JButton btnChupHinh;
    private JButton btnLuu;
    private JPanel jPanel1;
    private JPanel jPanelDesktopRemote;
    // End of variables declaration                   
}