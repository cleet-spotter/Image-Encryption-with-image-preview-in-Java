import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;

public class ImageEncryption_AES {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    public static void encrypt(File file, String password, int status) {
        if (file != null) {
            try {
                Key secretKey = new SecretKeySpec(password.getBytes(), ALGORITHM);
                Cipher cipher = Cipher.getInstance(TRANSFORMATION);
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);

                FileInputStream inputStream = new FileInputStream(file);
                byte[] inputBytes = new byte[(int) file.length()];
                inputStream.read(inputBytes);

                byte[] outputBytes = cipher.doFinal(inputBytes);

                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(outputBytes);

                inputStream.close();
                outputStream.close();

                if (status == 1) {
                    JOptionPane.showMessageDialog(null, "Image has been successfully encrypted using AES algorithm");
                } else {
                    JOptionPane.showMessageDialog(null, "Image has been successfully decrypted");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "No image file selected");
        }
    }

    public static void decrypt(File file, String password, int status) {
        if (file != null) {
            try {
                Key secretKey = new SecretKeySpec(password.getBytes(), ALGORITHM);
                Cipher cipher = Cipher.getInstance(TRANSFORMATION);
                cipher.init(Cipher.DECRYPT_MODE, secretKey);

                FileInputStream inputStream = new FileInputStream(file);
                byte[] inputBytes = new byte[(int) file.length()];
                inputStream.read(inputBytes);

                byte[] outputBytes = cipher.doFinal(inputBytes);

                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(outputBytes);

                inputStream.close();
                outputStream.close();

                if (status == 1) {
                    JOptionPane.showMessageDialog(null, "Image has been successfully encrypted");
                } else {
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
