import java.io.*;

public class NamesFileManager {

    public static final String FILE_NAME = "names.txt";
    public static final String FILE_PATH = ".\\data\\";

    // Constructor to check if the file exists and perform appropriate action
    public NamesFileManager() {
        File file = new File(FILE_PATH + FILE_NAME);
        if (!file.exists()) {
            System.out.println("File not found. Creating new file...");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File found.");
        }
    }

    // Method to create a new text file and write names to it
    public static void createNamesFile(String[] names) {
        try {
            // Create a new File object
            File file = new File(FILE_PATH + FILE_NAME);

            // Check if the file exists
            boolean fileExists = file.exists();

            // Create a FileWriter object in append mode if the file exists, or create a new file if it doesn't exist
            FileWriter writer = new FileWriter(file, fileExists);

            // If the file does not exist, write names to the text file
            if (!fileExists) {
                for (String name : names) {
                    writer.write(name + "\n");
                }
            } else {
                // If the file exists, add new names to the end of the file
                writer.write("\n"); // Add a blank line before the new names
                for (String name : names) {
                    writer.write(name + "\n");
                }
            }

            // Close the FileWriter object
            writer.close();

            System.out.println("Text file created/modified successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Method to search for a name in the text file and delete it
    public static boolean deleteName(String nameToDelete) {
        try {
            // Create a BufferedReader object to read the contents of the text file
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH + FILE_NAME));

            // Create a temporary file to store the modified contents of the original file
            File tempFile = new File(FILE_PATH + "temp.txt");
            FileWriter writer = new FileWriter(tempFile);

            // Loop through each line in the text file
            String currentLine;
            boolean nameFound = false;
            while ((currentLine = reader.readLine()) != null) {
                // If the line contains the name that you want to delete, skip it and continue to the next line
                if (currentLine.equals(nameToDelete)) {
                    nameFound = true;
                    continue;
                }
                // Otherwise, write the line to the temporary file
                writer.write(currentLine + "\n");
            }

            // Close the BufferedReader and FileWriter objects
            writer.close();
            reader.close();

            if (nameFound) {
                // Delete the original file
                File originalFile = new File(FILE_PATH + FILE_NAME);
                if (originalFile.delete()) {
                    System.out.println("Name '" + nameToDelete + "' deleted successfully.");
                } else {
                    System.out.println("Failed to delete name '" + nameToDelete + "'.");
                }

                // Rename the temporary file to the original file name
                if (tempFile.renameTo(originalFile)) {
                    System.out.println("File renamed successfully.");
                } else {
                    System.out.println("Failed to rename file.");
                }
            } else {
                System.out.println("Name '" + nameToDelete + "' not found in file.");
                return false;
            }
    } catch (IOException e) {
            e.printStackTrace();
        }
    return true;}
}
