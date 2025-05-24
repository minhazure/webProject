package fileio;
import entities.Librarian;

import java.io.*;

public class LibrarianFileHandler {
    private static final String LIBRARIAN_FILE = "resources/Librarian.txt";

    public static Librarian[] getAllLibrarians() {
        int size = countLinesInFile();
        Librarian[] librarians = new Librarian[size];
        try (BufferedReader reader = new BufferedReader(new FileReader(LIBRARIAN_FILE))) {
            String line;
            int index = 0;
            while ((line = reader.readLine()) != null) {
                librarians[index++] = Librarian.fromFileFormat(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading Librarian file: " + e.getMessage());
        }
        return librarians;
    }

    public static Librarian findLibrarianByUsername(String username) {
        Librarian[] librarians = getAllLibrarians();
        for (Librarian librarian : librarians) {
            if (librarian != null && librarian.getUsername().equals(username)) {
                return librarian;
            }
        }
        return null;
    }

    private static int countLinesInFile() {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(LIBRARIAN_FILE))) {
            while (reader.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            System.out.println("Error counting lines in Librarians file: " + e.getMessage());
        }
        return count;
    }
}


