import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JTextField;

public class JPlaceholderTextField extends JTextField {

    private String placeholder;
    private Font placeholderFont;
    private Color placeholderColor;

    public JPlaceholderTextField(String placeholder, int placeholderSize) {
        this.placeholder = placeholder;
        this.placeholderFont = getFont().deriveFont(Font.ITALIC, placeholderSize);
        this.placeholderColor = Color.GRAY;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getText().isEmpty()) {
            g.setColor(placeholderColor);
            g.setFont(placeholderFont);
            g.drawString(placeholder, getInsets().left, g.getFontMetrics().getMaxAscent() + getInsets().top);
        }
    }
}
