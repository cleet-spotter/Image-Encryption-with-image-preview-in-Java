import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;

public class ImageEncryption_XOR {

    public static void encrypt(File file, int key,int status) {
        if (file != null) {
            try {
                FileInputStream fis = new FileInputStream(file);
                byte[] data = new byte[fis.available()];
                fis.read(data);

                // encrypt image
                for (int i = 0; i < data.length; i++) {
                    data[i] = (byte) (data[i] ^ key);
                }

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(data);
                fos.close();
                fis.close();
                if (status == 1){
                    JOptionPane.showMessageDialog(null, "Image has been successfully encrypted using XOR algorithm");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Image has been successfully decrypted");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "No image file selected");
        }
    }
}
