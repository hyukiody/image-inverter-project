package backend.entities.gui;

import worker.ImageProcessingWorker;
import javax.swing.*;

import backend.nodes.ImageNode;

import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class MainFrame extends JFrame {

    private JLabel imageLabel;
    private ImageNode rootNode; // Nó atual

    public MainFrame() {
        setTitle("Image Processor Node System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Área da Imagem ---
        imageLabel = new JLabel("Nenhuma imagem carregada", SwingConstants.CENTER);
        JScrollPane scrollPane = new JScrollPane(imageLabel);
        scrollPane.getViewport().setBackground(Color.DARK_GRAY); // Fundo escuro para contraste
        add(scrollPane, BorderLayout.CENTER);

        // --- Painel de Botões ---
        JPanel buttonPanel = new JPanel();
        JButton loadBtn = new JButton("Carregar Imagem");
        JButton invertBtn = new JButton("Inverter Cores");
        
        buttonPanel.add(loadBtn);
        buttonPanel.add(invertBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // --- Ações ---
        
        // 1. Carregar
        loadBtn.addActionListener(e -> carregarImagem());

        // 2. Inverter (Usando o Worker Genérico)
        invertBtn.addActionListener(e -> {
            if (rootNode == null || !rootNode.hasImage()) {
                JOptionPane.showMessageDialog(this, "Carregue uma imagem primeiro.");
                return;
            }

            invertBtn.setEnabled(false); // Evita duplo clique
            
            // Inicia o Worker
            new ImageProcessingWorker(rootNode.getBufferedImage(), (resultado) -> {
                // Código executado quando termina
                rootNode.setBufferedImage(resultado);
                displayImage(resultado);
                invertBtn.setEnabled(true);
                JOptionPane.showMessageDialog(this, "Processamento Concluído!");
            }).execute();
        });
    }

    private void carregarImagem() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                // Cria o Nó e carrega a imagem
                rootNode = new ImageNode("ID_" + System.currentTimeMillis(), file.getName());
                BufferedImage img = ImageIO.read(file);
                
                if (img != null) {
                    rootNode.setBufferedImage(img);
                    displayImage(img);
                } else {
                    JOptionPane.showMessageDialog(this, "O arquivo selecionado não é uma imagem válida.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void displayImage(BufferedImage img) {
        if (img != null) {
            // Escalar imagem para caber na tela se for muito grande
            ImageIcon icon = new ImageIcon(img);
            imageLabel.setText("");
            imageLabel.setIcon(icon);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}