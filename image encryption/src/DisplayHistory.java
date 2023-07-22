import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DisplayHistory {

    public static void show(String filePath) {
        JFrame frame = new JFrame("History");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // create table model with two columns: NAME and ALGORITHM
        DefaultTableModel model = new DefaultTableModel(new Object[]{"NAME", "ALGORITHM"}, 0) {
            // override isCellEditable to prevent editing by user
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // read file and add non-empty lines to table model
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber >= 3 && !line.isEmpty()) {
                    String name = line.substring(0, line.length() - 7);
                    String algorithm = line.substring(line.length() - 7);
                    algorithm = algorithm.substring(2,5);
                    model.addRow(new Object[]{name, algorithm});
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // create table with model and add to scroll pane
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // set preferred size and add scroll pane to frame
        scrollPane.setPreferredSize(new Dimension(500, 300));
        frame.getContentPane().add(scrollPane);

        // pack and show frame
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
