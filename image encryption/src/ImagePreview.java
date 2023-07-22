import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ImagePreview {

    public static void preview(File file, JFrame parentFrame, int status) {
        if (file != null) {
            try {
                FileInputStream fis = new FileInputStream(file);
                byte[] data = new byte[fis.available()];
                fis.read(data);

                // preview original image
                JFrame previewFrame = new JFrame("Preview");
                previewFrame.setLocationRelativeTo(parentFrame);
                previewFrame.setLayout(null);
                previewFrame.setResizable(false); // set resizable to false
                previewFrame.setExtendedState(JFrame.MAXIMIZED_HORIZ); // ensure JFrame is maximized on horizontal axis
                ImageIcon previewImage = new ImageIcon(data);
                Image previewScaled = previewImage.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
                ImageIcon previewScaledIcon = new ImageIcon(previewScaled);
                JLabel previewLabel = new JLabel();
                previewLabel.setIcon(previewScaledIcon);
                previewLabel.setBounds(0, 0, previewScaledIcon.getIconWidth(), previewScaledIcon.getIconHeight());
                previewFrame.add(previewLabel);

                // add second image overlay
                String f_path;
                if (status == 1) {
                    f_path = "./data/images/en.png";
                } else {
                    f_path = "./data/images/dn.png";
                }
                ImageIcon overlayImage = new ImageIcon(f_path);
                JLabel overlayLabel = new JLabel();
                overlayLabel.setIcon(overlayImage);
                overlayLabel.setBounds(0, 15, overlayImage.getIconWidth(), overlayImage.getIconHeight());
                previewFrame.add(overlayLabel);

                // bring overlayLabel to the front
                previewFrame.setComponentZOrder(overlayLabel, 0); // set overlayLabel to be at the top of the Z-order
                previewFrame.setComponentZOrder(previewLabel, 1); // set previewLabel to be below the overlayLabel in the Z-order

                previewFrame.pack();
                previewFrame.setVisible(true);

                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "No image file selected");
        }
    }
}
