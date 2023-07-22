import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class ImageEncryptionGUI {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Image Encryption");
        frame.setPreferredSize(new Dimension(500, 300)); // increase height to make room for new button
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Font font = new Font("Roboto", Font.BOLD, 25);

        JButton selectButton = new JButton("Select Image");
        selectButton.setFont(font);

        JButton historyButton = new JButton("Show History"); // new button for showing history
        historyButton.setFont(font);

        JTextField passwordTextField = new JPlaceholderTextField("Enter Private Key",30);
        passwordTextField.setFont(font);
        passwordTextField.setMaximumSize(new Dimension(400*2+200, 50));

        JRadioButton aesRadioButton = new JRadioButton("AES");
        aesRadioButton.setFont(font);
        aesRadioButton.setSelected(true);  

        JRadioButton xorRadioButton = new JRadioButton("XOR");
        xorRadioButton.setFont(font);

        ButtonGroup algorithmButtonGroup = new ButtonGroup();
        algorithmButtonGroup.add(aesRadioButton);
        algorithmButtonGroup.add(xorRadioButton);

        selectButton.addActionListener(e -> {
            String password = passwordTextField.getText();
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Password field empty");
            } else if (aesRadioButton.isSelected() && (password.length() != 16 && password.length() != 24 && password.length() != 32)) {
                JOptionPane.showMessageDialog(frame, "AES encryption requires a password length of 16, 24, or 32");
            }else if (xorRadioButton.isSelected() && !isInt(password)){
                JOptionPane.showMessageDialog(frame, "XOR encryption requires 1 or more numeric values");
            } else {
                JFileChooser chooser = new JFileChooser();
                int returnValue = chooser.showOpenDialog(frame);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    if (file != null) {
                        String[] fileName = {file.getName()};
                        File txtfile = new File(NamesFileManager.FILE_PATH + NamesFileManager.FILE_NAME);
                        boolean fileExists = txtfile.exists();
                        String algorithm = aesRadioButton.isSelected() ? "@_AES_@" : "@_XOR_@";
                        fileName[0]+=algorithm;
                        if (fileExists) {
                            if (NamesFileManager.deleteName(fileName[0])) {
                                if (aesRadioButton.isSelected()) {
                                    ImageEncryption_AES.decrypt(file, password, 0);
                                } else {
                                    ImageEncryption_XOR.encrypt(file, Integer.parseInt(password), 0);
                                }
                                ImagePreview.preview(file, frame, 0);
                            } else {
                                ImagePreview.preview(file, frame, 1);
                                NamesFileManager.createNamesFile(fileName);
                                if (aesRadioButton.isSelected()) {
                                    ImageEncryption_AES.encrypt(file, password, 1);
                                } else {
                                    ImageEncryption_XOR.encrypt(file, Integer.parseInt(password), 1);
                                }
                            }
                        } else {
                            String[] init_f = {"file initialization"};
                            NamesFileManager.createNamesFile(init_f);
                            JOptionPane.showMessageDialog(frame, "ERROR FIXED! Insert file again");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "No file selected");
                    }
                }
            }
        });

        historyButton.addActionListener(e -> DisplayHistory.show(NamesFileManager.FILE_PATH+NamesFileManager.FILE_NAME));

        Box container = new Box(BoxLayout.Y_AXIS);
        container.add(aesRadioButton);
        container.add(Box.createVerticalStrut(10));
        container.add(xorRadioButton);
        container.add(Box.createVerticalStrut(10));
        container.add(passwordTextField);
        container.add(Box.createVerticalStrut(10));
        container.add(selectButton);
        container.add(Box.createVerticalStrut(10));
        container.add(historyButton);
        container.add(Box.createVerticalStrut(10));
        frame.setContentPane(container);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static boolean isInt(String s) {
        boolean isValidInt = true;
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            isValidInt = false;
        }
        return isValidInt;
    }

}
