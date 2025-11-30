package Entities;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;
import javax.swing.plaf.FileChooserUI;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author yukio
 */
public class ImageInverterGUI {

    private JLabel status;
    private JFrame frame;
    private File selectedFile;

    public ImageInverterGUI() {
        // Initialize the UI components
        frame = new JFrame("Image Inverter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel guiPanel = new JPanel();
        guiPanel.setLayout(new FlowLayout());

        JButton selectBtn = new JButton("Select Image File");
        selectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFile();
            }
        });
        guiPanel.add(selectBtn);

        status = new JLabel("Status: Ready to invert");
        guiPanel.add(status);

        // Add preview panels
        JPanel previews = new JPanel();
        previews.setLayout(new FlowLayout());
        previews.add(new JLabel("Original Image"));
        previews.add(new JLabel("Inverted Image"));
        guiPanel.add(previews);

        frame.add(guiPanel);
        frame.pack();
        frame.setVisible(true);
    }

    private void selectFile() {
        File file = fileChooser().showOpenDialog(frame);
        if (file != null) {
            status.setText("Processing image");
            processImage(file);
        }
    }

    private FileChooserUI fileChooser() {
        return new FileChooserUI() {
            @Override
            public FileFilter getAcceptAllFileFilter(JFileChooser fc) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public FileView getFileView(JFileChooser fc) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public String getApproveButtonText(JFileChooser fc) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public String getDialogTitle(JFileChooser fc) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void rescanCurrentDirectory(JFileChooser fc) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void ensureFileIsVisible(JFileChooser fc, File f) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        };
    }

    private void displayImage(BufferedImage bi) {
        // Update the preview panels
        // This would involve setting the image on the UI components.
    }

    public static void main(String[] args) {
        new ImageInverterGUI();
    }
}

}
