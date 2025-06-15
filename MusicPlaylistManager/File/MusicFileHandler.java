package File;

import Entity.Song;
import java.io.*;
import java.util.*;

public class MusicFileHandler {
    private static final String FILE_NAME = "Watchlist.txt";

    public static void saveSong(Song song) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(song.getTitle() + ";" + song.getArtist() + ";" + song.getAlbum() + ";" + song.getDuration());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Song> readSongs() {
        List<Song> songs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 4) {
                    songs.add(new Song(parts[0], parts[1], parts[2], Integer.parseInt(parts[3])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return songs;
    }

    public static void overwriteSongs(List<Song> songs) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Song s : songs) {
                writer.write(s.getTitle() + ";" + s.getArtist() + ";" + s.getAlbum() + ";" + s.getDuration());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}